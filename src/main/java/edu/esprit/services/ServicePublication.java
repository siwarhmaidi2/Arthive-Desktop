package edu.esprit.services;

import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicePublication implements IServicePublication<Publication> {
    Connection cnx = DataSource.getInstance().getCnx();

    private ServiceUser serviceUser = new ServiceUser();

    public ServicePublication() {
    this.serviceUser = new ServiceUser();
    }


    @Override
    public void add(Publication publication) {
        String req = "INSERT INTO `publications`(`contenu_publication`, `d_creation_publication`, `id_user`, `url_file`) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, publication.getContenu_publication());
            ps.setTimestamp(2, publication.getD_creation_publication());
            ps.setInt(3, publication.getUser().getId_user());
            ps.setString(4, publication.getUrl_file());

            ps.executeUpdate();
            System.out.println("Publication added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Publication publication) {
            String req = "UPDATE `publications` SET `contenu_publication` = ?, `d_creation_publication` = ?, `id_user` = ?, `url_file`= ? WHERE `id_publication` = ?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, publication.getContenu_publication());
                ps.setTimestamp(2, publication.getD_creation_publication());
                ps.setInt(3, publication.getUser().getId_user());
                ps.setString(4, publication.getUrl_file());
                ps.setInt(5, publication.getId_publication());
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
        Publication publication = null;
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
                String urlFile = resultSet.getString("url_file");



                // Check if User retrieval is successful
                User user = serviceUser.getOneByID(idUser);

                if (user != null) {
                    publication = new Publication(idPublication, contenuPublication, urlFile, dateCreationPublication, user);
                } else {
                    System.out.println("User with ID " + idUser + " not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return publication;
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
                String urlFile = resultSet.getString("url_file");
                Publication publication = new Publication(idPublication, contenuPublication, urlFile,dateCreationPublication, serviceUser.getOneByID(idUser));
                publications.add(publication);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return publications;
    }



    // Helper method to get the current likes count for a publication
    public int getCurrentLikesCount(int publicationId) {
        String likesQuery = "SELECT COUNT(*) FROM reactions WHERE id_publication = ?";
        try {
            PreparedStatement likesStatement = cnx.prepareStatement(likesQuery);
            likesStatement.setInt(1, publicationId);
            ResultSet likesResult = likesStatement.executeQuery();

            if (likesResult.next()) {
                return likesResult.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0; // Default to 0 if there's an error or no likes found
    }



    public int addLike(int publicationId, int userId) {
        if (!hasUserLiked(publicationId, userId)) {
            String query = "INSERT INTO reactions (id_user, id_publication, d_ajout_reaction) VALUES (?, ?, CURRENT_TIMESTAMP)";
            try {
                PreparedStatement ps = cnx.prepareStatement(query);
                ps.setInt(1, userId);
                ps.setInt(2, publicationId);
                ps.executeUpdate();
                return 1; // Indicate success
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return 0; // Indicate failure or that the user has already liked
    }

    public int removeLike(int publicationId, int userId) {
        if (hasUserLiked(publicationId, userId)) {
            String query = "DELETE FROM reactions WHERE id_user = ? AND id_publication = ?";
            try {
                PreparedStatement ps = cnx.prepareStatement(query);
                ps.setInt(1, userId);
                ps.setInt(2, publicationId);
                ps.executeUpdate();
                return 1; // Indicate success
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return 0; // Indicate failure or that the user hasn't liked
    }

    private boolean hasUserLiked(int publicationId, int userId) {
        String query = "SELECT COUNT(*) FROM reactions WHERE id_user = ? AND id_publication = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, publicationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }



}