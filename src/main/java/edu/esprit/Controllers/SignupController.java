package edu.esprit.Controllers;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import edu.esprit.tests.MainFx;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;


public class SignupController {

    @FXML
    private TextField name;
    @FXML
    private TextField fname;
    @FXML
    private TextField region;
    @FXML
    private TextField numTel;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button submitbtn;



    public void submit() throws IOException {
        if(checkForm() && !checkExist()){
            registerUser();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Inscription réussie");
            alert.setHeaderText(null);
            alert.setContentText("Votre inscription a été effectuée avec succès, vous pouvez revenir à la page de connexion pour vous connecter");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                alert.close();
                MainFx m = new MainFx();
                m.changeScene("/Login.fxml");
            }
        }else if(checkExist()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email déjà utilisé");
            alert.setHeaderText(null);
            alert.setContentText("L'email que vous avez entré est déjà utilisé");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Formulaire Invalide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir correctement tous les champs");
            alert.showAndWait();

        }
    }

    private void registerUser(){
        ServiceUser su = new ServiceUser();
        User u = new User(name.getText(), fname.getText(), email.getText(), password.getText(), java.sql.Date.valueOf(birthDate.getValue()), region.getText().toString(), numTel.getText().toString(),"ayoub", "/Image/profile.png", "ROLE_USER");
        su.add(u);
    }

    private boolean checkForm(){
        return !name.getText().isEmpty() && !fname.getText().isEmpty()
                && name.getText().matches("[a-zA-Z]+") && fname.getText().matches("[a-zA-Z]+")
                && !email.getText().isEmpty() && email.getText().matches("[^@]+@[^@]+\\.[a-zA-Z]{2,}")
                && !password.getText().isEmpty() && !region.getText().isEmpty()
                && !numTel.getText().isEmpty() && numTel.getText().matches("\\d*")
                && birthDate.getValue() != null;
    }

    private boolean checkExist(){
        ServiceUser su = new ServiceUser();
        return su.checkEmail(email.getText());
    }




}
