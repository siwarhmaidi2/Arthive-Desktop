package edu.esprit.controllers;

import edu.esprit.entities.User;

import edu.esprit.entities.UserData;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
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
    Label productsCount;
    @FXML
    Label groupsCount;
    @FXML
    Label EventsCount;

    @FXML
    ScrollPane scrollPane;

    @FXML
    Button addBtn;

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



    public HomeAdminController getHomeAdminController() {
        return this;
    }
    public void initialize() throws Exception{
        addBtn.setVisible(false);
        User loggedinUser = UserData.getInstance().getLoggedInUser();

        String pfpPath = loggedinUser.getPhoto();
        URI pfpUri = new URI(pfpPath);
        String filePath = Paths.get(pfpUri).toString();
        File file = new File(filePath);
        Image imgUser = new Image(file.toURI().toString());

        name.setText(loggedinUser.getNom_user() + " " + loggedinUser.getPrenom_user());
        photo.setImage(imgUser);

        ServiceUser su = new ServiceUser();
        userCount.setText(String.valueOf(su.getTotalUsers()));

        //Load Admin Home into Edit controller to refresh list on submit (NOT WORKING)
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/AdminResources/EditProfileAdmin.fxml"));
            Parent editProfileRoot = loader2.load();
            ProfileEditAdminController profileEditAdminController = loader2.getController();
            profileEditAdminController.setHomeAdminController(this.getHomeAdminController());
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void showUsers(ActionEvent event) throws IOException {
        addBtn.setVisible(true);
        GridPane contentBox = new GridPane();




        GridPane userItemTitle = FXMLLoader.load(getClass().getResource("/AdminResources/Item.fxml"));
        // Hide the buttons in userItemTitle
        for (Node node : userItemTitle.getChildren()) {
            if (node instanceof Button) {
                node.setVisible(false);
            }
        }

        userItemTitle.setAlignment(Pos.TOP_LEFT);
        contentBox.setAlignment(Pos.TOP_LEFT);



        contentBox.getChildren().add(userItemTitle);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        ServiceUser su = new ServiceUser();
        Set<User> users = su.getItems();

        // Access the controller of Item.fxml to set user data
        try {
            int row = 1;
            for (User user : users) {
                // Load the Item.fxml layout for each user
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/Item.fxml"));
                GridPane userItem = loader.load();


                userItem.setAlignment(Pos.TOP_LEFT);

                // Access the controller of Item.fxml to set user data
                ItemController itemController = loader.getController();
                itemController.setUserData(user);

                contentBox.getChildren().add(userItem);
                GridPane.setRowIndex(userItem,row);
                row++;

            }

            scrollPane.setContent(contentBox);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public void addUser(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/AddProfileAdmin.fxml"));
            Parent addProfileRoot = loader.load();

            Stage newStage = new Stage();
            Scene newScene = new Scene(addProfileRoot, 600, 400);
            newStage.setScene(newScene);
            newStage.setTitle("Add Profile");
            newStage.show();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void reloadUsers() throws IOException{
        // Reload users...
        showUsers(null); // You might need to pass an ActionEvent if needed
    }




}
