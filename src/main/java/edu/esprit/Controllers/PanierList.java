package edu.esprit.Controllers;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PanierList extends Node implements Initializable {

    @FXML
    private ImageView imagePanier;

    @FXML
    private Label nomPanier;

    @FXML
    private Label prixPanier;

    @FXML
    private Spinner<Integer> quantite;

    public void setQuantite(int quantite) {
        this.quantite.getValueFactory().setValue(quantite);
    }


    public int getProduitId() {
        return produitId;
    }





    @FXML
    private Label alerte;

    private AjouterPanier ajouterPanierController;


    // Méthode pour initialiser la référence au contrôleur AjouterPanier
    public void setAjouterPanierController(AjouterPanier ajouterPanierController) {
        this.ajouterPanierController = ajouterPanierController;
    }

    private MarketPlace marketPlaceController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }


    private int totalProductsInCart;




    private int produitId;
    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }
    private int stockPanier ;

    public void setStockPanier(int stockPanier) {
        this.stockPanier = stockPanier;
    }



    public ImageView getImagePanier() {
        return imagePanier;
    }

    public Label getNomPanier() {
        return nomPanier;
    }

    public Label getPrixPanier() {
        return prixPanier;
    }


    public void setImagePanier(String imageUrl) {
        Image image = new Image(imageUrl);
        imagePanier.setImage(image);
    }


    public void setNomPanier(String nom) {
        nomPanier.setText(nom);
    }

    public void setPrixPanier(String prix) {
        prixPanier.setText(prix);
    }


    @FXML
    void supprimer(ActionEvent event) {
        ServiceUser serviceUser = new ServiceUser();
        ServiceProduit sp = new ServiceProduit();
       // User loggedInUser = serviceUser.authenticateUser("chams@gmail.com", "chams2000");

        User loggedInUser = UserData.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            ServicePanier servicePanier = new ServicePanier();
            Produit produit = sp.getOneByID(produitId);

            if (produit != null) {
                servicePanier.supprimerProduitDuPanier(loggedInUser, produit);
                // Rafraîchir l'affichage après la suppression
                ajouterPanierController.rafraichirAffichagePanier();

                // Vérifier si le contrôleur MarketPlace est initialisé avant d'appeler des méthodes sur lui
                if (marketPlaceController != null) {
                    marketPlaceController.decrementCounter();
                    totalProductsInCart = 0;
                } else {
                    System.out.println("MarketPlaceController is null, unable to decrement counter.");
                }
            }
        }
    }




    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

    ServicePanier servicePanier = new ServicePanier();
    ServiceUser serviceUser = new ServiceUser();
    ServiceProduit serviceProduit = new ServiceProduit();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        quantite.setValueFactory(valueFactory);
        // Récupérer l'utilisateur connecté
        ServiceUser serviceUser = new ServiceUser();
        User loggedInUser = UserData.getInstance().getLoggedInUser();
       // User loggedInUser = serviceUser.authenticateUser("chams@gmail.com", "chams2000");

        if (loggedInUser != null) {
            // Récupérer le produit associé à cet élément de panier
            ServiceProduit sp = new ServiceProduit();
            Produit produit = sp.getOneByID(produitId);

            if (produit != null) {
                // Récupérer la quantité de ce produit dans le panier
                ServicePanier servicePanier = new ServicePanier();
                long quantiteProduits = servicePanier.countProductInPanier(loggedInUser, produit);

                // Utiliser la quantité récupérée pour initialiser la valeur du Spinner
                valueFactory.setValue((int) quantiteProduits);
                quantite.setValueFactory(valueFactory);
            }
        }


    }





}


