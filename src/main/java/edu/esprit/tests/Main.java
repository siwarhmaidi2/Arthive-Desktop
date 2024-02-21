package edu.esprit.tests;


import edu.esprit.services.ServiceUser;
import edu.esprit.services.ServiceMessage;

import java.io.IOException;

import java.time.LocalDate;
import java.util.Date;

import edu.esprit.entities.User;
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
        stage = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Arthive Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
    }

    public static void main(String[] args) {

        ServiceUser su = new ServiceUser() ;
        ServiceMessage sm = new ServiceMessage() ;


        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        User u1 = new User("zhiri", "zied", "ziedzhiri@gmail.com", "1234", java.sql.Date.valueOf(birthDate), "tunis", 87654321, "ROLE_USER");
        User u2 = new User("toujeni", "ayoub", "toujeniayoub@gmail.com", "4321", java.sql.Date.valueOf(birthDate), "tunis", 12345678, "ROLE_USER");


        su.add(u1);
        su.add(u2);
        System.out.println(su.getAll());

        long currentMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentMillis);
        java.sql.Date sqlDate = new java.sql.Date(currentMillis);

        Message m1 = new Message(23, 24, "blaset l 3ada?", sqlDate);


        sm.send(m1);
        System.out.println(sm.getAll());
        launch(args);

        
    }
}



