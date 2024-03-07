package edu.esprit.Controllers;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;


public class AfficherProduit implements Initializable {


    @FXML
    private ImageView art;

    @FXML
    private ImageView image2;

    @FXML
    private Button add;

    @FXML
    private Button annule;

    @FXML
    private Label imag;

    @FXML
    private GridPane gridList;

    @FXML
    private TextField searchField;


    @FXML
    private ImageView messageImage;

    @FXML
    private Label messageLabel;

    @FXML
    private AnchorPane messageBox;

    @FXML
    private Label name2;

    private ServiceUser serviceUser = new ServiceUser();
    private ServiceProduit serviceProduit = new ServiceProduit();

    private AfficherProduit afficherProduitController;
    private ProduitList produitListController;


    public void setAfficherProduitController(AfficherProduit afficherProduitController) {
        this.afficherProduitController = afficherProduitController;
    }

    private MarketPlace marketPlaceController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        afficherProduitsUtilisateurConnecte();
    }

    public void afficherProduitsUtilisateurConnecte() {
        gridList.getChildren().clear();
//        User loggedInUser = serviceUser.authenticateUser("chams@gmail.com", "chams2000");
        User loggedInUser = UserData.getInstance().getLoggedInUser();

        if (loggedInUser != null) {
            Set<Produit> produitsUtilisateur = serviceProduit.getProduitsByUser(loggedInUser);
            for (Produit produit : produitsUtilisateur) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitList.fxml"));
                    AnchorPane produitNode = loader.load();
                    ProduitList produitController = loader.getController();
                    produitController.setProduitNom("Nom : " + produit.getNom_produit());
                    produitController.setDescriptionProduit("Description : " + produit.getDescription_produit());
                    produitController.setPrixProduit(String.valueOf("Prix : " + produit.getPrix_produit() + " $"));
                    produitController.setStockProduit(String.valueOf("Stock : " + produit.getStock_produit()));
                    produitController.setPublicationTime(produit.getD_publication_produit().toString());
                    Image produitImage = new Image(produit.getImage_produit());
                    produitController.setProduitImage(produitImage);
                    produitController.setProduitId(produit.getId_produit());
                    //refrech AfficherProduit
                    produitController.setAfficherProduitController(this);
                    gridList.add(produitNode, 0, gridList.getRowCount());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ajoutProd(ActionEvent actionEvent) {
        try {
            // Charger la vue FXML de AjouterProduit
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre (stage)
            Stage stage = new Stage();
            stage.setScene(scene);

            // Afficher la fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void annuler(ActionEvent actionEvent) {
        Node sourceNode = (Node) actionEvent.getSource();
        Scene scene = sourceNode.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    public void setProduitListController(ProduitList produitListController) {
        this.produitListController = produitListController;
    }

    public void refreshProductList() {
        afficherProduitsUtilisateurConnecte();
        // Rafraîchir la liste des produits dans MarketPlace
        marketPlaceController.refreshProductList();
    }



    @FXML
    void rechercher(ActionEvent event) {

        String searchTerm = searchField.getText().toLowerCase();

        List<Produit> filteredProducts = filterProducts(searchTerm);
        if (filteredProducts.isEmpty()) {

            messageImage.setImage(new Image("/image/ay.png"));
            messageLabel.setText("Aucun événement trouvé pour le terme de recherche : " + searchTerm);
            messageBox.setVisible(true);
            gridList.getChildren().clear();
        } else {

            updateProductView(filteredProducts);
            messageBox.setVisible(false);
        }
    }

    private List<Produit> filterProducts(String searchTerm) {
        return serviceProduit.getAll().stream()
                .filter(produit -> productMatchesSearchTerm(produit, searchTerm))
                .collect(Collectors.toList());
    }

    private boolean productMatchesSearchTerm(Produit produit, String searchTerm) {
        // Modify this method based on how you want to perform the search
        return produit.getNom_produit().toLowerCase().contains(searchTerm)
                || produit.getCateg_produit().toString().toLowerCase().contains(searchTerm);
    }


    private void updateProductView(List<Produit> filteredProducts) {
        // Clear existing nodes in the GridPane
        gridList.getChildren().clear();

        // Update the GridPane with the filtered products
        for (Produit produit : filteredProducts) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitList.fxml"));
                AnchorPane produitNode = loader.load();
                ProduitList produitController = loader.getController();
                produitController.setAfficherProduitController(this);
                produitController.setProduitNom("Nom : " + produit.getNom_produit());
                produitController.setDescriptionProduit("Description : " + produit.getDescription_produit());
                produitController.setPrixProduit(String.valueOf("Prix : " + produit.getPrix_produit() + " $"));
                produitController.setStockProduit(String.valueOf("Stock : " + produit.getStock_produit()));
                produitController.setPublicationTime(produit.getD_publication_produit().toString());
                Image produitImage = new Image(produit.getImage_produit());
                produitController.setProduitImage(produitImage);
                produitController.setProduitId(produit.getId_produit());


                gridList.add(produitNode, 0, gridList.getRowCount());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}







