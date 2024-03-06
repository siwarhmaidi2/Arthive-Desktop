package edu.esprit.Controllers;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import edu.esprit.entities.Publication;
import edu.esprit.entities.Reaction;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceReaction;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class AfficherPublicationController {
    @FXML
    private ImageView share;
    @FXML
    private MenuItem signaler;
    @FXML
    private Label commentsLabel;

    @FXML
    private Label dateLabel;
    @FXML
    private ImageView heartIcon;

    @FXML
    private Label likesLabel;

    @FXML
    private ImageView postImage;

    @FXML
    private Label postTextLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label usernameLabel;
    private Stage stage;
    private Scene scene;

    private Parent root;
    private Map<Integer, Label> publicationLikesLabels = new HashMap<>();
    private Publication publication;

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    private ServicePublication servicePublication = new ServicePublication();
    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
    private ServiceUser serviceUser = new ServiceUser();

    private static final User loggedInUser = UserData.getInstance().getLoggedInUser();


    private Reaction reaction;

    private ServiceReaction serviceReaction = new ServiceReaction();
    private boolean userLiked = false; // Flag to track whether the user has already liked the publication


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


    public void setData(Publication publication) {
        try {
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
                    System.out.println("User does not have a valid photo URL.");
                    // Consider using a default photo or displaying a placeholder image
                }//
//

            }
            // Load post image
            String postImageUrl = publication.getUrl_file();
            Image postImage = new Image(postImageUrl);
            this.postImage.setImage(postImage);
            // Set an empty text for postTextLabel initially
            this.postTextLabel.setText("");

            //  Create a Tooltip with the publication content
            Tooltip tooltip = new Tooltip(publication.getContenu_publication());

            // Attach the Tooltip to the postTextLabel
            Tooltip.install(postTextLabel, tooltip);

            // Add a mouse entered event to update and show the text on mouse hover
            postTextLabel.setOnMouseEntered(event -> {
                postTextLabel.setText(publication.getContenu_publication());
                applyLuminosityEffect(true);

            });

            // Add a mouse exited event to hide the text when the mouse leaves
            postTextLabel.setOnMouseExited(event -> {
                postTextLabel.setText("");
                applyLuminosityEffect(false);
            });
            Font boldFont = Font.font(postTextLabel.getFont().getFamily(), FontWeight.BOLD, postTextLabel.getFont().getSize());
            postTextLabel.setFont(boldFont);
            this.publication = publication;
            this.usernameLabel.setText(publication.getUser().getNom_user() + " " + publication.getUser().getPrenom_user());

            LocalDateTime publicationDateTime = publication.getD_creation_publication().toLocalDateTime();
            String timeElapsed = calculateTimeElapsed(publicationDateTime);
            this.dateLabel.setText(timeElapsed);

            if (!publicationLikesLabels.containsKey(publication.getId_publication())) {
                Label likesLabel = new Label(String.valueOf(servicePublication.getCurrentLikesCount(publication.getId_publication())));
                publicationLikesLabels.put(publication.getId_publication(), likesLabel);

                // Add the likesLabel to the HBox (heartIcon container)
                HBox heartIconContainer = (HBox) heartIcon.getParent();
                heartIconContainer.getChildren().add(likesLabel);
            } else {
                // Update likes label with the actual likes count
                Label likesLabel = publicationLikesLabels.get(publication.getId_publication());
                int likesCount = servicePublication.getCurrentLikesCount(publication.getId_publication());
                likesLabel.setText(String.valueOf(likesCount));
            }
            updateCommentCountLabel(publication.getId_publication());
            //updateLikesLabel(); // Update likes label
        } catch (Exception e) {
            e.printStackTrace();
        }
        int publicationId = publication.getId_publication();
        userLiked = serviceReaction.hasUserLiked(publicationId, loggedInUser.getId_user());

        // Update the heartIcon based on the like status
        if (userLiked) {
            // User has liked the publication
            heartIcon.setImage(new Image("/Image/fullheart.png"));
        } else {
            // User has not liked the publication
            heartIcon.setImage(new Image("/Image/emptyHeart.png"));
        }
    }

    private void applyLuminosityEffect(boolean onHover) {
        ColorAdjust colorAdjust = new ColorAdjust();

        if (onHover) {
            // Reduce brightness, increase contrast, and desaturate the image on mouse hover
            colorAdjust.setBrightness(-0.5);


        }

        postImage.setEffect(colorAdjust);
    }

    private void updateCommentCountLabel(int publicationId) {
        int commentsCount = serviceCommentaire.getCommentsCountForPublication(publicationId);
        commentsLabel.setText("(" + commentsCount + ")");
    }

    private void updateLikesLabel() {
        int likesCount = servicePublication.getCurrentLikesCount(publication.getId_publication());
        Label likesLabel = publicationLikesLabels.get(publication.getId_publication());

        if (likesLabel != null) {
            likesLabel.setText(String.valueOf(likesCount));
        }
    }


    @FXML
    void likeButtonClicked(MouseEvent event) {

        int publicationId = publication.getId_publication();

        if (!userLiked) {
            // User is liking the publication
            serviceReaction.addLike(publicationId, loggedInUser.getId_user());
            userLiked = true;

            // Change the heartIcon image to the "full heart" image
            heartIcon.setImage(new Image("/Image/fullheart.png"));

            // Play the like animation

        } else {
            // User is unliking the publication
            serviceReaction.removeLike(publicationId, loggedInUser.getId_user());
            userLiked = false;

            // Change the heartIcon image to the "empty heart" image
            heartIcon.setImage(new Image("/Image/emptyHeart.png"));
        }
        updateLikesLabel(); // Update likes label
    }

    @FXML
    public void switchToCommentAdd(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addCommentairefinal - Copie.fxml"));
            root = loader.load();

            // Pass the publication to the Commentaire Controller
            AddCommentaireController addCommentaireController = loader.getController();
            addCommentaireController.setPublication(publication);

            Stage stage = new Stage();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSignalerClicked(ActionEvent event) {
        // Get the currently logged-in user (assuming you have a method to retrieve it)
        if (loggedInUser != null) {
            // Call the serviceUser method to report the publication
            serviceUser.reportPublication(loggedInUser.getId_user(), publication.getId_publication());

            // Display a confirmation message or update UI as needed
            System.out.println("Publication reported by user: " + loggedInUser.getId_user());
        } else {
            // Handle the case where the user is not logged in
            System.out.println("User not logged in. Cannot report publication.");
        }
    }

    @FXML
    void onShareClicked(MouseEvent event) {
        // Your Facebook page access token
        String accessToken = "EAAaRcAqBaygBO7bKLhHoFUpmfFLOZBqgkJT4YFqVPKTbFnK9lgo1ZBPqYkabrz8wx5OmZCh41ajF95LF0Fn9acgOQvdKIm3HF6OoMOyMh0OpfgJBZAmoZB6OykKhZAFG03zf4o3RZCSNkzY4ZBrs2Srps57WCS7NlJ2YFShTn3miSZCIPifZBec1mQLU6pgNOZCvZBZCMh6IVnZCCxMdP4WElmB0xlv0NM67BVeD5RXkUUpZBOjyZAWVcKD6gKvMsyPRVn94ZAh4ZD";

        // Create a Facebook client
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_11_0);

        // Message to be posted on Facebook
        String message = "Check out this publication on my JavaFX project!";

        // URL of the image to be shared (replace with your image URL)
        //i want to share the image of the publication
        String imageUrl = publication.getUrl_file();


//        String imageUrl = "URL_OF_YOUR_IMAGE";

        // Create a FacebookType object to represent the result of the post
        FacebookType response = facebookClient.publish("me/feed", FacebookType.class,
                com.restfb.Parameter.with("message", message),
                com.restfb.Parameter.with("link", imageUrl));

        // Log the post ID to check if the post was successful
        System.out.println("Post ID: " + response.getId());
    }

}
