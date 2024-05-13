package edu.esprit.Controllers;


import edu.esprit.entities.*;

import edu.esprit.services.CrudEvent;
import edu.esprit.services.ServiceProduit;
import edu.esprit.tests.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Set;

import edu.esprit.entities.UserData;
import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.Initializable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class HomeAdminController {

    @FXML
    Label name;
    @FXML
    ImageView photo;
    @FXML
    Label userCount;
//    @FXML
//    private Label nbrUser;

    @FXML
    private Label nbrPub;

    @FXML
    private Label nbrProd;

    @FXML
    private Label nbrGrp;

    @FXML
    private Label nbrEvent;

    @FXML
    Label productsCount;
    @FXML
    Label groupsCount;
    @FXML
    Label EventsCount;

    @FXML
    ScrollPane scrollPane;

    @FXML
    Button addBtn;

    @FXML
    Button btnPublications;
    @FXML
    Button btnUsers;
    @FXML
    Button btnProducts;
    @FXML
    Button btnGroups;
    @FXML
    Button btnEvents;
    @FXML
    Button btnSettings;
    @FXML
    Button btnSignout;




    @FXML
    private Button buttonPublication;
    @FXML
    private GridPane postGrid;

    private List<Publication> posts;

    private ServicePublication servicePublication = new ServicePublication();
    private ServiceUser serviceUser = new ServiceUser();


    private User loggedInUser = UserData.getInstance().getLoggedInUser();


    
  
      public HomeAdminController getHomeAdminController() {
        return this;
    }

    public void initialize() throws Exception{
              loggedInUser = UserData.getInstance().getLoggedInUser();
            posts = new ArrayList<>(data());
            refreshContent();
        addBtn.setVisible(false);
        User loggedinUser = UserData.getInstance().getLoggedInUser();
        String userPhotoUrl = loggedInUser.getPhoto();
        // Step 5: Load and display the user's photo
        String pfpPath = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+userPhotoUrl;
        this.photo.setImage(new Image(pfpPath));


        name.setText(loggedinUser.getNom_user() + " " + loggedinUser.getPrenom_user());


        ServiceUser su = new ServiceUser();
        userCount.setText(String.valueOf(su.getTotalUsers()));

        //Load Admin Home into Edit controller to refresh list on submit (NOT WORKING)
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/AdminResources/EditProfileAdmin.fxml"));
            Parent editProfileRoot = loader2.load();
            ProfileEditAdminController profileEditAdminController = loader2.getController();
            profileEditAdminController.setHomeAdminController(this.getHomeAdminController());
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateProductCountLabel();
        updatePubCountLabel();
       // updatUserCountLabel();
        updatEventCountLabel();
    }
    public void showUsers(ActionEvent event) throws IOException {
        addBtn.setVisible(true);
        postGrid.setVisible(false);
        GridPane contentBox = new GridPane();

        GridPane userItemTitle = FXMLLoader.load(getClass().getResource("/AdminResources/Item.fxml"));
        // Hide the buttons in userItemTitle
        for (Node node : userItemTitle.getChildren()) {
            if (node instanceof Button) {
                node.setVisible(false);
            }
        }

        userItemTitle.setAlignment(Pos.TOP_LEFT);
        contentBox.setAlignment(Pos.TOP_LEFT);

        contentBox.getChildren().add(userItemTitle);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        ServiceUser su = new ServiceUser();
        Set<User> users = su.getItems();

        // Access the controller of Item.fxml to set user data
        try {
            int row = 1;
            for (User user : users) {
                // Load the Item.fxml layout for each user
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/Item.fxml"));
                GridPane userItem = loader.load();


                userItem.setAlignment(Pos.TOP_LEFT);

                // Access the controller of Item.fxml to set user data
                ItemController itemController = loader.getController();
                itemController.setUserData(user);

                contentBox.getChildren().add(userItem);
                GridPane.setRowIndex(userItem,row);
                row++;

            }

            scrollPane.setContent(contentBox);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public void addUser(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/AddProfileAdmin.fxml"));
            Parent addProfileRoot = loader.load();

            Stage newStage = new Stage();
            Scene newScene = new Scene(addProfileRoot, 600, 400);
            newStage.setScene(newScene);
            newStage.setTitle("Add Profile");
            newStage.show();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

//    public void reloadUsers() throws IOException{
//        // Reload users...
//        showUsers(null); // You might need to pass an ActionEvent if needed
//    }

    public void refreshContent() {
        posts = new ArrayList<>(data());

        refreshGrid();

    }

    private void refreshGrid() {
        postGrid.getChildren().clear();

        int columns = 0;
        int rows = 1; // Start at 1 to avoid the header

        for (int i = 0; i < posts.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PublicationAdmin.fxml"));
                VBox postBox = fxmlLoader.load();

                PublicationAdminController controller = fxmlLoader.getController();
                controller.setData(posts.get(i));

                if (columns == 3) {
                    columns = 0;
                    rows++;
                }
                postGrid.add(postBox, columns++, rows);
                GridPane.setMargin(postBox, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Publication> data() {
        return new ArrayList<>(servicePublication.getAll());
    }


    //SwitchToFetchAllPublications
    public void switchToFetchAllPublications() throws IOException {
        addBtn.setVisible(false);
        postGrid.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/HomeAdmin.fxml"));
        Parent root = loader.load();
        HomeAdminController controller = loader.getController();
        btnPublications.getScene().setRoot(root);
    }

    ServiceProduit serviceProduit = new ServiceProduit();
    public void afficherProduits() {
         addBtn.setVisible(false);
        postGrid.setVisible(true);
        postGrid.getChildren().clear();
        Set<Produit> produits = serviceProduit.getAll();
        // Convertir l'ensemble en liste
        List<Produit> produitsList = new ArrayList<>(produits);

        // Tri des produits par ID
        produitsList.sort(Comparator.comparingInt(Produit::getId_produit));
        int rowIndex = 0;
        int colIndex = 0;

        for (Produit produit : produitsList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/produitAdmin.fxml"));
                AnchorPane produitNode = loader.load();
                ProduitAdmin produitController = loader.getController();

                // Remplir les données du produit dans le ProduitPost
                produitController.setNomUser(produit.getUser().getNom_user() + " " + produit.getUser().getPrenom_user());
                produitController.setIsmproduit(produit.getNom_produit());
                produitController.setPublicationTime(produit.getD_publication_produit().toString()); // Vous devez formater cette date selon vos besoins
                produitController.setPrixProduit("Prix :" + produit.getPrix_produit() + "$");
                produitController.setStockProduit(produit.getStock_produit() + " en Stock");
                produitController.setDescriptionProduit(produit.getDescription_produit());
                String userPhoto = loggedInUser.getPhoto();
                String userPhotoUrl = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+userPhoto;
                Image userImage = new Image(userPhotoUrl);
                produitController.setAvatarImage(userImage);


                produitController.setProduitId(produit.getId_produit());

                //Charger et définir l'image du produit
                String produitImage = produit.getImage_produit();
                // Assuming your images are stored in a specific directory, construct the full URL
                String produitImageUrl = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+produitImage;
                Image image = new Image(produitImageUrl);
                produitController.setProduitImage(image);

                // Ajouter le ProduitPost au GridPane
                postGrid.add(produitNode, colIndex, rowIndex);
                // Initialiser le Tooltip
                //  produitController.initialize();
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
    @FXML
    public void AdminMarket(ActionEvent actionEvent) {
        afficherProduits();

    }
    public void logout(ActionEvent event) throws IOException {
        UserData.getInstance().setLoggedInUser(null);
        Main.changeScene("/Login.fxml");
    }

    private void updateProductCountLabel() {
        Set<Produit> produits = serviceProduit.getAll();
        long totalProducts = produits.stream().count();
        nbrProd.setText(String.valueOf(totalProducts));
    }

    private void updatePubCountLabel() {
        Set<Publication> publications = servicePublication.getAll();
        long totalPubs = publications.stream().count();
        nbrPub.setText(String.valueOf(totalPubs));
    }

//    private void updatUserCountLabel() {
//        Set<User> users = serviceUser.getAll();
//        long totalUsers = users.stream().count();
//        nbrUser.setText(String.valueOf(totalUsers));
//    }

    private void updatEventCountLabel() {
        Set<Event> events = crudEvent.getAll();
        long totalEvent = events.stream().count();
        nbrEvent.setText(String.valueOf(totalEvent));
    }


    private CrudEvent crudEvent = new CrudEvent();

    private GridPane createEventPane(Event evenement) {
        GridPane pane = new GridPane();

        pane.add(new javafx.scene.control.Label("Titre: " + evenement.getTitre_evenement()), 0, 0);
        pane.add(new javafx.scene.control.Label("Date: " + evenement.getD_debut_evenement()), 0, 1);
        pane.add(new javafx.scene.control.Label("Lieu: " + evenement.getLieu_evenement()), 0, 2);

        Button supprimer = new Button("Supprimer");

        supprimer.setUserData(evenement);
        pane.add(supprimer, 0, 3);


        Hyperlink voirDetail = new Hyperlink("Voir détail");
        voirDetail.setOnAction(this::voirDetailClicked);
        voirDetail.setUserData(evenement); // Assurez-vous que evenement est un objet de type Event
        pane.add(voirDetail, 0, 4);
        pane.add(voirDetail, 0, 4);



        ImageView eventImageView = new ImageView();
        eventImageView.setFitWidth(300); // Initial width
        eventImageView.setFitHeight(100); // Initial height

        EventAdminController eventView = new EventAdminController();
        //eventView.initializeEvent(evenement, 50, 50);
        pane.add(eventView, 0, 5); // Ajoutez EventView à la grille

        return pane;

    }


    private void afficherEvenements() {
        addBtn.setVisible(false);
        postGrid.setVisible(true);
        postGrid.getChildren().clear();
        Set<Event> evenements = crudEvent.getAll();
        List<Event> evenementsList = new ArrayList<>(evenements);
        // Tri des événements par date de début de manière croissante
        evenementsList.sort(Comparator.comparing(Event::getD_debut_evenement));
        AfficherEvent afficherEvent = new AfficherEvent();


        int colIndex = 0;
        int rowIndex = 0;

        for (Event evenement : evenements) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminResources/EventAdmin.fxml"));
                //Parent root = loader.load();
                AnchorPane anchorPane = loader.load();
                EventAdminController eventViewController = loader.getController();
                // Initialiser les données de l'événement après avoir chargé le FXML
               // eventViewController.initialize(evenement, 50, 50);
                // Load and set the image for the event
                String imagePath = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+ evenement.getImage();

                Image eventImage = new Image(imagePath);
                eventViewController.setEventImageView(eventImage);

                // Vérifier si la date de fin de l'événement est passée
                if (evenement.getD_fin_evenement().toLocalDateTime().isBefore(LocalDateTime.now())) {
                    Hyperlink voirDetail = eventViewController.getVoirDetail();
                    voirDetail.setDisable(true);

                }

                // Ajouter EventView à la GridPane
                postGrid.add(anchorPane, colIndex, rowIndex);


                // Incrémenter les indices de colonne et de ligne
                colIndex++;

                if (colIndex >= 4) { // Si vous voulez afficher 3 produits par ligne, vous pouvez ajuster cette valeur selon vos besoins
                    colIndex = 0;
                    rowIndex++;
                }
                //set grid width
                postGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                postGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                postGrid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                postGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                postGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                postGrid.setMaxHeight(Region.USE_PREF_SIZE);
                //GridPane.setMargin(anchorPane, new Insets(10,20,30,40));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void voirDetailClicked(ActionEvent event) {
        Hyperlink source = (Hyperlink) event.getSource();

        // Assurez-vous que userData est de type Event
        Object userData = source.getUserData();

        System.out.println("Type of userData: " + userData.getClass().getName());

        if (userData instanceof Event) {
            Event evenement = (Event) userData;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/voirDetail.fxml"));
                Parent root = loader.load();

                VoirDetail detailsController = loader.getController();

                // Utilisez la classe EventDetailsController pour initialiser les détails
                detailsController.initializeDetails(evenement);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle(" Détail");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("UserData is not an instance of Event. Check your setup.");
        }

    }

    public void showEvents(ActionEvent event) {
        addBtn.setVisible(false);
        postGrid.setVisible(true);
        afficherEvenements();
    }
}
