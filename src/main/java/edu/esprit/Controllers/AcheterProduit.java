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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AcheterProduit implements Initializable {
    @FXML
    private ImageView art;

    @FXML
    private ImageView image2;

    @FXML
    private Label name2;

    @FXML
    private Label publicationTime;

    @FXML
    private Label descriptProduit;

    @FXML
    private Label nbrStock;

    @FXML
    private Label nomProduit;

    @FXML
    private Label prix;

    @FXML
    private ImageView imageProduit;
    private int produitId;

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    private MarketPlace marketPlaceController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }

    private AjouterPanier ajouterPanierController;

    // Méthode pour initialiser la référence au contrôleur AjouterPanier
    public void setAjouterPanierController(AjouterPanier ajouterPanierController) {
        this.ajouterPanierController = ajouterPanierController;
    }


    ServiceProduit sp = new ServiceProduit();


    public void initData(Image image, String userName, String publicationTime, String nomProduit, String prix, String nbrStock, String descriptProduit, int produitId) {
        this.imageProduit.setImage(image);
        this.name2.setText(userName);
        this.publicationTime.setText(publicationTime);
        this.nomProduit.setText(nomProduit);
        this.prix.setText(prix + " Par Pièce");
        this.nbrStock.setText(" Pièce(s) disponible : " + nbrStock);
        this.descriptProduit.setText(descriptProduit);
        this.produitId = produitId;

    }

    @FXML
    public void ajouterPanier(ActionEvent actionEvent) {
        ServiceUser serviceUser = new ServiceUser();
        ServicePanier servicePanier = new ServicePanier();
        ServiceProduit serviceProduit = new ServiceProduit(); // Ajoutez le service de produit
        //User loggedInUser = serviceUser.authenticateUser("chams@gmail.com", "chams2000");

        User loggedInUser = UserData.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            Produit produit = sp.getOneByID(produitId);

            if (produit != null) {
                servicePanier.ajouterAuPanier(loggedInUser, produit);

                // Mettre à jour le stock du produit
                int stockActuel = produit.getStock_produit();
                int quantiteAjoutee = 1; // Vous pouvez modifier cela en fonction de la quantité ajoutée
                int nouveauStock = stockActuel - quantiteAjoutee;
                produit.setStock_produit(nouveauStock);
                serviceProduit.update(produit); // Mettre à jour le stock dans la base de données

                if (marketPlaceController != null) {
                    marketPlaceController.updatePanierButton();
                    marketPlaceController.refreshProductList();
                } else {
                    System.out.println("marketPlaceController is null!");
                }
                marketPlaceController.incrementCounter();

                // Mettre à jour la valeur du spinner quantité correspondant au produit ajouté
                if (ajouterPanierController != null) {
                    ajouterPanierController.updateQuantiteSpinner(produitId);
                }
            }
        }
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
    }
}


