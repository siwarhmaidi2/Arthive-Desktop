package edu.esprit.services;

import edu.esprit.entities.Groupe;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceGroupe implements IService<Groupe> {
    Connection cnx = DataSource.getInstance().getCnx();

    private ServiceUser serviceUser = new ServiceUser();

    public ServiceGroupe() {
        this.serviceUser = new ServiceUser();
    }

    public void ajouter(Groupe groupe) {
        String req = "INSERT INTO `groups`(`nom_group`, `description_group`, `id_user`, `image`) VALUES (?,?,?,?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, groupe.getNom_group());
            ps.setString(2, groupe.getDescription_group());
            ps.setInt(3, groupe.getId_user().getId_user());
            ps.setString(4, groupe.getImage());
            ps.executeUpdate();
            System.out.println("Groupe " + groupe.getNom_group() + " added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(Groupe groupe) {
        String req = "UPDATE `groups` SET `nom_group`=?, `description_group`=?, `image`=? WHERE `id_group`=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, groupe.getNom_group());
            ps.setString(2, groupe.getDescription_group());
            ps.setString(3, groupe.getImage());
            ps.setInt(4, groupe.getId_group());
            ps.executeUpdate();
            System.out.println("Groupe with ID " + groupe.getId_group() + " updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimer(int id) {
        String req = "DELETE FROM `groups` WHERE `id_group`=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Groupe with ID " + id + " deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<Groupe> getAll() {
        Set<Groupe> groupes = new HashSet<>();

        String req = "SELECT * FROM `groups`";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt("id_group");
                String nom = rs.getString("nom_group");
                String description = rs.getString("description_group");
                String image = rs.getString("image");

                ServiceUser SV = new ServiceUser();
                User user = SV.getOneByID(rs.getInt("id_user"));

                Groupe groupe = new Groupe();
                groupe.setId_group(id);
                groupe.setNom_group(nom);
                groupe.setDescription_group(description);
                groupe.setId_user(user);
                groupe.setImage(image);

                groupes.add(groupe);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return groupes;
    }

    @Override
    public Groupe getOneByID(int id) {
        String req = "SELECT * FROM `groups` WHERE `id_group`=?";
        Groupe groupe = null;

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                groupe = new Groupe();
                groupe.setId_group(rs.getInt("id_group"));
                groupe.setNom_group(rs.getString("nom_group"));
                groupe.setDescription_group(rs.getString("description_group"));
                groupe.setImage(rs.getString("image"));
                ServiceUser SV = new ServiceUser();
                User user = SV.getOneByID(rs.getInt("id_user"));
                groupe.setId_user(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return groupe;
    }

    public Groupe getOneByNom(String nom) {
        String req = "SELECT * FROM `groups` WHERE `nom_group`=?";
        Groupe groupe = null;

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                groupe = new Groupe();
                groupe.setId_group(rs.getInt("id_group"));
                groupe.setNom_group(rs.getString("nom_group"));
                groupe.setDescription_group(rs.getString("description_group"));
                groupe.setImage(rs.getString("image"));
                ServiceUser SV = new ServiceUser();
                User user = SV.getOneByID(rs.getInt("id_user"));
                groupe.setId_user(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return groupe;
    }

    @Override
    public void add(Groupe groupe) {
        ajouter(groupe);
    }

    @Override
    public void update(Groupe groupe) {
        modifier(groupe);
    }

    @Override
    public void delete(int id) {
        supprimer(id);
    }
}
