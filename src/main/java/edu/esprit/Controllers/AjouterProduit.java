package edu.esprit.Controllers;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.enums.TypeCategorie;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class AjouterProduit implements Initializable {


    @FXML
    private TextField addname;

    @FXML
    private Spinner<Double> priceSpinner;

    @FXML
    private Spinner<Integer> stockSpinner;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView imageview;

    @FXML
    private ImageView arthive;

    @FXML
    private Button annule;


    @FXML
    private ComboBox<TypeCategorie> category;


    @FXML
    private Label userName;

    @FXML
    private ImageView avatar;

    @FXML
    private CheckBox dispo;

    @FXML
    private Button upload;
    @FXML
    private Button add;
    @FXML
    private Button deleteArt;

    @FXML
    private TextArea description;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private Label descriptionErrorLabel;

    @FXML
    private Label stockErrorLabel;

    private static final int LONGUEUR_MINIMUM = 4;

    private static final String MESSAGE_DISPO = "Veuillez cocher la case 'Disponible'";
    private static final String MESSAGE_SAISIE_CHIFFRES = "Saisissez uniquement des chiffres";

    private MarketPlace marketPlaceController;


    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Créer une SpinnerValueFactory distincte pour chaque Spinner
        SpinnerValueFactory<Double> priceFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0, 0.1);
        SpinnerValueFactory<Integer> stockFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);

        // Définir les SpinnerValueFactory pour chaque Spinner
        priceSpinner.setValueFactory(priceFactory);
        stockSpinner.setValueFactory(stockFactory);

        // Remplir le ChoiceBox avec les valeurs de l'énumération TypeCategorie
        ObservableList<TypeCategorie> categories = FXCollections.observableArrayList(TypeCategorie.values());
        category.setItems(categories);


        // Ajouter un validateur pour priceSpinner pour accepter uniquement des nombres
        priceSpinner.getEditor().addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String text = priceSpinner.getEditor().getText();
            if (!event.getCharacter().matches("[0-9.]") || (text.contains(".") && event.getCharacter().equals("."))) {
                event.consume();
            }
        });
        priceSpinner.setValueFactory(priceFactory);

        // Ajouter un validateur pour stockSpinner pour accepter uniquement des nombres
        TextFormatter<Integer> stockFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, c ->
                c.getControlNewText().matches("\\d*") ? c : null);
        stockSpinner.getEditor().setTextFormatter(stockFormatter);
        stockSpinner.setValueFactory(stockFactory);

        stockErrorLabel.setText(MESSAGE_DISPO);


        addname.textProperty().addListener((observable, oldValue, newValue) -> {
            validateTextField(addname, newValue, nameErrorLabel);
        });

        description.textProperty().addListener((observable, oldValue, newValue) -> {
            validateTextField(description, newValue, descriptionErrorLabel);
        });
    }


    @FXML
    boolean checkdispo(ActionEvent event) {
        boolean disponible = dispo.isSelected();
        stockSpinner.setDisable(!disponible); // Désactiver stockSpinner si disponible est faux

        if (!disponible) {
            // si "Disponible" n'est pas cochée
            stockErrorLabel.setText(MESSAGE_DISPO);
        } else {
            // Si la case "Disponible" est cochée
            stockErrorLabel.setText(MESSAGE_SAISIE_CHIFFRES);
        }

        return disponible;
    }

    @FXML
    void uploadArt(MouseEvent event) {
        // Créer un sélecteur de fichiers pour les images
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        // Filtrer les fichiers pour afficher uniquement les images
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(filter);

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Charger l'image sélectionnée dans l'interface utilisateur
        if (selectedFile != null) {
            // Vous pouvez implémenter le chargement de l'image dans un ImageView
            Image image = new Image(selectedFile.toURI().toString());
            System.out.println("Chemin de l'image sélectionnée : " + selectedFile.toURI().toString()); // Imprimer le chemin de l'image
            imageview.setImage(image);

            upload.setVisible(false);
            deleteArt.setVisible(true);
        }
    }

    @FXML
    void deleteArt(ActionEvent event) {

        // Réinitialiser l'image dans imageview à null
        imageview.setImage(null);

        // Rendre le bouton upload visible à nouveau
        upload.setVisible(true);
        deleteArt.setVisible(false);
    }


    @FXML
    public void canceladd(ActionEvent actionEvent) {
        // Afficher une alerte de confirmation pour confirmer l'annulation de l'ajout
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment annuler l'ajout du produit ?");
        Optional<ButtonType> result = alert.showAndWait();

        // Si l'utilisateur clique sur le bouton OK, fermer la fenêtre
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) annule.getScene().getWindow();
            stage.close();
        }
    }


    private ServiceProduit serviceProduit = new ServiceProduit();
    private ServiceUser serviceUser = new ServiceUser();

    @FXML
    void AjouterProduit(ActionEvent event) {

        String nomProduit = addname.getText();
        String descriptionProduit = description.getText();

        if (nomProduit.length() < LONGUEUR_MINIMUM || descriptionProduit.length() < LONGUEUR_MINIMUM) {
            // Créer un message d'alerte personnalisé en fonction du champ non valide
            String message = "";
            if (nomProduit.length() < LONGUEUR_MINIMUM) {
                message += "Le nom du produit est trop court. ";
            }
            if (descriptionProduit.length() < LONGUEUR_MINIMUM) {
                message += "La description du produit est trop courte. ";
            }

            // Afficher une alerte indiquant que les champs sont trop courts
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs non valides");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
            return; // Sortir de la méthode sans ajouter le produit
        }


        double prixProduit = priceSpinner.getValue();
        int stockProduit = stockSpinner.getValue();
        String urlImage = "";
        Image image = imageview.getImage();
        if (image != null) {
            urlImage = image.getUrl();
        }
        boolean disponibilite = dispo.isSelected();
        TypeCategorie categorieProduit = category.getValue();

        // Initialiser une chaîne pour stocker les champs manquants
        String champsManquants = "";

        // Vérifier chaque champ et ajouter à la chaîne de champs manquants le cas échéant
        boolean champRempli = false; // Variable pour suivre si au moins un champ a été rempli
        if (!nomProduit.isEmpty() || prixProduit > 0 || !descriptionProduit.isEmpty() || stockProduit > 0 || !urlImage.isEmpty() || disponibilite || categorieProduit != null) {
            champRempli = true; // Au moins un champ a été rempli
        }
        if (!champRempli) {
            // Afficher une alerte indiquant que tous les champs doivent être remplis
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs incomplets");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les champs afin d'ajouter le produit.");
            alert.showAndWait();
            return; // Sortir de la méthode car tous les champs ne sont pas remplis
        }

        if (nomProduit.isEmpty()) {
            champsManquants += "Nom du produit\n";
        }
        if (descriptionProduit.isEmpty()) {
            champsManquants += "Description\n";
        }
        if (urlImage.isEmpty()) {
            champsManquants += "Image\n";
        }
        if (categorieProduit == null) {
            champsManquants += "Catégorie\n";
        }

        // Vérifier si des champs sont manquants
        if (!champsManquants.isEmpty()) {
            // Afficher une alerte pour informer l'utilisateur de remplir tous les champs
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs incomplets");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les champs suivants :\n" + champsManquants);
            alert.showAndWait();
        } else {
            // Le reste du code pour ajouter le produit comme précédemment
            // Obtenez l'utilisateur actuellement connecté depuis votre service utilisateur
//            User loggedInUser = serviceUser.authenticateUser("chams@gmail.com", "chams2000");

            User loggedInUser = UserData.getInstance().getLoggedInUser();

            if (loggedInUser != null) {
                // Créer un nouvel objet Produit
                Produit produit = new Produit();
                produit.setUser(loggedInUser); // Associez l'utilisateur actuel au produit
                produit.setNom_produit(nomProduit);
                produit.setPrix_produit(prixProduit);
                produit.setDescription_produit(descriptionProduit);
                produit.setStock_produit(stockProduit);
                produit.setImage_produit(urlImage);
                produit.setDisponibilite(disponibilite);
                produit.setCateg_produit(categorieProduit);
                produit.setD_publication_produit(new Timestamp(System.currentTimeMillis()));

                // Appel de la méthode ajouter de votre service ServiceProduit
                serviceProduit.add(produit);

                // Rafraîchir la liste des produits dans MarketPlace
                marketPlaceController.refreshProductList();

                // Afficher une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Produit ajouté avec succès!");
                alert.showAndWait();
                // Fermer la fenêtre actuelle
                Stage stage = (Stage) addname.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Erreur: Utilisateur non trouvé.");
            }
        }
    }


    // Méthode pour valider le contenu d'un champ de texte
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


