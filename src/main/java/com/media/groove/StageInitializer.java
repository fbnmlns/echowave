package com.media.groove;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    @Value("classpath:/ui/welcome-page.fxml")
    private Resource screenSource;
    private Stage stage;
    private final ApplicationContext applicationContext;

    public StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.screenSource.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            this.stage = event.getStage();
            this.stage.setScene(new Scene(parent, 800, 600));
            this.stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchScene(Resource screenSource) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(screenSource.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            Parent parent = fxmlLoader.load();
            this.stage.getScene().setRoot(parent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Scene getScene() {
        return this.stage.getScene();
    }

    public void setStageTitle(String title) {
        this.stage.setTitle(title);
    }
}
