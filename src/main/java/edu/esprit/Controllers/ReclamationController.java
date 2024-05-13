package edu.esprit.Controllers;

import edu.esprit.entities.Groupe;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceGroupe;
import edu.esprit.services.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ReclamationController implements Initializable {
    @FXML
    private ChoiceBox<String> ChoiceBoxGroupe;

    @FXML
    private TextArea TextAreaDescRec;

    @FXML
    private Button signalerBut;
    ServiceGroupe SG = new ServiceGroupe();
    ServiceReclamation SR = new ServiceReclamation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing ReclamationController...");

        // Initialize your ServiceGroupe instance
        ServiceGroupe SG = new ServiceGroupe();

        // Fetch group names from the database
        Set<Groupe> groupes = SG.getAll();

        // Clear the ChoiceBoxGroupe first, in case it had any previous items
        ChoiceBoxGroupe.getItems().clear();

        // Add group names to the ChoiceBoxGroupe
        for (Groupe groupe : groupes) {
            ChoiceBoxGroupe.getItems().add(groupe.getNom_group());
        }

        System.out.println("Initialization completed.");
    }

    @FXML
    private void signalerButtonClicked(ActionEvent event) {
        String selectedGroupName = ChoiceBoxGroupe.getValue();
        String description = TextAreaDescRec.getText();
        Reclamation r = new Reclamation();
        r.setDesc_Reclamation(description);

        // Recherche du groupe par son nom
        Groupe groupe = null;
        try {
            groupe = SG.getOneByNom(selectedGroupName);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Une erreur est survenue lors de la recherche du groupe !");
            return; // Arrêter l'exécution si une erreur se produit
        }

        if (groupe == null) {
            showAlert("Le groupe sélectionné n'a pas été trouvé !");
            return; // Arrêter l'exécution si le groupe n'est pas trouvé
        }

        // Ajout de la réclamation en utilisant l'ID du groupe
        try {
            SR.ajouter2(r, groupe.getId_group());
            showAlert("Réclamation effectuée avec succès !");
            //close the window
            Stage currentStage = (Stage) signalerBut.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Une erreur est survenue lors de l'ajout de la réclamation !");
        }
    }

    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
