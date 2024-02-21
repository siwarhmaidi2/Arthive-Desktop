package edu.esprit.Controllers;

import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.time.Duration;
import java.time.LocalDateTime;

public class AfficherPublicationController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label commentsLabel;

    @FXML
    private Label likesLabel;

    @FXML
    private ImageView postImage;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label usernameLabel;

    private Publication publication;
    private ServicePublication servicePublication = new ServicePublication();
    private boolean userLiked = false; // Flag to track whether the user has already liked the publication

    private String calculateTimeElapsed(LocalDateTime publicationDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(publicationDateTime, currentDateTime);

        if (duration.toDays() > 0) {
            return duration.toDays() + " days ago";
        } else if (duration.toHours() > 0) {
            return duration.toHours() + " hours ago";
        } else if (duration.toMinutes() > 0) {
            return duration.toMinutes() + " minutes ago";
        } else {
            return "just now";
        }
    }

    public void setData(Publication publication) {
        try {
            // Load post image
            String postImageUrl = publication.getUrl_file();
            Image postImage = new Image(postImageUrl);
            this.postImage.setImage(postImage);

            // Set other data
            this.publication = publication;
            this.usernameLabel.setText(publication.getUser().getNom_user() + " " + publication.getUser().getPrenom_user());

            LocalDateTime publicationDateTime = publication.getD_creation_publication().toLocalDateTime();
            String timeElapsed = calculateTimeElapsed(publicationDateTime);
            this.dateLabel.setText(timeElapsed);
            //updateLikesLabel(); // Update likes label
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void likeButtonClicked(MouseEvent event) {
        // Assuming publication.getId_publication() retrieves the publication ID
        int publicationId = publication.getId_publication();

        // Call the static method to simulate adding a like
        int newLikesCount = ServicePublication.addLikeForTesting(publicationId);

        // Update the likes label with the simulated likes count
        this.likesLabel.setText(String.valueOf(newLikesCount));
    }

    /*
    @FXML
    void likeButtonClicked(MouseEvent event) {
        if (!userLiked) {
            // Assuming you have a method to handle like button click in your ServicePublication class
            // This method should add a like to the publication in the database
            // Example: servicePublication.addLike(publication.getId());

            // Update the likes label
            updateLikesLabel();

            // Set the flag to indicate that the user has liked the publication
            userLiked = true;
        }
    }

    // Method to update likes label
    private void updateLikesLabel() {
        // Get the logged-in user
        User loggedInUser = ServiceUser.getLoggedInUser();

        // Check if the user is logged in
        if (loggedInUser != null) {
            int userId = loggedInUser.getId_user(); // Retrieve the current user ID

            // Assuming publication.getId_publication() retrieves the publication ID
            int publicationId = publication.getId_publication();

            // Call addLike with both publicationId and userId
            int newLikesCount = servicePublication.addLike(publicationId, userId);

            this.likesLabel.setText(String.valueOf(newLikesCount));
        } else {
            // Handle the case when the user is not logged in (optional)
            System.out.println("User not logged in. Cannot update likes.");
        }
    }

*/
}
