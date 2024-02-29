package edu.esprit.services;

import edu.esprit.entities.ReactionCommentaire;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceReactionCommentaire implements IServiceReactionCommentaire<ReactionCommentaire> {

    Connection cnx = DataSource.getInstance().getCnx();
    private ServiceUser serviceUser = new ServiceUser();
    private ServicePublication servicePublication = new ServicePublication();
    private ServiceCommentaire serviceCommentaire = new ServiceCommentaire();

    @Override
    public void add(ReactionCommentaire reactionCommentaire) {
        String req = "INSERT INTO reactions_commentaires (id_user, id_publication, id_commentaire, d_ajout_reaction_commentaire) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, reactionCommentaire.getUser().getId_user());
            ps.setInt(2, reactionCommentaire.getPublication().getId_publication());
            ps.setInt(3, reactionCommentaire.getCommentaire().getIdCommentaire());
            ps.setTimestamp(4, reactionCommentaire.getDateAjoutReactionCommentaire());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                reactionCommentaire.setIdReactionCommentaire(generatedKeys.getInt(1));
            }

            System.out.println("ReactionCommentaire added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM reactions_commentaires WHERE id_reaction_commentaire = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("ReactionCommentaire deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<ReactionCommentaire> getAll() {
        Set<ReactionCommentaire> rc = new HashSet<>();

        String query = "SELECT * FROM reactions_commentaires";
        try (
                Statement statement = cnx.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int idRC = resultSet.getInt("id_reaction_commentaire");
                int idUser = resultSet.getInt("id_user");
                int idPublication = resultSet.getInt("id_publication");
                int idCommentaire = resultSet.getInt("id_commentaire");
                Timestamp dateAjoutReactionCommentaire = resultSet.getTimestamp("d_ajout_reaction_commentaire");

                ReactionCommentaire reactionCommentaire = new ReactionCommentaire(idRC,  serviceCommentaire.getOneByID(idCommentaire),serviceUser.getOneByID(idUser),
                        servicePublication.getOneByID(idPublication), dateAjoutReactionCommentaire);

                rc.add(reactionCommentaire);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rc;
    }

    public int getLikesCountForComment(int commentId) {
        String query = "SELECT COUNT(*) FROM reactions_commentaires WHERE id_commentaire = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, commentId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0; // Return 0 if there's an issue or no likes found
    }
}
