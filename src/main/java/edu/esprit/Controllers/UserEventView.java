package edu.esprit.Controllers;

import edu.esprit.entities.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserEventView  extends AnchorPane implements Initializable {


    @FXML
    private AnchorPane eventViewAnchor;

    @FXML
    private ImageView eventImageVieww;

    @FXML
    private Label titleTextt;

    @FXML
    private Label dateTextt;

    @FXML
    private Label lieuText;


    @FXML
    private Button participerButton;
    @FXML
    private Label descriptionText;


    public ImageView getImage() {
        return eventImageVieww;
    }

    public void setImage(ImageView image) {
        this.eventImageVieww= image;
    }



    public ImageView getEventImageView() {
        return eventImageVieww;
    }

    public void setEventImageView(Image image) {
        eventImageVieww.setImage(image);
    }

    public Label getTitleText() {
        return titleTextt;
    }

    public void setTitleText(String title) {
        titleTextt.setText(title);
    }

    public Label getDateText() {
        return dateTextt;
    }

    public void setDateText(String date) {
        dateTextt.setText(date);
    }

    public Label getLieuText() {
        return lieuText;
    }

    public void setLieuText(String lieu) {
        lieuText.setText(lieu);
    }

    public Button getViewDetailsButton() {
        return participerButton;
    }

    public void setDescriptionText(String description) {
        descriptionText.setText(description);
    }


    public void initializeEventUser(Event evenement, double imageWidth, double imageHeight) {
        ImageView eventImageView = new ImageView();

        eventImageView.setFitWidth(50);
        eventImageView.setFitHeight(50);

        if (evenement != null) {
            // Assurez-vous que la propriété "imagePath" de la classe Event correspond à votre chemin d'image
            String imagePath = evenement.getImage();

            // Chargez et définissez l'image
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image(new File(imagePath).toURI().toString());
                eventImageView.setImage(image);
            }
        }

        titleTextt.setText("Titre: " + evenement.getTitre_evenement());
        dateTextt.setText(evenement.getD_debut_evenement().toString());
        lieuText.setText("Lieu: " + evenement.getLieu_evenement());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}






