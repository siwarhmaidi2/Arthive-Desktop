package edu.esprit.Controllers;

import edu.esprit.services.CrudEvent;
import edu.esprit.entities.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EventAdminController extends AnchorPane implements Initializable  {
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;


    @FXML
    private AnchorPane eventViewAnchorPane;

    @FXML
    private ImageView eventImageView;

    @FXML
    private Label titleText;

    @FXML
    private Label dateText;

    @FXML
    private Label lieuText;
    @FXML
    private Label ParticipantText;

    @FXML
    private Hyperlink voirDetail;
    @FXML
    private Button participerButton;
    @FXML
    private Label descriptionText;


    @FXML
    private TextField titreTextField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField lieuField;
    @FXML
    private ImageView starImageView;

    private AfficherEvent afficherEvent;
    public TextField getTitreTextField() {
        return titreTextField;
    }

    public void setTitreTextField(TextField titreTextField) {
        this.titreTextField = titreTextField;
    }

    public DatePicker getDateDebutPicker() {
        return dateDebutPicker;
    }

    public void setDateDebutPicker(DatePicker dateDebutPicker) {
        this.dateDebutPicker = dateDebutPicker;
    }

    public DatePicker getDateFinPicker() {
        return dateFinPicker;
    }

    public void setDateFinPicker(DatePicker dateFinPicker) {
        this.dateFinPicker = dateFinPicker;
    }

    public TextArea getDescriptionArea() {
        return descriptionArea;
    }

    public void setDescriptionArea(TextArea descriptionArea) {
        this.descriptionArea = descriptionArea;
    }

    public TextField getLieuField() {
        return lieuField;
    }

    public void setLieuField(TextField lieuField) {
        this.lieuField = lieuField;
    }

    public Button getModifierButton() {
        return modifierButton;
    }

    public void setModifierButton(Button modifierButton) {
        this.modifierButton = modifierButton;
    }

    public Button getSupprimerButton() {
        return supprimerButton;
    }

    public void setSupprimerButton(Button supprimerButton) {
        this.supprimerButton = supprimerButton;
    }






    public ImageView getImage() {
        return eventImageView;
    }

    public void setImage(ImageView image) {
        this.eventImageView = image;
    }

    public Hyperlink getVoirDetail() {
        return voirDetail;
    }

    public void setVoirDetail(ImageView image) {
        this.voirDetail = voirDetail;
    }

    public ImageView getEventImageView() {
        return eventImageView;
    }

    public void setEventImageView(Image image) {
        eventImageView.setImage(image);
    }

    public Label getTitleText() {
        return titleText;
    }

    public void setTitleText(String title) {
        titleText.setText(title);
    }

    public Label getDateText() {
        return dateText;
    }

    public void setDateText(String date) {
        dateText.setText(date);
    }

    public Label getLieuText() {
        return lieuText;
    }

    public void setLieuText(String lieu) {
        lieuText.setText(lieu);
    }

    public Label getParticipantText() {
        return ParticipantText;
    }

    public void setParticipantText(Label participantText) {
        ParticipantText = participantText;
    }

    public Button getViewDetailsButton() {
        return participerButton;
    }

    public void setDescriptionText(String description) {
        descriptionText.setText(description);
    }

    public void setAfficherEvent(AfficherEvent afficherEvent) {
        this.afficherEvent = afficherEvent;
    }

    public void supprimerButtonClicked(ActionEvent event) {

        CrudEvent crudEvent = new CrudEvent();

        Button source = (Button) event.getSource();
        Object userData = source.getUserData();
        if (userData instanceof Event) {
            Event evenement = (Event) userData;
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer cet événement ?");
            alert.setContentText("Cliquez sur OK pour confirmer la suppression, ou Annuler pour annuler.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Appel à la méthode de suppression du CrudEvent
                crudEvent.delete(evenement);
            } else {
                System.out.println("Failed to delete event with ID " + evenement.getId_event());
            }
        } else {
            System.out.println("UserData is not an instance of Event. Check your setup.");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
