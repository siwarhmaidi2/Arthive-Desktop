package edu.esprit.entities;



import java.sql.Date;
import java.util.Objects;

public class User {

    private int id_user;
    private String nom_user;
    private String prenom_user;

    private String email;

    private String mdp_user;

    private Date d_naissance_user;

    private String ville;

    private int num_tel_user;




    public User() {
    }

    public User (int id_user){
        this.id_user = id_user;
    }

    public User(int id_user, String nom_user, String prenom_user, String email, String mdp_user, Date d_naissance_user, String ville, int num_tel_user) {
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email = email;
        this.mdp_user = mdp_user;
        this.d_naissance_user = d_naissance_user;
        this.ville = ville;
        this.num_tel_user = num_tel_user;
    }

    public User(String nom_user, String prenom_user, String email, String mdp_user, Date d_naissance_user, String ville, int num_tel_user) {
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email = email;
        this.mdp_user = mdp_user;
        this.d_naissance_user = d_naissance_user;
        this.ville = ville;
        this.num_tel_user = num_tel_user;
    }




    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getPrenom_user() {
        return prenom_user;
    }

    public void setPrenom_user(String prenom_user) {
        this.prenom_user = prenom_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp_user() {
        return mdp_user;
    }

    public void setMdp_user(String mdp_user) {
        this.mdp_user = mdp_user;
    }

    public Date getD_naissance_user() {
        return d_naissance_user;
    }

    public void setD_naissance_user(Date d_naissance_user) {
        this.d_naissance_user = d_naissance_user;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getNum_tel_user() {
        return num_tel_user;
    }

    public void setNum_tel_user(int num_tel_user) {
        this.num_tel_user = num_tel_user;
    }

    @Override
    public String toString() {
        return "User{" +
                "nom_user='" + nom_user + '\'' +
                ", prenom_user='" + prenom_user + '\'' +
                ", email='" + email + '\'' +
                ", mdp_user='" + mdp_user + '\'' +
                ", d_naissance_user=" + d_naissance_user +
                ", ville='" + ville + '\'' +
                ", num_tel_user=" + num_tel_user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id_user == user.id_user && num_tel_user == user.num_tel_user && Objects.equals(nom_user, user.nom_user) && Objects.equals(prenom_user, user.prenom_user) && Objects.equals(email, user.email) && Objects.equals(mdp_user, user.mdp_user) && Objects.equals(d_naissance_user, user.d_naissance_user) && Objects.equals(ville, user.ville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_user, nom_user, prenom_user, email, mdp_user, d_naissance_user, ville, num_tel_user);
    }

    public String getPhoto_user() {
        return null;
    }
}