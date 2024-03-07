package edu.esprit.controllers;

import edu.esprit.utils.DataSource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.esprit.services.ServiceUser;

public class UserMessagesController {

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private ListView<String> userMessagesListView;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private ListView<String> messagesListView;

    @FXML
    private Button sendButton;

    private int loggedInUserId;


    public void initialize(int loggedInUserId) {
        this.loggedInUserId = 23; //exemple 23, actual user id needs to be fetched from authentication service
        loadUsers();
    }

    private void loadUsers(){
        try{
            Connection cnx = DataSource.getInstance().getCnx();
            String query = "SELECT DISTINCT sender_id_user, receiver_id_user FROM messages";
            PreparedStatement statement = cnx.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int senderId = rs.getInt("sender_id_user");
                int receiverId = rs.getInt("receiver_id_user");
                ServiceUser su = new ServiceUser();

                if(loggedInUserId == senderId){
                    userMessagesListView.getItems().add(su.getOneByID(receiverId).getNom_user());
                }
                else if (loggedInUserId == receiverId){
                    userMessagesListView.getItems().add(su.getOneByID(senderId).getNom_user());
                }

            }
            rs.close();
            statement.close();
            cnx.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
