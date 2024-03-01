package edu.esprit.controllers;

import edu.esprit.tests.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import edu.esprit.services.ServiceUser;
import edu.esprit.entities.User;

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


    @FXML
    private Label nameLabel;
    @FXML
    private Label fnameLabel;
    @FXML
    private Label regionLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private  Label birthDateLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;


    public void initialize(){
        checkName();
        checkFname();
    }

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
                Main m = new Main();
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
        User u = new User(name.getText(), fname.getText(), email.getText(), password.getText(), java.sql.Date.valueOf(birthDate.getValue()), region.getText().toString(), numTel.getText().toString(),"", "file:/C:/Users/ziedz/Downloads/arthive-client/src/main/resources/Image/profil.png", "ROLE_USER");
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

    private void checkName(){
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                nameLabel.setText("Le nom ne doit pas être vide");
                nameLabel.setVisible(true);
                System.out.println("empty field");
            }
            else if (!newValue.matches("[a-zA-Z]+")) {
                nameLabel.setText("Le nom ne doit contenir que des lettres");
                nameLabel.setVisible(true);
            }
            else{
                nameLabel.setVisible(false);
            }
        });
    }

    private void checkFname(){
        fname.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                fnameLabel.setText("Le nom ne doit pas être vide");
                fnameLabel.setVisible(true);
                System.out.println("empty field");
            }
            else if (!newValue.matches("[a-zA-Z]+")) {
                fnameLabel.setText("Le nom ne doit contenir que des lettres");
                fnameLabel.setVisible(true);
            }
            else{
                fnameLabel.setVisible(false);
            }
        });
    }

    private void checkNumTel(){
        numTel.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                phoneLabel.setText("Le nom ne doit pas être vide");
                phoneLabel.setVisible(true);
                System.out.println("empty field");
            }
            else if (!newValue.matches("[a-zA-Z]+")) {
                phoneLabel.setText("Le nom ne doit contenir que des lettres");
                phoneLabel.setVisible(true);
            }
            else{
                phoneLabel.setVisible(false);
            }
        });
    }

    private boolean checkExist(){
        ServiceUser su = new ServiceUser();
        return su.checkEmail(email.getText());
    }




}
