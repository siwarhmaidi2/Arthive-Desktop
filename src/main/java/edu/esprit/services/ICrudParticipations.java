package edu.esprit.crud;

import edu.esprit.entities.Participation;

public interface ICrudParticipations <T>{
    public void add(T t);
    public void delete(T t);
   // Participation getOneByNameParticipant(String name);
}
