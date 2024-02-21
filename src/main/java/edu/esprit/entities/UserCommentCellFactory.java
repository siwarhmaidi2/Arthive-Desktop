package edu.esprit.entities;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class UserCommentCellFactory implements Callback<ListView<Commentaire>, ListCell<Commentaire>> {

    @Override
    public ListCell<Commentaire> call(ListView<Commentaire> param) {
        return new ListCell<Commentaire>() {
            @Override
            protected void updateItem(Commentaire item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    // Customize the appearance of each cell here
                    setText(item.getUser().getNom_user() + " " + item.getUser().getPrenom_user() + ": " + item.getContenuCommentaire());
                }
            }
        };
    }
}
