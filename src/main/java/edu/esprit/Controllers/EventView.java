package edu.esprit.Controllers;


import com.twilio.Twilio;
import edu.esprit.entities.UserData;
import edu.esprit.services.CrudEvent;
import edu.esprit.services.CrudParticipations;

import edu.esprit.entities.Event;

import edu.esprit.entities.Participation;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



public class EventView extends AnchorPane implements Initializable {
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



    public EventView() {
        crudParticipations = new CrudParticipations();
    }


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

    public Button getParticiperButton() {
        return participerButton;
    }

    public void setParticiperButton(Button ParticiperButton) {
        this.participerButton = ParticiperButton;
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

    // private Event evenement;
    private Event evenement;
    private VosEvenements vosEvenements;
    private static final User loggedInUser = UserData.getInstance().getLoggedInUser();


    public void modifierButtonClicked(ActionEvent event) {

        Button source = (Button) event.getSource();

        // Assurez-vous que userData est de type Event
        Object userData = source.getUserData();

        if (userData instanceof Event) {
            Event evenement = (Event) userData;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
                Parent root = loader.load();

                ModifierEvent modifierController = loader.getController();
                modifierController.initData(evenement);
                modifierController.initEventView(this);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle(" Modifier évènement");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("UserData is not an instance of Event. Check your setup.");
        }
    }


    public void refreshEventDetails(Event updatedEvent) {
        // Mettez à jour les éléments FXML avec les nouveaux détails de l'événement
        titleText.setText("Titre: " + updatedEvent.getTitre_evenement());

        // Assurez-vous que la date de début est non null avant de l'afficher
        if (updatedEvent.getD_debut_evenement() != null) {
            dateText.setText(updatedEvent.getD_debut_evenement().toString());
        } else {
            dateText.setText("Date non spécifiée");
        }

        lieuText.setText("Lieu: " + updatedEvent.getLieu_evenement());

        // Assurez-vous que la description est non null avant de l'afficher
        if (updatedEvent.getDescription_evenement() != null) {
            descriptionText.setText(updatedEvent.getDescription_evenement());
        } else {
            descriptionText.setText("Aucune description disponible");
        }


        // Vous devrez ajouter des mises à jour pour d'autres détails en fonction de votre classe Event
        System.out.println("Mise à jour des détails de l'événement : " + updatedEvent);

    }

    private ObservableList<Event> eventsList = FXCollections.observableArrayList();

    // Méthode pour mettre à jour la liste des événements dans votre classe EventView
    private void refreshEventList(List<Event> updatedEventList) {
        // Effacez la liste actuelle
        eventsList.clear();
        // Ajoutez les éléments mis à jour
        eventsList.addAll(updatedEventList);
    }


    public void supprimerButtonClicked(ActionEvent event) {
        starImageView.setVisible(false);

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
                // Rafraîchir la liste des événements dans AfficherEvent
                if (afficherEvent != null) {
                    afficherEvent.refreshEvenementsList();
                }
                // Rafraîchir la liste des événements dans VosEvenements
                if (vosEvenements != null) {
                    vosEvenements.refreshEventList((List<Event>) crudEvent.getAll());
                }

                // Rafraîchir la liste des événements dans la vue actuelle (EventView)

                refreshEventList((List<Event>) crudEvent.getAll()); // Correction : Passer la liste mise à jour ici

            } else {
                System.out.println("Failed to delete event with ID " + evenement.getId_event());
            }
        } else {
            System.out.println("UserData is not an instance of Event. Check your setup.");
        }

    }

    private int participantId;
    private Participation participation;
    CrudParticipations crudParticipations;

    private Participation currentParticipation;
   // private boolean isParticipationAdded = false;
   private boolean isParticipationAdded = false;

    public void participerButton(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();

        // Assurez-vous que le userData est correctement défini sur le bouton
        Object userData = sourceButton.getUserData();
        if (userData instanceof Event) {
            ServiceUser serviceUser = new ServiceUser();
            Event evenement = (Event) userData;  // Récupérez l'événement à partir du userData
            // Utilisez l'ID de l'utilisateur connecté comme participantId
          //  User loggedInUser = serviceUser.authenticateUser("toujnaiayoub808gmail.com", "1234");
            int participantId = loggedInUser.getId_user();
            boolean isParticipationAdded = crudParticipations.isParticipant(participantId, evenement.getId_event());
            if (isParticipationAdded) {
                Participation existingParticipation = crudParticipations.getParticipation( evenement.getId_event(),participantId);
                crudParticipations.delete(existingParticipation);
                evenement.removeParticipant(existingParticipation);
                updateParticipantCount(evenement);
                starImageView.setImage(new Image("/Image/etoileContour.png"));

                // Réinitialisez la variable isParticipationAdded ici

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Participation supprimée");
                alert.setHeaderText(null);
                alert.setContentText("Votre participation à cet événement a été supprimée.");
                alert.showAndWait();
            } else {
                // Si la participation n'est pas ajoutée, ajoutez-la
                Participation newParticipation = new Participation(loggedInUser, evenement);
                crudParticipations.add(newParticipation);
                evenement.addParticipant(newParticipation);
                updateParticipantCount(evenement);
                starImageView.setImage(new Image("/Image/etoileColore.png"));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vous avez participé");
                alert.setHeaderText(null);
                alert.setContentText("Réservez la date...!");
                alert.showAndWait();
                String ACCOUNT_SID = "AC830d90420c028fbee80b831b4fb7c216";
                String AUTH_TOKEN = "f23c4f80acf0709e57a15dc64009792f";
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                String userPhoneNumber ;
                Message message = Message.creator(
                        new PhoneNumber("+21699356653"),
                        new PhoneNumber("+13103073062"),
                        "Vous avez participé à l'événement ! Enregistrez la date." + evenement.getD_debut_evenement()).create();

                System.out.println("SMS sent successfully to " + "+21692978106");

            }

            // Inversez l'état de la participation
            //isParticipationAdded = !isParticipationAdded;

            // Mettez à jour l'interface utilisateur avec le nombre de participants actuel
            updateParticipantCount(evenement);
            isParticipationAdded = !isParticipationAdded;

        } else {
            System.out.println("UserData n'est pas une instance de Event. Vérifiez votre configuration.");
        }
    }


    public void voirDetailClicked(ActionEvent event) {
        Hyperlink source = (Hyperlink) event.getSource();

        // Assurez-vous que userData est de type Event
        Object userData = source.getUserData();

        if (userData instanceof Event) {
            Event evenement = (Event) userData;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/voirDetail.fxml"));
                Parent root = loader.load();

                VoirDetail detailsController = loader.getController();

                // Utilisez la classe EventDetailsController pour initialiser les détails
                detailsController.initializeDetails(evenement);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("UserData is not an instance of Event. Check your setup.");
        }
    }

    private void updateParticipantCount(Event evenement) {
        boolean isParticipating = crudParticipations.isParticipant(participantId, evenement.getId_event());

        // Obtenez le nombre de participants pour l'événement
        int currentNumberOfParticipants = crudParticipations.getParticipantsForEvent(evenement.getId_event()).size();

        // Vérifiez si l'utilisateur participe actuellement ou non
        if (isParticipating ) {
            // Si l'utilisateur participe, mettez à jour le nombre total de participants en le réduisant de 1
            int newNumberOfParticipants = currentNumberOfParticipants - 1;
            // Mettez à jour l'interface utilisateur
            Platform.runLater(() -> {
                ParticipantText.setText(newNumberOfParticipants + " participants");
            });
        } else {
            // Si l'utilisateur ne participe pas, mettez à jour le nombre total de participants en ajoutant 1
            int newNumberOfParticipants = currentNumberOfParticipants + 1;
            // Mettez à jour l'interface utilisateur
            Platform.runLater(() -> {
                ParticipantText.setText(newNumberOfParticipants + " participants");
            });
        }
    }

    private CrudEvent crudEvent = new CrudEvent();

    public void initializeEvent(Event evenement, double imageWidth, double imageHeight) {
        ImageView eventImageView = new ImageView();

        eventImageView.setFitWidth(50);
        eventImageView.setFitHeight(50);

        if (evenement != null) {
            // Assurez-vous que la propriété "imagePath" de la classe Event correspond à votre chemin d'image
            String imagePath = evenement.getImage();

            // Chargez et définissez l'image
            if (imagePath != null && !imagePath.isEmpty()) {
                // Image image = new Image(imagePath);
                Image image = new Image(new File(imagePath).toURI().toString());
                eventImageView.setImage(image);

            }
        }
        starImageView.setImage(new Image("/Image/etoileContour.png"));



        titleText.setText(evenement.getTitre_evenement());
        dateText.setText(evenement.getD_debut_evenement().toString());
        lieuText.setText(evenement.getLieu_evenement());
        Set<Participation> participations = crudEvent.getParticipationsForEvent(evenement.getId_event());
        if (participations != null) {
            ParticipantText.setText(participations.size() + " " + "paticipants");
        } else {
            ParticipantText.setText("La liste des participants est nulle.");
        }

        voirDetail.setUserData(evenement);

        // Ajoutez le code ici pour définir le userData du bouton "Modifier"
        modifierButton.setUserData(evenement);
        modifierButton.setOnAction(this::modifierButtonClicked);
        supprimerButton.setUserData(evenement);
        supprimerButton.setOnAction(this::supprimerButtonClicked);
        participerButton.setUserData(evenement);
        participerButton.setOnAction(this::participerButton);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descriptionText = new Label();
        supprimerButton.setOnAction(this::supprimerButtonClicked);
        participerButton.setOnAction(this::participerButton);

    }

    //    public void initializeEventView(ObservableList<Event> eventsList) {
//        // Configurez la vue avec la liste observable
//        // ...
//    }
    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }








}


