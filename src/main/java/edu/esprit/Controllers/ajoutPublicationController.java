package edu.esprit.Controllers;

import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.scene.control.Alert.AlertType;

public class ajoutPublicationController {

    @FXML
    private ImageView imageview;
    @FXML
    private TextField txtContenu;
    @FXML
    private Button upload;
    private File selectedFile; // added field to store selected file

    private final ServicePublication servicePublication = new ServicePublication();
    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    void uploadArt(MouseEvent event) {
        // Créer un sélecteur de fichiers pour les images
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        // Filtrer les fichiers pour afficher uniquement les images
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images", ".jpg", ".png", "*.gif");
        fileChooser.getExtensionFilters().add(filter);

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Charger l'image sélectionnée dans l'interface utilisateur
        if (selectedFile != null) {
            // Vous pouvez implémenter le chargement de l'image dans un ImageView
            Image image = new Image(selectedFile.toURI().toString());
            System.out.println("Chemin de l'image sélectionnée : " + selectedFile.toURI().toString()); // Imprimer le chemin de l'image
            imageview.setImage(image);

            upload.setVisible(false);
        }
    }

        @FXML
        void addPublication (ActionEvent event){
            String contenu = txtContenu.getText();
            String url_file = selectedFile.toURI().toString();

            // Assuming your ServiceUser class has a method like: User authenticateUser(String email, String password)
            User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "1234563");

            if (loggedInUser != null) {
                // Assuming your Publication class has a constructor like: Publication(String contenu, String url_file, User user)
                Publication publication = new Publication(contenu, url_file, loggedInUser);

                publication.setD_creation_publication(Timestamp.valueOf(LocalDateTime.now())); // Set current timestamp

                // Assuming your ServicePublication class has a method like: void add(Publication publication)
                servicePublication.add(publication);

                // Show a success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Publication added successfully!");
                alert.showAndWait();

                // Close the current stage (onglet)
                Stage stage = (Stage) txtContenu.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Error: User not found.");
            }
        }
    }




