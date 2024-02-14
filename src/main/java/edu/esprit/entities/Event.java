package edu.esprit.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Event {
    private int id;
    private String titre;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;
    private String lieu;
    private String user;
    private List<String> participants;

    public Event() {}

    public Event(String titre, LocalDate dateDebut, LocalDate dateFin, String description, String lieu, String user, List<String> participants) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.lieu = lieu;
        this.user = user;
        this.participants = participants;
    }

    public Event(int id, String titre, LocalDate dateDebut, LocalDate dateFin, String description, String lieu, String user, List<String> participants) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.lieu = lieu;
        this.user = user;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Event{" +
                "titre='" + titre + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", user='" + user + '\'' +
                ", participants=" + participants +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

