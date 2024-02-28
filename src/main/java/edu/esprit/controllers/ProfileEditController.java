package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class ProfileEditController {


    @FXML
    private TextField name;
    @FXML
    private TextField fname;
    @FXML
    private TextField region;
    @FXML
    private TextField numTel;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextArea bio;
    @FXML
    private Button submit;

    private User loggedInUser;
    private ServiceUser su = new ServiceUser();

    public void initialize(){
        User loggedInUser = UserData.getInstance().getLoggedInUser();
    }


    public void updateProfile(){
        if(checkForm()){
            loggedInUser.setNom_user(name.getText());
            loggedInUser.setPrenom_user(fname.getText());
            loggedInUser.setVille(region.getText());
            loggedInUser.setNum_tel_user(numTel.getText());
            loggedInUser.setD_naissance_user(java.sql.Date.valueOf(birthDate.getValue()));
            loggedInUser.setBio(bio.getText());
            su.updateProfile(loggedInUser);
        }
    }

    private boolean checkForm(){
        return !name.getText().isEmpty() && !fname.getText().isEmpty() && !region.getText().isEmpty() && !numTel.getText().isEmpty() && numTel.getText().matches("\\d*") && birthDate.getValue() != null;
    }
}
