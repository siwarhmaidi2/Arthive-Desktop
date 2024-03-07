package edu.esprit.entities;

import java.util.Objects;

public class Participation {
    private int id_participation;
    private Event event;
    private User user;

    public Participation() {
    }

    public Participation(int id_participation, User user, Event event) {
        this.id_participation = id_participation;
        this.event = event;
        this.user = user;
    }

    public Participation(User user, Event event) {
        this.event = event;
        this.user = user;
    }

    public Participation(int participationId, int idUser, int idEvent) {
    }

    public int getId_participation() {
        return id_participation;
    }

    public void setId_participation(int id_participation) {
        this.id_participation = id_participation;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id_participation=" + id_participation +
                ", event=" + event +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participation)) return false;
        Participation that = (Participation) o;
        return id_participation == that.id_participation &&
                Objects.equals(event, that.event) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_participation, event, user);
    }
}
