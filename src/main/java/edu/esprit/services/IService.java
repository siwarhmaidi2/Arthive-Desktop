package edu.esprit.services;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Publication;

import java.util.List;
import java.util.Set;

public interface IService <T>{
  //Publication service
    public void add(T t);
    public void update(T t);
    public void delete(int id);

    public T getOneByID(int id);

    public Set<T> getAll();


}
