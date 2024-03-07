package edu.esprit.entities ;


import java.util.List;
import java.util.Objects;

public class Panier {

    private int id_Panier;
    private User user;
    private List<Produit> produits;

    public Panier() {

    }

    public Panier(User user, List<Produit> produits) {
        this.user = user;
        this.produits = produits;
    }

    public Panier(int id_Panier, User user, List<Produit> produits) {
        this.id_Panier = id_Panier;
        this.user = user;
        this.produits = produits;
    }

    public int getId_Panier() {
        return id_Panier;
    }

    public void setId_Panier(int id_Panier) {
        this.id_Panier = id_Panier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "user=" + user +
                ", produits=" + produits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panier panier = (Panier) o;
        return id_Panier == panier.id_Panier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_Panier);
    }
}
