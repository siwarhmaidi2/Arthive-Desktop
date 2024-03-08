package edu.esprit.Controllers;

import edu.esprit.entities.UserData;
import edu.esprit.services.CrudEvent;
import edu.esprit.services.CrudParticipations;

import edu.esprit.entities.Event;
import edu.esprit.entities.User;
import edu.esprit.enums.CategorieEvenement;
import edu.esprit.tests.Main;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.TextField;
import edu.esprit.services.ServiceUser;


public class AfficherEvent implements Initializable {
    private ModifierEvent getUpdatedEventDetails;
    private Event evenementToModify;
    @FXML
    private VBox messageBox;

    @FXML
    private ImageView photo;

    @FXML
    private Hyperlink name ;

    @FXML
    private ImageView messageImage;
    @FXML
    private Label messageLabel;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;
    @FXML
    private Button vosEvenementsButton;
    @FXML
    private Label descriptionText;

    @FXML
    private GridPane eventsGrid;
    @FXML
    private Button participerButton;
    @FXML
    private Hyperlink voirDetail;
    @FXML
    private Label titleText;

    @FXML
    private Label dateText;

    @FXML
    private Label lieuText;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ImageView eventImageView;
    @FXML
    private ComboBox<CategorieEvenement> categorieComboBox;
    @FXML
    private ImageView starImageView;

    private Event evenement; // Declare the variable evenement
    private EventView eventView;


    public void setEvenement(Event evenement) {
        this.evenement = evenement;
    }

    private CrudEvent crudEvent = new CrudEvent();
    private CrudParticipations cp = new CrudParticipations();
    private ServiceUser userService = new ServiceUser();
    EventView eventViewController = new EventView();
    private static final User loggedInUser = UserData.getInstance().getLoggedInUser();


    //    public void participerButton(ActionEvent event) {
//
//        participerButton.setText("Participer");
//
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Vous avez participer");
//        alert.setHeaderText(null);
//        alert.setContentText("Réservez la date...!");
//        alert.showAndWait();
//        Stage stage = (Stage) participerButton.getScene().getWindow();
//        stage.close();
//
//    }
    public void voirDetailClicked(ActionEvent event) {
        Hyperlink source = (Hyperlink) event.getSource();

        // Assurez-vous que userData est de type Event
        Object userData = source.getUserData();

        System.out.println("Type of userData: " + userData.getClass().getName());

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
                stage.setTitle(" Détail");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("UserData is not an instance of Event. Check your setup.");
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficherEvenements();
        this.name.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        this.photo.setImage(new Image(loggedInUser.getPhoto()));
        eventsGrid.setVgap(30); // Espace vertical de 10 pixels
        eventsGrid.setHgap(40); // Espace horizontal de 10 pixels
        Button vosEvenementsButton = new Button("Vos évènements");
        vosEvenementsButton.setOnAction(this::afficherVosEvenements);
        categorieComboBox.setItems(FXCollections.observableArrayList(CategorieEvenement.values()));


    }

    private GridPane createEventPane(Event evenement) {
        GridPane pane = new GridPane();

        pane.add(new javafx.scene.control.Label("Titre: " + evenement.getTitre_evenement()), 0, 0);
        pane.add(new javafx.scene.control.Label("Date: " + evenement.getD_debut_evenement()), 0, 1);
        pane.add(new javafx.scene.control.Label("Lieu: " + evenement.getLieu_evenement()), 0, 2);

            Button participerButton = new Button("Participer");
        // participerButton.setOnAction(this::participerButton);
        participerButton.setUserData(evenement);
        pane.add(participerButton, 0, 3);


        Hyperlink voirDetail = new Hyperlink("Voir détail");
        voirDetail.setOnAction(this::voirDetailClicked);
        voirDetail.setUserData(evenement); // Assurez-vous que evenement est un objet de type Event
        pane.add(voirDetail, 0, 4);
        pane.add(voirDetail, 0, 4);



        ImageView eventImageView = new ImageView();
        eventImageView.setFitWidth(300); // Initial width
        eventImageView.setFitHeight(100); // Initial height

        EventView eventView = new EventView();
        eventView.initializeEvent(evenement, 50, 50);
        pane.add(eventView, 0, 5); // Ajoutez EventView à la grille

        return pane;

    }


    private void afficherEvenements() {
        List<Event> evenements = new ArrayList<>(crudEvent.getAll()) ;
        // Tri des événements par date de début de manière croissante
        evenements.sort(Comparator.comparing(Event::getD_debut_evenement));
        AfficherEvent afficherEvent = new AfficherEvent();


        int colIndex = 0;
        int rowIndex = 0;

        for (Event evenement : evenements) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventView.fxml"));
                //Parent root = loader.load();
                AnchorPane anchorPane = loader.load();
                EventView eventViewController = loader.getController();
                // Initialiser les données de l'événement après avoir chargé le FXML
                eventViewController.initializeEvent(evenement, 50, 50);
                // Load and set the image for the event
                String imagePath = "/Image/" + evenement.getImage();
                InputStream inputStream = getClass().getResourceAsStream(imagePath);

                if (inputStream != null) {
                    Image eventImage = new Image(inputStream);
                    eventViewController.setEventImageView(eventImage);
                } else {
                    System.out.println("Failed to load image: " + imagePath);
                }

                // Vérifier si la date de fin de l'événement est passée
                if (evenement.getD_fin_evenement().toLocalDateTime().isBefore(LocalDateTime.now())) {
                    // Appliquer une classe CSS spécifique pour les événements passés
                    anchorPane.getStyleClass().add("evenement-passe");
                    // Désactiver le bouton "Participer" pour les événements passés
                    Button participerButton = eventViewController.getParticiperButton();
                    participerButton.setDisable(true);

                    // Désactiver le lien "Voir détail" pour les événements passés
                    Hyperlink voirDetail = eventViewController.getVoirDetail();
                    voirDetail.setDisable(true);

                }

                // Ajouter EventView à la GridPane
                eventsGrid.add(anchorPane, colIndex, rowIndex);


                // Incrémenter les indices de colonne et de ligne
                colIndex++;

                if (colIndex >= 4) { // Si vous voulez afficher 3 produits par ligne, vous pouvez ajuster cette valeur selon vos besoins
                    colIndex = 0;
                    rowIndex++;
                }
                //set grid width
                eventsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                eventsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                eventsGrid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                eventsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                eventsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                eventsGrid.setMaxHeight(Region.USE_PREF_SIZE);
                //GridPane.setMargin(anchorPane, new Insets(10,20,30,40));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        String searchTerm = searchField.getText().toLowerCase();

        List<Event> filteredEvents = filterEvents(searchTerm);
        if (filteredEvents.isEmpty()) {
            messageImage.setImage(new Image("/Image/ay.png"));
            // Aucun événement trouvé, afficher un message
            messageLabel.setText("Aucun événement trouvé pour le terme de recherche : " + searchTerm);
            // Vous pouvez également effacer la liste des événements actuellement affichés
            eventsGrid.getChildren().clear();
            messageBox.setVisible(true);
        } else {
            // Afficher les événements filtrés
            updateEventView(filteredEvents);
            // Cacher le message
            messageBox.setVisible(false);
        }

        //updateEventView(filteredEvents);
    }

    private List<Event> filterEvents(String searchTerm) {
        return crudEvent.getAll().stream()
                .filter(event -> eventMatchesSearchTerm(event, searchTerm))
                .collect(Collectors.toList());
    }

    private boolean eventMatchesSearchTerm(Event event, String searchTerm) {
        // Adapt this method based on how you want to perform the search
        return event.getTitre_evenement().toLowerCase().contains(searchTerm)
                || event.getDescription_evenement().toLowerCase().contains(searchTerm)
                || event.getLieu_evenement().toLowerCase().contains(searchTerm);
    }

    private void updateEventView(List<Event> filteredEvents) {
        // Clear existing nodes in the GridPane
        eventsGrid.getChildren().clear();

        // Update the GridPane with the filtered events
        int colIndex = 0;
        int rowIndex = 0;

        for (Event evenement : filteredEvents) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Eventview.fxml"));
                AnchorPane anchorPane = loader.load();
                EventView eventViewController = loader.getController();
                eventViewController.initializeEvent(evenement, 50, 50);

                // Load and set the image for the event
                String imagePath = "/Image/" + evenement.getImage();
                InputStream inputStream = getClass().getResourceAsStream(imagePath);

                if (inputStream != null) {
                    Image eventImage = new Image(inputStream);
                    eventViewController.setEventImageView(eventImage);
                } else {
                    System.out.println("Failed to load image: " + imagePath);
                }

                eventsGrid.add(anchorPane, colIndex, rowIndex);

                colIndex++;

                if (colIndex >= 4) {
                    colIndex = 0;
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void afficherVosEvenements(ActionEvent event) {
        String userName = "ayoub";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VosEvenements.fxml"));
            Parent root = loader.load();
            // Créez une instance de ServiceUser
            ServiceUser serviceUser = new ServiceUser();
            // Vous pouvez transmettre des données à la nouvelle fenêtre si nécessaire
            VosEvenements vosEvenementsController = loader.getController();
            // Récupérez le nom de l'utilisateur connecté
           // User loggedInUser = serviceUser.authenticateUser("toujnaiayoub808gmail.com", "1234");

            int userId = loggedInUser.getId_user();
            vosEvenementsController.initData(userId);

           Stage stage = new Stage();
         stage.setScene(new Scene(root));
           stage.show();
            Stage homeStage = (Stage) vosEvenementsButton.getScene().getWindow();
            homeStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private Label ParticipantText;
    private CrudParticipations crudParticipations = new CrudParticipations();

//    private void updateParticipantCount(Event evenement) {
//        //Set<String> participants = crudParticipations.getParticipantsForEvent(evenement.getId_event());
//       // int numberOfParticipants = participants != null ? participants.size() : 0;
//       // ParticipantText.setText("Nombre de participants : " + numberOfParticipants);
//    }


    public void updateEventInView(Event updatedEvent) {

        AfficherEvent afficherEvent = new AfficherEvent();

        if (this.eventsGrid == null) {
            //System.out.println("eventsGrid est null. Assurez-vous qu'il est correctement initialisé.");
            return;
        }

        if (eventView != null) {
            eventView.refreshEventDetails(updatedEvent);
        } else {
            System.out.println("EventView non initialisé.");
        }

        // Mettez à jour l'affichage de l'événement dans la vue AfficherEvent
        if (afficherEvent != null) {
            afficherEvent.updateEventInView(updatedEvent);
        } else {
            System.out.println("AfficherEvent non initialisé.");
        }

        for (Node node : this.eventsGrid.getChildren()) {
            if (node instanceof AnchorPane) {
                AnchorPane anchorPane = (AnchorPane) node;
                Object userData = anchorPane.getUserData();

                if (userData instanceof Event) {
                    Event eventInView = (Event) userData;

                    // Vérifiez si l'événement dans la vue correspond à l'événement mis à jour
                    if (eventInView.getId_event() == updatedEvent.getId_event()) {
                        // Utilisez la nouvelle approche pour obtenir l'EventView
                        EventView eventView = findEventViewChild(anchorPane);

                        if (eventView != null) {
                            // Mettez à jour les détails de l'événement dans l'EventView
                            eventView.refreshEventDetails(updatedEvent);
                        } else {
                            System.out.println("EventView non trouvé parmi les enfants de l'AnchorPane.");
                        }

                        // Sortez de la boucle dès que l'événement est mis à jour
                        break;
                    }
                }
            }
        }
    }
    private EventView findEventViewChild(AnchorPane anchorPane) {
        for (Node child : anchorPane.getChildren()) {
            if (child instanceof EventView) {
                return (EventView) child;
            }
        }
        return null;
    }
    private ObservableList<Event> eventList;


    public ObservableList<Event> getEventList() {
        return eventList;
    }
    public void refreshEvenementsList() {
        afficherEvenements();
    }
    @FXML
    private void filterEventsByCategory(ActionEvent event) {
        // Récupérer la catégorie sélectionnée
        CategorieEvenement selectedCategory = categorieComboBox.getValue();

        if (selectedCategory != null) {
            // Filtrer les événements par la catégorie sélectionnée
            List<Event> filteredEvents = crudEvent.getEventsByCategory(selectedCategory);

            // Vérifier si la liste filtrée est vide
            if (filteredEvents.isEmpty()) {
                // Aucun événement trouvé, afficher l'image "ay.png" avec un message
                messageImage.setImage(new Image("/Image/ay.png"));
                messageLabel.setText("Aucun événement trouvé pour la catégorie : " + selectedCategory);
                // Effacer la liste des événements actuellement affichés
                eventsGrid.getChildren().clear();
                // Afficher la messageBox
                messageBox.setVisible(true);
            } else {
                // Afficher les événements filtrés
                updateEventView(filteredEvents);
                // Cacher le message
                messageBox.setVisible(false);
            }
        } else {
            // Si aucune catégorie n'est sélectionnée, afficher tous les événements
            refreshEvenementsList();
        }
    }

    public void logout(ActionEvent event) throws IOException {
        UserData.getInstance().setLoggedInUser(null);
        Main.changeScene("/Login.fxml");
    }
    public void SwitchToProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent root = loader.load();
            //dont open new window
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }

    public void SwitchToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    public void SwitchToEvents(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherEvent.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}










