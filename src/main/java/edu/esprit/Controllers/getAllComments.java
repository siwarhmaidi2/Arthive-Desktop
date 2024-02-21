package edu.esprit.Controllers;

import edu.esprit.services.ServiceCommentaire;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import edu.esprit.entities.Commentaire;

import java.text.SimpleDateFormat;

public class getAllComments {

    @FXML
    private TextArea content;

    @FXML
    private Label date;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label username;

    private Commentaire commentaire;

    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();

    public void setData(Commentaire commentaire) {
        try {
            this.commentaire = commentaire;
            this.content.setText(commentaire.getContenuCommentaire());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(commentaire.getDateAjoutCommentaire());

            this.date.setText(formattedDate);
            if (commentaire.getUser() != null) {
                this.username.setText(commentaire.getUser().getNom_user() + " " + commentaire.getUser().getPrenom_user());
                // profileImage.setImage(commentaire.getUser().getPhoto_user());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}








