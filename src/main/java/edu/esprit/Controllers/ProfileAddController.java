package edu.esprit.Controllers;

import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;
import edu.esprit.utils.CountryComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ProfileAddController {
    @FXML
    private TextField name;
    @FXML
    private TextField fname;
    @FXML
    private ComboBox<String> countries;
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
    private TextArea bio;
    @FXML
    private Button submit;

    private ServiceUser su = new ServiceUser();

    public void initialize() {
        CountryComboBox.populateCountriesComboBox(countries);
    }

    public void AddUser(ActionEvent event) throws IOException {
        if(checkForm() && !checkExist()){
            registerUser();
            closeAndUpdateProfilePage();
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
        User u = new User(name.getText(), fname.getText(), email.getText(), password.getText(), java.sql.Date.valueOf(birthDate.getValue()), countries.getValue().toString(), numTel.getText().toString(),"", "file:/C:/Users/ziedz/Downloads/arthive-client/src/main/resources/Image/profil.png", "ROLE_USER");
        su.add(u);
    }

    private boolean checkExist(){
        ServiceUser su = new ServiceUser();
        return su.checkEmail(email.getText());
    }


    private boolean checkForm() {
        return !name.getText().isEmpty() && !fname.getText().isEmpty()
                && name.getText().matches("[a-zA-Z]+") && fname.getText().matches("[a-zA-Z]+")
                && !email.getText().isEmpty() && email.getText().matches("[^@]+@[^@]+\\.[a-zA-Z]{2,}")
                && !password.getText().isEmpty() && !countries.getSelectionModel().isEmpty()
                && !numTel.getText().isEmpty() && numTel.getText().matches("\\d*")
                && birthDate.getValue() != null && isAgeValid(birthDate)
                && password.getText().length() >= 8 && passwordRepeat.getText().equals(password.getText());
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

    private void navigateOnPress(){
        countries.setOnKeyTyped(event -> {
            String typedText = event.getCharacter();
            if (typedText != null && !typedText.isEmpty()) {
                // Find the index of the first item starting with the typed text
                for (int i = 0; i < countries.getItems().size(); i++) {
                    String item = countries.getItems().get(i);
                    if (item.toLowerCase().startsWith(typedText.toLowerCase())) {
                        // Set the selected index to the found item
                        countries.getSelectionModel().select(i);
                        ComboBoxListViewSkin<?> skin = (ComboBoxListViewSkin<?>) countries.getSkin();
                        ListView<?> list = (ListView<?>) skin.getPopupContent();
                        list.scrollTo(i);
                        break;
                    }
                }
            }
        });
    }
    private void closeAndUpdateProfilePage() throws IOException {
        Stage stage = (Stage) submit.getScene().getWindow();
        stage.close();
    }




}
