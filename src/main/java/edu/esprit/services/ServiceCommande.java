package edu.esprit.services;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Panier;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceCommande implements IService<Commande> {

    Connection cnx = DataSource.getInstance().getCnx();
    ServicePanier sp = new ServicePanier();

//    @Override
//    public void ajouter(Commande commande) {
//        String req = "INSERT INTO commandes (id_panier, nom_client, prenom_client, telephone, e_mail, adresse_livraison) VALUES (?, ?, ?, ?, ?, ?)";
//        try {
//            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, commande.getPanier().getId_Panier());
//            ps.setString(2, commande.getNom_client());
//            ps.setString(3, commande.getPrenom_client());
//            ps.setInt(4, commande.getTelephone());
//            ps.setString(5, commande.getE_mail());
//            ps.setString(6, commande.getAdresse_livraison());
//            ps.executeUpdate();
//
//            ResultSet rs = ps.getGeneratedKeys();
//            if (rs.next()) {
//                commande.setId_commande(rs.getInt(1));
//            }
//            System.out.println("Commande ajoutée !");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    @Override
    public Set<Commande> getAll() {
        Set<Commande> commandes = new HashSet<>();
        String req = "SELECT * FROM commandes";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_commande = rs.getInt("id_commande");
                int id_panier = rs.getInt("id_panier");
                String nom_client = rs.getString("nom_client");
                String prenom_client = rs.getString("prenom_client");
                int telephone = rs.getInt("telephone");
                String e_mail = rs.getString("e_mail");
                String adresse_livraison = rs.getString("adresse_livraison");
                Panier panier = sp.getOneByID(id_panier);
                Commande commande = new Commande(id_commande, panier, nom_client, prenom_client, telephone, e_mail, adresse_livraison);
                commandes.add(commande);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return commandes;
    }

    @Override
    public Commande getOneByID(int id) {
        Commande commande = null;
        String req = "SELECT * FROM commandes WHERE id_commande = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_commande = rs.getInt("id_commande");
                int id_panier = rs.getInt("id_panier");
                String nom_client = rs.getString("nom_client");
                String prenom_client = rs.getString("prenom_client");
                int telephone = rs.getInt("telephone");
                String e_mail = rs.getString("e_mail");
                String adresse_livraison = rs.getString("adresse_livraison");
                Panier panier = sp.getOneByID(id_panier);
                commande = new Commande(id_commande, panier, nom_client, prenom_client, telephone, e_mail, adresse_livraison);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return commande;
    }

    @Override
    public void update(Commande commande) {
        String req = "UPDATE commandes SET id_panier = ?, nom_client = ?, prenom_client = ?, telephone = ?, e_mail = ?, adresse_livraison = ? WHERE id_commande = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, commande.getPanier().getId_Panier());
            ps.setString(2, commande.getNom_client());
            ps.setString(3, commande.getPrenom_client());
            ps.setInt(4, commande.getTelephone());
            ps.setString(5, commande.getE_mail());
            ps.setString(6, commande.getAdresse_livraison());
            ps.setInt(7, commande.getId_commande());
            ps.executeUpdate();
            System.out.println("Commande modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void add(Commande commande) {
        // Récupérer tous les paniers de l'utilisateur associé à cette commande
        Set<Panier> paniersUtilisateur = sp.getAllByUser(commande.getPanier().getUser());

        // Pour chaque panier, ajouter une commande
        for (Panier panier : paniersUtilisateur) {
            String req = "INSERT INTO commandes (id_panier, nom_client, prenom_client, telephone, e_mail, adresse_livraison) VALUES (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, panier.getId_Panier());  // Utilisez l'ID du panier actuel
                ps.setString(2, commande.getNom_client());
                ps.setString(3, commande.getPrenom_client());
                ps.setInt(4, commande.getTelephone());
                ps.setString(5, commande.getE_mail());
                ps.setString(6, commande.getAdresse_livraison());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idCommande = rs.getInt(1);
                    System.out.println("Commande ajoutée avec ID : " + idCommande);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM commandes WHERE id_commande = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commande supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
