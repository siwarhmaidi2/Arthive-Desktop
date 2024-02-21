package edu.esprit.controllers;

import edu.esprit.tests.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOError;
import java.io.IOException;

import edu.esprit.services.ServiceUser;
import edu.esprit.entities.User;
public class LoginController {

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button submitbtn;

    private ServiceUser su = new ServiceUser();
    private User loggedInUser;

    public void submit(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException {
        loggedInUser = su.authenticateUser(email.getText(), password.getText());
        Main m = new Main();
        if (loggedInUser != null) {
            System.out.println("Login successful");
            //redirect to home page, replace with actual Home page
             m.changeScene("Home.fxml");

        } else {
            System.out.println("Login failed");
            //show error message
        }
    }
}
