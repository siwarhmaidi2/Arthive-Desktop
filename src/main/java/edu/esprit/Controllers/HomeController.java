package edu.esprit.Controllers;

import edu.esprit.entities.Publication;
import edu.esprit.services.ServicePublication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    @FXML
    private AfficherPublicationController afficherPublicationController;

    @FXML
    private GridPane postGrid;

    private List<Publication> posts;

    private ServicePublication servicePublication = new ServicePublication(); // Initialize the servicePublication

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        posts = new ArrayList<>(data());

        int columns = 0;
        int rows = 1;

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
}

