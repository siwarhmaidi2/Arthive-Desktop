package edu.esprit.Controllers;

import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServiceUser;
import edu.esprit.utils.CountryComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ProfileEditController {


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
    private TextArea bio;
    @FXML
    private Button submit;

    private User loggedInUser;
    private ServiceUser su = new ServiceUser();

    private ProfileController profileController;

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }

    public void initialize() throws IOException {

        loggedInUser = UserData.getInstance().getLoggedInUser();
        CountryComboBox.populateCountriesComboBox(countries);

        name.setText(loggedInUser.getNom_user());
        fname.setText(loggedInUser.getPrenom_user());
        countries.setValue(loggedInUser.getVille());
        numTel.setText(loggedInUser.getNum_tel_user());
        birthDate.setValue(loggedInUser.getD_naissance_user().toLocalDate());
        bio.setText(loggedInUser.getBio());


    }


    public void updateProfile(ActionEvent event) {
        if (checkForm()) {
            loggedInUser.setNom_user(name.getText());
            loggedInUser.setPrenom_user(fname.getText());
            loggedInUser.setVille(countries.getValue());
            loggedInUser.setNum_tel_user(numTel.getText());
            loggedInUser.setD_naissance_user(java.sql.Date.valueOf(birthDate.getValue()));
            loggedInUser.setBio(bio.getText());
            su.updateProfile(loggedInUser);
            UserData.getInstance().setLoggedInUser(loggedInUser);
            closeAndUpdateProfilePage();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields correctly");
            alert.showAndWait();
        }
    }



    private boolean checkForm() {
        return !name.getText().isEmpty() && !fname.getText().isEmpty()
                && name.getText().matches("[a-zA-Z]+") && fname.getText().matches("[a-zA-Z]+")
                && !countries.getSelectionModel().isEmpty()
                && !numTel.getText().isEmpty() && numTel.getText().matches("\\d*")
                && birthDate.getValue() != null && isAgeValid(birthDate);
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

    private void closeAndUpdateProfilePage() {
        Stage stage = (Stage) submit.getScene().getWindow();
        try{
            profileController.initialize();
        }catch(Exception e){
            e.printStackTrace();
        }

        stage.close();
    }

}
