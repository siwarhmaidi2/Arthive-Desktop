package edu.esprit.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class Commentaire {
    private int idCommentaire;
    private String contenuCommentaire;
    private Timestamp dateAjoutCommentaire;
    private int idUser; // Foreign key referencing User
    private int idPublication; // Foreign key referencing Publication


    public Commentaire() {
    }

    public Commentaire(int idCommentaire, String contenuCommentaire, Timestamp dateAjoutCommentaire, int idUser, int idPublication) {
        this.idCommentaire = idCommentaire;
        this.contenuCommentaire = contenuCommentaire;
        this.dateAjoutCommentaire = dateAjoutCommentaire;
        this.idUser = idUser;
        this.idPublication = idPublication;
    }

    public Commentaire(String contenuCommentaire, Timestamp dateAjoutCommentaire, int idUser, int idPublication) {
        this.contenuCommentaire = contenuCommentaire;
        this.dateAjoutCommentaire = dateAjoutCommentaire;
        this.idUser = idUser;
        this.idPublication = idPublication;
    }

    public int getIdCommentaire() {
        return idCommentaire;
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public String getContenuCommentaire() {
        return contenuCommentaire;
    }

    public void setContenuCommentaire(String contenuCommentaire) {
        this.contenuCommentaire = contenuCommentaire;
    }

    public Timestamp getDateAjoutCommentaire() {
        return dateAjoutCommentaire;
    }

    public void setDateAjoutCommentaire(Timestamp dateAjoutCommentaire) {
        this.dateAjoutCommentaire = dateAjoutCommentaire;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commentaire that = (Commentaire) o;
        return idCommentaire == that.idCommentaire && idUser == that.idUser && idPublication == that.idPublication && Objects.equals(contenuCommentaire, that.contenuCommentaire) && Objects.equals(dateAjoutCommentaire, that.dateAjoutCommentaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommentaire, contenuCommentaire, dateAjoutCommentaire, idUser, idPublication);
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "contenuCommentaire='" + contenuCommentaire + '\'' +
                ", dateAjoutCommentaire=" + dateAjoutCommentaire +
                ", idUser=" + idUser +
                ", idPublication=" + idPublication +
                '}';
    }


}
