package edu.esprit.Controllers;

import edu.esprit.entities.Publication;
import edu.esprit.entities.Reaction;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceReaction;
import edu.esprit.services.ServiceUser;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private Reaction reaction;

    private ServiceReaction serviceReaction = new ServiceReaction();
    private boolean userLiked = false; // Flag to track whether the user has already liked the publication



    private String calculateTimeElapsed(LocalDateTime publicationDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(publicationDateTime, currentDateTime);

        if (duration.toDays() > 0) {
            // Si la durée est supérieure à un jour
            return duration.toDays() + " jours";
        } else if (duration.toHours() > 0) {
            // Si la durée est supérieure à une heure
            return duration.toHours() + " heures";
        } else if (duration.toMinutes() > 0) {
            // Si la durée est supérieure à une minute
            return duration.toMinutes() + " minutes";
        } else {
            // Si la durée est inférieure à une minute
            return "à l'instant";
        }
    }

    public void setData(Publication publication) {
        try {
            User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "1234563");
            if (loggedInUser != null) {
                // Step 3: User is authenticated, proceed to retrieve photo
                String userPhotoUrl = loggedInUser.getPhoto_user();
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

            // Create a Tooltip with the publication content
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
        userLiked = serviceReaction.hasUserLiked(publicationId, 44);

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
            // Increase brightness/luminosity on mouse hover
            colorAdjust.setBrightness(0.1);
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
        int userId = 44; // Replace with the actual user ID
        int publicationId = publication.getId_publication();

        if (!userLiked) {
            // User is liking the publication
            serviceReaction.addLike(publicationId, userId);
            userLiked = true;

            // Change the heartIcon image to the "full heart" image
            heartIcon.setImage(new Image("/Image/fullheart.png"));

            // Play the like animation

        } else {
            // User is unliking the publication
            serviceReaction.removeLike(publicationId, userId);
            userLiked = false;

            // Change the heartIcon image to the "empty heart" image
            heartIcon.setImage(new Image("/Image/emptyHeart.png"));
        }
        updateLikesLabel(); // Update likes label
    }

/*
    @FXML
    void likeButtonClicked(MouseEvent event) {
        // Get the logged-in user
        ServiceUser serviceUser = new ServiceUser();
        User loggedInUser = serviceUser.getLoggedInUser();

        // Check if the user is logged in
        if (loggedInUser != null) {
            // Retrieve the current user ID
            int userId = 44;
            //loggedInUser.getId_user();

            // Assuming publication.getId_publication() retrieves the publication ID
            int publicationId = 28;
            //publication.getId_publication();

            // Call addLike with both publicationId and userId
            int newLikesCount = servicePublication.addLike(publicationId, userId);

            // Update the likes label
            this.likesLabel.setText(String.valueOf(newLikesCount));

            // Play the like animation
            //addLikeAnimation();
        } else {
            // Handle the case when the user is not logged in (optional)
            System.out.println("User not logged in. Cannot update likes.");
        }
    }

*/
   /* private void addLikeAnimation() {
        Duration duration = Duration.ofMillis(200);

        ScaleTransition scaleTransition = new ScaleTransition(duration, heartIcon);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setOnFinished(event -> {
            heartIcon.setScaleX(1);
            heartIcon.setScaleY(1);
        });
        scaleTransition.play();
    }
    */

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






}

