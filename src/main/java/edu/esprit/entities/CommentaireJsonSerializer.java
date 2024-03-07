package edu.esprit.entities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CommentaireJsonSerializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveCommentaires(List<Commentaire> commentaires, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), commentaires);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Commentaire> loadCommentaires(String fileName) {
        try {
            return objectMapper.readValue(new File(fileName), objectMapper.getTypeFactory().constructCollectionType(List.class, Commentaire.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
