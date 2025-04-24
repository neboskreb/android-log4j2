package com.github.neboskreb.log4j2.examples.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.github.neboskreb.log4j2.examples.app.databinding.ActivityMainBinding;
import com.github.neboskreb.log4j2.examples.lib.AwesomeWorker;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding databinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        databinding.setController(new GreatManager(new AwesomeWorker()));

        log.info("Activity created");
    }
}
