package edu.esprit.crud;

import edu.esprit.entities.Event;

import java.util.List;

public interface ICrud<T> {
    public void add(T t);
    public void delete(T t);
    public void update(T t);
    public List<T> getAll();
    public T getOneByID(int id);

    Event getOneByName(String name);
}
