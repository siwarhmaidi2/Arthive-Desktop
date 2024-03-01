package edu.esprit.tests;


import edu.esprit.services.ServiceUser;

import java.io.IOException;

import java.time.LocalDate;
import java.util.Date;

import edu.esprit.entities.Message;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.stage = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        primaryStage.setTitle("Arthive Login");
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.show();
    }

    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Main.class.getResource(fxml));
        stage.getScene().setRoot(pane);
    }

    public static void main(String[] args) {

        ServiceUser su = new ServiceUser() ;


        LocalDate birthDate = LocalDate.of(1990, 5, 15);

        System.out.println(su.getAll());

        long currentMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentMillis);
        java.sql.Date sqlDate = new java.sql.Date(currentMillis);

        Message m1 = new Message(23, 24, "blaset l 3ada?", sqlDate);

        su.reportPublication(69,1);



        launch(args);

        
    }
}



