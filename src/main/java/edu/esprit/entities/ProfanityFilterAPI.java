package edu.esprit.entities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfanityFilterAPI {

    private static final String API_URL = "https://api.api-ninjas.com/v1/profanityfilter";
    private static final String API_KEY = "o+TFvKe5xOXlSGvQKTDTRA==Nbrw2KJlLFTAb1Xl"; // Replace with your API key

    public static ProfanityResult checkProfanity(String text) throws IOException {
        URL url = new URL(API_URL + "?text=" + text);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("X-Api-Key", API_KEY);

        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseStream);

        String original = root.path("original").asText();
        String censored = root.path("censored").asText();
        boolean hasProfanity = root.path("has_profanity").asBoolean();

        return new ProfanityResult(original, censored, hasProfanity);
    }

    public static class ProfanityResult {
        private final String original;
        private final String censored;
        private final boolean hasProfanity;

        public ProfanityResult(String original, String censored, boolean hasProfanity) {
            this.original = original;
            this.censored = censored;
            this.hasProfanity = hasProfanity;
        }

        public String getOriginal() {
            return original;
        }

        public String getCensored() {
            return censored;
        }

        public boolean hasProfanity() {
            return hasProfanity;
        }
    }
}
