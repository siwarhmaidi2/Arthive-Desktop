package edu.esprit.Controllers;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import edu.esprit.entities.Commentaire;

import java.text.SimpleDateFormat;
import java.util.Optional;

public class getAllComments {

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

    private Commentaire commentaire;

    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
    private ServiceUser serviceUser = new ServiceUser();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleEditComment(ActionEvent event) {
        // Assuming you have a method to get the logged-in user
        User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "1234563");

        // Check if the logged-in user is the owner of the comment
        if (commentaire.getUser().equals(loggedInUser)) {
            // Enable editing for the TextArea
            content.setEditable(true);
        } else {
            showAlert("You don't have permission to edit this comment.");
        }
    }
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

