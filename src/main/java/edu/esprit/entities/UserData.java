package edu.esprit.entities;

import edu.esprit.entities.User;
public class UserData {
    private static UserData instance;
    private User loggedInUser;

    private UserData() {}

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }
}
