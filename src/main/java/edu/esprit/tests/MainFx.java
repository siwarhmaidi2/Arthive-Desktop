package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class MainFx extends Application {
    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        MainFx.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene scene = new Scene(root );
        stage.setTitle("ArtHive");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(MainFx.class.getResource(fxml));
        stage.getScene().setRoot(pane);
    }


}
