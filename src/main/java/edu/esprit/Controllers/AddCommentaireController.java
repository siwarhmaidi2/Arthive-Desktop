package edu.esprit.Controllers;

import edu.esprit.entities.*;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;



public class AddCommentaireController implements Initializable {

    @FXML
    private ImageView postImage;
    @FXML
    private ImageView profileImage;
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
    private Publication currentPublication;

    public void setCurrentPublication(Publication publication) {
        this.currentPublication = publication;
    }

    private static final User loggedInUser = UserData.getInstance().getLoggedInUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User loggedInUser = UserData.getInstance().getLoggedInUser();
        if (currentPublication != null) {
            updateCommentList();
        }
        if (loggedInUser != null) {
            // Step 3: User is authenticated, proceed to retrieve photo
            String userPhotoUrl = loggedInUser.getPhoto();
            // Step 5: Load and display the user's photo
            String userPath = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+userPhotoUrl;
            this.profileImage.setImage(new Image(userPath));
            }
        }

    public void setPublication(Publication publication) {
        this.currentPublication = publication;
        if (commentContainer != null) {
            updateCommentList();

            String postImage = currentPublication.getUrl_file();
                String postImageUrl = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+postImage;
                Image publicationImage = new Image(postImageUrl);
                this.postImage.setImage(publicationImage);

        }
    }
    private void updateCommentList() {

        comments = new ArrayList<>(data());

        commentContainer.getChildren().clear(); // Clear existing children

        // Filter comments based on the current publication ID
        List<Commentaire> publicationComments = comments.stream()
                .filter(comment -> comment.getPublication().getId_publication() == currentPublication.getId_publication())
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
        try {
            if (currentPublication == null) {
                showAlert("Veuillez sélectionner une publication avant d'ajouter un commentaire.");
                return;
            }

        String commentContent = contentComment.getText().trim(); // Trim to remove leading/trailing whitespaces

        if (commentContent.isEmpty()) {
            showAlert("Veuillez saisir un commentaire avant d'ajouter.");
            return; // Do not proceed with adding the comment if it's empty
        }

        // Assuming the publication ID is set before calling this method

        Commentaire newComment = new Commentaire( commentContent, new java.sql.Timestamp(System.currentTimeMillis()), loggedInUser, currentPublication);
        // Assuming you have a method to get the logged-in user
        serviceCommentaire.add(newComment);

        contentComment.clear();
        updateCommentList(); // Update the UI after adding a new comment

    }  catch (Exception e) {
            e.printStackTrace();
            showAlert("Une erreur s'est produite lors de l'ajout du commentaire.");
        }
        saveCommentaires();
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
        int commentsCount = serviceCommentaire.getCommentsCountForPublication(currentPublication.getId_publication());
        nbOfComments.setText("(" + commentsCount + ")");
    }

     // Initialize with a default value



    private void saveCommentaires() {
        Set<Commentaire> commentaireSet = serviceCommentaire.getAll();
        List<Commentaire> commentaires = new ArrayList<>(commentaireSet);
        // Get your list of publications
        CommentaireJsonSerializer.saveCommentaires(commentaires , "commentaires.json");
    }

    private void loadCommentaires() {
        List<Commentaire> commentaires = CommentaireJsonSerializer.loadCommentaires("commentaires.json");
        // Process loaded comments as needed
    }
}
