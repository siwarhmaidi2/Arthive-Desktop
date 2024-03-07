package edu.esprit.Controllers;
import edu.esprit.services.CrudEvent;
import edu.esprit.services.ServiceUser;
import edu.esprit.entities.Event;
import edu.esprit.enums.CategorieEvenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ModifierEvent implements Initializable {

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
    private Button validerButton;
    @FXML
    private ImageView eventImageView;
    private Event evenementToModify;
    private Event currentEvent;
    private EventView eventView;
    @FXML
    private GridPane eventsGrid;
    @FXML
    private ComboBox<CategorieEvenement> category;

    private final CrudEvent crudEvent = new CrudEvent();
    private final ServiceUser serviceUser = new ServiceUser();
    private AfficherEvent afficherEvent;


    public void setAfficherEvent(AfficherEvent afficherEvent) {
        this.afficherEvent = afficherEvent;
    }
    public void initAfficherEvent(AfficherEvent afficherEvent) {
        this.afficherEvent = afficherEvent;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validerButton.setOnAction(this::validerButtonClicked);
        if (category != null) {
            validerButton.setOnAction(this::validerButtonClicked);
        }
    }

    public void initData(Event evenement) {
        this.evenementToModify = evenement;

        titreTextField.setText(evenement.getTitre_evenement());

        if (evenement.getD_debut_evenement() != null) {
            LocalDateTime dateDebutLocalDateTime = evenement.getD_debut_evenement().toLocalDateTime();
            dateDebutPicker.setValue(dateDebutLocalDateTime.toLocalDate());
        } else {
            // Gérer le cas où la date de début est null
            dateDebutPicker.setValue(null);
        }

        if (evenement.getD_fin_evenement() != null) {
            LocalDateTime dateFinLocalDateTime = evenement.getD_fin_evenement().toLocalDateTime();
            dateFinPicker.setValue(dateFinLocalDateTime.toLocalDate());
        } else {
            // Gérer le cas où la date de fin est null
            dateFinPicker.setValue(null);
        }

        lieuField.setText(evenement.getLieu_evenement());
        descriptionArea.setText(evenement.getDescription_evenement());
        ObservableList<CategorieEvenement> categories = FXCollections.observableArrayList(CategorieEvenement.values());
        category.setItems(categories);
        category.setValue(evenement.getCategorieEvenement());

    }

    public void validerButtonClicked(ActionEvent event) {
        CrudEvent crudEvent = new CrudEvent();
      // Event events = crudEvent.getOneByID(currentEvent.getId_event());
        Event updatedEvent = getUpdatedEventDetails();
        AfficherEvent afficherEvent = new AfficherEvent();

        if (updatedEvent != null) {
            crudEvent.update(updatedEvent);


            // Afficher une alerte de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Modification réussie");
            successAlert.setHeaderText(null);
            successAlert.setContentText("L'événement a été modifié avec succès.");
            successAlert.showAndWait();

            // Mettez à jour l'affichage de l'événement dans EventView

           // eventView.refreshEventDetails(updatedEvent);
            // Mettez à jour l'affichage de l'événement dans EventView

            if (eventView != null) {
                eventView.refreshEventDetails(updatedEvent);
            } else {
                System.out.println("EventView non initialisé.");
            }

            afficherEvent.updateEventInView(updatedEvent);


            //afficherEvent.refreshEventDetails(updatedEvent);
            // Fermez la fenêtre de modification
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            //Stage stage = (Stage) validerButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Erreur lors de la récupération des détails de l'événement.");
        }
    }
    private Event getUpdatedEventDetails() {
        // Récupérez les modifications apportées aux détails de l'événement depuis les éléments FXML
        String updatedTitre = titreTextField.getText();
        LocalDateTime updatedDateDebut = dateDebutPicker.getValue().atStartOfDay();
        LocalDateTime updatedDateFin = dateFinPicker.getValue().atStartOfDay();
        String updatedDescription = descriptionArea.getText();
        String updatedLieu = lieuField.getText();


        // Assurez-vous que l'utilisateur associé à l'événement n'est pas null
        if (evenementToModify.getUser() != null) {
            // Créez un nouvel objet Event avec ces modifications
            Event updatedEvent = new Event();
            updatedEvent.setId_event(evenementToModify.getId_event());
            updatedEvent.setUser(evenementToModify.getUser());
            updatedEvent.setTitre_evenement(updatedTitre);
            updatedEvent.setD_debut_evenement(Timestamp.valueOf(updatedDateDebut));
            updatedEvent.setD_fin_evenement(Timestamp.valueOf(updatedDateFin));
            updatedEvent.setDescription_evenement(updatedDescription);
            updatedEvent.setLieu_evenement(updatedLieu);
            updatedEvent.setCategorieEvenement(category.getValue()); // Ajout de la catégorie

// Conservez l'image d'origine uniquement si elle n'est pas nulle
            String originalImage = evenementToModify.getImage();
            updatedEvent.setImage(originalImage != null ? originalImage : "");
            // Vous devrez définir d'autres propriétés en fonction de votre classe Event

            return updatedEvent;
        } else {
            System.out.println("L'utilisateur associé à l'événement est null. Veuillez vérifier votre logique.");
            return null;
        }
    }
    public void initEventView(EventView eventView) {
        this.eventView = eventView;
    }
//    public void initEventVieww (AfficherEvent afficherEvent) {
//        this.afficherEvent = afficherEvent;
//    }

}






