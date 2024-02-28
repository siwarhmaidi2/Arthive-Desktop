package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    @FXML
    ImageView image;
    @FXML
    Text name;
    @FXML
    Text region;
    @FXML
    Text email;
    @FXML
    Text bio;
    @FXML
    Button editBtn;




    public void initialize(){
        User loggedInUser = UserData.getInstance().getLoggedInUser();

        name.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        region.setText(loggedInUser.getVille());
        email.setText(loggedInUser.getEmail());
        bio.setText(loggedInUser.getBio());
    }


    public void openEditProfileWindow(ActionEvent event){
        try{
            Parent editProfileRoot = FXMLLoader.load(getClass().getResource("/EditProfile.fxml"));
            Stage newStage = new Stage();
            Scene newScene = new Scene(editProfileRoot, 600, 400);
            newStage.setScene(newScene);
            newStage.setTitle("Edit Profile");
            newStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
