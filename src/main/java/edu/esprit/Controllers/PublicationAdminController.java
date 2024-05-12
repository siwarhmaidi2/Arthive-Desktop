package edu.esprit.Controllers;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PublicationAdminController {


    @FXML
    private MenuItem updatePost;
    @FXML
    private MenuItem deletePost;
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

            // User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "123");
            User loggedInUser = UserData.getInstance().getLoggedInUser();
            if (loggedInUser != null) {
                // Step 3: User is authenticated, proceed to retrieve photo
                String userPhoto = loggedInUser.getPhoto();
                String userPhotoUrl = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+userPhoto;
                Image userImage = new Image(userPhotoUrl);
                this.profileImage.setImage(userImage);
            }
            String postImageFileName = publication.getUrl_file();
            // Assuming your images are stored in a specific directory, construct the full URL
            String postImageUrl = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+postImageFileName;
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


//    @FXML
//    public void switchToUpdatePost(ActionEvent event) {
//        try {
//            // Load the FXML file
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierPost.fxml"));
//            Parent root = loader.load();
//
//            // Create a new stage
//            Stage newStage = new Stage();
//
//            // Get the controller from the loader
//            ModifierPublicationController controller = loader.getController();
//
//            // Set the new stage in the ModifierPublicationController
//            controller.setStage(newStage);
//
//            // Set the publication before initializing the controller
//            controller.setPublication(publication);
//
//            // Create a scene
//            Scene scene = new Scene(root);
//
//            // Set up the new stage
//            newStage.setTitle("Update Post");
//            newStage.setScene(scene);
//
//            // Show the new stage
//            newStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private PublicationAdminController publicationAdminController;

    public void setPublicationAdminController(PublicationAdminController publicationAdminController) {
        this.publicationAdminController = publicationAdminController;
    }

    private ProfileController profileController;

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }

    @FXML
    private void deletePublication(ActionEvent event) {
        // Show a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this publication?");

        // Get the user's response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed deletion, proceed with deletion
            servicePublication.delete(publication.getId_publication());

            // Remove the post from the UI immediately
            removePostFromUI();

            // Check if profilePublicationsController is not null before calling refreshPosts

        }
    }

    private void removePostFromUI() {
        // Remove the post from the UI immediately
        Node postBox = postTextLabel.getParent();
        if (postBox instanceof VBox) {
            VBox parentVBox = (VBox) postBox.getParent();
            parentVBox.getChildren().remove(postBox);
        }
    }



    private HomeAdminController homeAdminPublication;

    public void setHomeAdminController(HomeAdminController homeAdminController) {
        this.homeAdminPublication = homeAdminController;
    }
}