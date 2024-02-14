package edu.esprit.entities;


import edu.esprit.enums.TypeCategorie;

import java.util.Objects;

public class Produit {
    private int id;
    private String nom;
    //private User artiste;
    private double prix;
    private String description;
    private int stock;
    private boolean disponible;
    private TypeCategorie categorie;

    public Produit() {
    }

    public Produit(String nom /*User artiste*/ , double prix, String description, int stock, boolean disponible, TypeCategorie categorie) {
        this.nom = nom;
       // this.artiste = artiste;
        this.prix = prix;
        this.description = description;
        this.stock = stock;
        this.disponible = disponible;
        this.categorie = categorie;
    }

    public Produit(int id, String nom /*User artiste*/, double prix, String description, int stock, boolean disponible, TypeCategorie categorie) {
        this.id = id;
        this.nom = nom;
       // this.artiste = artiste;
        this.prix = prix;
        this.description = description;
        this.stock = stock;
        this.disponible = disponible;
        this.categorie = categorie;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public TypeCategorie getCategorie() {
        return categorie;
    }

    public void setCategorie(TypeCategorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "nom='" + nom + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", disponible=" + disponible +
                ", categorie=" + categorie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return Objects.equals(id, produit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
