package edu.esprit.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class ReactionCommentaire {

    private int idReactionCommentaire;
    private int idCommentaire;
    private int idUser;
    private int idPublication;

    private Timestamp dateAjoutReactionCommentaire;


    public ReactionCommentaire() {
    }


    public ReactionCommentaire(int idReactionCommentaire, int idCommentaire, int idUser, int idPublication, Timestamp dateAjoutReactionCommentaire) {
        this.idReactionCommentaire = idReactionCommentaire;
        this.idCommentaire = idCommentaire;
        this.idUser = idUser;
        this.idPublication = idPublication;
        this.dateAjoutReactionCommentaire = dateAjoutReactionCommentaire;
    }



    public ReactionCommentaire(int idUser, int idPublication, int idCommentaire) {
        this.idUser = idUser;
        this.idPublication = idPublication;
        this.idCommentaire = idCommentaire;
    }

    public int getIdReactionCommentaire() {
        return idReactionCommentaire;
    }

    public void setIdReactionCommentaire(int idReactionCommentaire) {
        this.idReactionCommentaire = idReactionCommentaire;
    }

    public int getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
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

    public Timestamp getDateAjoutReactionCommentaire() {
        return dateAjoutReactionCommentaire;
    }

    public void setDateAjoutReactionCommentaire(Timestamp dateAjoutReactionCommentaire) {
        this.dateAjoutReactionCommentaire = dateAjoutReactionCommentaire;
    }

    @Override
    public String toString() {
        return "ReactionCommentaire{" +
                "idCommentaire=" + idCommentaire +
                ", idUser=" + idUser +
                ", idPublication=" + idPublication +
                ", dateAjoutReactionCommentaire=" + dateAjoutReactionCommentaire +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionCommentaire that = (ReactionCommentaire) o;
        return idReactionCommentaire == that.idReactionCommentaire && idCommentaire == that.idCommentaire && idUser == that.idUser && idPublication == that.idPublication && Objects.equals(dateAjoutReactionCommentaire, that.dateAjoutReactionCommentaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReactionCommentaire, idCommentaire, idUser, idPublication, dateAjoutReactionCommentaire);
    }
}
