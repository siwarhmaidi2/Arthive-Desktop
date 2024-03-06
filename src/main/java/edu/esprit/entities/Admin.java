package edu.esprit.entities;

import java.sql.Date;

public class Admin extends User{

    public Admin() {}
    public Admin(int id, String nom, String prenom, String email, String mdp,Date date_naissance, String ville, String num_tel, String bio, String photo, String role) {
        super(id, nom, prenom, email, mdp, date_naissance, ville, num_tel, bio, photo, role);
    }
    public Admin(String nom, String prenom, String email, String mdp,Date date_naissance, String ville, String num_tel, String bio, String photo, String role) {
        super(nom, prenom, email, mdp, date_naissance, ville, num_tel, bio, photo, role);
    }

}
