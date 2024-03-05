package edu.esprit.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CountriesJsonParser {
    public List<String> extractOfficialName(String jsonData) throws Exception {

        List<String> countryNames = new ArrayList<>();
        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Read JSON data into a JsonNode
        JsonNode rootNode = objectMapper.readTree(jsonData);

        // Iterate over the array of countries
        if (rootNode.isArray()) {
            Iterator<JsonNode> iterator = rootNode.elements();
            while (iterator.hasNext()) {
                JsonNode countryNode = iterator.next();
                JsonNode nameNode = countryNode.get("name");
                JsonNode commonNode = nameNode.get("common");
                String commonName = commonNode.asText();
                countryNames.add(commonName);
            }
        }

        return countryNames;
    }
}
