package edu.esprit.Controllers;

import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;


import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.scene.control.Alert.AlertType;

public class ajoutPublicationController {


    @FXML
    private Button addFile;

    @FXML
    private Button addPost;

    @FXML
    private Button cancelPost;

    @FXML
    private TextArea contentPost;

    @FXML
    private ImageView imagePost;


    private File selectedFile;
    private Stage stage;
    private Scene scene;

    private Parent root;
    private final ServicePublication servicePublication = new ServicePublication();
    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    void uploadArt(MouseEvent event) {
        // Créer un sélecteur de fichiers pour les images
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
                Image image = new Image(selectedFile.toURI().toString());
                System.out.println("Chemin de l'image sélectionnée : " + selectedFile.toURI().toString()); // Imprimer le chemin de l'image
                imagePost.setImage(image);
                addFile.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading image: " + e.getMessage());
            }

        }

    }
    @FXML
    void addPublication(ActionEvent event) {

        String contenu = contentPost.getText();
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

            // Close the current stage
            Stage stage = (Stage) imagePost.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Error: User not found.");
        }
    }

    public void switchToHomePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




