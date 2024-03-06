package edu.esprit.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Event {
    private int id_evenement;
    private String titre_evenement;
    private LocalDate d_debut_evenement;
    private LocalDate d_fin_evenement;
    private String description_evenement;
    private String lieu_evenement;
    private int id_user;
    private  Participations participants;

    public Event() {

    }

    public Event(int id_evenement, String titre_evenement, LocalDate d_debut_evenement, LocalDate d_fin_evenement, String description_evenement, String lieu_evenement, int id_user) {
        this.id_evenement = id_evenement;
        this.titre_evenement = titre_evenement;
        this.d_debut_evenement = d_debut_evenement;
        this.d_fin_evenement = d_fin_evenement;
        this.description_evenement = description_evenement;
        this.lieu_evenement = lieu_evenement;
        this.id_user = id_user;
    }

    public Event(String titre_evenement, LocalDate d_debut_evenement, LocalDate d_fin_evenement, String description_evenement, String lieu_evenement, int id_user) {
        this.titre_evenement = titre_evenement;
        this.d_debut_evenement = d_debut_evenement;
        this.d_fin_evenement = d_fin_evenement;
        this.description_evenement = description_evenement;
        this.lieu_evenement = lieu_evenement;
        this.id_user = id_user;
    }

    public int getId_event() {
        return id_evenement;
    }

    public void setId_event(int id_event) {
        this.id_evenement = id_event;
    }

    public String getTitre_evenement() {
        return titre_evenement;
    }

    public void setTitre_evenement(String titre_evenement) {
        this.titre_evenement = titre_evenement;
    }

    public LocalDate getD_debut_evenement() {
        return d_debut_evenement;
    }

    public void setD_debut_evenement(LocalDate d_debut_evenement) {
        this.d_debut_evenement = d_debut_evenement;
    }

    public LocalDate getD_fin_evenement() {
        return d_fin_evenement;
    }

    public void setD_fin_evenement(LocalDate d_fin_evenement) {
        this.d_fin_evenement = d_fin_evenement;
    }

    public String getDescription_evenement() {
        return description_evenement;
    }

    public void setDescription_evenement(String description_evenement) {
        this.description_evenement = description_evenement;
    }

    public String getLieu_evenement() {
        return lieu_evenement;
    }

    public void setLieu_evenement(String lieu_evenement) {
        this.lieu_evenement = lieu_evenement;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Participations getParticipants() {
        return participants;
    }

    public void setParticipants(Participations participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Event{" +
                "titre_evenement='" + titre_evenement + '\'' +
                ", d_debut_evenement=" + d_debut_evenement +
                ", d_fin_evenement=" + d_fin_evenement +
                ", description_evenement='" + description_evenement + '\'' +
                ", lieu_evenement='" + lieu_evenement + '\'' +
                ", id_user=" + id_user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return id_evenement == event.id_evenement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_evenement);
    }
}

