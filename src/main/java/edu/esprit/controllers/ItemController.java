package edu.esprit.controllers;

import edu.esprit.entities.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
public class ItemController {

    @FXML
    private Label name;
    @FXML
    private Label country;
    @FXML
    private Label birthDate;
    @FXML
    private Label phone;
    @FXML
    private Label email;


    public void setUserData(User user) {
        name.setText(user.getNom_user() + " " + user.getPrenom_user());
        country.setText(user.getVille());
        birthDate.setText(user.getD_naissance_user().toString());
        phone.setText(user.getNum_tel_user());
        email.setText(user.getEmail());
    }
}
