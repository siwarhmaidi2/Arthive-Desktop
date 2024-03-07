package edu.esprit.entities;

import java.util.Objects;

public class Commande {
    private int id_commande ;
    private Panier panier ;
    private String nom_client ;
    private String prenom_client ;
    private int telephone ;
    private String e_mail ;
    private String adresse_livraison ;

    public Commande() {
    }

    public Commande(int id_commande, Panier panier, String nom_client, String prenom_client, int telephone, String e_mail, String adresse_livraison) {
        this.id_commande = id_commande;
        this.panier = panier;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.telephone = telephone;
        this.e_mail = e_mail;
        this.adresse_livraison = adresse_livraison;
    }

    public Commande(Panier panier, String nom_client, String prenom_client, int telephone, String e_mail, String adresse_livraison) {
        this.panier = panier;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.telephone = telephone;
        this.e_mail = e_mail;
        this.adresse_livraison = adresse_livraison;
    }

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getAdresse_livraison() {
        return adresse_livraison;
    }

    public void setAdresse_livraison(String adresse_livraison) {
        this.adresse_livraison = adresse_livraison;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "panier=" + panier +
                ", nom_client='" + nom_client + '\'' +
                ", prenom_client='" + prenom_client + '\'' +
                ", telephone=" + telephone +
                ", e_mail='" + e_mail + '\'' +
                ", adresse_livraison='" + adresse_livraison + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return id_commande == commande.id_commande;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_commande);
    }
}
