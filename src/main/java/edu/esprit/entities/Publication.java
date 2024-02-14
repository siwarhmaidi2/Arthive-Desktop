package edu.esprit.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Publication {

    private int id_publication;
    private String  contenu_publication ;

    private Timestamp d_creation_publication;

    private int id_user;

    public Publication() {
    }

    public Publication(int id_publication, String contenu_publication, Timestamp d_creation_publication, int id_user) {
        this.id_publication = id_publication;
        this.contenu_publication = contenu_publication;
        this.d_creation_publication = d_creation_publication;
        this.id_user = id_user;
    }

    public Publication(String contenu_publication, Timestamp d_creation_publication, int id_user) {
        this.contenu_publication = contenu_publication;
        this.d_creation_publication = d_creation_publication;
        this.id_user = id_user;
    }

    public int getId_publication() {
        return id_publication;
    }

    public void setId_publication(int id_publication) {
        this.id_publication = id_publication;
    }

    public String getContenu_publication() {
        return contenu_publication;
    }

    public void setContenu_publication(String contenu_publication) {
        this.contenu_publication = contenu_publication;
    }

    public Timestamp getD_creation_publication() {
        return d_creation_publication;
    }

    public void setD_creation_publication(Timestamp d_creation_publication) {
        this.d_creation_publication = d_creation_publication;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return id_publication == that.id_publication && Objects.equals(contenu_publication, that.contenu_publication) && Objects.equals(d_creation_publication, that.d_creation_publication) && Objects.equals(id_user, that.id_user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_publication, contenu_publication, d_creation_publication, id_user);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id_publication=" + id_publication +
                ", contenu_publication='" + contenu_publication + '\'' +
                ", d_creation_publication=" + d_creation_publication +
                ", id_user=" + id_user +
                '}';
    }
}
