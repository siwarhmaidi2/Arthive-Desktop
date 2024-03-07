package edu.esprit.services;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.enums.TypeCategorie;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceProduit implements IService<Produit> {

    Connection cnx = DataSource.getInstance().getCnx();

       ServiceUser su = new ServiceUser();

    @Override
    public void add(Produit produit) {
        String req = "INSERT INTO produits (id_user, nom_produit, image_produit, prix_produit, description_produit, disponibilite, categ_produit, stock_produit, d_publication_produit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS); //RETURN_GENERATED_KEYS bech yraja3lek la valeur mta3 attribut auto incremended
            ps.setInt(1, produit.getUser().getId_user()); // Accès à l'id_user à partir de l'objet User
            ps.setString(2, produit.getNom_produit());
            ps.setString(3, produit.getImage_produit());
            ps.setDouble(4, produit.getPrix_produit());
            ps.setString(5, produit.getDescription_produit());
            ps.setBoolean(6, produit.isDisponibilite());
            ps.setString(7, produit.getCateg_produit().name());
            ps.setInt(8, produit.getStock_produit());
            ps.setTimestamp(9, produit.getD_publication_produit());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                produit.setId_produit(rs.getInt(1));
            }
            System.out.println("Produit ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

}

    @Override
    public Set<Produit> getAll() {
        Set<Produit> produits = new HashSet<>();
        String req = "SELECT * FROM produits";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_produit = rs.getInt("id_produit");
                int id_user = rs.getInt("id_user");
                String nom_produit = rs.getString("nom_produit");
                String image_produit = rs.getString("image_produit");
                double prix_produit = rs.getDouble("prix_produit");
                String description_produit = rs.getString("description_produit");
                boolean disponibilite = rs.getBoolean("disponibilite");
                TypeCategorie categ_produit = TypeCategorie.valueOf(rs.getString("categ_produit"));
                int stock_produit = rs.getInt("stock_produit");
                Timestamp d_publication_produit = rs.getTimestamp("d_publication_produit");
                User user = su.getOneByID(id_user);
                Produit produit = new Produit(id_produit,user, nom_produit, image_produit, prix_produit, description_produit, disponibilite, categ_produit, stock_produit, d_publication_produit);
                produits.add(produit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return produits;
    }

    @Override
    public Produit getOneByID(int id) {
        Produit produit = null;
        String req = "SELECT * FROM produits WHERE id_produit = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_produit = rs.getInt("id_produit");
                int id_user = rs.getInt("id_user");
                String nom_produit = rs.getString("nom_produit");
                String image_produit = rs.getString("image_produit");
                double prix_produit = rs.getDouble("prix_produit");
                String description_produit = rs.getString("description_produit");
                boolean disponibilite = rs.getBoolean("disponibilite");
                TypeCategorie categ_produit = TypeCategorie.valueOf(rs.getString("categ_produit"));
                int stock_produit = rs.getInt("stock_produit");
                Timestamp d_publication_produit = rs.getTimestamp("d_publication_produit");
                User user = su.getOneByID(id_user);
                produit = new Produit(id_produit, user, nom_produit, image_produit, prix_produit, description_produit, disponibilite, categ_produit, stock_produit, d_publication_produit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return produit;
    }

    @Override
    public void update(Produit produit) {
        String req = "UPDATE produits SET id_user = ?, nom_produit = ?, image_produit = ?, prix_produit = ?, description_produit = ?, disponibilite = ?, categ_produit = ?, stock_produit = ?, d_publication_produit = ? WHERE id_produit = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, produit.getUser().getId_user());
            ps.setString(2, produit.getNom_produit());
            ps.setString(3, produit.getImage_produit());
            ps.setDouble(4, produit.getPrix_produit());
            ps.setString(5, produit.getDescription_produit());
            ps.setBoolean(6, produit.isDisponibilite());
            ps.setString(7, produit.getCateg_produit().name());
            ps.setInt(8, produit.getStock_produit());
            ps.setTimestamp(9, produit.getD_publication_produit());
            ps.setInt(10, produit.getId_produit());
            ps.executeUpdate();
            System.out.println("Produit modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM produits WHERE id_produit = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Produit supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Set<Produit> getProduitsByUser(User user) {
        Set<Produit> produits = new HashSet<>();
        String req = "SELECT * FROM produits WHERE id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId_user());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId_produit(rs.getInt("id_produit"));
                produit.setNom_produit(rs.getString("nom_produit"));
                produit.setDescription_produit(rs.getString("description_produit"));
                produit.setPrix_produit(rs.getDouble("prix_produit"));
                produit.setStock_produit(rs.getInt("stock_produit"));
                produit.setD_publication_produit(rs.getTimestamp("d_publication_produit"));
                produit.setImage_produit(rs.getString("image_produit"));
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }


}
