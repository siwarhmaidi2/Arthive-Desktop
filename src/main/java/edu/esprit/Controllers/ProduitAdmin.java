package edu.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ProduitAdmin  extends AnchorPane {
    @FXML
    private ImageView produitImage;

    @FXML
    private ImageView image2;

    @FXML
    private Label name2;

    @FXML
    private Label publicationTime;

    @FXML
    private Label ismproduit;

    @FXML
    private Label prixProduit;

    @FXML
    private Label stockProduit;

    private int produitId;
    public int getProduitId() {
        return produitId;
    }


    public void setProduitId(int id) {
        this.produitId = id;
    }

    private String descriptionProduit;

    public void setDescriptionProduit(String descriptionProduit) {
        this.descriptionProduit = descriptionProduit;
    }

    // Méthode pour obtenir la description du produit
    public String getDescriptionProduit() {
        return descriptionProduit;
    }
    public ImageView getProduitImage() {
        return produitImage;
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

//    public void initialize() {
//        // Création d'un Tooltip avec la description du produit
//        Tooltip tooltip = new Tooltip(descriptionProduit);
//        // Définition du délai d'affichage du Tooltip en millisecondes
//        tooltip.setShowDelay(javafx.util.Duration.millis(100));
//        // Associer le Tooltip à l'élément AnchorPane
//        Tooltip.install(produitAnchorPane, tooltip);
//    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }



}
