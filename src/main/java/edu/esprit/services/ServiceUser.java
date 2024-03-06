package edu.esprit.services;


import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;



public class ServiceUser implements IServiceUser<User> {

    Connection cnx = DataSource.getInstance().getCnx();
    private static User loggedInUser;
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

        String req = "INSERT INTO `users`(`nom_user`, `prenom_user`, `email`, `mdp_user`, `d_naissance_user`, `ville`, `num_tel_user`, `bio`, `photo`, `role`)  VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom_user());
            ps.setString(2, user.getPrenom_user());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMdp_user());
            ps.setDate(5, user.getD_naissance_user());
            ps.setString(6, user.getVille());
            ps.setString(7, user.getNum_tel_user());
            ps.setString(8, user.getBio());
            ps.setString(9, user.getPhoto());
            ps.setString(10, user.getRole());
            ps.executeUpdate();
            System.out.println("User added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void reportPublication(int idUser, int idPub) {
        String req =" INSERT INTO `siganler`(`id_user`, `id_publication`) VALUES (?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idUser);
            ps.setInt(2, idPub);
            ps.executeUpdate();
            System.out.println("Publication reported !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePhoto(User user) {
        String req = "UPDATE users SET photo = ? WHERE id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getPhoto());
            ps.setInt(2, user.getId_user());
            ps.executeUpdate();
            System.out.println("User photo updated !");
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
                String numtel = rs.getString("num_tel_user");
                String bio = rs.getString("bio");
                String photo = rs.getString("photo");
                String role = rs.getString("role");
                User u = new User(id,nom,prenom,email,password,dateNaiss,ville,numtel,bio,photo, role);
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
                String numtel = rs.getString("num_tel_user");
                String bio = rs.getString("bio");
                String role = rs.getString("role");
                String photo = rs.getString("photo");
                user = new User(userId, nom, prenom, email, password, dateNaiss, ville, numtel, bio, photo, role);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public boolean checkEmail(String email) {
        String req = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void update(User user) {
        String req = "UPDATE users SET nom_user = ?, prenom_user = ?, email = ?, mdp_user = ?, d_naissance_user = ?, ville = ?, num_tel_user = ?, bio = ?, photo = ? WHERE id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom_user());
            ps.setString(2, user.getPrenom_user());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMdp_user());
            ps.setDate(5, user.getD_naissance_user());
            ps.setString(6, user.getVille());
            ps.setString(7, user.getNum_tel_user());
            ps.setString(8, user.getBio());
            ps.setString(9, user.getPhoto());
            ps.setInt(8, user.getId_user());
            ps.executeUpdate();
            System.out.println("User updated !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateProfile(User user) {
        String req = "UPDATE users SET nom_user = ?, prenom_user = ?, d_naissance_user = ?, ville = ?, num_tel_user = ?, bio = ? WHERE id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom_user());
            ps.setString(2, user.getPrenom_user());
            ps.setDate(3, user.getD_naissance_user());
            ps.setString(4, user.getVille());
            ps.setString(5, user.getNum_tel_user());
            ps.setString(6, user.getBio());
            ps.setInt(7, user.getId_user());
            ps.executeUpdate();
            System.out.println("User Profile updated !");
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

    public User authenticateUser(String email, String password) {
        // Perform authentication logic here (query the database, check credentials, etc.)
        // If authentication is successful, set the loggedInUser
        // Otherwise, return null or handle authentication failure as needed

        // Example authentication logic (modify as per your database schema and authentication flow)
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
                String numtel = rs.getString("num_tel_user");
                String bio = rs.getString("bio");
                String role = rs.getString("role");
                String photo = rs.getString("photo");
                loggedInUser = new User(userId, nom, prenom, emailUser, passwordUser, dateNaiss, ville, numtel, bio, photo, role);


                return loggedInUser;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Authentication failed
        return null;
    }
}
