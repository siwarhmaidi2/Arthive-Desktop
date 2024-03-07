package edu.esprit.Controllers;

import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProduitPost extends AnchorPane {


    @FXML
    private AnchorPane produitPostAnchorPane;

    @FXML
    private ImageView produitImage;

    @FXML
    private ImageView image2;

    @FXML
    private Label ismproduit;

    @FXML
    private Label publicationTime;

    @FXML
    private Label prixProduit;

    @FXML
    private Label stockProduit;

    @FXML
    private Label name2;

    @FXML
    private Button acheterButton;


    private int produitId;

    public void setProduitId(int id) {
        this.produitId = id;
    }

    public int getProduitId() {
        return produitId;
    }

    private AjouterPanier ajouterPanierController;

    // Méthode pour initialiser la référence au contrôleur AjouterPanier
    public void setAjouterPanierController(AjouterPanier ajouterPanierController) {
        this.ajouterPanierController = ajouterPanierController;
    }


    private String descriptionProduit;

    public void setDescriptionProduit(String descriptionProduit) {
        this.descriptionProduit = descriptionProduit;
    }

    // Méthode pour obtenir la description du produit
    public String getDescriptionProduit() {
        return descriptionProduit;
    }

    private MarketPlace marketPlaceController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }
    public MarketPlace getMarketPlaceController() {
        return marketPlaceController;
    }

    // Add getters and setters for each field

    public ImageView getProduitImage() {
        return produitImage;
    }

    public Button getAcheterButton(){
        return acheterButton;
    }
    public void setAcheterButton(Button button){
        acheterButton.setDisable(true);
    }
    public void setProduitImage(Image image) {
        produitImage.setImage(image);
    }

    public ImageView getAvatarImage() {
        return image2;
    }

    public void setAvatarImage(Image image) {
        image2.setImage(image);
    }

    public Label getIsmproduit() {
        return  ismproduit;
    }

    public void setIsmproduit(String nom) {
        ismproduit.setText(nom);
    }

    public Label getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(String time) {
        publicationTime.setText(time);
    }

    public Label getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(String prix) {
        prixProduit.setText(String.valueOf(prix));
    }

    public Label getStockProduit() {
        return stockProduit;
    }

    public void setStockProduit(String stock) {
        stockProduit.setText(String.valueOf(stock));
    }

    public Label getNomUser() {
        return name2;
    }

    public void setNomUser(String nom) {
        name2.setText(nom);
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

    @FXML
    void acheter(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AcheterProduit.fxml"));
        Parent root;
        try {
            root = loader.load();
            AcheterProduit controller = loader.getController();


            MarketPlace marketPlaceController = getMarketPlaceController(); // Replace with your logic

            controller.setMarketPlaceController(marketPlaceController);


            controller.initData(produitImage.getImage(), name2.getText(), publicationTime.getText(), ismproduit.getText(), prixProduit.getText(), stockProduit.getText(), descriptionProduit , produitId);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public void initialize() {

        // Création d'un Tooltip avec la description du produit
        Tooltip tooltip = new Tooltip(descriptionProduit);
        // Définition du délai d'affichage du Tooltip en millisecondes
        tooltip.setShowDelay(javafx.util.Duration.millis(100));
        // Associer le Tooltip à l'élément AnchorPane
        Tooltip.install(produitPostAnchorPane, tooltip);
    }
}
