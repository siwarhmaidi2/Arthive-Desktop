package edu.esprit.entities;

import java.util.Objects;

public class Groupe {
    private int id_group;
    private String nom_group;
    private String description_group;
    private User id_user;

    private String image;


    public Groupe() {
    }

    public Groupe(int id_group, String nom_group, String description_group, User id_user, String image) {
        this.id_group = id_group;
        this.nom_group = nom_group;
        this.description_group = description_group;
        this.id_user = id_user;
        this.image = image;
    }

    public Groupe(String nom_group, String description_group, User id_user, String image) {
        this.nom_group = nom_group;
        this.description_group = description_group;
        this.id_user = id_user;
        this.image = image;
    }

    public Groupe(String nom_group, String description_group, User id_user) {
        this.nom_group = nom_group;
        this.description_group = description_group;
        this.id_user = id_user;
    }

    public Groupe(String nom_group, String description_group) {
        this.nom_group = nom_group;
        this.description_group = description_group;
    }


    public int getId_group() {
        return id_group;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    public String getNom_group() {
        return nom_group;
    }

    public void setNom_group(String nom_group) {
        this.nom_group = nom_group;
    }

    public String getDescription_group() {
        return description_group;
    }

    public void setDescription_group(String description_group) {
        this.description_group = description_group;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groupe groupe = (Groupe) o;
        return id_group == groupe.id_group && Objects.equals(nom_group, groupe.nom_group) && Objects.equals(description_group, groupe.description_group) && Objects.equals(id_user, groupe.id_user) && Objects.equals(image, groupe.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_group, nom_group, description_group, id_user, image);
    }

    @Override
    public String toString() {
        return "Groupe{" +
                "id_group=" + id_group +
                ", nom_group='" + nom_group + '\'' +
                ", description_group='" + description_group + '\'' +
                ", id_user=" + id_user +
                ", image='" + image + '\'' +
                '}';
    }
}


