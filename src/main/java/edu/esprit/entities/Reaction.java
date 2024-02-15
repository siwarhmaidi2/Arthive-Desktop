package edu.esprit.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class Reaction {
    private int idReaction;
    private int idUser;
    private int idPublication;

    private Timestamp dateAjoutReaction;

    public Reaction() {
    }

    public Reaction(int idReaction, int idUser, int idPublication, int idCommentaire, Timestamp dateAjoutReaction) {
        this.idReaction = idReaction;
        this.idUser = idUser;
        this.idPublication = idPublication;
        this.dateAjoutReaction = dateAjoutReaction;
    }

    public Reaction(int idUser, int idPublication, int idCommentaire, Timestamp dateAjoutReaction) {
        this.idUser = idUser;
        this.idPublication = idPublication;
        this.dateAjoutReaction = dateAjoutReaction;
    }

    public int getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(int idReaction) {
        this.idReaction = idReaction;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
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
        return idReaction == reaction.idReaction && idUser == reaction.idUser && idPublication == reaction.idPublication && Objects.equals(dateAjoutReaction, reaction.dateAjoutReaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReaction, idUser, idPublication, dateAjoutReaction);
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "idUser=" + idUser +
                ", idPublication=" + idPublication +
                ", dateAjoutReaction=" + dateAjoutReaction +
                '}';


    }
}




