package edu.esprit.Controllers;

import edu.esprit.entities.Commentaire;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddCommentaireController implements Initializable {

    @FXML
    private Button addComment;

    @FXML
    private TextArea contentComment;

    @FXML
    private VBox commentContainer;

    @FXML
    private Label nbOfComments;

    private final ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
    private final ServiceUser serviceUser = new ServiceUser();
    private final ServicePublication servicePublication = new ServicePublication();

    private List<Commentaire> comments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCommentList();
    }
    private void updateCommentList() {
        comments = new ArrayList<>(data());

        commentContainer.getChildren().clear(); // Clear existing children

        for (Commentaire comment : comments) {
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
        int currentPublicationId = 28;
        int currentUserId = 44;

        Commentaire newComment = new Commentaire(commentContent);
        newComment.setContenuCommentaire(commentContent);
        newComment.setDateAjoutCommentaire(new java.sql.Timestamp(System.currentTimeMillis()));
        newComment.setPublication(servicePublication.getOneByID(currentPublicationId));
        newComment.setUser(serviceUser.getOneByID(currentUserId));

        serviceCommentaire.add(newComment);

        contentComment.clear();
        updateCommentList(); // Update the UI after adding a new comment
    }

    private List<Commentaire> data() {
        return new ArrayList<>(serviceCommentaire.getAll());
    }

    private void updateCommentCountLabel() {
        int commentsCount = serviceCommentaire.getAll().size();
        nbOfComments.setText("(" + commentsCount + ")");
    }
}
