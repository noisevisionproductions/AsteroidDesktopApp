package com.asteroids;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class AsteroidsApplication extends Application {

    public static void main(String[] args) {
        Application.launch(AsteroidsApplication.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Asteroids Game");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/asteroids/fxml/mainmenu.fxml")));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
