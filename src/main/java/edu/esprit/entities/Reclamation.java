package edu.esprit.entities;

import java.util.Objects;

public class Reclamation {
    private Groupe id_groupe;
    private String Desc_Reclamation;
    private int id_reclamation;

    public Reclamation() {

    }

    public Reclamation(Groupe groupe, String desc_Reclamation) {
        this.id_groupe = groupe;
        Desc_Reclamation = desc_Reclamation;
    }

    public Reclamation(Groupe groupe, String desc_Reclamation, int id_reclamation) {
        this.id_groupe = groupe;
        Desc_Reclamation = desc_Reclamation;
        this.id_reclamation = id_reclamation;
    }

    public Groupe getRec_groupe() {
        return id_groupe;
    }

    public void setRec_groupe(Groupe id_groupe) {
        this.id_groupe = id_groupe;
    }

    public String getDesc_Reclamation() {
        return Desc_Reclamation;
    }

    public void setDesc_Reclamation(String desc_Reclamation) {
        Desc_Reclamation = desc_Reclamation;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return id_reclamation == that.id_reclamation && Objects.equals(id_groupe, that.id_groupe) && Objects.equals(Desc_Reclamation, that.Desc_Reclamation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_groupe, Desc_Reclamation, id_reclamation);
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id_groupe=" + id_groupe +
                ", Desc_Reclamation='" + Desc_Reclamation + '\'' +
                ", id_reclamation=" + id_reclamation +
                '}';
    }
}
