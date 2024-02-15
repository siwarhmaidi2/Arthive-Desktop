package edu.esprit.services;

import edu.esprit.entities.ReactionCommentaire;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceReactionCommentaire implements IServiceReactionCommentaire<ReactionCommentaire> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void add(ReactionCommentaire reactionCommentaire) {
        String req = "INSERT INTO reactions_commentaires (id_user, id_publication, id_commentaire, d_ajout_reaction_commentaire) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, reactionCommentaire.getIdUser());
            ps.setInt(2, reactionCommentaire.getIdPublication());
            ps.setInt(3, reactionCommentaire.getIdCommentaire());
            ps.setTimestamp(4, reactionCommentaire.getDateAjoutReactionCommentaire()); // Assuming you have this attribute in your ReactionCommentaire class
            ps.executeUpdate();
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
                ReactionCommentaire reactionCommentaire = new ReactionCommentaire(idRC, idUser, idPublication, idCommentaire, dateAjoutReactionCommentaire);
                rc.add(reactionCommentaire);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rc;

    }
}
