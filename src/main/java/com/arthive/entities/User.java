package com.arthive.entities;



import java.sql.Date;
import java.util.Objects;

public class User {

    private int id ;
    private String nom ;
    private String prenom ;

    private String email ;

    private String password ;

    private Date dateNaiss ;

    private String ville ;

    private int numtel ;


    public User(){

    }
    public User(int id, String nom, String prenom, String email, String password, Date dateNaiss, String ville, int numtel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.dateNaiss = dateNaiss;
        this.ville = ville;
        this.numtel = numtel;
    }

    public User(String nom, String prenom, String email, String password, Date dateNaiss, String ville, int numtel) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.dateNaiss = dateNaiss;
        this.ville = ville;
        this.numtel = numtel;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Date getDateNaiss() {
        return dateNaiss;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    @Override
    public String toString() {
        return "User{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateNaiss=" + dateNaiss +
                ", ville='" + ville + '\'' +
                ", numtel=" + numtel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && numtel == user.numtel && Objects.equals(nom, user.nom) && Objects.equals(prenom, user.prenom) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(dateNaiss, user.dateNaiss) && Objects.equals(ville, user.ville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, email, password, dateNaiss, ville, numtel);
    }
}
