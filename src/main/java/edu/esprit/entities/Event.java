package edu.esprit.entities;

import edu.esprit.enums.CategorieEvenement;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Event {
    private int id_evenement;
    private String titre_evenement;
    private Timestamp d_debut_evenement;
    private Timestamp d_fin_evenement;
    private String description_evenement;
    private String lieu_evenement;
    //private Participation participants;
    private User user;
    private String image;
    private Set<Participation> participants;
    private CategorieEvenement categorieEvenement;


    public Event() {
        this.participants = new HashSet<>();

    }


    public CategorieEvenement getCategorieEvenement() {
        return categorieEvenement;
    }

    public void setCategorieEvenement(CategorieEvenement categorieEvenement) {
        this.categorieEvenement = categorieEvenement;
    }


    public Event(int id_evenement, String titre_evenement, Timestamp d_debut_evenement, Timestamp d_fin_evenement, String description_evenement, String lieu_evenement, User user, String image,Set<Participation> participants,CategorieEvenement categorieEvenement) {
        this.id_evenement = id_evenement;
        this.titre_evenement = titre_evenement;
        this.d_debut_evenement = d_debut_evenement;
        this.d_fin_evenement = d_fin_evenement;
        this.description_evenement = description_evenement;
        this.lieu_evenement = lieu_evenement;
        this.user = user;
        this.image = image;
        this.participants = participants != null ? participants : new HashSet<>();
        this.categorieEvenement = categorieEvenement;
    }


    public Event(String titre_evenement, Timestamp d_debut_evenement, Timestamp d_fin_evenement, String description_evenement, String lieu_evenement, User user, String image,CategorieEvenement categorieEvenement) {
        this.titre_evenement = titre_evenement;
        this.d_debut_evenement = d_debut_evenement;
        this.d_fin_evenement = d_fin_evenement;
        this.description_evenement = description_evenement;
        this.lieu_evenement = lieu_evenement;
        this.user = user;
        this.image = image;
        this.categorieEvenement = categorieEvenement;
    }

    public Event(int id_evenement, String titre_evenement, Timestamp d_debut_evenement, Timestamp d_fin_evenement, String description_evenement, String lieu_evenement, User user, String image,CategorieEvenement categorieEvenement) {
        this.id_evenement = id_evenement;
        this.titre_evenement = titre_evenement;
        this.d_debut_evenement = d_debut_evenement;
        this.d_fin_evenement = d_fin_evenement;
        this.description_evenement = description_evenement;
        this.lieu_evenement = lieu_evenement;
        this.user = user;
        this.image = image;
        this.categorieEvenement = categorieEvenement;
    }

    public Event(int eventId) {
    }


    public int getId_event() {
        return id_evenement;
    }

    public void setId_event(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public String getTitre_evenement() {
        return titre_evenement;
    }

    public void setTitre_evenement(String titre_evenement) {
        this.titre_evenement = titre_evenement;
    }

    public Timestamp getD_debut_evenement() {
        return d_debut_evenement;
    }

    public void setD_debut_evenement(Timestamp d_debut_evenement) {
        this.d_debut_evenement = d_debut_evenement;
    }

    public Timestamp getD_fin_evenement() {
        return d_fin_evenement;
    }

    public void setD_fin_evenement(Timestamp d_fin_evenement) {
        this.d_fin_evenement = d_fin_evenement;
    }

    public String getDescription_evenement() {
        return description_evenement;
    }

    public void setDescription_evenement(String description_evenement) {
        this.description_evenement = description_evenement;
    }

    public String getLieu_evenement() {
        return lieu_evenement;
    }

    public void setLieu_evenement(String lieu_evenement) {
        this.lieu_evenement = lieu_evenement;
    }



    public Set<Participation> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participation> participants) {
        this.participants =  participants;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id_evenement=" + id_evenement +
                ", titre_evenement='" + titre_evenement + '\'' +
                ", d_debut_evenement=" + d_debut_evenement +
                ", d_fin_evenement=" + d_fin_evenement +
                ", description_evenement='" + description_evenement + '\'' +
                ", lieu_evenement='" + lieu_evenement + '\'' +
                ", user=" + user +
                ", image='" + image + '\'' +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return id_evenement == event.id_evenement;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id_evenement);
    }


    private int numberOfParticipants;

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }
//    public void removeParticipant() {
//        numberOfParticipants--;
//    }
//    public void addParticipant() {
//        numberOfParticipants++;
//    }
public void removeParticipant(Participation participation) {
    if (participants != null && participants.remove(participation)) {
        numberOfParticipants--;
    }
}


    public void addParticipant(Participation participation) {
        if (participants == null) {
            participants = new HashSet<>();
        }

        if (participants.add(participation)) {
            numberOfParticipants++;
        }
    }


}

