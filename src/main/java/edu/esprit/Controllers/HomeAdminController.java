package edu.esprit.Controllers;

import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.Initializable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
public class HomeAdminController implements Initializable {

    @FXML
    private Button buttonPublication;
    @FXML
    private GridPane postGrid;

    private List<Publication> posts;

    private ServicePublication servicePublication = new ServicePublication();
    private ServiceUser serviceUser = new ServiceUser();


    private User loggedInUser = UserData.getInstance().getLoggedInUser();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInUser = UserData.getInstance().getLoggedInUser();

            posts = new ArrayList<>(data());
            refreshContent();

    }

    public void refreshContent() {
        posts = new ArrayList<>(data());

        refreshGrid();

    }

    private void refreshGrid() {
        postGrid.getChildren().clear();

        int columns = 0;
        int rows = 1; // Start at 1 to avoid the header

        for (int i = 0; i < posts.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PublicationAdmin.fxml"));
                VBox postBox = fxmlLoader.load();

                PublicationAdminController controller = fxmlLoader.getController();
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
        return new ArrayList<>(servicePublication.getAll());
    }


    //SwitchToFetchAllPublications
    public void switchToFetchAllPublications() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/HomeAdmin.fxml"));
        Parent root = loader.load();
        HomeAdminController controller = loader.getController();
        buttonPublication.getScene().setRoot(root);
    }

}