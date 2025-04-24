package net.loune.log4j2android;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.Getter;

import static java.util.Objects.requireNonNullElseGet;

/**
 * Class PrivateSecurityManagerStackTraceUtil uses a SecurityManager to populate the stack trace array
 * (which is, apparently, much faster than the standard approach). However on Android security managers are not used
 *, and the implementation of SecurityManager class contains just stubs; hence the returned array is always null.
 * This results in an NPE when Log4J tries to use it to fill the stack trace.
 * <p>
 * Fortunately, Log4J library will first check `isEnabled()` before using the SecurityManager.
 * <p>
 * By tweaking the internals of PrivateSecurityManagerStackTraceUtil we force it to always return `isEnabled() == false`.
 */
public class WorkaroundSecurityManagerNPE {
    @Getter
    private static boolean isApplied;
    @Getter
    private static Exception error;

    public static boolean applyIfNeeded() {
        if (!isApplied) {
            isApplied = true;
            try {
                new WorkaroundSecurityManagerNPE().apply();
            } catch (PatchException e) {
                error = e;
            }
        }
        return (error == null);
    }


    private static final class PatchException extends Exception {
        public PatchException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private Method methodIsEnabled;
    private Field fieldSecurityManager;

    private WorkaroundSecurityManagerNPE() {}

    private void apply() throws PatchException {
        loadTargetClass();
        // Expecting that the static initializer in that class has executed, let's check:
        boolean isEnabled = checkIsEnabled();
        if (!isEnabled) {
            // Not sure how it didn't get enabled but we are happy this way
            return;
        }
        // Yep, it's set.  Let's reset it back to null
        clearSecurityManager();

        isEnabled = checkIsEnabled();
        if (isEnabled) {
            throw new PatchException("Failed to apply the patch. The PrivateSecurityManagerStackTraceUtil is still enabled", null);
        }
    }

    private void loadTargetClass() throws PatchException {
        ClassLoader classLoader = requireNonNullElseGet(this.getClass().getClassLoader(), ClassLoader::getSystemClassLoader);
        try {
            Class<?> klass = Class.forName("org.apache.logging.log4j.util.PrivateSecurityManagerStackTraceUtil", true, classLoader);
            methodIsEnabled = klass.getDeclaredMethod("isEnabled");
            methodIsEnabled.setAccessible(true);
            fieldSecurityManager = klass.getDeclaredField("SECURITY_MANAGER");
            fieldSecurityManager.setAccessible(true);

        } catch (ClassNotFoundException | LinkageError | NoSuchMethodException | NoSuchFieldException e) {
            throw new PatchException("The structure of class PrivateSecurityManagerStackTraceUtil differs from the expected", e);
        }
    }

    private boolean checkIsEnabled() throws PatchException {
        try {
            Boolean result = (Boolean) methodIsEnabled.invoke(null);
            //noinspection DataFlowIssue,UnnecessaryUnboxing - covered by the catch clause below
            return result.booleanValue();

        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
            throw new PatchException("Failed to call isEnabled() on PrivateSecurityManagerStackTraceUtil", e);
        }
    }

    private void clearSecurityManager() throws PatchException {
        try {
            fieldSecurityManager.set(null, null);
        } catch (IllegalAccessException e) {
            throw new PatchException("Failed to set static field SECURITY_MANAGER in PrivateSecurityManagerStackTraceUtil", e);
        }
    }
}
