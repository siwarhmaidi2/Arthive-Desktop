package edu.esprit.services;

import java.util.Set;

public interface IServiceReaction<T> {
   public void add(T reaction);
   public void update(T reaction);
   public void delete(int id);
   public T getOneByID(int id);
   public Set<T> getAll();
}
