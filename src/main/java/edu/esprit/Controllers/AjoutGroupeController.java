package edu.esprit.Controllers;

import edu.esprit.entities.*;
import edu.esprit.services.ServiceGroupe;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public class AjoutGroupeController {

    @FXML
    private Button deleteFile;
    @FXML
    private Button addFile;




    @FXML
    private Button addGroupe;

    @FXML
    private Button cancelGroupe;



    @FXML
    private TextArea description;

    @FXML
    private ImageView imagePost;

    @FXML
    private TextArea nomGroupe;

    private File selectedFile;
    private Stage stage;
    private Scene scene;

    private Parent root;
    private final ServiceGroupe serviceGroupe = new ServiceGroupe();
    private final ServiceUser serviceUser = new ServiceUser();
    private HomeGroupe homeController;



    public void setHomeController(HomeGroupe homeController) {
        this.homeController = homeController;
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }
    private boolean isPhotoSelected = false;

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
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);

                int targetWidth = 500;
                int targetHeight = 500;
                BufferedImage processedImage = resizeImage(bufferedImage, targetWidth, targetHeight);

                // Convert BufferedImage to JavaFX Image
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                System.out.println("Chemin de l'image sélectionnée : " + selectedFile.toURI().toString());
                imagePost.setImage(image);
                addFile.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading image: " + e.getMessage());
            }
        }
        isPhotoSelected = true;

        if (selectedFile != null)
            deleteFile.setVisible(true); // Make the deleteFile button visible
    }
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }
    @FXML
    void addGroupe(ActionEvent event) {
        if (selectedFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une photo.");
            alert.showAndWait();
            return;
        }

        String contenu = description.getText();
        String nom = nomGroupe.getText();
        String fileName = selectedFile.getName();

        // Assuming your ServiceUser class has a method like: User authenticateUser(String email, String password)
        User loggedInUser = UserData.getInstance().getLoggedInUser();
        System.out.println(loggedInUser);

        if (loggedInUser != null) {
            // Assuming your Publication class has a constructor like: Publication(String contenu, String url_file, User user)
            Publication publication = new Publication(contenu, fileName, loggedInUser);

            publication.setD_creation_publication(Timestamp.valueOf(LocalDateTime.now())); // Set current timestamp

            // Assuming your ServicePublication class has a method like: void add(Publication publication)
            serviceGroupe.ajouter(new Groupe(nom,contenu,loggedInUser,fileName));

            // Show a success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Groupe ajoutée avec succée!");
            alert.showAndWait();

            Stage currentStage = (Stage) addGroupe.getScene().getWindow();
            currentStage.close();
            if (homeController != null) {
                homeController.refreshContent();
            }

        } else {
            System.out.println("Error: User not found.");
        }


    }

    @FXML
    void deleteArt(ActionEvent event) {
        // Clear the selected file and reset the image view
        selectedFile = null;
        imagePost.setImage(null);
        addFile.setVisible(true);
        isPhotoSelected = false;
    }
    @FXML
    void cancelGroupe(ActionEvent event) {
        if (isPhotoSelected) {
            // Ask for confirmation before closing
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Êtes-vous sûr de vouloir fermer la fenêtre ?");
            alert.setContentText("Si vous fermez la fenêtre maintenant, les modifications non sauvegardées seront perdues.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User clicked OK, close the window
                Stage currentStage = (Stage) cancelGroupe.getScene().getWindow();
                currentStage.close();
            }
        } else {
            // No photo selected, close the window without confirmation
            Stage currentStage = (Stage) cancelGroupe.getScene().getWindow();
            currentStage.close();
        }
    }

    @FXML
    void initialize() {

        Platform.runLater(() -> {
            // Set up window close request handler
            Stage stage = (Stage) addGroupe.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                if (isPhotoSelected) {
                    // Ask for confirmation before closing
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Êtes-vous sûr de vouloir fermer la fenêtre ?");
                    alert.setContentText("Si vous fermez la fenêtre maintenant, les modifications non sauvegardées seront perdues.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                        // Cancel the close request
                        event.consume();
                    }
                }
            });
            deleteFile.setVisible(false);
        });
    }


}




