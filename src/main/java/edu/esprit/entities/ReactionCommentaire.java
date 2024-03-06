package edu.esprit.entities;

import java.sql.Timestamp;
import java.util.Objects;

public class ReactionCommentaire {

    private int idReactionCommentaire;
    private Commentaire commentaire;
    private User user ;
    private Publication publication;

    private Timestamp dateAjoutReactionCommentaire;


    public ReactionCommentaire() {
    }

    public ReactionCommentaire(int idReactionCommentaire, Commentaire commentaire, User user, Publication publication, Timestamp dateAjoutReactionCommentaire) {
        this.idReactionCommentaire = idReactionCommentaire;
        this.commentaire = commentaire;
        this.user = user;
        this.publication = publication;
        this.dateAjoutReactionCommentaire = dateAjoutReactionCommentaire;
    }

    public ReactionCommentaire(Commentaire commentaire, User user, Publication publication, Timestamp dateAjoutReactionCommentaire) {
        this.commentaire = commentaire;
        this.user = user;
        this.publication = publication;
        this.dateAjoutReactionCommentaire = dateAjoutReactionCommentaire;
    }

    public int getIdReactionCommentaire() {
        return idReactionCommentaire;
    }

    public void setIdReactionCommentaire(int idReactionCommentaire) {
        this.idReactionCommentaire = idReactionCommentaire;
    }

    public Commentaire getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
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

    public Timestamp getDateAjoutReactionCommentaire() {
        return dateAjoutReactionCommentaire;
    }

    public void setDateAjoutReactionCommentaire(Timestamp dateAjoutReactionCommentaire) {
        this.dateAjoutReactionCommentaire = dateAjoutReactionCommentaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionCommentaire that = (ReactionCommentaire) o;
        return idReactionCommentaire == that.idReactionCommentaire && Objects.equals(commentaire, that.commentaire) && Objects.equals(user, that.user) && Objects.equals(publication, that.publication) && Objects.equals(dateAjoutReactionCommentaire, that.dateAjoutReactionCommentaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReactionCommentaire, commentaire, user, publication, dateAjoutReactionCommentaire);
    }

    @Override
    public String toString() {
        return "ReactionCommentaire{" +
                "commentaire=" + commentaire +
                ", user=" + user +
                ", publication=" + publication +
                ", dateAjoutReactionCommentaire=" + dateAjoutReactionCommentaire +
                '}';
    }
}
