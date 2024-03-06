package edu.esprit.entities;

import java.util.Objects;

public class Participations {
    private int id_participant;
    private int id_evenement;

    Participations(){}
    public Participations(int id_participant, int id_evenement) {
        this.id_participant = id_participant;
        this.id_evenement = id_evenement;
    }

    public int getId_participant() {
        return id_participant;
    }

    public void setId_participant(int id_participant) {
        this.id_participant = id_participant;
    }

    public int getId_event() {
        return id_evenement;
    }

    public void setId_event(int id_event) {
        this.id_evenement = id_event;
    }

    @Override
    public String toString() {
        return "Participations{" +
                "id_participant=" + id_participant +
                ", id_event=" + id_evenement +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participations that)) return false;
        return id_participant == that.id_participant && id_evenement == that.id_evenement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_participant);
    }
}
