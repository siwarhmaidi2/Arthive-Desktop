package edu.esprit.services;

import edu.esprit.entities.Produit;
import edu.esprit.enums.TypeCategorie;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ServiceProduit implements IServiceProduit<Produit> {

    private Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Produit produit) {
        String req = "INSERT INTO `produits`(`nom_produit`, `prix_produit`, `description_produit`, `disponibilite`, `categ_produit`, `stock_produit`, `id_user`, `d_publication_produit`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, produit.getNom_produit());
            ps.setDouble(2, produit.getPrix_produit());
            ps.setString(3, produit.getDescription_produit());
            ps.setBoolean(4, produit.isDisponibilite());
            ps.setString(5, produit.getCateg_produit().toString());
            ps.setInt(6, produit.getStock_produit());
            ps.setInt(7, produit.getId_user());
            ps.setObject(8, produit.getD_publication_produit());
            ps.executeUpdate();
            System.out.println("Produit ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Produit produit) {
        String req = "UPDATE `produits` SET `nom_produit`=?,`prix_produit`=?,`description_produit`=?,`disponibilite`=?,`categ_produit`=?,`stock_produit`=?,`id_user`=?,`d_publication_produit`=? WHERE `id_produit`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, produit.getNom_produit());
            ps.setDouble(2, produit.getPrix_produit());
            ps.setString(3, produit.getDescription_produit());
            ps.setBoolean(4, produit.isDisponibilite());
            ps.setString(5, produit.getCateg_produit().toString());
            ps.setInt(6, produit.getStock_produit());
            ps.setInt(7, produit.getId_user());
            ps.setObject(8, produit.getD_publication_produit());
            ps.setInt(9, produit.getId_produit());
            ps.executeUpdate();
            System.out.println("Produit modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `produits` WHERE `id_produit`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Produit supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<Produit> getAll() {
        Set<Produit> produits = new HashSet<>();
        String req = "SELECT * FROM `produits`";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_produit = rs.getInt("id_produit");
                String nom_produit = rs.getString("nom_produit");
                double prix_produit = rs.getDouble("prix_produit");
                String description_produit = rs.getString("description_produit");
                boolean disponibilite = rs.getBoolean("disponibilite");
                TypeCategorie categ_produit = TypeCategorie.valueOf(rs.getString("categ_produit"));
                int stock_produit = rs.getInt("stock_produit");
                int id_user = rs.getInt("id_user");
                LocalDateTime d_publication_produit = rs.getObject("d_publication_produit", LocalDateTime.class);
                Produit p = new Produit(id_produit, nom_produit, prix_produit, description_produit, disponibilite, categ_produit, stock_produit, id_user, d_publication_produit);
                produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return produits;
    }

    @Override
    public Produit getOneByID(int id) {
        Produit produit = null;
        String req = "SELECT * FROM `produits` WHERE `id_produit`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nom_produit = rs.getString("nom_produit");
                double prix_produit = rs.getDouble("prix_produit");
                String description_produit = rs.getString("description_produit");
                boolean disponibilite = rs.getBoolean("disponibilite");
                TypeCategorie categ_produit = TypeCategorie.valueOf(rs.getString("categ_produit"));
                int stock_produit = rs.getInt("stock_produit");
                int id_user = rs.getInt("id_user");
                LocalDateTime d_publication_produit = rs.getObject("d_publication_produit", LocalDateTime.class);
                produit = new Produit(id, nom_produit, prix_produit, description_produit, disponibilite, categ_produit, stock_produit, id_user, d_publication_produit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return produit;
    }
}