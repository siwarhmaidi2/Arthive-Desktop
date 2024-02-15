package edu.esprit.services;

import edu.esprit.entities.Message;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceMessage implements IServiceMessages<Message> {
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void send(Message message) {
        try {
            String requete = "INSERT INTO messages (sender_id_user, receiver_id_user, contenu_message, d_envoi_message) VALUES (?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, message.getSenderId());
            pst.setInt(2, message.getReceiverId());
            pst.setString(3, message.getContent());
            pst.setDate(4, message.getDateSent());
            pst.executeUpdate();
            System.out.println("Message sent");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            String requete = "DELETE FROM messages WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Message deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<Message> getAll() {
        Set<Message> messages = new HashSet<>();
        try {
            String requete = "SELECT * FROM messages";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id_message"));
                message.setSenderId(rs.getInt("sender_id_user"));
                message.setReceiverId(rs.getInt("receiver_id_user"));
                message.setContent(rs.getString("contenu_message"));
                message.setDateSent(rs.getDate("d_envoi_message"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

}
