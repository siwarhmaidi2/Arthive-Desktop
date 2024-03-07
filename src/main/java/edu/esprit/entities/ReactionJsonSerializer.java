package edu.esprit.entities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReactionJsonSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveReactions(List<Reaction> reactions, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), reactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Reaction> loadReactions(String fileName) {
        try {
            return objectMapper.readValue(new File(fileName), objectMapper.getTypeFactory().constructCollectionType(List.class, Reaction.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
