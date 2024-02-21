package edu.esprit.Controllers;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.entities.UserCommentCellFactory;
import edu.esprit.services.ServiceCommentaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.sql.Timestamp;

public class ajouterCommentaireController {

    @FXML
    private ListView<Commentaire> commentsListView;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Button addCommentButton;

    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();

    @FXML
    void addComment(ActionEvent event) {
        String commentContent = commentTextArea.getText();
        if (!commentContent.isEmpty()) {
            // Assuming you have a Publication and User instance
            // Replace 'yourPublicationInstance' and 'yourUserInstance' with actual instances

            Commentaire newComment = new Commentaire(
                    commentContent,
                    new Timestamp(System.currentTimeMillis()),null,null);
                   // new User(30,"John", "Doe", "john.doe@example.com", "password", Date.valueOf("1990-01-01"), "City", 123456789),
                  //  new Publication(17,"Test Content", "testfile.txt", new Timestamp(System.currentTimeMillis()),
                          //  new User(44,"John","Doe", "john.doe@example.com", "password", Date.valueOf("1990-01-01"), "City", 123456789))


            // Add the new comment to the database
            serviceCommentaire.add(newComment);

            // Refresh the comments list view
            refreshCommentsList();

            // Clear the text area after adding the comment
            commentTextArea.clear();
        }
    }

    // This method refreshes the comments list view with the latest comments
    private void refreshCommentsList() {
        commentsListView.getItems().clear();
        commentsListView.getItems().addAll(serviceCommentaire.getAll());
    }

    @FXML
    void initialize() {
        // Set up the comments list view cell factory
        commentsListView.setCellFactory(new UserCommentCellFactory());

        // Initialize the comments list view
        refreshCommentsList();
    }
}
