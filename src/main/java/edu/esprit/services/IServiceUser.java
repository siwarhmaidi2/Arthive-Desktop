package edu.esprit.services;

import java.util.Set;

public interface IServiceUser <T> {
    public void add(T t);
    public void update(T t);
    public void delete(int id);
    public Set<T> getAll();
    public T getOneByID(int id);


}
