package edu.esprit.services;

import edu.esprit.entities.Event;
import edu.esprit.entities.Participation;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;
import edu.esprit.enums.CategorieEvenement;

import java.sql.*;
import java.util.*;


public class CrudEvent implements IService<Event> {
Connection cnx = DataSource.getInstance().getCnx();
    public ServiceUser serviceUser = new ServiceUser();
    public CrudEvent() {
        this.serviceUser = new ServiceUser();
    }


    @Override
    public void add(Event event) {
        String req = "INSERT INTO `evenements`(`titre_evenement`, `d_debut_evenement`, `d_fin_evenement`,  `description_evenement`,`lieu_evenement`, `id_user`,`image`,`categorieEvenement`) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, event.getTitre_evenement());
            ps.setTimestamp(2, Timestamp.valueOf(event.getD_debut_evenement().toLocalDateTime()));
            ps.setTimestamp(3, Timestamp.valueOf(event.getD_fin_evenement().toLocalDateTime()));
            ps.setString(4, event.getDescription_evenement());
            ps.setString(5, event.getLieu_evenement());
            ps.setInt(6, event.getUser().getId_user());
            ps.setString(7, event.getImage());
            ps.setString(8, event.getCategorieEvenement().name()); // Convertir la catégorie en chaîne de caractères
            ps.executeUpdate();
            System.out.println("Event added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void delete(Event event) {
        String req = "DELETE FROM `evenements` WHERE `id_evenement` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, event.getId_event());  // Mettez à jour l'appel à getId_event()
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event with ID " + event.getId_event() + " deleted successfully!");  // Mettez à jour l'appel à getId_event()
            } else {
                System.out.println("Event with ID " + event.getId_event() + " not found.");  // Mettez à jour l'appel à getId_event()
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void update(Event event) {
        String req = "UPDATE `evenements` SET `titre_evenement`=?, `d_debut_evenement`=?, `d_fin_evenement`=?, `description_evenement`=?, `lieu_evenement`=?, `id_user`=?, `image`=?, `categorieEvenement`=? WHERE `id_evenement`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, event.getTitre_evenement());  // Mettez à jour l'appel à getTitre_event()
            ps.setTimestamp(2, event.getD_debut_evenement());
            ps.setTimestamp(3, event.getD_fin_evenement());
            ps.setString(4, event.getDescription_evenement());
            ps.setString(5, event.getLieu_evenement());
            ps.setInt(6, event.getUser().getId_user());
            ps.setString(7, event.getImage());
            ps.setString(8, event.getCategorieEvenement().toString());
            ps.setInt(9, event.getId_event());  // Mettez à jour l'appel à getId_event()


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event with ID " + event.getId_event() + " updated successfully!");  // Mettez à jour l'appel à getId_event()
            } else {
                System.out.println("Event with ID " + event.getId_event() + " not found.");  // Mettez à jour l'appel à getId_event()
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {

    }


    @Override
    public Set<Event> getAll() {
        Set<Event> events = new HashSet<>();
        String req = "SELECT * FROM evenements";
        try (PreparedStatement ps = cnx.prepareStatement(req); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_evenement");
                String titre = rs.getString("titre_evenement");
                Timestamp dateDebut = rs.getTimestamp("d_debut_evenement");
                Timestamp dateFin = rs.getTimestamp("d_fin_evenement");
                String description = rs.getString("description_evenement");
                String lieu = rs.getString("lieu_evenement");
                int idUser = rs.getInt("id_user");
                String image = rs.getString("image");
                CategorieEvenement categorieEvenement = CategorieEvenement.valueOf(rs.getString("categorieEvenement"));

                Event event = new Event(id, titre, dateDebut, dateFin, description, lieu,  serviceUser.getOneByID(idUser), image, categorieEvenement);

                // Ajouter les participations à l'événement

                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }

    public Set<Participation> getParticipationsForEvent(int eventId) {
        Set<Participation> participations = new HashSet<>();
        String req = "SELECT * FROM `participation` WHERE id_evenement = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idParticipant = rs.getInt("id_user");
                    //String participantName = rs.getString("nom_user");
                    int idParticipation = rs.getInt("id_participation");
                    Participation participation = new Participation(idParticipation, new User(idParticipant), new Event(eventId));
                    participations.add(participation);
                    Event event = participation.getEvent();

                    // Ajoutez la participation à l'événement
                    if (event != null) {
                        event.addParticipant(participation);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return participations;
    }


//    public Map<Event, List<Participation>> getParticipantsForAllEvents() {
//        Map<Event, List<Participation>> participantsMap = new HashMap<>();
//
//        String req = "SELECT * FROM `participation`";
//        try (PreparedStatement ps = cnx.prepareStatement(req); ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                int idEvent = rs.getInt("id_evenement");
//                Event event = getOneByID(idEvent);
//
//                if (!participantsMap.containsKey(event)) {
//                    participantsMap.put(event, new ArrayList<>());
//                }
//
//                int idParticipant = rs.getInt("id_participant");
//                int idParticipation = rs.getInt("id_participation");
//
//                Participation participation = new Participation(idParticipation, idParticipant, idEvent);
//                participantsMap.get(event).add(participation);
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return participantsMap;
//    }




    @Override
    public Event getOneByID(int id) {
        String req = "SELECT * FROM `evenements` WHERE `id_evenement`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String titre = rs.getString("titre_evenement");
                    Timestamp dateDebut = rs.getTimestamp("d_debut_evenement");
                    Timestamp dateFin = rs.getTimestamp("d_fin_evenement");
                    String description = rs.getString("description_evenement");
                    String lieu = rs.getString("lieu_evenement");
                    int idUser = rs.getInt("id_user");
                    String image = rs.getString("image");
                    String participantsString = rs.getString("id_user");
                    CategorieEvenement categorieEvenement = CategorieEvenement.valueOf(rs.getString("categorieEvenement"));

                    Set<Participation> participants = getParticipationsForEvent(id);
                    System.out.println("Fetched participations: " + participants);
                    return new Event(id, titre, dateDebut, dateFin, description, lieu, serviceUser.getOneByID(idUser), image , participants,categorieEvenement);
                } else {
                    System.out.println("Event with ID " + id + " not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Event getOneByName(String name) {
        String req = "SELECT * FROM `evenements` WHERE `titre_evenement`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_evenement");
                    String titre = rs.getString("titre_evenement");
                    Timestamp dateDebut = rs.getTimestamp("d_debut_evenement");
                    Timestamp dateFin = rs.getTimestamp("d_fin_evenement");
                    String description = rs.getString("description_evenement");
                    String lieu = rs.getString("lieu_evenement");
                    int idUser = rs.getInt("id_user");
                    String image = rs.getString("image");
                    CategorieEvenement categorieEvenement = CategorieEvenement.valueOf(rs.getString("categorieEvenement"));

                    //Set<String> participants = participations.getParticipantsForEvent(id);
                    return new Event(titre,dateDebut, dateFin, description, lieu,serviceUser.getOneByID(idUser), image,categorieEvenement);
                } else {
                    System.out.println("Event with name " + name + " not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public List<Event> getEventsForUser(int userId) {
        List<Event> eventsForUser = new ArrayList<>();
        // Écrivez la requête SQL pour récupérer les événements associés à l'utilisateur par l'ID
        String query = "SELECT * FROM evenements WHERE id_user = ?";

        try {
            Connection cnx = DataSource.getInstance().getCnx();

            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Lire les données de l'événement depuis le résultat de la requête
                int eventId = rs.getInt("id_evenement");
                String titre = rs.getString("titre_evenement");
                Timestamp dateDebut = rs.getTimestamp("d_debut_evenement");
                Timestamp dateFin = rs.getTimestamp("d_fin_evenement");
                String description = rs.getString("description_evenement");
                String lieu = rs.getString("lieu_evenement");
                String image = rs.getString("image");
                CategorieEvenement categorieEvenement = CategorieEvenement.valueOf(rs.getString("categorieEvenement"));
                // Récupérer le nom de l'utilisateur associé à l'événement
                int idUser = rs.getInt("id_user");
                String userName = serviceUser.getOneByID(idUser).getNom_user();

                // Créer une instance d'Event avec les données lues
                Event event = new Event(eventId, titre, dateDebut, dateFin, description, lieu, serviceUser.getOneByID(userId), image,categorieEvenement);

                // Ajouter l'événement à la liste
                eventsForUser.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return eventsForUser;
    }

    public int getNumberOfParticipantsForEvent(int eventId) {
        String req = "SELECT COUNT(*) as totalParticipants FROM `participation` WHERE id_evenement = ?";
        int numberOfParticipants = 0;

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    numberOfParticipants = rs.getInt("totalParticipants");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du nombre de participants pour l'événement : " + e.getMessage());
        }

        return numberOfParticipants;
    }

    public Participation participer(User participant, Event event) {
        System.out.println("Event ID: " + event.getId_event());
        System.out.println("Participant ID: " + participant.getId_user());
        String req = "INSERT INTO `participation`(`id_user`, `id_evenement`) VALUES (?,?)";

        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, participant.getId_user());
            ps.setInt(2, event.getId_event());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int participationId = generatedKeys.getInt(1);
                System.out.println("Participant added to event! Participation ID: " + participationId);

                // Retournez la participation créée avec l'ID attribué
                return new Participation(participationId, participant.getId_user(), event.getId_event());
            } else {
                System.out.println("Failed to add participant to event!");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimez les détails de l'exception pour un diagnostic
            System.out.println("Error: " + e.getMessage());
        }

        return null;  // En cas d'échec
    }


    // Ajoutez une nouvelle méthode pour récupérer les événements par catégorie
    public List<Event> getEventsByCategory(CategorieEvenement categorieEvenement) {
        List<Event> events = new ArrayList<>();
        String req = "SELECT * FROM evenements";

        if (categorieEvenement != null) {
            req += " WHERE categorieEvenement = ?";
        }

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            if (categorieEvenement != null) {
                ps.setString(1, categorieEvenement.name());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event event = createEventFromResultSet(rs);
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }
    private Event createEventFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_evenement");
        String titre = rs.getString("titre_evenement");
        Timestamp dateDebut = rs.getTimestamp("d_debut_evenement");
        Timestamp dateFin = rs.getTimestamp("d_fin_evenement");
        String description = rs.getString("description_evenement");
        String lieu = rs.getString("lieu_evenement");
        int idUser = rs.getInt("id_user");
        String image = rs.getString("image");

        // ... code existant pour récupérer les données de l'événement

        CategorieEvenement categorieEvenement = CategorieEvenement.valueOf(rs.getString("categorieEvenement"));

        // ... code existant pour créer l'objet Event
        User user = serviceUser.getOneByID(idUser);
        Set<Participation> participants = getParticipationsForEvent(id);
        Event event = new Event(id, titre, dateDebut, dateFin, description, lieu, user, image, participants, categorieEvenement);

        return event;
    }


}
