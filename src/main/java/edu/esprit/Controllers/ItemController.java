package edu.esprit.Controllers;

import edu.esprit.entities.User;

import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;

    private User user;


    public void setUserData(User user) {
        name.setText(user.getNom_user() + " " + user.getPrenom_user());
       country.setText(user.getVille());
        birthDate.setText(user.getD_naissance_user().toString());
        phone.setText(user.getNum_tel_user());
        email.setText(user.getEmail());
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public ItemController getItemController(){
        return this;
    }

    public void editUser(ActionEvent event){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/EditProfileAdmin.fxml"));
            Parent editProfileRoot = loader.load();
            ProfileEditAdminController profileEditAdminController = loader.getController();
            //profileEditAdminController.setUser(user);

            profileEditAdminController.getItemControllerInEdit(this.getItemController());

            Stage newStage = new Stage();
            Scene newScene = new Scene(editProfileRoot, 600, 400);
            newStage.setScene(newScene);
            newStage.setTitle("Edit Profile");
            newStage.show();
            profileEditAdminController.initFields();
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    public void hideButtons() {
        editBtn.setVisible(false);
        deleteBtn.setVisible(false);
    }

    public void deleteUser(ActionEvent event){
        ServiceUser su = new ServiceUser();
        su.delete(user.getId_user());
        System.out.println("user deleted");
    }




}
