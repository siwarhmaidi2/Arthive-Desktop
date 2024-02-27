package edu.esprit.Controllers;

import edu.esprit.entities.ReactionCommentaire;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceReactionCommentaire;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import edu.esprit.entities.Commentaire;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class getAllComments {


    @FXML
    private MenuItem updateComment;
    @FXML
    private MenuItem deleteComment;
    @FXML
    private VBox comment;

    @FXML
    private TextArea content;

    @FXML
    private Label date;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label username;

    @FXML
    private ImageView iconLikes;

    @FXML
    private Button likeComment;

    @FXML
    private Label nbLikesComments;

    @FXML
    private Button saveCommentButton;
//    @FXML
//    private VBox repliesContainer;
//
//    @FXML
//    private TextArea replyTextArea;
//
//    @FXML
//    private Button submitReplyButton;
    private Commentaire commentaire;

    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
    private ServiceReactionCommentaire serviceReactionCommentaire = new ServiceReactionCommentaire();

    private ServicePublication servicePublication = new ServicePublication();


    private ServiceUser serviceUser = new ServiceUser();

    int currentPublicationId = 37;

    @FXML
    void initialize() {

    }
    public void setData(Commentaire commentaire) {
        try {
            this.commentaire = commentaire;
            this.content.setText(commentaire.getContenuCommentaire());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(commentaire.getDateAjoutCommentaire());

            this.date.setText(formattedDate);
            if (commentaire.getUser() != null) {
                this.username.setText(commentaire.getUser().getNom_user() + " " + commentaire.getUser().getPrenom_user());
                // profileImage.setImage(commentaire.getUser().getPhoto_user());
            }
            updateLikeCountLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// add a like on a comment and remove it if it's already liked
    @FXML
    void handleLikeComment(ActionEvent event) {

        // Assuming you have a method to get the logged-in user
        User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "1234563");

        // Check if the user has already liked this comment
        if (!hasUserLikedComment(loggedInUser)) {
            // Create a new ReactionCommentaire representing the like
            ReactionCommentaire like = new ReactionCommentaire();
            like.setCommentaire(serviceCommentaire.getOneByID(commentaire.getIdCommentaire()));
            like.setUser(loggedInUser);


            like.setPublication(servicePublication.getOneByID(currentPublicationId));

            like.setDateAjoutReactionCommentaire(new java.sql.Timestamp(System.currentTimeMillis()));


            // Add the like using the ServiceReactionCommentaire
            serviceReactionCommentaire.add(like);

            // Update the like count label
            updateLikeCountLabel();
        } else {
            removeLike(loggedInUser);

            // Update the like count label
            updateLikeCountLabel();
        }
    }
    private void removeLike(User user) {
        // Find and remove the like from the database
        ReactionCommentaire likeToRemove = null;
        for (ReactionCommentaire reaction : serviceReactionCommentaire.getAll()) {
            if (reaction.getUser().equals(user) && reaction.getCommentaire().equals(commentaire)) {
                likeToRemove = reaction;
                break;
            }
        }

        if (likeToRemove != null) {
            // Remove the like using the ServiceReactionCommentaire
            serviceReactionCommentaire.delete(likeToRemove.getIdReactionCommentaire());
        }
    }
    private boolean hasUserLikedComment(User user) {
        // Check if the user has already liked this comment
        for (ReactionCommentaire reaction : serviceReactionCommentaire.getAll()) {
            if (reaction.getUser().equals(user) && reaction.getCommentaire().equals(commentaire)) {
                return true;
            }
        }
        return false;
    }

    private void updateLikeCountLabel() {
        if (commentaire == null) {
            nbLikesComments.setText("0"); // or handle it as appropriate
            return;
        }

        // Get the updated like count from the database
        int likeCount = serviceReactionCommentaire.getLikesCountForComment(commentaire.getIdCommentaire());

        // Update the like count label
        nbLikesComments.setText(String.valueOf(likeCount));
    }

    @FXML
    void handleEditComment(ActionEvent event) {
        // Enable editing in the TextArea

        User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "1234563");
        if (commentaire.getUser().equals(loggedInUser)) {
            content.setEditable(true);

            // Change the appearance to indicate editing is active
            content.setStyle("-fx-border-color: blue; -fx-border-width: 2px;");
            saveCommentButton.setVisible(true);
            // You can also change other styles as needed, such as background color or font style.
        }
    }

    @FXML
    void saveEditedComment(ActionEvent event) {
        // Disable editing in the TextArea
        content.setEditable(false);

        // Save the edited content to the database
        String editedContent = content.getText();
        commentaire.setContenuCommentaire(editedContent);
        serviceCommentaire.update(commentaire);

        // Change the appearance to indicate editing is complete
        content.setStyle(""); // Reset styles to default
        saveCommentButton.setVisible(false);
        // You may also want to update other UI elements or change the appearance to indicate that editing is complete.
    }


// edit and delete a comment

    @FXML
    void handleDeleteComment(ActionEvent event) {
        // Assuming you have a method to get the logged-in user
        User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "1234563");

        // Check if the logged-in user is the owner of the comment
        if (commentaire.getUser().equals(loggedInUser)) {
            // Display a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this comment?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Delete the comment from the database
                serviceCommentaire.delete(commentaire.getIdCommentaire());

                // Remove the comment from the UI
                removeCommentNode();

                updateCommentCountLabel(); // Update comment count label
            }
        } else {
            showAlert("You don't have permission to delete this comment.");
        }
    }

// Other methods...


    private void removeCommentNode() {
        // Access the parent VBox from the comment node and remove it
        VBox parentVBox = (VBox) comment.getParent();
        parentVBox.getChildren().remove(comment);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateCommentCountLabel() {
        // Assuming you have a method to update the comment count label
        int commentsCount = serviceCommentaire.getCommentsCountForPublication(commentaire.getPublication().getId_publication());
        // Assuming you have a label to display the comment count
        Label commentsLabel = new Label("(" + commentsCount + ")");
        commentsLabel.setId("commentsLabel");
    }
}

