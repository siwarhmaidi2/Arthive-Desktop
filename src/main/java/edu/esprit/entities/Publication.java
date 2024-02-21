package edu.esprit.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Publication {

    private int id_publication;
    private String contenu_publication;

    private String url_file;

    private Timestamp d_creation_publication;
    private User user ;

    public Publication() {
    }

    public Publication(int id_publication, String contenu_publication, String url_file, Timestamp d_creation_publication, User user) {
        this.id_publication = id_publication;
        this.contenu_publication = contenu_publication;
        this.url_file = url_file;
        this.d_creation_publication = d_creation_publication;
        this.user = user;
    }

    public Publication(String contenu_publication, String url_file, Timestamp d_creation_publication, User user) {
        this.contenu_publication = contenu_publication;
        this.url_file = url_file;
        this.d_creation_publication = d_creation_publication;
        this.user = user;
    }

    public Publication(String testContent, String file, Timestamp dCreationPublication, int idUser) {
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

    public String getUrl_file() {
        return url_file;
    }

    public void setUrl_file(String url_file) {
        this.url_file = url_file;
    }

    public Timestamp getD_creation_publication() {
        return d_creation_publication;
    }

    public void setD_creation_publication(Timestamp d_creation_publication) {
        this.d_creation_publication = d_creation_publication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "contenu_publication='" + contenu_publication + '\'' +
                ", url_file='" + url_file + '\'' +
                ", d_creation_publication=" + d_creation_publication +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return id_publication == that.id_publication && Objects.equals(contenu_publication, that.contenu_publication) && Objects.equals(url_file, that.url_file) && Objects.equals(d_creation_publication, that.d_creation_publication) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_publication, contenu_publication, url_file, d_creation_publication, user);
    }

}