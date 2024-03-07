package edu.esprit.Controllers;
import edu.esprit.entities.Event;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class VoirDetail implements Initializable {
    @FXML
    private Label titreLabel;
    @FXML
    private Label descriptionText;
    @FXML
    private ImageView eventImageView;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label dateText;

    @FXML
    private Label lieuText;
    @FXML
    private Label dateTextFin;
    @FXML
    private StackPane mapView; // Ajout du composant WebView pour afficher la carte
    private String locationDetails; // Déclarez locationDetails comme un champ de classe


    private void afficherImage(String cheminImage) {
        // Charger l'image depuis les ressources du projet
        InputStream stream = getClass().getResourceAsStream("/Image/" + cheminImage);

        if (stream != null) {
            Image image = new Image(stream);
            eventImageView.setImage(image);
        } else {
            System.out.println("Image file not found in resources: " + cheminImage);
        }
    }


    public void initializeDetails(Event evenement) {
         if (evenement != null) {
             titreLabel.setText(evenement.getTitre_evenement());
             descriptionText.setText(evenement.getDescription_evenement());
             lieuText.setText(evenement.getLieu_evenement());
             dateText.setText(evenement.getD_debut_evenement().toString());
             dateTextFin.setText(evenement.getD_fin_evenement().toString());
             lieuText.setText(evenement.getLieu_evenement());
            // updateMapWithLocation(evenement.getLieu_evenement());
             afficherImage(evenement.getImage());

         } else {
             // Gérer le cas où evenement est null, par exemple en effaçant le texte
             titreLabel.setText("");
             descriptionText.setText("");
             lieuText.setText("");
             dateText.setText("");
         }
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // WebEngine webEngine = mapWebView.getEngine();
        //webEngine.load(getClass().getResource("/Maps.html").toExternalForm());
    }
//private void loadMap(String clubName, String governorate, String city) {
//    WebEngine webEngine = mapWebView.getEngine();
//
//    // Generate HTML content with the correct map URL
//    String htmlContent = generateMapHtml(clubName, governorate, city);
//
//    // Load the HTML content into the WebView
//    webEngine.loadContent(htmlContent);
//}

//    private String generateMapHtml(String clubName, String governorate, String city) {
//        // Construct the map URL based on the club name, governorate, and city
//        String mapUrl = "https://maps.google.com/maps?q=" +
//                clubName.replace(" ", "%20") + ",%20" +
//                governorate.replace(" ", "%20") + ",%20" +
//                city.replace(" ", "%20") + "&t=k&z=16&output=embed";
//
//        // Generate HTML content with the correct map URL
//        return "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "    <title>Google Maps Example</title>\n" +
//                "    <style>\n" +
//                "        /* Adjust the size and position of the map */\n" +
//                "        #mapouter {\n" +
//                "            position: relative;\n" +
//                "            text-align: right;\n" +
//                "            height: 500px; /* Adjust the height as needed */\n" +
//                "            width: 500px; /* Adjust the width as needed */\n" +
//                "        }\n" +
//                "\n" +
//                "        #gmap_canvas2 {\n" +
//                "            overflow: hidden;\n" +
//                "            background: none !important;\n" +
//                "            height: 500px; /* Adjust the height as needed */\n" +
//                "            width: 500px; /* Adjust the width as needed */\n" +
//                "        }\n" +
//                "\n" +
//                "        #gmap_canvas {\n" +
//                "            width: 100%;\n" +
//                "            height: 100%;\n" +
//                "            border: 0;\n" +
//                "            margin: 0;\n" +
//                "            padding: 0;\n" +
//                "        }\n" +
//                "    </style>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "<div id=\"mapouter\">\n" +
//                "    <div id=\"gmap_canvas2\">\n" +
//                "        <iframe id=\"gmap_canvas\"\n" +
//                "                src=\"" + mapUrl + "\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n" +
//                "    </div>\n" +
//                "</div>\n" +
//                "</body>\n" +
//                "</html>";
//    }
   
    }


