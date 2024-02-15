package edu.esprit.crud;

import edu.esprit.entities.Participations;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudParticipations implements ICrudParticipations<Participations> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Participations participations) {

        String req = "INSERT INTO `participations`(`id_user`, `id_evenement`) VALUES (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, participations.getId_participant());
            ps.setInt(2, participations.getId_event());
            ps.executeUpdate();
            System.out.println("Participation added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(Participations participations) {
        String req = "DELETE FROM `participations` WHERE 0";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, participations.getId_participant());
            ps.setInt(2, participations.getId_event());
            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Participation deleted!");
            } else {
                System.out.println("Participation not found or not deleted!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
