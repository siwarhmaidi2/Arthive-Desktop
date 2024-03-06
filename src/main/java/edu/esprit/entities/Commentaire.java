package edu.esprit.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class Commentaire {
    private int idCommentaire;
    private String contenuCommentaire;
    private Timestamp dateAjoutCommentaire;
   private User user ;
   private Publication publication;

       public Commentaire() {
    }

    public Commentaire(int idCommentaire, String contenuCommentaire, Timestamp dateAjoutCommentaire, User user, Publication publication) {
        this.idCommentaire = idCommentaire;
        this.contenuCommentaire = contenuCommentaire;
        this.dateAjoutCommentaire = dateAjoutCommentaire;
        this.user = user;
        this.publication = publication;
    }

    public Commentaire(String contenuCommentaire, Timestamp dateAjoutCommentaire, User user, Publication publication) {
        this.contenuCommentaire = contenuCommentaire;
        this.dateAjoutCommentaire = dateAjoutCommentaire;
        this.user = user;
        this.publication = publication;
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

    @Override
    public String toString() {
        return "Commentaire{" +
                "contenuCommentaire='" + contenuCommentaire + '\'' +
                ", dateAjoutCommentaire=" + dateAjoutCommentaire +
                ", user=" + user +
                ", publication=" + publication +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idCommentaire;
        hash = 97 * hash + Objects.hashCode(this.contenuCommentaire);
        hash = 97 * hash + Objects.hashCode(this.dateAjoutCommentaire);
        hash = 97 * hash + Objects.hashCode(this.user);
        hash = 97 * hash + Objects.hashCode(this.publication);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commentaire that = (Commentaire) o;
        return idCommentaire == that.idCommentaire && Objects.equals(contenuCommentaire, that.contenuCommentaire) && Objects.equals(dateAjoutCommentaire, that.dateAjoutCommentaire) && Objects.equals(user, that.user) && Objects.equals(publication, that.publication);
    }
}
