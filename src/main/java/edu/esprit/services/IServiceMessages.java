package edu.esprit.services;

import java.util.Set;
public interface IServiceMessages<T> {
    public void send(T t);
    public void delete(int id);
    public Set<T> getAll();
}
