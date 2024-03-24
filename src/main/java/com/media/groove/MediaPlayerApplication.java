package com.media.groove;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MediaPlayerApplication extends Application {
    private ConfigurableApplicationContext applicationContext;


    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(GrooveApplication.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }
}
