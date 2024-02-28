package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.entities.UserData;

public class HomeController {


    public void initialize(int id) {
        User loggedInUser = UserData.getInstance().getLoggedInUser();
    }


}
