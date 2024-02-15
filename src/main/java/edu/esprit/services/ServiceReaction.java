package edu.esprit.services;

import edu.esprit.entities.Reaction;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceReaction implements IServiceReaction<Reaction> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void add(Reaction reaction) {
        String req = "INSERT INTO reactions (id_user, id_publication, d_ajout_reaction) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, reaction.getIdUser());
            ps.setInt(2, reaction.getIdPublication());
            ps.setTimestamp(3, reaction.getDateAjoutReaction());
            ps.executeUpdate();
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
            ps.setInt(1, reaction.getIdUser());
            ps.setInt(2, reaction.getIdPublication());
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
                return new Reaction(idReaction, idUser, idPublication, dateAjoutReaction);
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
                Reaction reaction = new Reaction(idReaction, idUser, idPublication, dateAjoutReaction);
                reactions.add(reaction);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reactions;
    }
}

