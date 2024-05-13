package edu.esprit.Controllers;




import edu.esprit.entities.Groupe;

import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServiceGroupe;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeGroupe implements Initializable {
    @FXML
    private ImageView profileImage;
    @FXML
    private Hyperlink nom;

    @FXML
    private GridPane postGrid;

    @FXML
    private Button addPost;
    @FXML
    private Circle circle;

    private List<Groupe> groups;
    private Groupe selectedGroup;

    public GridPane getPostGrid() {
        return postGrid;
    }

    public void setPostGrid(GridPane postGrid) {
        this.postGrid = postGrid;
    }

    public List<Groupe> getGroups() {
        return groups;
    }

    public void setGroups(List<Groupe> groups) {
        this.groups = groups;
    }

    public Groupe getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Groupe selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Button getReclamationBut() {
        return ReclamationBut;
    }

    public void setReclamationBut(Button reclamationBut) {
        ReclamationBut = reclamationBut;
    }

    @FXML
    private Button ReclamationBut;

    ServiceGroupe SG= new ServiceGroupe();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User loggedInUser = UserData.getInstance().getLoggedInUser();
        groups = new ArrayList<>(data());
        String path = loggedInUser.getPhoto();
        String userImageUrl = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+path;
        Image userImage = new Image(userImageUrl);
        profileImage.setImage(userImage);
        nom.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        nom.setFont(new Font("System Bold", 17.0));
        nom.setTextFill(Color.BLACK);
        refreshGrid();

    }

    public void refreshContent() {
        groups = new ArrayList<>(data());
        refreshGrid();
    }

    private void refreshGrid() {
        postGrid.getChildren().clear();

        int columns = 0;
        int rows = 1; // Start at 1 to avoid the header

        for (int i = 0; i < groups.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/VueGroupe.fxml"));
                AnchorPane postBox = fxmlLoader.load();

                VueGroupe controller = fxmlLoader.getController();
                controller.setdata(groups.get(i));


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


    private List<Groupe> data() {
        // Fetch data using servicePublication.getAll()
        return new ArrayList<>(SG.getAll());
    }

    private void initialize() {
//        // Attacher un gestionnaire d'événements au bouton ReclamationBut
//        ReclamationBut.setOnAction(event -> {
//            try {
//                // Charger le fichier FXML de la page de réclamation
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("reclamation.fxml"));
//                Parent root = loader.load();
//
//                // Créer une nouvelle scène avec la page de réclamation
//                Scene scene = new Scene(root);
//
//                // Obtenir la fenêtre principale et la mettre à jour avec la nouvelle scène
//                Stage stage = (Stage) ReclamationBut.getScene().getWindow();
//                stage.setScene(scene);
//                stage.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }

    public void switchToReclamation(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToAddPublication(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterGroupe.fxml"));
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

    // switch to market

    @FXML
    void switchtomarketplace(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketPlace.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("MarketPlace");
            stage.show();

            // Close the current window (Home)
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.close();
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
    public void SwitchToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) postGrid.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void SwitchToGroups(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeGroupe.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("MarketPlace");
            stage.show();
            // Close the current window (Home)
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
