package edu.esprit.services;

import java.util.Set;

public interface IServicePublication<T> {

    public void add(T t);
    public void update(T t);
    public void delete(int id);
    public T getOneByID(int id);

    public Set<T> getAll();




}
