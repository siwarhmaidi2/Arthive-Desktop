package edu.esprit.Controllers;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.enums.TypeCategorie;
import edu.esprit.services.ServiceProduit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class ModifierProduit implements Initializable {

    @FXML
    private ImageView imageMod;

    @FXML
    private TextField nomModifier;

    @FXML
    private Spinner<Double> priceSpinnerMod;

    @FXML
    private TextArea descripModifier;

    @FXML
    private Spinner<Integer> stockSpinnerMod;

    @FXML
    private ImageView img;

    @FXML
    private ImageView art;

    @FXML
    private Label name2;

    @FXML
    private ImageView image2;

    @FXML
    private CheckBox dispoModifier;

    @FXML
    private ComboBox<TypeCategorie> categoryModifier;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private Label descriptionErrorLabel;

    private static final int LONGUEUR_MINIMUM = 4;

    private static final String MESSAGE_DISPO = "Veuillez cocher la case 'Disponible'";
    private static final String MESSAGE_SAISIE_CHIFFRES = "Saisissez uniquement des chiffres";

    private int produitId;
    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    private MarketPlace marketPlaceController;
    private AfficherProduit afficherProduitController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }

    public void setAfficherProduitController(AfficherProduit afficherProduitController) {
        this.afficherProduitController = afficherProduitController;
    }

    private ProduitList produitListController;

    public void setProduitListController(ProduitList produitListController) {
        this.produitListController = produitListController;
    }

    @FXML
    private Label stockErrorLabel;


    @FXML
    void annulerMod(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Annuler la modification");
        alert.setContentText("Êtes-vous sûr d'annuler la modification ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Scene scene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        }
    }

    @FXML
    boolean checkdispo(ActionEvent event) {
        boolean disponible = dispoModifier.isSelected();
        stockSpinnerMod.setDisable(!disponible);

        if (!disponible) {
            stockErrorLabel.setText(MESSAGE_DISPO);
        } else {
            stockErrorLabel.setText(MESSAGE_SAISIE_CHIFFRES);
        }
        return disponible;

    }

    @FXML
    void enregistrer(ActionEvent event) {
        ServiceProduit serviceProduit = new ServiceProduit();
        Produit produit = serviceProduit.getOneByID(produitId);
        if (produit != null) {
            produit.setNom_produit(nomModifier.getText());
            produit.setDescription_produit(descripModifier.getText());
            produit.setPrix_produit(priceSpinnerMod.getValue());
            produit.setStock_produit(stockSpinnerMod.getValue());
            produit.setCateg_produit(categoryModifier.getValue());
            serviceProduit.update(produit);
            System.out.println("Modification du produit enregistrée !");

            // Afficher une alerte de succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Modification réussie");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Le produit a été modifié avec succès.");
            successAlert.showAndWait();

            if (afficherProduitController != null) {
                afficherProduitController.refreshProductList();
            }
            if (marketPlaceController != null) {
                marketPlaceController.refreshProductList();
            }
            // Fermer la fenêtre de modification
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Produit non trouvé !");
        }
    }



    public void recuprerInfoProduitSelectionner(int produitId) {
        // Récupérer les détails du produit à modifier
        ServiceProduit serviceProduit = new ServiceProduit();
        Produit produit = serviceProduit.getOneByID(produitId);
        Image produitImage = new Image(produit.getImage_produit());
        imageMod.setImage(produitImage);
        nomModifier.setText(produit.getNom_produit());
        descripModifier.setText(produit.getDescription_produit());
        priceSpinnerMod.getValueFactory().setValue(produit.getPrix_produit());
        stockSpinnerMod.getValueFactory().setValue(produit.getStock_produit());
        categoryModifier.setValue(produit.getCateg_produit());
        dispoModifier.setSelected(produit.isDisponibilite());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User loggedInUser = UserData.getInstance().getLoggedInUser();

        if (loggedInUser != null) {
            // Step 3: User is authenticated, proceed to retrieve photo
            String userPhotoUrl = loggedInUser.getPhoto();
            // Step 4: Check if the user has a valid photo URL
            if (userPhotoUrl != null && !userPhotoUrl.isEmpty()) {
                // Step 5: Load and display the user's photo
                Image userPhoto = new Image(userPhotoUrl);
                this.image2.setImage(userPhoto);
            } else {
                // Step 6: User does not have a valid photo URL
                System.out.println("User does not have a valid photo URL.");
                // Consider using a default photo or displaying a placeholder image
            }//
            name2.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());}


        SpinnerValueFactory<Double> priceFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0, 0.1);
        SpinnerValueFactory<Integer> stockFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);

        // Définir les SpinnerValueFactory pour chaque Spinner
        priceSpinnerMod.setValueFactory(priceFactory);
        stockSpinnerMod.setValueFactory(stockFactory);

        // Remplir le ChoiceBox avec les valeurs de l'énumération TypeCategorie
        ObservableList<TypeCategorie> categories = FXCollections.observableArrayList(TypeCategorie.values());
        categoryModifier.setItems(categories);

        // Ajouter un validateur pour priceSpinner pour accepter uniquement des nombres
        priceSpinnerMod.getEditor().addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String text = priceSpinnerMod.getEditor().getText();
            if (!event.getCharacter().matches("[0-9.]") || (text.contains(".") && event.getCharacter().equals("."))) {
                event.consume();
            }
        });

        priceSpinnerMod.setValueFactory(priceFactory);

        // Ajouter un validateur pour stockSpinner pour accepter uniquement des nombres
        TextFormatter<Integer> stockFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, c ->
                c.getControlNewText().matches("\\d*") ? c : null);
        stockSpinnerMod.getEditor().setTextFormatter(stockFormatter);
        stockSpinnerMod.setValueFactory(stockFactory);

        // Lier l'état initial de dispoModifier avec stockSpinnerMod
        stockSpinnerMod.setDisable(!dispoModifier.isSelected());

        // Lier l'état initial de stockSpinnerMod avec dispoModifier
        if (stockSpinnerMod.isDisabled()) {
            stockErrorLabel.setText(MESSAGE_DISPO);
        } else {
            stockErrorLabel.setText(MESSAGE_SAISIE_CHIFFRES);
        }

        // Lier l'état de la case à cocher dispoModifier avec stockSpinnerMod
        dispoModifier.selectedProperty().addListener((observable, oldValue, newValue) -> {
            stockSpinnerMod.setDisable(!newValue);
            if (!newValue) {
                stockSpinnerMod.getValueFactory().setValue(0); // Si décoché, stock automatiquement à 0
                stockErrorLabel.setText(MESSAGE_DISPO);
            } else {
                stockErrorLabel.setText(MESSAGE_SAISIE_CHIFFRES);
            }
        });

        // Lier l'état de stockSpinnerMod avec dispoModifier
        stockSpinnerMod.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue > 0) {
                dispoModifier.setSelected(true); // Si stock supérieur à 0, coche la case
            } else {
                dispoModifier.setSelected(false); // Sinon, décoche la case
            }
        });

        // Ajouter un validateur pour le champ de texte nomModifier
        nomModifier.textProperty().addListener((observable, oldValue, newValue) -> {
            validateTextField(nomModifier, newValue, nameErrorLabel);
        });

        // Ajouter un validateur pour la zone de texte descripModifier
        descripModifier.textProperty().addListener((observable, oldValue, newValue) -> {
            validateTextField(descripModifier, newValue, descriptionErrorLabel);
        });

    }

    private void validateTextField(TextField textField, String value, Label errorLabel) {
        // Si la longueur de la valeur est inférieure à LONGUEUR_MINIMUM
        if (value.length() < LONGUEUR_MINIMUM) {
            textField.setStyle("-fx-border-color: red;");
            errorLabel.setText("Le champ est trop court.");
            errorLabel.setVisible(true);
        } else {
            // Réinitialiser le style à son état par défaut
            textField.setStyle("");
            errorLabel.setVisible(false);
        }
    }

    // Méthode pour valider le contenu d'une zone de texte
    private void validateTextField(TextArea textArea, String value, Label errorLabel) {
        // Si la longueur de la valeur est inférieure à LONGUEUR_MINIMUM
        if (value.length() < LONGUEUR_MINIMUM) {
            textArea.setStyle("-fx-border-color: red;");
            errorLabel.setText("Le champ est trop court.");
            errorLabel.setVisible(true);
        } else {
            // Réinitialiser le style à son état par défaut
            textArea.setStyle("");
            errorLabel.setVisible(false);
        }
    }
}
