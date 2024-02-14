package com.arthive.services;


import com.arthive.entities.User;
import com.arthive.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceUser implements IServiceUser<User> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void add(User user) {
         /*String req = "INSERT INTO `personne`(`nom`, `prenom`) VALUES ('"+personne.getNom()+"','"+personne.getPrenom()+"')";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        String req = "INSERT INTO `users`(`nom_user`, `prenom_user`, `email`, `mdp_user`, `d_naissance_user`, `ville`, `num_tel_user`)  VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom_user());
            ps.setString(2, user.getPrenom_user());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMdp_user());
            ps.setDate(5, user.getD_naissance_user());
            ps.setString(6, user.getVille());
            ps.setInt(7, user.getNum_tel_user());
            ps.executeUpdate();
            System.out.println("User added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public Set<User> getAll() {
        Set<User> users = new HashSet<>();

        String req = "Select * from users";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                int id = rs.getInt("id_user");
                String nom = rs.getString("nom_user");
                String prenom = rs.getString("prenom_user");
                String email = rs.getString("email");
                String password = rs.getString("mdp_user");
                Date dateNaiss = rs.getDate("d_naissance_user");
                String ville = rs.getString("ville");
                Integer numtel = rs.getInt("num_tel_user");
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
        User user = null;

        String req = "SELECT * FROM users WHERE id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id_user");
                String nom = rs.getString("nom_user");
                String prenom = rs.getString("prenom_user");
                String email = rs.getString("email");
                String password = rs.getString("mdp_user");
                Date dateNaiss = rs.getDate("d_naissance_user");
                String ville = rs.getString("ville");
                Integer numtel = rs.getInt("num_tel_user");
                user = new User(userId, nom, prenom, email, password, dateNaiss, ville, numtel);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    @Override
    public void update(User user) {
        String req = "UPDATE users SET nom_user = ?, prenom_user = ?, email = ?, mdp_user = ?, d_naissance_user = ?, ville = ?, num_tel_user = ? WHERE id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom_user());
            ps.setString(2, user.getPrenom_user());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMdp_user());
            ps.setDate(5, user.getD_naissance_user());
            ps.setString(6, user.getVille());
            ps.setInt(7, user.getNum_tel_user());
            ps.setInt(8, user.getId_user());
            ps.executeUpdate();
            System.out.println("User updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM users WHERE id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("User deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
