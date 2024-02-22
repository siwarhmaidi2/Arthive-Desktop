package edu.esprit.Controllers;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddCommentaireController implements Initializable {

    @FXML
    private Button addComment;

    @FXML
    private TextArea contentComment;

    @FXML
    private VBox commentContainer;

    @FXML
    private Label nbOfComments;
    private Stage stage;
    private Scene scene;

    private Parent root;
    private final ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
    private final ServiceUser serviceUser = new ServiceUser();
    private final ServicePublication servicePublication = new ServicePublication();

    private List<Commentaire> comments;
    private int currentPublicationId = 37;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateCommentList();
    }
    public void setPublicationId(int publicationId) {
        this.currentPublicationId = publicationId;
    }

    private void updateCommentList() {

        comments = new ArrayList<>(data());

        commentContainer.getChildren().clear(); // Clear existing children

        // Filter comments based on the current publication ID
        List<Commentaire> publicationComments = comments.stream()
                .filter(comment -> comment.getPublication().getId_publication() == currentPublicationId)
                .collect(Collectors.toList());

        // Sort comments by the most recent ones
        publicationComments.sort(Comparator.comparing(Commentaire::getDateAjoutCommentaire).reversed());

        for (Commentaire comment : publicationComments) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/getallcomments.fxml"));
                VBox commentBox = fxmlLoader.load();

                getAllComments controller = fxmlLoader.getController();
                controller.setData(comment);


                commentContainer.getChildren().add(commentBox);

                // Adjust the spacing between comments
                VBox.setMargin(commentBox, new Insets(5, 0, 0, 0)); // You can adjust the top margin (5 in this case)
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        updateCommentCountLabel();
    }

    @FXML
    void handleAddComment() {
        String commentContent = contentComment.getText();

        // Assuming the publication ID is set before calling this method
        int currentPublicationId = 37;

        Commentaire newComment = new Commentaire(commentContent);
        newComment.setContenuCommentaire(commentContent);
        newComment.setDateAjoutCommentaire(new java.sql.Timestamp(System.currentTimeMillis()));
        newComment.setPublication(servicePublication.getOneByID(currentPublicationId));
        User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "1234563");
        newComment.setUser(loggedInUser); // Assuming you have a method to get the logged-in user
        serviceCommentaire.add(newComment);

        contentComment.clear();
        updateCommentList(); // Update the UI after adding a new comment
        showAlert("Commentaire ajouté !");
    }
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private List<Commentaire> data() {
        return new ArrayList<>(serviceCommentaire.getAll());
    }

    private void updateCommentCountLabel() {
        int commentsCount = serviceCommentaire.getCommentsCountForPublication(currentPublicationId);
        nbOfComments.setText("(" + commentsCount + ")");
    }

     // Initialize with a default value



}
