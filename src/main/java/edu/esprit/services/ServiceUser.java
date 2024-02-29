package edu.esprit.services;


import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceUser implements IService<User> {

    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void add(User user) {
        String req = "INSERT INTO `users`(`nom_user`, `prenom_user`, `email`, `mdp_user`, `d_naissance_user`, `ville`, `num_tel_user`, `photo_user`, `role`, `bio`)  VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom_user());
            ps.setString(2, user.getPrenom_user());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMdp_user());
            ps.setDate(5, user.getD_naissance_user());
            ps.setString(6, user.getVille());
            ps.setInt(7, user.getNum_tel_user());
            ps.setString(8, user.getPhoto_user());
            ps.setString(9, user.getRole());
            ps.setString(10, user.getBio());
            ps.executeUpdate();
            System.out.println("User added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Set<User> getAll() {
        Set<User> users = new HashSet<>();

        String req = "SELECT * FROM users";
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
                String photoUser = rs.getString("photo_user");
                String role = rs.getString("role");
                String bio = rs.getString("bio");
                User u = new User(id, nom, prenom, email, password, dateNaiss, ville, numtel, photoUser, role, bio);
                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }


    @Override
    public void update(User user) {
        String req = "UPDATE users SET nom_user = ?, prenom_user = ?, email = ?, mdp_user = ?, d_naissance_user = ?, ville = ?, num_tel_user = ?, photo_user = ?, role = ?, bio = ? WHERE id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom_user());
            ps.setString(2, user.getPrenom_user());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMdp_user());
            ps.setDate(5, user.getD_naissance_user());
            ps.setString(6, user.getVille());
            ps.setInt(7, user.getNum_tel_user());
            ps.setString(8, user.getPhoto_user());
            ps.setString(9, user.getRole());
            ps.setString(10, user.getBio());
            ps.setInt(11, user.getId_user());
            ps.executeUpdate();
            System.out.println("User updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
                String photoUser = rs.getString("photo_user");
                String role = rs.getString("role");
                String bio = rs.getString("bio");
                user = new User(userId, nom, prenom, email, password, dateNaiss, ville, numtel, photoUser, role, bio);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
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

    private static User loggedInUser;

    // Other methods...

    // Method to authenticate user based on email and password
    // Method to authenticate user based on email and password
    public User authenticateUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND mdp_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id_user");
                String nom = rs.getString("nom_user");
                String prenom = rs.getString("prenom_user");
                String emailUser = rs.getString("email");
                String passwordUser = rs.getString("mdp_user");
                Date dateNaiss = rs.getDate("d_naissance_user");
                String ville = rs.getString("ville");
                int numtel = rs.getInt("num_tel_user");
                String photoUser = rs.getString("photo_user");
                String role = rs.getString("role");
                String bio = rs.getString("bio");
                return new User(userId, nom, prenom, emailUser, passwordUser, dateNaiss, ville, numtel, photoUser, role, bio);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Authentication failed
        return null;
    }

    // Method to get the logged-in user
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static void logInUser(User user) {

        loggedInUser = user;
    }

    public static void logOutUser() {
        loggedInUser = null;
    }


}
