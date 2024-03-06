package edu.esprit.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.Collections;
import java.util.List;

public class CountryComboBox {


    public static void populateCountriesComboBox(ComboBox<String> comboBox){
        try{
            RestApiClient client = new RestApiClient();
            String jsonData = client.fetchDataFromApi("https://restcountries.com/v3.1/all?fields=name");

            CountriesJsonParser parser = new CountriesJsonParser();
            List<String> countryNames = parser.extractOfficialName(jsonData);
            Collections.sort(countryNames);
            ObservableList<String> items = FXCollections.observableArrayList(countryNames);
            comboBox.setItems(items);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
