package edu.esprit.crud;

import edu.esprit.entities.Event;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CrudEvent implements ICrud<Event> {
Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(Event event) {
        String req = "INSERT INTO `evenements`(`titre_evenement`, `d_debut_evenement`, `d_fin_evenement`,  `description_evenement`,`lieu_evenement`, `id_user`) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, event.getTitre_evenement());
            ps.setDate(2, Date.valueOf(event.getD_debut_evenement()));
            ps.setDate(3, Date.valueOf(event.getD_fin_evenement()));
            ps.setString(4, event.getDescription_evenement());
            ps.setString(5, event.getLieu_evenement());
            ps.setInt(6, event.getId_user());
            ps.executeUpdate();
            System.out.println("Event added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    @Override
    public void supprimer(Event event) {
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
    public void modifier(Event event) {
        String req = "UPDATE `evenements` SET `titre_evenement`=?, `d_debut_evenement`=?, `d_fin_evenement`=?, `description_evenement`=?, `lieu_evenement`=?, `id_user`=? WHERE `id_evenement`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, event.getTitre_evenement());  // Mettez à jour l'appel à getTitre_event()
            ps.setDate(2, Date.valueOf(event.getD_debut_evenement()));
            ps.setDate(3, Date.valueOf(event.getD_fin_evenement()));
            ps.setString(4, event.getDescription_evenement());
            ps.setString(5, event.getLieu_evenement());
            ps.setInt(6, event.getId_user());
            ps.setInt(7, event.getId_event());  // Mettez à jour l'appel à getId_event()

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
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        String req = "SELECT * FROM evenements";
        try (PreparedStatement ps = cnx.prepareStatement(req); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_evenement");
                String titre = rs.getString("titre_evenement");
                LocalDate dateDebut = rs.getDate("d_debut_evenement").toLocalDate();
                LocalDate dateFin = rs.getDate("d_fin_evenement").toLocalDate();
                String description = rs.getString("description_evenement");
                String lieu = rs.getString("lieu_evenement");
                int user = rs.getInt("id_user");
                String participantsString = rs.getString("id_user");

                Event event = new Event(id, titre, dateDebut, dateFin, description, lieu, user);
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }


    @Override
    public Event getOneByID(int id) {
        String req = "SELECT * FROM `evenements` WHERE `id_evenement`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String titre = rs.getString("titre_evenement");
                    LocalDate dateDebut = rs.getDate("d_debut_evenement").toLocalDate();
                    LocalDate dateFin = rs.getDate("d_fin_evenement").toLocalDate();
                    String description = rs.getString("description_evenement");
                    String lieu = rs.getString("lieu_evenement");
                    int user = rs.getInt("id_user");
                    return new Event(id, titre, dateDebut, dateFin, description, lieu, user);
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
    @Override
    public Event getOneByName(String name) {
        String req = "SELECT * FROM `evenements` WHERE `titre_evenement`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_evenement");
                    LocalDate dateDebut = rs.getDate("d_debut_evenement").toLocalDate();
                    LocalDate dateFin = rs.getDate("d_fin_evenement").toLocalDate();
                    String description = rs.getString("description_evenement");
                    String lieu = rs.getString("lieu_evenement");
                    int user = rs.getInt("id_user");
                    return new Event(id, name, dateDebut, dateFin, description, lieu, user);
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

}
