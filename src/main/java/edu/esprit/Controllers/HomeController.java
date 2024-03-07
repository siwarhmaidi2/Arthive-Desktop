package edu.esprit.Controllers;

import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServicePublication;
import edu.esprit.tests.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeController implements Initializable {
    @FXML
    private Hyperlink logoutBtn;
    @FXML
    private VBox messageBox;

    @FXML
    private ImageView messageImage;

    @FXML
    private Label messageLabel;
    @FXML
    private GridPane postGrid;
@FXML
private ImageView profileImage;
@FXML
private Hyperlink nom;
    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;
    private List<Publication> posts;

    private ServicePublication servicePublication = new ServicePublication(); // Initialize the servicePublication

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User loggedInUser = UserData.getInstance().getLoggedInUser();
        posts = new ArrayList<>(data());
        String path = loggedInUser.getPhoto();
        profileImage.setImage(new javafx.scene.image.Image(path));

        nom.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        nom.setFont(new Font("System Bold", 17.0));
        nom.setTextFill(Color.BLACK);

        refreshContent();
        searchButton.setOnAction(this::handleSearch);


    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchText = searchField.getText().trim();

        List<Publication> searchResults = performSearch(searchText);

        if (searchResults.isEmpty()) {
            // If the text is empty, i want to set an image shows that there is no result and a text says "No result found"


            try {
                // Load the image resource from the classpath

               messageImage.setImage(messageImage.getImage());
                messageLabel.setText(messageLabel.getText() + " \"" + searchText + "\"");
                postGrid.getChildren().clear();
                messageBox.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Update the grid with the search results
            updateGridWithSearchResults(searchResults);
            messageBox.setVisible(false);
        }
    }

    private List<Publication> performSearch(String searchText) {
        return servicePublication.searchPublications(searchText);
    }

    private void updateGridWithSearchResults(List<Publication> searchResults) {
        posts = searchResults;
        refreshGrid();
    }


        public void refreshContent() {
        posts = new ArrayList<>(data());
        posts.sort(Comparator.comparing(Publication::getD_creation_publication).reversed());

        refreshGrid();

    }

    private void refreshGrid() {
        postGrid.getChildren().clear();

        int columns = 0;
        int rows = 1; // Start at 1 to avoid the header

        for (int i = 0; i < posts.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/listerPublication.fxml"));
                VBox postBox = fxmlLoader.load();

                AfficherPublicationController controller = fxmlLoader.getController();
                controller.setData(posts.get(i));

                if (columns == 3) {
                    columns = 0;
                    rows++;
                }
                postGrid.add(postBox, columns++, rows);
                GridPane.setMargin(postBox, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Publication> data() {
        // Fetch data using servicePublication.getAll()
        return new ArrayList<>(servicePublication.getAll());
    }


    public void switchToAddPublication(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addPublication.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SwitchToProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent root = loader.load();
            //dont open new window
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }

    @FXML
    void switchToEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherEvent.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Evènements");
            stage.show();

            // Close the current window (Home)
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) throws IOException {
        UserData.getInstance().setLoggedInUser(null);
        Main.changeScene("/Login.fxml");
    }

}

