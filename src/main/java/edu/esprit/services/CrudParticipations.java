package edu.esprit.services;

import edu.esprit.entities.Event;
import edu.esprit.entities.Participation;
import edu.esprit.services.IService;
import edu.esprit.utils.DataSource;
import edu.esprit.entities.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CrudParticipations implements IService<Participation> {
    private static CrudParticipations instance;
    private Connection cnx = DataSource.getInstance().getCnx();
    private ServiceUser serviceUser = new ServiceUser();
    private CrudEvent crudEvent = new CrudEvent();

    public void add(Participation participation) {
        Event event = participation.getEvent();

        if (event == null) {
            System.out.println("Error: Event is null for the participation.");
            return;
        }

        int eventId = event.getId_evenement();
        int participantId = participation.getUser().getId_user();

        String req = "INSERT INTO `participation`(`id_evenement`, `id_user`) VALUES (?,?)";

        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, eventId);
            ps.setInt(2, participantId);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int participationId = generatedKeys.getInt(1);
                System.out.println("Participant added to event! Participation ID: " + participationId);

                // Mettez à jour l'objet Participation avec l'ID attribué
                participation.setId_participation(participationId);

                // Mettez à jour l'objet Event avec le participant ajouté
                event.addParticipant(participation);

            } else {
                System.out.println("Failed to add participant to event!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Participation participation) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Participation getOneByID(int id) {
        return null;
    }

    @Override
    public Set<Participation> getAll() {
        return null;
    }



    public void delete(Participation participation) {
        String req = "DELETE FROM participation WHERE id_participation = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, participation.getId_participation());
            ps.executeUpdate();
            System.out.println("participation supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    public Set<String> getParticipantsForEvent(int eventId) {
        Set<String> participants = new HashSet<>();
        String req = "SELECT id_user FROM `participation` WHERE id_evenement = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idUser = rs.getInt("id_user");
                    String userName = serviceUser.getOneByID(idUser).getNom_user();
                    participants.add(userName);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return participants;
    }
    public boolean isParticipant(int participantId, int eventId) {
        String req = "SELECT * FROM `participation` WHERE id_evenement = ? AND id_user = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, eventId);
            ps.setInt(2, participantId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();  // Retourne true si l'utilisateur participe, sinon false
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
//    public Participation isParticipant(int participantId, int eventId) {
//        String req = "SELECT * FROM `participation` WHERE id_evenement = ? AND id_user = ?";
//        try (PreparedStatement ps = cnx.prepareStatement(req)) {
//            ps.setInt(1, eventId);
//            ps.setInt(2, participantId);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    int idParticipation = rs.getInt("id_participation");
//                    return new Participation(idParticipation, new User(participantId), new Event(eventId));
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }

//    public Participation isParticipant(int participantId, int eventId) {
//        String req = "SELECT * FROM `participation` WHERE id_evenement = ? AND id_user = ?";
//        try (PreparedStatement ps = cnx.prepareStatement(req)) {
//            ps.setInt(1, eventId);
//            ps.setInt(2, participantId);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    int idParticipation = rs.getInt("id_participation");
//                    return new Participation(idParticipation, new User(participantId), new Event(eventId));
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }

    public Participation getParticipation(int eventId, int participantId) {
        String req = "SELECT * FROM `participation` WHERE id_evenement = ? AND id_user = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, eventId);
            ps.setInt(2, participantId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idParticipation = rs.getInt("id_participation");
                    return new Participation(idParticipation, new User(participantId), new Event(eventId));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
