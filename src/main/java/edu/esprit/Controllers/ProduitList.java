package edu.esprit.Controllers;

import edu.esprit.services.ServiceProduit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ProduitList extends Node {

    @FXML
    private ImageView produitImage;
    @FXML
    private Label timePublication;

    @FXML
    private Label produitNom;

    @FXML
    private Label descriptionProduit;

    @FXML
    private Label prixProduit;

    @FXML
    private Label stockProduit;


    private int produitId;

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }


    private AfficherProduit afficherProduitController;

    public void setAfficherProduitController(AfficherProduit afficherProduitController) {
        this.afficherProduitController = afficherProduitController;
    }


    private MarketPlace marketPlaceController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }



    public ImageView getProduitImage() {
        return produitImage;
    }

    public Label getTimePublication() {
        return timePublication;
    }

    public Label getProduitNom() {
        return produitNom;
    }

    public Label getDescriptionProduit() {
        return descriptionProduit;
    }

    public Label getPrixProduit() {
        return prixProduit;
    }

    public Label getStockProduit() {
        return stockProduit;
    }

    public void setProduitImage(Image image) {
        produitImage.setImage(image);
    }

    public void setProduitNom(String nom) {
        produitNom.setText(nom);
    }

    public void setDescriptionProduit(String description) {
        descriptionProduit.setText(description);
    }

    public void setPrixProduit(String prix) {
        prixProduit.setText(prix);
    }

    public void setStockProduit(String stock) {
        stockProduit.setText(stock);
    }

    public void setPublicationTime(String time) {
        timePublication.setText(time);
    }
//    @FXML
//    void modifierProdit(ActionEvent event) {
//
//    }


    private ServiceProduit serviceProduit = new ServiceProduit();



    @FXML
    void supprimerProduit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce produit ?");
        alert.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            serviceProduit.delete(produitId);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Suppression réussie");
            successAlert.setHeaderText("Le produit a été supprimé avec succès.");
            successAlert.showAndWait();

            // Rafraîchir la liste des produits dans AfficherProduit
            if (afficherProduitController != null) {
                afficherProduitController.refreshProductList();
            }

            // Rafraîchir la liste des produits dans MarketPlace
            if (marketPlaceController != null) {
                marketPlaceController.refreshProductList();
            }
        }

    }



    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }


    @FXML
    public void modifierProduit(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierProduit.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierProduit controller = loader.getController();
            controller.setProduitId(produitId);
            controller.setProduitListController(this);
            controller.recuprerInfoProduitSelectionner(produitId);
            if (afficherProduitController != null) {
                afficherProduitController.refreshProductList();
            }
            if (marketPlaceController != null) {
                marketPlaceController.refreshProductList();
            }

            Stage stage = new Stage();
            stage.setTitle("Modifier Produit");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
}
}
