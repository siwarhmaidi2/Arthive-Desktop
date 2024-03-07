package edu.esprit.Controllers;

import edu.esprit.entities.UserData;
import edu.esprit.tests.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;


import javafx.event.ActionEvent;

import java.io.IOException;

import edu.esprit.services.ServiceUser;
import edu.esprit.entities.User;
public class LoginController {

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button submitbtn;

    private ServiceUser su = new ServiceUser();
    private User loggedInUser;

    public void submit(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException {
        loggedInUser = su.authenticateUser(email.getText(), password.getText());
        Main m = new Main();
        if (loggedInUser != null && loggedInUser.getRole().equals("ROLE_USER")) {
            System.out.println("Login successful");
            UserData.getInstance().setLoggedInUser(loggedInUser);


            // Pass the logged-in user to the home page controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent homeRoot = loader.load();
            HomeController homeController = loader.getController();

            // Pass the logged-in user to the profile page controller
            FXMLLoader profileLoder = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent profileRoot = profileLoder.load();
            ProfileController profileController = profileLoder.getController();

            //redirect to home page, replace with actual Home page
            Main.changeScene("/Profile.fxml");
        }
        else{
            if(loggedInUser != null && loggedInUser.getRole().equals("ROLE_ADMIN")){
                UserData.getInstance().setLoggedInUser(loggedInUser);
                System.out.println(UserData.getInstance().getLoggedInUser());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/HomeAdmin.fxml"));
                Parent homeRoot = loader.load();
                HomeAdminController homeAdminController = loader.getController();
                Main.changeScene("/AdminResources/HomeAdmin.fxml");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password");
                alert.showAndWait();
                System.out.println("Login failed");
                //show error message
            }

        }
    }

    public void handleHyperlinkAction(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("/Signup.fxml");
    }
}
