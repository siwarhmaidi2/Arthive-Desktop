package edu.esprit.services;

import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicePublication implements IServicePublication<Publication> {
    Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void add(Publication publication) {
        String req = "INSERT INTO `publications`(`contenu_publication`, `d_creation_publication`, `id_user`)VALUES (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, publication.getContenu_publication());
            ps.setTimestamp(2, publication.getD_creation_publication());
            ps.setInt(3, publication.getId_user());
            ps.executeUpdate();
            System.out.println("Publication added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Publication publication) {
            String req = "UPDATE `publications` SET `contenu_publication` = ?, `d_creation_publication` = ?, `id_user` = ? WHERE `id_publication` = ?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, publication.getContenu_publication());
                ps.setTimestamp(2, publication.getD_creation_publication());
                ps.setInt(3, publication.getId_user());
                ps.setInt(4, publication.getId_publication());
                ps.executeUpdate();
                System.out.println("Publication updated !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM `publications` WHERE `id_publication` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Publication deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Publication getOneByID(int id) {
        String req = "SELECT * FROM `publications` WHERE `id_publication` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int idPublication = resultSet.getInt("id_publication");
                String contenuPublication = resultSet.getString("contenu_publication");
                Timestamp dateCreationPublication = resultSet.getTimestamp("d_creation_publication");
                int idUser = resultSet.getInt("id_user");
                return new Publication(idPublication, contenuPublication, dateCreationPublication, idUser);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Set<Publication> getAll() {
       Set<Publication> publications = new HashSet<>();

        String query = "SELECT * FROM publications";
        try (
                Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int idPublication = resultSet.getInt("id_publication");
                String contenuPublication = resultSet.getString("contenu_publication");
                Timestamp dateCreationPublication = resultSet.getTimestamp("d_creation_publication");
                int idUser = resultSet.getInt("id_user");
                Publication publication = new Publication(idPublication, contenuPublication, dateCreationPublication, idUser);
                publications.add(publication);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return publications;
    }




}