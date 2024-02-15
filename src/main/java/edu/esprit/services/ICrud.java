package edu.esprit.crud;

import edu.esprit.entities.Event;

import java.util.List;

public interface ICrud<T> {
    public void ajouter(T t);
    public void supprimer(T t);
    public void modifier(T t);
    public List<T> getAll();
    public T getOneByID(int id);

    Event getOneByName(String name);
}
