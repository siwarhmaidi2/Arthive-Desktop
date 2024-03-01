package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServiceUser;
import edu.esprit.tests.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

public class ProfileController {

    @FXML
    ImageView image;
    @FXML
    Text name;
    @FXML
    Text name2;
    @FXML
    Text region;
    @FXML
    Text email;
    @FXML
    Text bio;
    @FXML
    Button editBtn;
    @FXML
    Button editPfpBtn;
    @FXML
    ImageView image2;
    @FXML
    Hyperlink logoutBtn;

    private File selectedFile;





    public void initialize() throws Exception{
        User loggedInUser = UserData.getInstance().getLoggedInUser();
//        File file = new File(loggedInUser.getPhoto());
//        Image imgUser = new Image(file.toURI().toString());
        String pfpPath = loggedInUser.getPhoto();
        URI pfpUri = new URI(pfpPath);
        String filePath = Paths.get(pfpUri).toString();
        File file = new File(filePath);
        Image imgUser = new Image(file.toURI().toString());

        name.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        name2.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        region.setText(loggedInUser.getVille());
        email.setText(loggedInUser.getEmail());
        bio.setText(loggedInUser.getBio());
        image.setImage(imgUser);
        image2.setImage(imgUser);
    }

    public void switchToHomePage(Action event){}



    public void openEditProfileWindow(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProfile.fxml"));
            Parent editProfileRoot = loader.load();
            ProfileEditController profileEditController = loader.getController();
            profileEditController.setProfileController(this);
            Stage newStage = new Stage();
            Scene newScene = new Scene(editProfileRoot, 600, 400);
            newStage.setScene(newScene);
            newStage.setTitle("Edit Profile");
            newStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void changePfp(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        // Filtrer les fichiers pour afficher uniquement les images
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(filter);

        // Afficher la boîte de dialogue de sélection de fichier
        selectedFile = fileChooser.showOpenDialog(new Stage());
        // Charger l'image sélectionnée dans l'interface utilisateur
        if (selectedFile != null) {
            // Vous pouvez implémenter le chargement de l'image dans un ImageView
            try {
                Image selectedImage = new Image(selectedFile.toURI().toString());
                System.out.println("Chemin de l'image sélectionnée : " + selectedFile.toURI().toString()); // Imprimer le chemin de l'image

                image.setImage(selectedImage);
                image2.setImage(selectedImage);
                User loggedInUser = UserData.getInstance().getLoggedInUser();
                loggedInUser.setPhoto(selectedFile.toURI().toString());
                ServiceUser su = new ServiceUser();
                su.updatePhoto(loggedInUser);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading image: " + e.getMessage());
            }

        }
    }

    public void logout(ActionEvent event) throws IOException {
        UserData.getInstance().setLoggedInUser(null);
        Main.changeScene("/Login.fxml");
    }

}
