package edu.esprit.Controllers;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import edu.esprit.entities.*;
import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PasserCommande  implements Initializable {

    @FXML
    private Hyperlink name2;

    @FXML
    private ImageView image2;

    @FXML
    private Button btnPanier;

    @FXML
    private Label incrementer;

    @FXML
    private TextField entrerNom;

    @FXML
    private TextField entrerPrenom;
    @FXML
    private TextField entrerTelephone;


    @FXML
    private TextField entrerMail;

    @FXML
    private TextField entrerLocal;

    @FXML
    private Label alerteNom;

    @FXML
    private Label alertePrenom;

    @FXML
    private Label alerteTel;

    @FXML
    private Label alertMail;

    @FXML
    private Label alertLocal;

    @FXML
    private CheckBox enligne;

    @FXML
    private CheckBox livraison;

    @FXML
    private Label numlab;

    @FXML
    private TextField num;

    @FXML
    private PasswordField code;

    @FXML
    private Label codelab;

    @FXML
    private Label alertePay;



    private PanierState panierState = PanierState.getInstance();

    public void setPanierState(PanierState panierState) {
        this.panierState = panierState;
    }

    private MarketPlace marketPlaceController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }


    @FXML
    void afficherPanier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPanier.fxml"));
            Parent root = loader.load();
            AjouterPanier ajouterPanierController = loader.getController();

            ajouterPanierController.setPanierState(panierState);
            // Passer la référence du contrôleur MarketPlace à AjouterPanier
            ajouterPanierController.setMarketPlaceController(marketPlaceController);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void annulerComm(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation d'annulation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir annuler cette commande ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Redirection vers l'interface MarketPlace.fxml
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketPlace.fxml"));
                Parent root = loader.load();
                Stage marketPlaceStage = new Stage();
                marketPlaceStage.setTitle("MarketPlace");
                marketPlaceStage.setScene(new Scene(root));
                marketPlaceStage.show();

                // Fermer la fenêtre actuelle
                Stage stage = (Stage) btnPanier.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void marketPlace(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de redirection");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir accéder à la page MarketPlace ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Redirection vers l'interface MarketPlace.fxml
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketPlace.fxml"));
                Parent root = loader.load();
                Stage marketPlaceStage = new Stage();
                marketPlaceStage.setTitle("MarketPlace");
                marketPlaceStage.setScene(new Scene(root));
                marketPlaceStage.show();

                // Fermer la fenêtre actuelle
                Stage stage = (Stage) btnPanier.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void passerComm(ActionEvent event) {
        // Initialize empty error messages
        String champsManquants = "";
        alerteNom.setText("");
        alertePrenom.setText("");
        alerteTel.setText("");
        alertMail.setText("");
        alertLocal.setText("");
        alertePay.setText("");

        // Validate name
        if (entrerNom.getText().length() < 4) {
            entrerNom.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alerteNom.setText("Nom invalide (doit contenir au moins 4 caractères)");
            return;
        } else {
            entrerNom.setStyle("");
            alerteNom.setText("");
        }

        // Vérifier la longueur du prénom
        if (entrerPrenom.getText().length() < 4) {
            entrerPrenom.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alertePrenom.setText("Prénom invalide (doit contenir au moins 4 caractères)");
            return;
        } else {
            entrerPrenom.setStyle("");
            alertePrenom.setText("");
        }

        // Vérifier le format du numéro de téléphone
        if (!entrerTelephone.getText().matches("^\\d{8}$")) {
            entrerTelephone.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alerteTel.setText("Numéro de téléphone invalide (10 chiffres)");
            return;
        } else {
            entrerTelephone.setStyle("");
            alerteTel.setText("");
        }

        // Vérifier le format de l'email
        if (!entrerMail.getText().matches("^[\\w-_.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            entrerMail.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alertMail.setText("Email invalide");
            return;
        } else {
            entrerMail.setStyle("");
            alertMail.setText("");
        }

        // Vérifier si l'adresse de livraison est vide
        if (entrerLocal.getText().isEmpty()) {
            entrerLocal.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alertLocal.setText("Veuillez entrer une adresse de livraison");
            return;
        } else {
            entrerLocal.setStyle("");
            alertLocal.setText("");
        }

        if (enligne.isSelected()) {
            // Validate num field (ensure it's not empty and has the correct format)
            if (num.getText().isEmpty() || !num.getText().matches("^\\d{4} \\d{4} \\d{4} \\d{4}$")) {
                num.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); // Set red border color for invalid input
                alertePay.setText("Numéro de carte invalide");
                return;
            } else {
                num.setStyle(""); // Reset border color
            }

            // Validate code field (ensure it's not empty and has exactly 3 digits)
            if (code.getText().isEmpty() || !code.getText().matches("^\\d{3}$")) {
                code.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); // Set red border color for invalid input
                alertePay.setText("Code de sécurité invalide");
                return;
            } else {
                code.setStyle(""); // Reset border color
            }
        }

        // Si au moins un champ est manquant, afficher une alerte informant l'utilisateur des champs manquants
        if (!champsManquants.isEmpty()) {
            Alert champsManquantsAlert = new Alert(Alert.AlertType.WARNING);
            champsManquantsAlert.setTitle("Champs manquants");
            champsManquantsAlert.setHeaderText(null);
            champsManquantsAlert.setContentText("Veuillez remplir les champs suivants :\n" + champsManquants);
            champsManquantsAlert.showAndWait();
            return; // Sortir de la méthode car des champs sont manquants
        }

        ServiceUser su = new ServiceUser();
        // Afficher une alerte de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de la commande");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir passer cette commande ?");

        // Attendre la réponse de l'utilisateur
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Si l'utilisateur clique sur "OK"
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer les informations du formulaire
            String nom = entrerNom.getText();
            String prenom = entrerPrenom.getText();
            int telephone = Integer.parseInt(entrerTelephone.getText());
            String email = entrerMail.getText();
            String adresseLivraison = entrerLocal.getText();

           // User loggedInUser = su.authenticateUser("chams@gmail.com", "chams2000");
            User loggedInUser = UserData.getInstance().getLoggedInUser();

            // Créer une instance de ServicePanier
            ServicePanier servicePanier = new ServicePanier();


            List<Panier> paniersUtilisateur = servicePanier.getAll().stream()
                    .filter(panier -> panier.getUser().equals(loggedInUser))
                    .collect(Collectors.toList());
            if (!paniersUtilisateur.isEmpty()) {
                for (Panier panier : paniersUtilisateur) {

                    Commande commande = new Commande();
                    commande.setNom_client(entrerNom.getText());
                    commande.setPrenom_client(entrerPrenom.getText());
                    commande.setTelephone(Integer.parseInt(entrerTelephone.getText()));
                    commande.setE_mail(entrerMail.getText());
                    commande.setAdresse_livraison(entrerLocal.getText());
                    commande.setPanier(panier);

                    // Ajouter la commande
                    ServiceCommande serviceCommande = new ServiceCommande();
                    serviceCommande.add(commande);

                }

                // Envoyer un e-mail en fonction de l'option de paiement choisie
                if (enligne.isSelected()) {
                    // Envoyer l'e-mail de confirmation de paiement
                    sendPaymentConfirmationEmail(entrerMail.getText());
                } else {
                    // Envoyer l'e-mail de confirmation de commande avec instruction de paiement à la livraison
                    sendConfirmationEmail(entrerMail.getText());
                }

                // Si le paiement en ligne est sélectionné, procéder au paiement
                if (enligne.isSelected()) {
                    processPayment();
                }

                // Réinitialiser les champs du formulaire
                entrerNom.clear();
                entrerPrenom.clear();
                entrerTelephone.clear();
                entrerMail.clear();
                entrerLocal.clear();

                // Afficher une confirmation à l'utilisateur
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Commande passée");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Votre commande a été passée avec succès ! Vous allez recevoir votre commande Apres 48h ");
                successAlert.showAndWait();

                // Fermer la fenêtre actuelle
                Stage stage = (Stage) btnPanier.getScene().getWindow();
                stage.close();


                for (Panier panier : paniersUtilisateur) {
                    servicePanier.supprimerPanierUtilisateur(loggedInUser);
                }

                // Charger la fenêtre MarketPlace.fxml
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketPlace.fxml"));
                    Parent root = loader.load();
                    Stage marketPlaceStage = new Stage();
                    marketPlaceStage.setTitle("MarketPlace");
                    marketPlaceStage.setScene(new Scene(root));
                    marketPlaceStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Si l'utilisateur n'a aucun panier, afficher un message d'erreur
                Alert erreurAlert = new Alert(Alert.AlertType.ERROR);
                erreurAlert.setTitle("Erreur");
                erreurAlert.setHeaderText(null);
                erreurAlert.setContentText("Vous n'avez aucun panier.");
                erreurAlert.showAndWait();
            }
        }
    }
    private void sendConfirmationEmail(String recipientEmail) {
        // Create SendGrid object with your API key
        SendGrid sendgrid = new SendGrid("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");

        // Create Email object for the recipient
        Email recipient = new Email(recipientEmail);

        // Create Email object for the sender
        Email sender = new Email("shamsbensaid456@gmail.com"); // Update with your email

        // Set subject and content of the email
        String subject = "Confirmation de commande";
        String messageContent = "Votre commande a été passée avec succès ! Vous allez recevoir votre commande Apres 48h Veuillez payer votre commande a la livraison";

        // Create Content object
        Content content = new Content("text/plain", messageContent);

        // Create Mail object
        Mail mail = new Mail(sender, subject, recipient, content);

        // Send the email using SendGrid API
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendgrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendPaymentConfirmationEmail(String recipientEmail) {
        // Créer un objet SendGrid avec votre clé API
        SendGrid sendgrid = new SendGrid("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");
        // Créer un objet Email pour le destinataire
        Email recipient = new Email(recipientEmail);
        // Créer un objet Email pour l'expéditeur
        Email sender = new Email("shamsbensaid456@gmail.com"); // Mettez à jour avec votre adresse e-mail
        // Définir le sujet et le contenu de l'e-mail
        String subject = "Confirmation de paiement";
        String messageContent = "Votre paiement a été traité avec succès ! Vous allez recevoir votre commande Apres 48h";

        // Créer un objet Content
        Content content = new Content("text/plain", messageContent);

        // Créer un objet Mail
        Mail mail = new Mail(sender, subject, recipient, content);

        // Envoyer l'e-mail en utilisant l'API SendGrid
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendgrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    ServicePanier servicePanier = new ServicePanier();

    private double calculateTotalPrice(Commande commande) {
        ServiceUser serviceUser = new ServiceUser();
        User loggedInUser = serviceUser.authenticateUser("chams@gmail.com", "chams2000");

        // Implement your logic to calculate total price for the order
        double totalPrice = 0.0;
        for (Produit produit : commande.getPanier().getProduits()) {
            totalPrice += produit.getPrix_produit() * servicePanier.countProductInPanier(loggedInUser, produit);
        }
        return totalPrice;
    }



    @FXML
    void enligne(ActionEvent event) {
        if (enligne.isSelected()) {
            // Si l'option de paiement en ligne est sélectionnée, rendre les champs de paiement en ligne visibles
            numlab.setVisible(true);
            num.setVisible(true);
            code.setVisible(true);
            codelab.setVisible(true);

            // Décocher l'option de paiement à la livraison si elle est cochée
            livraison.setSelected(false);
        } else {
            // Sinon, rendre les champs de paiement en ligne invisibles
            numlab.setVisible(false);
            num.setVisible(false);
            code.setVisible(false);
            codelab.setVisible(false);
        }
    }

    @FXML
    void livraison(ActionEvent event) {
        if (livraison.isSelected()) {
            // Si l'option de paiement à la livraison est sélectionnée, rendre les champs de paiement en ligne invisibles
            numlab.setVisible(false);
            num.setVisible(false);
            code.setVisible(false);
            codelab.setVisible(false);

            // Décocher l'option de paiement en ligne si elle est cochée
            enligne.setSelected(false);
        }
    }


   /* private void processPayment() {
        try {
            // Set your secret key here
            Stripe.apiKey = "sk_test_51OqzOo08LA3XIQ1gQCOI8jQ5LlS31uBqWmFh1lW72yikzP6KQscO2HooOUMm06R4haFRAXALv9HZi7Xm4x1V6krW00aYXjVTsR";

            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(1000L) // Montant en cents (par exemple, 10,00 $)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // Si le paiement a réussi, affichez un message de succès
            System.out.println("Paiement réussi. Identifiant du PaymentIntent: " + intent.getId());


        } catch (StripeException e) {
            // S'il y a eu une erreur lors du traitement du paiement, affichez le message d'erreur
            System.out.println("Paiement échoué. Erreur: " + e.getMessage());
        }
    }
*/
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
            name2.setText(loggedInUser.getNom_user() + " " + loggedInUser.getPrenom_user());
        }

        incrementer.setText("(" + PanierState.getInstance().getItemCount() + ")");
        btnPanier.setStyle(PanierState.getInstance().getButtonColor());
    }

    @FXML
    public void profil(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent root = loader.load();
            ProfileController profileController = loader.getController();

            // Définir le contrôleur MarketPlace dans la fenêtre du profil
            profileController.setMarketPlaceController(marketPlaceController);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Profil");
            stage.show();

            // Fermer la fenêtre actuelle (MarketPlace)
            Stage marketPlaceStage = (Stage) name2.getScene().getWindow();
            marketPlaceStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


