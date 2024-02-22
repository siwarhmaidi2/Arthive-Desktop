package edu.esprit.services;

import edu.esprit.entities.Reaction;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceReaction implements IServiceReaction<Reaction> {
    Connection cnx = DataSource.getInstance().getCnx();
    private ServiceUser serviceUser = new ServiceUser();
    private ServicePublication servicePublication = new ServicePublication();

    @Override
    public void add(Reaction reaction) {
        String req = "INSERT INTO reactions (id_user, id_publication, d_ajout_reaction) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, reaction.getUser().getId_user());
            ps.setInt(2, reaction.getPublication().getId_publication());
            ps.setTimestamp(3, reaction.getDateAjoutReaction());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                reaction.setIdReaction(generatedKeys.getInt(1));
            }

            System.out.println("Reaction added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Reaction reaction) {
        String req = "UPDATE reactions SET id_user = ?, id_publication = ?, d_ajout_reaction = ? WHERE id_reaction = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, reaction.getUser().getId_user());
            ps.setInt(2, reaction.getPublication().getId_publication());
            ps.setTimestamp(3, reaction.getDateAjoutReaction());
            ps.setInt(4, reaction.getIdReaction());
            ps.executeUpdate();
            System.out.println("Reaction updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM reactions WHERE id_reaction = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Reaction deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Reaction getOneByID(int id) {
        String req = "SELECT * FROM reactions WHERE id_reaction = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int idReaction = resultSet.getInt("id_reaction");
                int idUser = resultSet.getInt("id_user");
                int idPublication = resultSet.getInt("id_publication");
                Timestamp dateAjoutReaction = resultSet.getTimestamp("d_ajout_reaction");

                Reaction reaction = new Reaction(idReaction, serviceUser.getOneByID(idUser),
                        servicePublication.getOneByID(idPublication), dateAjoutReaction);

                return reaction;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Set<Reaction> getAll() {
        Set<Reaction> reactions = new HashSet<>();

        String query = "SELECT * FROM reactions";
        try (
                Statement statement = cnx.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int idReaction = resultSet.getInt("id_reaction");
                int idUser = resultSet.getInt("id_user");
                int idPublication = resultSet.getInt("id_publication");
                Timestamp dateAjoutReaction = resultSet.getTimestamp("d_ajout_reaction");

                Reaction reaction = new Reaction(idReaction, serviceUser.getOneByID(idUser), servicePublication.getOneByID(idPublication), dateAjoutReaction);

                reactions.add(reaction);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reactions;
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
