package com.arthive.services;


import com.arthive.entities.User;
import com.arthive.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceUser implements IServiceUser<User> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(User user) {
         /*String req = "INSERT INTO `personne`(`nom`, `prenom`) VALUES ('"+personne.getNom()+"','"+personne.getPrenom()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        String req = "INSERT INTO `user`(nom`, `prenom`, `email`, `password`, `dateNaiss`, `ville`, `numtel`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setDate(5, user.getDateNaiss());
            ps.setString(6, user.getVille());
            ps.setInt(7, user.getNumtel());
            ps.executeUpdate();
            System.out.println("User added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(User user) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Set<User> getAll() {
        Set<User> users = new HashSet<>();

        String req = "Select * from user";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                int id = rs.getInt("id");
                String nom = rs.getString(2);
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Date dateNaiss = rs.getDate("dateNaiss");
                String ville = rs.getString("ville");
                Integer numtel = rs.getInt("numtel");
                User u = new User(id,nom,prenom,email,password,dateNaiss,ville,numtel);
                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;

    }

    @Override
    public User getOneByID(int id) {
        return null;
    }
}
