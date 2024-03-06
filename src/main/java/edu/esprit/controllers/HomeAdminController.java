package edu.esprit.controllers;

import edu.esprit.entities.User;

import edu.esprit.entities.UserData;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Set;

public class HomeAdminController {

    @FXML
    Label name;
    @FXML
    ImageView photo;
    @FXML
    Label userCount;
    @FXML
    Label onlineUsersCount;
    @FXML
    Label productsCount;
    @FXML
    Label groupsCount;
    @FXML
    Label EventsCount;

    @FXML
    ScrollPane scrollPane;

    @FXML
    Button btnPublications;
    @FXML
    Button btnUsers;
    @FXML
    Button btnProducts;
    @FXML
    Button btnGroups;
    @FXML
    Button btnEvents;
    @FXML
    Button btnSettings;
    @FXML
    Button btnSignout;


    public void initialize() throws Exception{
        User loggedinUser = UserData.getInstance().getLoggedInUser();

        String pfpPath = loggedinUser.getPhoto();
        URI pfpUri = new URI(pfpPath);
        String filePath = Paths.get(pfpUri).toString();
        File file = new File(filePath);
        Image imgUser = new Image(file.toURI().toString());

        name.setText(loggedinUser.getNom_user() + " " + loggedinUser.getPrenom_user());
        photo.setImage(imgUser);

    }

    public void showUsers(ActionEvent event) throws IOException {
        VBox contentBox = new VBox();
        HBox userItemTitle = FXMLLoader.load(getClass().getResource("/Item.fxml"));
        contentBox.getChildren().add(userItemTitle);

        ServiceUser su = new ServiceUser();
        Set<User> users = su.getItems();

        // Access the controller of Item.fxml to set user data
        try {
            for (User user : users) {
                // Load the Item.fxml layout for each user
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Item.fxml"));
                HBox userItem = loader.load();

                VBox.setVgrow(userItem, Priority.ALWAYS); // Vertically expand HBox
                HBox.setHgrow(userItem, Priority.ALWAYS); // Horizontally expand HBox

                // Access the controller of Item.fxml to set user data
                ItemController itemController = loader.getController();
                itemController.setUserData(user);

                contentBox.getChildren().add(userItem);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        VBox.setVgrow(contentBox, Priority.ALWAYS);
        scrollPane.setContent(contentBox);



    }




}
