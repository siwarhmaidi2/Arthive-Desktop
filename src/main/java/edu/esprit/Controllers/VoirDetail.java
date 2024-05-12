package edu.esprit.Controllers;
import edu.esprit.entities.Event;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class VoirDetail implements Initializable {
    @FXML
    private Label titreLabel;
    @FXML
    private Label descriptionText;
    @FXML
    private ImageView eventImageView;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label dateText;

    @FXML
    private Label lieuText;
    @FXML
    private Label dateTextFin;
    @FXML
    private StackPane mapView; // Ajout du composant WebView pour afficher la carte
    private String locationDetails; // Déclarez locationDetails comme un champ de classe

    public void initializeDetails(Event evenement) {
         if (evenement != null) {
             titreLabel.setText(evenement.getTitre_evenement());
             descriptionText.setText(evenement.getDescription_evenement());
             lieuText.setText(evenement.getLieu_evenement());
             dateText.setText(evenement.getD_debut_evenement().toString());
             dateTextFin.setText(evenement.getD_fin_evenement().toString());
             lieuText.setText(evenement.getLieu_evenement());
            // updateMapWithLocation(evenement.getLieu_evenement());
             String imagePath = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+ evenement.getImage();

             Image eventImage = new Image(imagePath);
                eventImageView.setImage(eventImage);
         } else {
             // Gérer le cas où evenement est null, par exemple en effaçant le texte
             titreLabel.setText("");
             descriptionText.setText("");
             lieuText.setText("");
             dateText.setText("");
         }
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    }


