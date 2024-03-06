package edu.esprit.Controllers;

import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifierPublicationController implements Initializable {

    private Stage stage;  // Add this field
    private boolean updated = false;
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> onCloseRequest(event));

    }

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView postImage;

    @FXML
    private TextField postTextTextField;

    @FXML
    private ImageView profileImage;

    @FXML
    private Button saveUpdate;

    @FXML
    private Label usernameLabel;

    private Publication publication;

    private ServicePublication servicePublication = new ServicePublication();
    private ServiceUser serviceUser = new ServiceUser();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (publication != null) {
            // Set the initial text from the current publication data
            postTextTextField.setText(publication.getContenu_publication());

        }
    }

    private String calculateTimeElapsed(LocalDateTime publicationDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(publicationDateTime, currentDateTime);

        if (duration.toDays() > 365) {
            // If the duration is greater than a year
            long years = duration.toDays() / 365;
            return "il y a " + years + (years > 1 ? " années" : " année");
        } else if (duration.toDays() > 0) {
            // If the duration is greater than a day
            return "il y a " + duration.toDays() + (duration.toDays() > 1 ? " jours" : " jour");
        } else if (duration.toHours() > 0) {
            // If the duration is greater than an hour
            return "il y a " + duration.toHours() + (duration.toHours() > 1 ? " heures" : " heure");
        } else if (duration.toMinutes() > 0) {
            // If the duration is greater than a minute
            return "il y a " + duration.toMinutes() + (duration.toMinutes() > 1 ? " minutes" : " minute");
        } else {
            // If the duration is less than a minute
            return "à l'instant";
        }
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
        if (postTextTextField != null) {
            postTextTextField.setText(publication.getContenu_publication());
        }
      //  User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "123");
        User loggedInUser = UserData.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            // Step 3: User is authenticated, proceed to retrieve photo
            String userPhotoUrl = loggedInUser.getPhoto();
            // Step 4: Check if the user has a valid photo URL
            if (userPhotoUrl != null && !userPhotoUrl.isEmpty()) {
                // Step 5: Load and display the user's photo
                Image userPhoto = new Image(userPhotoUrl);
                this.profileImage.setImage(userPhoto);
            } else {
                // Step 6: User does not have a valid photo URL
                System.out.println("L'utilisateur n'a pas d'URL de photo valide.");
                // Consider using a default photo or displaying a placeholder image
            }//
//

        }
        postImage.setImage(new Image(publication.getUrl_file()));

        // Set other UI elements as needed
        usernameLabel.setText(publication.getUser().getNom_user() + " " + publication.getUser().getPrenom_user());
        LocalDateTime publicationDateTime = publication.getD_creation_publication().toLocalDateTime();
        String timeElapsed = calculateTimeElapsed(publicationDateTime);
        dateLabel.setText(timeElapsed);
    }


    @FXML
    private void handleSaveUpdate(ActionEvent event) {
        if (publication != null) {
            // Get the updated caption
            String updatedCaption = postTextTextField.getText();

            // Update the publication
            publication.setContenu_publication(updatedCaption);

            // Update the publication in the database
            servicePublication.update(publication);

            System.out.println("Updated Caption: " + updatedCaption);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("La publication a été mise à jour avec succès!");
            alert.showAndWait();

            updated = true;
            Stage currentStage = (Stage) saveUpdate.getScene().getWindow();
            currentStage.close();



        }
    }

    private void onCloseRequest(WindowEvent event) {
        if (!updated) {
            // Show a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir partir sans mettre à jour la publication ?");

            // Get the user's response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                // If the user cancels, consume the event
                event.consume();
            }
        }
    }



}
