package edu.esprit.services;

import java.util.Set;

public interface IServiceReactionCommentaire <T>{

    public void add (T reactionCommentaire);
    public void delete (int id);
    public Set<T> getAll();

}
