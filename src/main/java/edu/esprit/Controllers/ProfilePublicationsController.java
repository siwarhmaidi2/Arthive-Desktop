package edu.esprit.Controllers;

import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class ProfilePublicationsController implements Initializable {

    @FXML
    private GridPane postGrid;

    private List<Publication> posts;

    private ServicePublication servicePublication = new ServicePublication();
    private ServiceUser serviceUser = new ServiceUser();
    User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "1234563");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User loggedInUser = serviceUser.authenticateUser("ayoubtoujani808@gmail.com", "1234563");
        posts = new ArrayList<>(data(loggedInUser));
        refreshPostsUI();
    }

    public void refreshPosts() {
        // Fetch and display new posts
        posts = new ArrayList<>(data(loggedInUser));
        refreshPostsUI();
    }

    private void refreshPostsUI() {
        postGrid.getChildren().clear(); // Clear existing posts in the GridPane

        int columns = 0;
        int rows = 1;

        for (int i = 0; i < posts.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/myPublications.fxml"));
                VBox postBox = fxmlLoader.load();

                MyPublicationsController controller = fxmlLoader.getController();
                controller.setData(posts.get(i));
                controller.setProfilePublicationsController(this);

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

    private List<Publication> data(User user) {
        return new ArrayList<>(servicePublication.getAllPublicationsByIdUser(user.getId_user()));
    }

    public void SwitchToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) postGrid.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
