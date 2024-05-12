package edu.esprit.Controllers;


import edu.esprit.entities.PanierState;
import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServicePublication;

import edu.esprit.services.ServiceUser;
import edu.esprit.tests.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;


public class ProfileController {

    @FXML
    private Hyperlink afficherEvent;
    @FXML
    private Hyperlink linkMarket;
    @FXML
    private Label incrementer;

    @FXML
    private Button btnPanier;
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
    Text birthDate;
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

    private List<Publication> posts;
    @FXML
    private GridPane postGrid;


    private ServicePublication servicePublication = new ServicePublication();

    private PanierState panierState = PanierState.getInstance();
    public void setPanierState(PanierState panierState) {
        this.panierState = panierState;
    }

    private MarketPlace marketPlaceController;


    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }




    public void initialize() throws Exception{
        User loggedInUser = UserData.getInstance().getLoggedInUser();


        if (loggedInUser != null) {
            // Step 3: User is authenticated, proceed to retrieve photo
            String userPhoto = loggedInUser.getPhoto();

            // Step 5: Load and display the user's photo
            String userPhotoUrl = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+userPhoto;
            Image userImage = new Image(userPhotoUrl);
            image.setImage(userImage);
            image2.setImage(userImage);


        name.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        name2.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        region.setText(loggedInUser.getVille());
        email.setText(loggedInUser.getEmail());
        birthDate.setText(loggedInUser.getD_naissance_user().toString());
        bio.setText(loggedInUser.getBio());


        posts = new ArrayList<>(data(loggedInUser));
        refreshPosts();
    }
    }
    private void refreshPostsUI() {
        postGrid.getChildren().clear(); // Clear existing posts in the GridPane

        int columns = 0;
        int rows = 1;

        for (int i = 0; i < posts.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/myPublications.fxml"));
                VBox postBox = fxmlLoader.load();

                MyPublicationsController controller = fxmlLoader.getController();
                controller.setData(posts.get(i));
                controller.setProfileController(this);

                if (columns == 3) {
                    columns = 0;
                    rows++;
                }
                postGrid.add(postBox, columns++, rows);
                GridPane.setMargin(postBox, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Publication> data(User user) {
        return new ArrayList<>(servicePublication.getAllPublicationsByIdUser(user.getId_user()));
    }
    public void refreshPosts() {
        // Fetch and display new posts
        User loggedInUser = UserData.getInstance().getLoggedInUser();
        posts = new ArrayList<>(data(loggedInUser));
        refreshPostsUI();
    }

    public void SwitchToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) postGrid.getScene().getWindow();
        stage.setScene(new Scene(root));
    }



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
                // add just the name of the selected file
                String name = selectedFile.getName();
                image.setImage(selectedImage);
                image2.setImage(selectedImage);
                User loggedInUser = UserData.getInstance().getLoggedInUser();
                loggedInUser.setPhoto(name);
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


    @FXML
    void marketPlace(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketPlace.fxml"));
            Parent root = loader.load();
            MarketPlace marketPlaceController = loader.getController();

            // Définir le contrôleur MarketPlace dans la fenêtre Home
            setMarketPlaceController(marketPlaceController);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("MarketPlace");
            stage.show();

            // Fermer la fenêtre actuelle (Home)
            Stage homeStage = (Stage) linkMarket.getScene().getWindow();
            homeStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void afficherPanier(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPanier.fxml"));
            Parent root = loader.load();
            AjouterPanier ajouterPanierController = loader.getController();

            ajouterPanierController.setPanierState(panierState);
            // Passer la référence du contrôleur MarketPlace à AjouterPanier
            ajouterPanierController.setMarketPlaceController(marketPlaceController);

            if (marketPlaceController != null) {
                marketPlaceController.decrementCounter();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void switchToEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherEvent.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Evènements");
            stage.show();

            // Close the current window
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
