package edu.esprit.entities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PublicationJsonSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void savePublications(List<Publication> publications, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), publications);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Publication> loadPublications(String fileName) {
        try {
            return objectMapper.readValue(new File(fileName), objectMapper.getTypeFactory().constructCollectionType(List.class, Publication.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
