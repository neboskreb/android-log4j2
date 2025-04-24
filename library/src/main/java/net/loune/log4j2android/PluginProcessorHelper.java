package net.loune.log4j2android;

import androidx.annotation.NonNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.PluginRegistry;
import org.apache.logging.log4j.core.config.plugins.util.PluginType;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.apache.logging.log4j.core.util.Loader;

import static java.util.Objects.requireNonNull;

public class PluginProcessorHelper {
    public static void injectPlugins(String packageName, Class<?>... classes) {
        Map<String, Map<String, List<PluginType<?>>>> mutableMap = getStringMapConcurrentMap(PluginRegistry.getInstance());
        Map<String, List<PluginType<?>>> plugins = loadFromClasses(classes);
        mutableMap.put(packageName, plugins);

        PluginManager.addPackage(packageName);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    private static Map<String, Map<String, List<PluginType<?>>>> getStringMapConcurrentMap(PluginRegistry reg) {
        try {
            Field field = reg.getClass().getDeclaredField("pluginsByCategoryByPackage");
            field.setAccessible(true);
            Object map = field.get(reg);
            requireNonNull(map);

            return (Map<String, Map<String, List<PluginType<?>>>>) map;

        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException | NullPointerException e) {
            throw new IllegalStateException("Unable to gain access to the plugin map", e);
        }
    }

    private static Map<String, List<PluginType<?>>> loadFromClasses(Class<?>[] classes) {
        final ResolverUtil resolver = new ResolverUtil();
        final ClassLoader classLoader = Loader.getClassLoader();
        if (classLoader != null) {
            resolver.setClassLoader(classLoader);
        }

        final Map<String, List<PluginType<?>>> result = new HashMap<>();
        for (final Class<?> clazz : classes) {
            List<PluginType<?>> list = getPluginAndAliases(clazz);

            // The first element is the plugin itself, followed by aliases
            PluginType<?> plugin = list.get(0);
            final String categoryKey = plugin.getCategory().toLowerCase();
            List<PluginType<?>> category = result.get(categoryKey);
            if (category == null) {
                result.put(categoryKey, list);
            } else {
                category.addAll(list);
            }
        }

        return result;
    }

    private static List<PluginType<?>> getPluginAndAliases(Class<?> clazz) {
        Plugin plugin = requireNonNull(clazz.getAnnotation(Plugin.class), "Plugin class must be annotated with @Plugin");

        List<PluginType<?>> result = new ArrayList<>();

        // Add the main name
        String name = plugin.name();
        result.add(toPluginType(name, clazz, plugin));

        // Add all aliases
        PluginAliases pluginAliases = clazz.getAnnotation(PluginAliases.class);
        if (pluginAliases != null) {
            for (String alias : pluginAliases.value()) {
                result.add(toPluginType(alias, clazz, plugin));
            }
        }

        return result;
    }

    @NonNull
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static PluginType<?> toPluginType(String name, Class<?> clazz, Plugin plugin) {
        String key = name.trim().toLowerCase();
        final PluginEntry entry = toPluginEntry(key, plugin, clazz);

        String elementName = plugin.elementType();
        if (isEmpty(elementName)) {
            elementName = name;
        }

        return (PluginType<?>) new PluginType(entry, clazz, elementName);
    }

    private static boolean isEmpty(String elementType) {
        return Plugin.EMPTY.equals(elementType);
    }

    @NonNull
    private static PluginEntry toPluginEntry(String key, Plugin plugin, Class<?> clazz) {
        final PluginEntry result = new PluginEntry();
        result.setKey(key);
        result.setName(plugin.name());
        result.setCategory(plugin.category());
        result.setClassName(clazz.getName());
        result.setPrintable(plugin.printObject());
        result.setDefer(plugin.deferChildren());
        return result;
    }
}
