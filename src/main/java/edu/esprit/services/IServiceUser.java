package edu.esprit.services;

import edu.esprit.entities.User;

import java.util.Set;

public interface IServiceUser <T> {
    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(int id);
    public Set<T> getAll();
    public T getOneByID(int id);

    void ajouter(User user);
}
