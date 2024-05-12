package edu.esprit.Controllers;

import edu.esprit.entities.UserData;
import edu.esprit.services.CrudEvent;
import edu.esprit.services.ServiceUser;
import edu.esprit.entities.Event;
import edu.esprit.entities.User;
import edu.esprit.enums.CategorieEvenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.w3c.dom.events.MouseEvent;


import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.paint.Color;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;



public class AjouterEvent implements Initializable {


    @FXML
    private TextField titreField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField lieuField;

    @FXML
    private Button ajouterEventButton; // Renommé pour éviter la confusion avec le fx:id
    @FXML
    private File selectedFile;
    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<CategorieEvenement> category;
    private final CrudEvent crudEvent = new CrudEvent();
    private final ServiceUser serviceUser = new ServiceUser();
    private static final User loggedInUser = UserData.getInstance().getLoggedInUser();


    private Timestamp convertDatePickerToTimestamp(DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            LocalDateTime localDateTime = datePicker.getValue().atStartOfDay();
            return Timestamp.valueOf(localDateTime);
        }
        return null;
    }
    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Utilisez le chemin absolu du fichier sélectionné
            String absoluteImagePath = selectedFile.toURI().toString();
            Image image = new Image(absoluteImagePath);
            // Affichez l'image dans l'ImageView
            imageView.setImage(image);
            // Vous pouvez afficher le chemin du fichier sélectionné ici si nécessaire
            System.out.println("Chemin de l'image sélectionnée : " + absoluteImagePath);
            System.out.println("Selected Image: " + selectedFile.getPath());
        } else {
            // User canceled file selection
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Image selection canceled.");
            alert.showAndWait();
        }
    }
    @FXML
    private void ajouterEvent(ActionEvent event) {
        if (!validateInput()) {
            return; // Validation failed, do not proceed with adding event
        }
        // Récupérez les valeurs des champs
        String titre = titreField.getText();
        Timestamp dateDebut = convertDatePickerToTimestamp(dateDebutPicker);
        Timestamp dateFin = convertDatePickerToTimestamp(dateFinPicker);
        String description = descriptionArea.getText();
        String lieu = lieuField.getText();

        CategorieEvenement categorieEvenement = category.getValue();
        String fileName = selectedFile.getName();
            Event eventt = new Event(titre,dateDebut,dateFin,description,lieu,loggedInUser,fileName, category.getValue());
            crudEvent.add(eventt);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("succès");
            alert.setHeaderText(null);
            alert.setContentText("Événement ajouté avec succès!");
            alert.showAndWait();

            Stage stage = (Stage) titreField.getScene().getWindow();
            stage.setTitle(" Ajouter évènement");
            stage.close();

    }

    private boolean validateInput() {
        // Validate each input field and show appropriate alert if validation fails
        if (titreField.getText().isEmpty() || dateDebutPicker.getValue() == null || dateFinPicker.getValue() == null
                || descriptionArea.getText().isEmpty() || lieuField.getText().isEmpty() || imageView.getImage() == null) {
            showAlert("Veuillez compléter tous les champs.");
            //highlightFieldError(titreField, "Le titre ne doit pas contenir de chiffres."); // Highlight the field in red

            return false;
        }
        if (dateDebutPicker.getValue() != null && dateFinPicker.getValue() != null
                && dateFinPicker.getValue().isBefore(dateDebutPicker.getValue())) {
            showAlert("La date de fin ne peut pas être antérieure à la date de début.");
            return false;
        }


        if (dateDebutPicker != null && dateDebutPicker.getValue().isBefore(LocalDate.now())) {
            showAlert( "Date de debut invalide.");
            return false;
        }
        // Validation : Assurez-vous que la date de fin n'est pas antérieure à la date de début
        if (dateFinPicker != null && dateDebutPicker != null && dateFinPicker.getValue().isBefore(LocalDate.now())) {
            showAlert("Date de fin invalide.");
            return false;
        }


        if (titreField.getText().length() > 30) {
            showAlert("Le titre ne doit pas dépasser 30 caractères.");
           // highlightFieldError(titreField, "Le titre ne depasse pas 30 lettres."); // Highlight the field in red
            return false;
        }

        if (descriptionArea.getText().length() > 1000) {
            showAlert("La description ne doit pas dépasser 1000 caractères.");
            return false;

        }
        if (containsDigits(titreField.getText())) {
            showAlert("Le titre ne peut pas contenir de chiffres.");
            //highlightFieldError(titreField, "Le titre ne doit pas contenir de chiffres.");

            return false;
        }

        return true; // All validations passed
    }
    // Helper method to highlight the input field in red
    private void highlightFieldError(TextField field, String errorMessage) {
        field.setStyle("-fx-border-color: red;");
        showAlert(errorMessage);

        field.setOnKeyTyped(event -> {
            // Remove the red border color when the user starts typing again
            field.setStyle("");
            field.setOnKeyTyped(null);
        });
    }
    private boolean containsDigits(String s) {
        // Check if the given string contains any digits
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
        
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add event handler to titreField to allow only letters
        titreField.setOnKeyTyped(event -> {
            char inputChar = event.getCharacter().charAt(0);

            // Check if the typed character is a letter
            if (!Character.isLetter(inputChar)) {
                event.consume(); // Consume the event if a non-letter character is typed
            }
        });

        // Add text length validation for titreField
        titreField.setTextFormatter(new TextFormatter<String>((Change c) -> {
            if (c.isAdded() && c.getControlNewText().length() > 30) {
                return null; // Reject the change if length exceeds 15 characters
            }
            return c;
        }));

        // Add text length validation for descriptionArea
        descriptionArea.setTextFormatter(new TextFormatter<String>((Change c) -> {
            if (c.isAdded() && c.getControlNewText().length() > 1000) {
                return null; // Reject the change if length exceeds 1000 characters
            }
            return c;
        }));

        dateFinPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (dateDebutPicker.getValue() != null && newValue != null &&
                    newValue.isBefore(dateDebutPicker.getValue())) {
                showAlert("La date de fin ne peut pas être antérieure à la date de début.");
                dateFinPicker.setValue(oldValue); // Revert to the old value
            }
        });

        dateDebutPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (dateFinPicker.getValue() != null && newValue != null &&
                    newValue.isAfter(dateFinPicker.getValue())) {
                showAlert("La date de début ne peut pas être postérieure à la date de fin.");
                dateDebutPicker.setValue(oldValue); // Revert to the old value
            }
        });



        ObservableList<CategorieEvenement> categories = FXCollections.observableArrayList(CategorieEvenement.values());
        category.setItems(categories);

    }

    }




