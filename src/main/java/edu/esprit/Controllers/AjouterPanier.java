package edu.esprit.Controllers;

import edu.esprit.entities.PanierState;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AjouterPanier implements Initializable {
    @FXML
     GridPane gridPanier;

    @FXML
    Label nbrProd;

    @FXML
     Label totalP;


    private double totalPrix = 0.0;

    private int totalProductsInCart;





    ServiceUser serviceUser = new ServiceUser();

    private MarketPlace marketPlaceController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }


    private PanierState panierState = PanierState.getInstance();
    public void setPanierState(PanierState panierState) {
        this.panierState = panierState;
    }
    @FXML
    public void passerCommande(ActionEvent actionEvent) {
        // Récupérer l'instance de la fenêtre actuelle
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Fermer la fenêtre actuelle
        stage.close();

        // Charger l'interface PasserCommande.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PasserCommande.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void supprimerPanier(ActionEvent event) {
        User loggedInUser = serviceUser.authenticateUser("chams@gmail.com", "chams2000");

        if (loggedInUser != null) {
            servicePanier.supprimerPanierUtilisateur(loggedInUser);
            rafraichirAffichagePanier();
            System.out.println("Le panier a été supprimé avec succès !");
            if (marketPlaceController != null) {
                marketPlaceController.refreshProductList();
                marketPlaceController.updateCounter(0); // Mettez à jour le compteur avec 0 car le panier est maintenant vide
            }
        } else {
            System.out.println("Utilisateur non connecté !");
        }

    }

    ServicePanier servicePanier = new ServicePanier();
    private void afficherProduitsDansPanier() {
        // Effacer le contenu actuel du GridPane
        gridPanier.getChildren().clear();

//        User loggedInUser = serviceUser.authenticateUser("chams@gmail.com", "chams2000");

        User loggedInUser = UserData.getInstance().getLoggedInUser();

        if (loggedInUser != null) {
            Set<Produit> produitsDansPanier = servicePanier.getProduitsDansPanierUtilisateur(loggedInUser);
            int rowIndex = 0;

            double totalPrix = 0.0;
            totalProductsInCart = produitsDansPanier.size();


            for (Produit produit : produitsDansPanier) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierList.fxml"));
                    Node produitNode = loader.load();
                    PanierList panierListController = loader.getController();

                    panierListController.setMarketPlaceController(marketPlaceController);

                    // Mettre à jour les attributs du contrôleur PanierList
                    panierListController.setProduitId(produit.getId_produit());
                    panierListController.setStockPanier(produit.getStock_produit());
                    panierListController.setImagePanier(produit.getImage_produit());
                    panierListController.setNomPanier(produit.getNom_produit());
                    panierListController.setPrixPanier(String.valueOf("Prix : " +produit.getPrix_produit()) + " $" );

                    long quantite = servicePanier.countProductInPanier(loggedInUser, produit);
                    panierListController.setQuantite((int) quantite);


                    panierListController.setAjouterPanierController(this);
                    // Ajouter le PanierList au GridPane
                    gridPanier.add(produitNode, 0, rowIndex++);

                    totalPrix += produit.getPrix_produit() * quantite;


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (totalP != null) {
                totalP.setText( "Total : " + String.valueOf(totalPrix) + " $");
            }

            if (marketPlaceController != null) {
                marketPlaceController.updateCounter(produitsDansPanier.size());
            }
        }

    }




    @FXML
    public void rafraichirAffichagePanier() {
        if (marketPlaceController != null) {
            afficherProduitsDansPanier();
            marketPlaceController.updateCounter(totalProductsInCart);
        } else {
            System.out.println("MarketPlaceController is null, unable to refresh cart display.");
            // Charger l'interface MarketPlace.fxml pour obtenir le contrôleur
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketPlace.fxml"));
            try {
                Parent root = loader.load();
                marketPlaceController = loader.getController();
                if (marketPlaceController != null) {
                    // Appeler les méthodes nécessaires sur le contrôleur MarketPlace
                    afficherProduitsDansPanier();
                    marketPlaceController.updateCounter(totalProductsInCart);
                } else {
                    System.out.println("Unable to retrieve MarketPlaceController.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateQuantiteSpinner(int produitId) {
        ServiceProduit serviceProduit = new ServiceProduit();
        if (serviceUser != null) {
            User loggedInUser = serviceUser.authenticateUser("chams@gmail.com", "chams2000");
            if (loggedInUser != null) {
                // Récupérer le produit correspondant à l'identifiant
                Produit produit = serviceProduit.getOneByID(produitId); // Assurez-vous d'avoir une méthode similaire dans votre service Produit

                if (produit != null) {
                    for (Node node : gridPanier.getChildren()) {
                        if (node instanceof PanierList) {
                            PanierList panierList = (PanierList) node;
                            if (panierList.getProduitId() == produitId) {
                                // Récupérer la quantité de produits dans le panier
                                int quantiteProduit = (int) servicePanier.countProductInPanier(loggedInUser, produit);
                                // Mettre à jour la valeur du spinner quantité
                                panierList.setQuantite(quantiteProduit);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherProduitsDansPanier();
    }
}




