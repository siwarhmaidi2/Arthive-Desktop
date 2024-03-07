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



public class ProfileEditAdminController {

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

    private User user;
    private ServiceUser su = new ServiceUser();

    private ItemController itemController;

    private HomeAdminController homeAdminController;

    public void setHomeAdminController(HomeAdminController homeAdminController){
        this.homeAdminController = homeAdminController;
    }


    public void getItemControllerInEdit(ItemController itemController) {
        this.itemController = itemController;
    }


    public void initialize() {
        navigateOnPress();

        CountryComboBox.populateCountriesComboBox(countries);
    }
    public void initFields() {
        user = itemController.getUser();
        name.setText(user.getNom_user());
        fname.setText(user.getPrenom_user());
        countries.setValue(user.getVille());
        numTel.setText(user.getNum_tel_user());
        birthDate.setValue(user.getD_naissance_user().toLocalDate());
        bio.setText(user.getBio());
        email.setText(user.getEmail());
        System.out.println(user.getMdp_user());
    }

    public void updateProfile(ActionEvent event) throws IOException {
        if (checkForm()) {
            user.setNom_user(name.getText());
            user.setPrenom_user(fname.getText());
            user.setVille(countries.getValue());
            user.setNum_tel_user(numTel.getText());
            user.setD_naissance_user(java.sql.Date.valueOf(birthDate.getValue()));
            if(bio.getText() == null || bio.getText().isEmpty()) user.setBio("");
            user.setBio(bio.getText());
            if(password.getText().isEmpty()) {
                user.setMdp_user(user.getMdp_user());
            }
            su.updateProfileUser(user);
            closeAndUpdateProfilePage();

        } else if(checkExist()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email déjà utilisé");
            alert.setHeaderText(null);
            alert.setContentText("L'email que vous avez entré est déjà utilisé");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields correctly");
            alert.showAndWait();
        }
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


    private boolean checkForm() {
        return !name.getText().isEmpty() && !fname.getText().isEmpty()
                && name.getText().matches("[a-zA-Z]+") && fname.getText().matches("[a-zA-Z]+")
                && !countries.getSelectionModel().isEmpty()
                && !numTel.getText().isEmpty() && numTel.getText().matches("\\d*")
                && birthDate.getValue() != null && isAgeValid(birthDate);
    }

    private boolean checkExist(){
        ServiceUser su = new ServiceUser();
        System.out.println(user.getId_user());
        return su.checkEmailUserAdmin(this.user);
    }

    private void closeAndUpdateProfilePage() throws IOException {
        Stage stage = (Stage) submit.getScene().getWindow();

        //this.homeAdminController.reloadUsers(); //this feature isnt working homeAdminController is being passed as null despite
        stage.close();
    }



}
