package edu.esprit.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class Reaction {
    private int idReaction;
    private User user;
    private Publication publication;

    private Timestamp dateAjoutReaction;


    public Reaction() {
    }

    public Reaction(int idReaction, User user, Publication publication, Timestamp dateAjoutReaction) {
        this.idReaction = idReaction;
        this.user = user;
        this.publication = publication;
        this.dateAjoutReaction = dateAjoutReaction;
    }

    public Reaction(User user, Publication publication, Timestamp dateAjoutReaction) {
        this.user = user;
        this.publication = publication;
        this.dateAjoutReaction = dateAjoutReaction;
    }

    public int getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(int idReaction) {
        this.idReaction = idReaction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Timestamp getDateAjoutReaction() {
        return dateAjoutReaction;
    }

    public void setDateAjoutReaction(Timestamp dateAjoutReaction) {
        this.dateAjoutReaction = dateAjoutReaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reaction reaction = (Reaction) o;
        return idReaction == reaction.idReaction && Objects.equals(user, reaction.user) && Objects.equals(publication, reaction.publication) && Objects.equals(dateAjoutReaction, reaction.dateAjoutReaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReaction, user, publication, dateAjoutReaction);
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "user=" + user +
                ", publication=" + publication +
                ", dateAjoutReaction=" + dateAjoutReaction +
                '}';
    }
}