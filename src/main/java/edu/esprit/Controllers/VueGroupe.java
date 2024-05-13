package edu.esprit.Controllers;


import edu.esprit.entities.Groupe;
import edu.esprit.entities.Publication;
import edu.esprit.services.ServiceGroupe;
import edu.esprit.services.ServiceUser;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;


public class VueGroupe {

    @FXML
    private Button SelectButt;

    @FXML
    private Label description;

    @FXML
    private ImageView imgGroupe;
    @FXML
    private Label nomGroupe;
    ServiceGroupe SG = new ServiceGroupe() ;
    private Groupe groupe;
    public void setGroupe(Groupe groupe) {

        this.groupe = groupe;
    }


    public void setdata(Groupe groupe)
    {
        String GroupeImage = groupe.getImage();
        // Assuming your images are stored in a specific directory, construct the full URL
        String GroupeImageUrl = "file:/C:/SymfonyProject/Nouveau_dossier/arthive_web/public/images/"+GroupeImage;
        Image groupImage = new Image(GroupeImageUrl);
        this.imgGroupe.setImage(groupImage);
        this.nomGroupe.setText(groupe.getNom_group());
        this.description.setText(groupe.getDescription_group());
    }
    }