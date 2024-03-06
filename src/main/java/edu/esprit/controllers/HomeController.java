package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.entities.Publication;

import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {

    @FXML
    private Button reportBtn;

    User loggedInUser;
    private Publication currentPublication;

    public void setCurrentPublication(Publication publication) {
        this.currentPublication = publication;
    }

    public void initialize(int id) {
        loggedInUser = UserData.getInstance().getLoggedInUser();
    }

    public void reportPublication(ActionEvent event) {
        if(this.currentPublication != null){
            ServiceUser su = new ServiceUser();
            su.reportPublication(loggedInUser.getId_user(), this.currentPublication.getId_publication());
        }

    }





}
