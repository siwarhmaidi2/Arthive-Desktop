package edu.esprit.Controllers;


import edu.esprit.entities.PanierState;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.entities.UserData;
import edu.esprit.enums.TypeCategorie;
import edu.esprit.services.ServiceProduit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MarketPlace implements Initializable {
    @FXML
    private GridPane produitGrid;

    @FXML
    private Label incrementer;
    private int count = 0;
    @FXML
    private Button btnPanier;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView messageImage;

    @FXML
    private Label messageLabel;

    @FXML
    private AnchorPane messageBox;
    @FXML
    private ComboBox<TypeCategorie> categorie;

    @FXML
    private Slider filtragePrix;
    @FXML
    private ProgressBar progress;

    @FXML
    private TextField prixMax;

    private List<Produit> produitsList;



    private PanierState panierState = PanierState.getInstance();
    public void setPanierState(PanierState panierState) {
        this.panierState = panierState;
    }


    private MarketPlace marketPlaceController;
    private PanierList panierListController;


    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }

    private AcheterProduit acheterProduitController;
    public void setAcheterProduitController(AcheterProduit acheterProduitController) {
        this.acheterProduitController = acheterProduitController;
    }

    public void setPanierListController(PanierList panierListController) {
        this.panierListController = panierListController;
    }

    private AjouterPanier ajouterPanierController;
    public void setAjouterPanierController(AjouterPanier ajouterPanierController) {
        this.ajouterPanierController = ajouterPanierController;
    }

    private static User loggedInUser = UserData.getInstance().getLoggedInUser();


//    private Home homeController;
//
//    public void setHome(Home homeController) {
//        this.homeController = homeController;
//    }

    private HomeController homeController;
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    @FXML
    void afficherliste(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduit.fxml"));
            Parent root = loader.load();
            AfficherProduit afficherProduitController = loader.getController();
            afficherProduitController.setMarketPlaceController(this);
            afficherProduitController.refreshProductList();// Rafraîchir la liste des produits

            Stage stage = new Stage();
            stage.setTitle("Votre List Produit");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ServiceProduit serviceProduit = new ServiceProduit();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User loggedInUser = UserData.getInstance().getLoggedInUser();
        produitsList = new ArrayList<>();
        afficherProduits();
        updatePanierButton();
        updateCounter();


        incrementer.setText("(" + PanierState.getInstance().getItemCount() + ")");
        btnPanier.setStyle(PanierState.getInstance().getButtonColor());


        // Remplir le ChoiceBox avec les valeurs de l'énumération TypeCategorie
        ObservableList<TypeCategorie> categories = FXCollections.observableArrayList(TypeCategorie.values());
        categorie.setItems(categories);
        categorie.setOnAction(this::filterByCategory);

        double minPrice = produitsList.stream().mapToDouble(Produit::getPrix_produit).min().orElse(0);
        double maxPrice = produitsList.stream().mapToDouble(Produit::getPrix_produit).max().orElse(6000);
        // Initialiser les valeurs min et max du Slider
        filtragePrix.setMin(minPrice);
        filtragePrix.setMax(maxPrice);

        // Ajouter un écouteur de changement de valeur au Slider
        filtragePrix.valueProperty().addListener((observable, oldValue, newValue) -> {
            prixMax.setText(String.valueOf(newValue.intValue()));
            filterByPrice();
        });

        // Ajouter un écouteur de changement de texte au TextField prixMax
        prixMax.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                double price = Double.parseDouble(newValue);
                filtragePrix.setValue(price);
                filterByPrice();
            }
        });
    }





    public void afficherProduits() {
        produitGrid.getChildren().clear();
        Set<Produit> produits = serviceProduit.getAll();
        // Convertir l'ensemble en liste
        List<Produit> produitsList = new ArrayList<>(produits);

        // Tri des produits par ID
        produitsList.sort(Comparator.comparingInt(Produit::getId_produit));
        int rowIndex = 0;
        int colIndex = 0;

        for (Produit produit : produitsList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitPost.fxml"));
                AnchorPane produitNode = loader.load();
                ProduitPost produitController = loader.getController();
                produitController.setMarketPlaceController(this);
                // Remplir les données du produit dans le ProduitPost
                produitController.setNomUser(produit.getUser().getNom_user() + " " + produit.getUser().getPrenom_user());
                produitController.setIsmproduit(produit.getNom_produit());
                produitController.setPublicationTime(produit.getD_publication_produit().toString()); // Vous devez formater cette date selon vos besoins
                produitController.setPrixProduit("Prix :" + produit.getPrix_produit() + "$");
                produitController.setStockProduit(produit.getStock_produit() + " en Stock");
                produitController.setDescriptionProduit(produit.getDescription_produit());

                produitController.setProduitId(produit.getId_produit());

                //Charger et définir l'image du produit
                Image produitImage = new Image(produit.getImage_produit());
                produitController.setProduitImage(produitImage);

                // Vérifier si le stock est égal à 0 et désactiver le bouton d'achat en conséquence
                if (produit.getStock_produit() == 0) {
                    produitController.getAcheterButton().setDisable(true); // Assurez-vous que le bouton est accessible depuis le contrôleur de ProduitPost
                }

                // Ajouter le ProduitPost au GridPane
                produitGrid.add(produitNode, colIndex, rowIndex);
                // Initialiser le Tooltip
                produitController.initialize();


                // Incrémenter les indices de colonne et de ligne
                colIndex++;
                if (colIndex >= 3) { // Si vous voulez afficher 3 produits par ligne, vous pouvez ajuster cette valeur selon vos besoins
                    colIndex = 0;
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void refreshProductList() {
        afficherProduits();
    }

    @FXML
    void btnAddP(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent root = loader.load();
            AjouterProduit ajouterProduitController = loader.getController();
            ajouterProduitController.setMarketPlaceController(this);
            Stage stage = new Stage();
            stage.setTitle("Ajouter Produit");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void afficherPanier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPanier.fxml"));
            Parent root = loader.load();
            AjouterPanier ajouterPanierController = loader.getController();
            ajouterPanierController.setMarketPlaceController(this);

            ajouterPanierController.rafraichirAffichagePanier();

            Stage stage = new Stage();
            stage.setTitle("Panier");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void marketPlace(ActionEvent actionEvent) {
    }

    public void incrementCounter() {

        count++;
        incrementer.setText("(" + count + ")");
    }

    public void decrementCounter() {
        count--;
        if (count < 0) {
            count = 0;
            incrementer.setText("(" + count + ")");
        }
    }



    public void updatePanierButton() {
        btnPanier.setStyle("-fx-background-color: #fa068f;");
    }

    public void updateCounter() {
        int totalCount = PanierState.getInstance().getItemCount(); // Fetching the count from somewhere
        updateCounter(totalCount); // Calling the original method with the count
    }
    @FXML
    public void groupe(ActionEvent actionEvent) {
    }

    @FXML
    public void message(ActionEvent actionEvent) {
    }

    @FXML
    public void event(ActionEvent actionEvent) {
    }

    @FXML
    public void home(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();

           // homeController.updateValues();
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle (MarketPlace)
            Stage currentStage = (Stage) produitGrid.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void rechercher(ActionEvent event) {
        String searchTerm = searchField.getText().toLowerCase();

        List<Produit> filteredProducts = filterProducts(searchTerm);
        if (filteredProducts.isEmpty()) {

            messageImage.setImage(new Image("/image/ay.png"));
            messageLabel.setText("Aucun événement trouvé pour le terme de recherche : " + searchTerm);
            messageBox.setVisible(true);
            produitGrid.getChildren().clear();
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
                || produit.getDescription_produit().toLowerCase().contains(searchTerm)
                || produit.getCateg_produit().toString().toLowerCase().contains(searchTerm);
    }


    private List<Produit> produitsFiltres = new ArrayList<>();
    private void updateProductView(List<Produit> filteredProducts) {
        // Clear existing nodes in the GridPane
        produitGrid.getChildren().clear();


        // Update the GridPane with the filtered products
        int colIndex = 0;
        int rowIndex = 0;

        for (Produit produit : filteredProducts) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitPost.fxml"));
                AnchorPane produitNode = loader.load();
                ProduitPost produitController = loader.getController();
                produitController.setMarketPlaceController(this);
                // Set product details
                produitController.setNomUser(produit.getUser().getNom_user() + " " + produit.getUser().getPrenom_user());
                produitController.setIsmproduit(produit.getNom_produit());
                produitController.setPublicationTime(produit.getD_publication_produit().toString());
                produitController.setPrixProduit("Prix :" + produit.getPrix_produit() + "$");
                produitController.setStockProduit(produit.getStock_produit() + " en Stock");
                produitController.setDescriptionProduit(produit.getDescription_produit());
                produitController.setProduitId(produit.getId_produit());
                // Load and set the image of the product
                Image produitImage = new Image(produit.getImage_produit());
                produitController.setProduitImage(produitImage);

                produitController.initialize();

                produitGrid.add(produitNode, colIndex, rowIndex);

                // Increment colIndex and rowIndex
                colIndex++;
                if (colIndex >= 3) {
                    colIndex = 0;
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



//        @FXML
//    void filterByCategory(ActionEvent event) {
//        TypeCategorie selectedCategory = categorie.getValue();
//        if (selectedCategory != null) {
//            List<Produit> filteredProductsByCategory = filterProductsByCategory(selectedCategory);
//            updateProductView(filteredProductsByCategory);
//        } else {
//            // Si aucune catégorie n'est sélectionnée, afficher tous les produits
//            afficherProduits();
//        }
//    }



    private List<Produit> filterProductsByCategory(TypeCategorie category) {
        return serviceProduit.getAll().stream()
                .filter(produit -> produit.getCateg_produit() == category)
                .collect(Collectors.toList());
    }




    private List<Produit> filterProductsByPrice(double price) {
        return serviceProduit.getAll().stream()
                .filter(produit -> produit.getPrix_produit() <= price )
                .collect(Collectors.toList());
    }

    private List<Produit> filterProductsByPrice(List<Produit> products, double price) {
        return products.stream()
                .filter(produit -> produit.getPrix_produit() <= price)
                .collect(Collectors.toList());
    }

    private void filterByPrice() {
        double selectedPrice = filtragePrix.getValue();
        List<Produit> filteredProducts = filterProductsByPrice(selectedPrice);
        updateProductView(filteredProducts);
    }




//    @FXML
//    void filterByCategory(ActionEvent event) {
//        TypeCategorie selectedCategory = categorie.getValue();
//        if (selectedCategory != null) {
//            double selectedPrice = filtragePrix.getValue();
//            List<Produit> filteredProductsByPriceAndCategory = filterProductsByPriceAndCategory(selectedPrice, selectedCategory);
//            updateProductView(filteredProductsByPriceAndCategory);
//        } else {
//            // Si aucune catégorie n'est sélectionnée, afficher tous les produits
//            afficherProduits();
//        }
//    }

//    @FXML
//    void filterByCategory(ActionEvent event) {
//        TypeCategorie selectedCategory = categorie.getValue();
//        if (selectedCategory != null) {
//            List<Produit> filteredProductsByCategory = filterProductsByCategory(selectedCategory);
//            updateProductView(filteredProductsByCategory);
//
//            // Maintenant, appliquez le filtre par prix sur les produits filtrés par catégorie
//            double selectedPrice = filtragePrix.getValue();
//            List<Produit> filteredProductsByPriceAndCategory = filterProductsByPrice(filteredProductsByCategory, selectedPrice);
//            updateProductView(filteredProductsByPriceAndCategory);
//        } else {
//            // Si aucune catégorie n'est sélectionnée, afficher tous les produits
//            afficherProduits();
//        }
//    }

    @FXML
    void filterByCategory(ActionEvent event) {
        TypeCategorie selectedCategory = categorie.getValue();
        if (selectedCategory != null) {
            List<Produit> filteredProductsByCategory = filterProductsByCategory(selectedCategory);
            // Ne pas appeler updateProductView ici
            // Maintenant, appliquer le filtre par prix sur les produits filtrés par catégorie
            double selectedPrice = filtragePrix.getValue();
            List<Produit> filteredProductsByPriceAndCategory = filterProductsByPrice(filteredProductsByCategory, selectedPrice);
            updateProductView(filteredProductsByPriceAndCategory);
        } else {
            // Si aucune catégorie n'est sélectionnée, afficher tous les produits
            afficherProduits();
        }
    }

    @FXML
    void filterByPrice(ActionEvent event) {
        double selectedPrice = filtragePrix.getValue();
        List<Produit> filteredProductsByPrice = filterProductsByPrice(selectedPrice);
        updateProductView(filteredProductsByPrice);
    }


//    @FXML
//    void filterByPrice(ActionEvent event) {
//        double selectedPrice = filtragePrix.getValue();
//        TypeCategorie selectedCategory = categorie.getValue();
//        if (selectedCategory != null) {
//            List<Produit> filteredProductsByPriceAndCategory = filterProductsByPriceAndCategory(selectedPrice, selectedCategory);
//            updateProductView(filteredProductsByPriceAndCategory);
//        } else {
//            // Si aucune catégorie n'est sélectionnée, afficher tous les produits
//            afficherProduits();
//        }
//    }

    private List<Produit> filterProductsByPriceAndCategory(double price, TypeCategorie category) {
        return serviceProduit.getAll().stream()
                .filter(produit -> produit.getPrix_produit() <= price && produit.getCateg_produit() == category)
                .collect(Collectors.toList());
    }

    public void updateCounter(int totalCount) {
        PanierState.getInstance().setItemCount(totalCount);
        incrementer.setText("(" + totalCount + ")");
        if (totalCount != 0) {
            PanierState.getInstance().setButtonColor("-fx-background-color: #fa068f;");
        } else {
            PanierState.getInstance().setButtonColor("-fx-background-color: #3333C4;");
        }
        btnPanier.setStyle(PanierState.getInstance().getButtonColor());
    }

}


