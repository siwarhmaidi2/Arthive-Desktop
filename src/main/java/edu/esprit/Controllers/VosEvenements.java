package edu.esprit.Controllers;

import edu.esprit.entities.UserData;
import edu.esprit.services.CrudEvent;
import edu.esprit.services.ServiceUser;
import edu.esprit.entities.Event;
import edu.esprit.entities.User;
import edu.esprit.tests.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.Image;



public class VosEvenements implements Initializable {

    @FXML
    private Hyperlink name ;

    @FXML
    private javafx.scene.image.ImageView photo;
    @FXML
    private ImageView eventImageView;

    @FXML
    private Label userNameLabel;

    @FXML
    private GridPane eventsGrid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Button vosEvenementsButton;
    @FXML
    private Button creerEvenement;

    private final CrudEvent crudEvent = new CrudEvent();
    private EventView eventView;

    public void setEventView(EventView eventView) {
        this.eventView = eventView;
    }
    private static final User loggedInUser = UserData.getInstance().getLoggedInUser();

    public void initData(int userId) {
        userNameLabel.setText("Vos évènements");
        List<Event> userEvents = crudEvent.getEventsForUser(userId);
        // ... le reste du code pour afficher les événements dans votre GridPane
        //afficherEvenements(userEvents);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.name.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        //this.photo.setImage(new Image(loggedInUser.getPhoto()));
       Button vosEvenementsButton = new Button("Vos évènements");
        vosEvenementsButton.setOnAction(this::afficherVosEvenements);
        ServiceUser serviceUser = new ServiceUser();
       // User loggedInUser = serviceUser.authenticateUser("toujnaiayoub808gmail.com", "1234");
        int userId = loggedInUser.getId_user();

        // Appeler la méthode pour afficher les événements de l'utilisateur spécifique
        afficherEvenementsUtilisateur(userId);
      //  eventsGrid.setVgap(100); // Espace vertical de 10 pixels
        //eventsGrid.setHgap(40); // Espace horizontal de 10 pixels

    }
    @FXML
    private void afficherVosEvenements(ActionEvent event) {
        String userName = "ayoub";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventView.fxml"));
            Parent root = loader.load();
            // Créez une instance de ServiceUser
            ServiceUser serviceUser = new ServiceUser();
            // Vous pouvez transmettre des données à la nouvelle fenêtre si nécessaire
            VosEvenements vosEvenementsController = loader.getController();
            // Récupérez le nom de l'utilisateur connecté
            int userId = loggedInUser.getId_user();
            vosEvenementsController.initData(userId);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void afficherEvenementsUtilisateur(int userId) {
        List<Event> evenements = crudEvent.getEventsForUser(userId);
        evenements.sort(Comparator.comparing(Event::getD_debut_evenement).reversed());

        int spacing = 10; // Espace entre chaque EventView
        int colIndex = 0;
        int rowIndex = 0;

        for (Event evenement : evenements) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventView.fxml"));
                AnchorPane anchorPane = loader.load();
                EventView eventViewController = loader.getController();

                // Initialiser les données de l'événement après avoir chargé le FXML
                eventViewController.initializeEvent(evenement, 10, 50);

                // Load and set the image for the event
                String imagePath = "/Image/" + evenement.getImage();
                InputStream inputStream = getClass().getResourceAsStream(imagePath);

                if (inputStream != null) {
                    Image eventImageView = new Image(inputStream);
                    eventViewController.setEventImageView(eventImageView);
                } else {
                    System.out.println("Failed to load image: " + imagePath);
                }

                // Afficher les boutons de modification et de suppression
                eventViewController.getVoirDetail().setVisible(true);
                eventViewController.getParticiperButton().setVisible(false);
                eventViewController.getModifierButton().setVisible(true);
                eventViewController.getSupprimerButton().setVisible(true);


                // Ajouter une marge entre chaque EventView
                GridPane.setMargin(anchorPane, new Insets(spacing));
                // Ajouter EventView à la GridPane
                eventsGrid.add(anchorPane, colIndex, rowIndex);

                // Incrémenter les indices de colonne et de ligne
                colIndex++;

                if (colIndex >= 4) {
                    colIndex = 0;
                    rowIndex++;
                }
                eventsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                eventsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                eventsGrid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                eventsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                eventsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                eventsGrid.setMaxHeight(Region.USE_PREF_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void CreerEvenement(ActionEvent event) {
        try {
            ServiceUser serviceUser = new ServiceUser();

            // Charger le fichier FXML de la fenêtre d'ajout d'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
            Parent root = loader.load();
            AjouterEvent ejouterEvent = loader.getController();
            // Récupérez le nom de l'utilisateur connecté

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre
            Stage ajoutEventStage = new Stage();
            ajoutEventStage.setTitle("Ajouter un événement");

            // Définir la scène pour la nouvelle fenêtre
            ajoutEventStage.setScene(scene);

            // Montrer la nouvelle fenêtre
            ajoutEventStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void refreshEventList(List<Event> updatedEventList) {
        // Mettez à jour la liste des événements ici
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
    public void logout(ActionEvent event) throws IOException {
        UserData.getInstance().setLoggedInUser(null);
        Main.changeScene("/Login.fxml");
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
