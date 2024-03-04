package edu.esprit.controllers;

import edu.esprit.tests.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import edu.esprit.services.ServiceUser;
import edu.esprit.entities.User;

import java.io.IOException;
import java.time.LocalDate;
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
    private PasswordField passwordRepeat;
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
    @FXML
    private Label passwordRepeatLabel;


    public void initialize(){
        checkName();
        checkFname();
        checkNumTel();
        checkRegion();
        checkBirthDate();
        checkEmail();
        checkPassword();
        checkPasswordRepeat();
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
                && birthDate.getValue() != null && isAgeValid(birthDate)
                && password.getText().length() >= 8 && passwordRepeat.getText().equals(password.getText());
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
                phoneLabel.setText("Le numero de Tel. ne doit pas être vide");
                phoneLabel.setVisible(true);
                System.out.println("empty field");
            }
            else if (!newValue.matches("\\d*")) {
                phoneLabel.setText("Le numero de Tel ne doit contenir que des chiffres");
                phoneLabel.setVisible(true);
            }
            else{
                phoneLabel.setVisible(false);
            }
        });
    }

    private void checkRegion(){
        region.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                regionLabel.setText("Le region ne doit pas être vide");
                regionLabel.setVisible(true);
                System.out.println("empty field");
            }
            else if (!newValue.matches("[a-zA-Z]+")) {
                regionLabel.setText("Le region ne doit contenir que des lettres");
                regionLabel.setVisible(true);
            }
            else{
                regionLabel.setVisible(false);
            }
        });
    }

    public static boolean isAgeValid(DatePicker birthDatePicker){
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = birthDatePicker.getValue();
        // Calculate age
        int age = currentDate.getYear() - birthDate.getYear();
        // Adjust age if birth date hasn't occurred yet this year
        if (birthDate.getMonthValue() > currentDate.getMonthValue() ||
                (birthDate.getMonthValue() == currentDate.getMonthValue() &&
                        birthDate.getDayOfMonth() > currentDate.getDayOfMonth())) {
            age--;
        }
        return age >= 18;

    }


    private void checkBirthDate(){
        birthDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null){
                birthDateLabel.setText("Veuillez selectionner votre date de naissance");
                birthDateLabel.setVisible(true);
                System.out.println("empty field");
            }
            else if(!isAgeValid(birthDate)){
                birthDateLabel.setText("Votre date de naissance doit etre supérieure à 18 ans");
                birthDateLabel.setVisible(true);
            }
            else{
                birthDateLabel.setVisible(false);
            }
        });
    }

    private void checkEmail(){
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                emailLabel.setText("L'email ne doit pas être vide");
                emailLabel.setVisible(true);
                System.out.println("empty field");
            }
            else if (!newValue.matches("[^@]+@[^@]+\\.[a-zA-Z]{2,}")) {
                emailLabel.setText("L'email n'est pas valide");
                emailLabel.setVisible(true);
            }
            else{
                emailLabel.setVisible(false);
            }
        });
    }

    private void checkPassword(){
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                passwordLabel.setText("Le mot de passe ne doit pas être vide");
                passwordLabel.setVisible(true);
                System.out.println("empty field");
            }
            else if (newValue.length() < 8) {
                passwordLabel.setText("Le mot de passe doit contenir au moins 8 caractères");
                passwordLabel.setVisible(true);
            }
            else{
                passwordLabel.setVisible(false);
            }
        });
    }

    private void checkPasswordRepeat(){
        passwordRepeat.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                passwordRepeatLabel.setText("Veuillez répéter votre mot de passe");
                passwordRepeatLabel.setVisible(true);
                System.out.println("empty field");
            }
            else if (!newValue.equals(password.getText())) {
                passwordRepeatLabel.setText("Les mots de passe ne correspondent pas");
                passwordRepeatLabel.setVisible(true);
            }
            else{
                passwordRepeatLabel.setVisible(false);
            }
        });
    }

    private boolean checkExist(){
        ServiceUser su = new ServiceUser();
        return su.checkEmail(email.getText());
    }




}
