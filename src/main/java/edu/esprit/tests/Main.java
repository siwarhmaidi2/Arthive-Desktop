package edu.esprit.tests;

import edu.esprit.entities.*;
import edu.esprit.services.*;

import edu.esprit.entities.Admin;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceUser;

import java.io.IOException;

import java.time.LocalDate;
import java.util.Date;

import edu.esprit.entities.Message;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


public class Main extends Application {

    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.stage = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        primaryStage.setTitle("Arthive Login");
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.show();
    }

    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Main.class.getResource(fxml));
        stage.getScene().setRoot(pane);
    }


    public static void main(String[] args) {
        // Create a user
        //create date of user

      // LocalDate birthDate = LocalDate.of(2001, 5, 15);
      //  LocalDate birthDateZied = LocalDate.of(2001, 11, 15);
      //  User user = new User("John", "Doe", "john.doe@example.com", "password", Date.valueOf("1990-01-01"), "City", 123456789);
       ServiceUser userService = new ServiceUser();

      //  User user2 = new User("ayoub", "toujani", "ayoubtoujani@gmail.com", "1234563", java.sql.Date.valueOf(birthDateAyoub), "tunis", 123456789);



        //Add a user
        //userService.add(user);
        //System.out.println(userService.getOneByID(30));
        //System.out.println(userService.getAll());



       /* // Get all users
        Set<User> users = userService.getAll();
        System.out.println("List of Users:");
        for (User u : users) {
            System.out.println(u);
        }
        */

        // Update a user
         // userService.update(new User(44,"Ayoub", "Toujani", "ayoubtoujani808@gmail.com", "1234563", java.sql.Date.valueOf(birthDate), "tunis", 123456789));

        // Delete a user
      //  userService.delete(38);

        // get one user by id
       //  System.out.println(userService.getOneByID(29));



        // Create a publication
      //Publication publication = new Publication("Test Content", "testfile.txt", new Timestamp(System.currentTimeMillis()),new User(44,"John", "Doe", "john.doe@example.com", "password", Date.valueOf("1990-01-01"), "City", 123456789));
       Publication publication = new Publication("Test Content", "testfile.txt", new Timestamp(System.currentTimeMillis()),userService.getOneByID(44));
        ServicePublication publicationService = new ServicePublication();

        // Add a publication
      //publicationService.add(publication);

/*
        // Get all publications
        Set<Publication> publications = publicationService.getAll();
        System.out.println("List of Publications:");
        for (Publication p : publications) {
            System.out.println(p);
        }
*/

        // Update a publication
//publicationService.update(new Publication(26, "waaaaa", "testfile.txt", new Timestamp(System.currentTimeMillis()),userService.getOneByID(44)));
        // Delete a publication
      //  publicationService.delete(26);

        // get one publication by id
        //System.out.println(publicationService.getOneByID(18));


        // Create a comment
Commentaire commentaire = new Commentaire("Nice post!", Timestamp.valueOf(LocalDateTime.now()), userService.getOneByID(44), publicationService.getOneByID(28));
      ServiceCommentaire commentaireService = new ServiceCommentaire();

        // Add a comment
       //commentaireService.add(commentaire);

        // Get all comments for a publication
       /* Set<Commentaire> commentsForPublication = commentaireService.getAll();

        // Print the comments
        System.out.println("List of Comments:");
        for (Commentaire c : commentsForPublication) {
            System.out.println(c);
        }


*/
        // Update a comment
//commentaireService.update(new Commentaire(11, "Nice post!", Timestamp.valueOf(LocalDateTime.now()), userService.getOneByID(30), publicationService.getOneByID(17)));
        // Delete a comment
        //commentaireService.delete(1);

        // get one comment by id
       // System.out.println(commentaireService.getOneByID(1));


        ServiceUser su = new ServiceUser() ;


        LocalDate birthDate = LocalDate.of(1990, 5, 15);

      //  System.out.println(su.getAll());

        long currentMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentMillis);
        java.sql.Date sqlDate = new java.sql.Date(currentMillis);

        //User u1 = new Admin("zied","zhiri","zied.zhiri@esprit.tn","123456",sqlDate, "Tunis", "12345678", "bio", "", "ROLE_ADMIN");
        //su.add(u1);



        launch(args);
        // Create a reaction

   //  Reaction reaction = new Reaction(userService.getOneByID(30), publicationService.getOneByID(17), Timestamp.valueOf(LocalDateTime.now()));
     //ServiceReaction reactionService = new ServiceReaction();

        // Add a reaction
     // reactionService.add(reaction);

        // Get all reactions for a publication
      /* //  Set<Reaction> reactionsForPublication = reactionService.getAll();

        // Print the reactions
       System.out.println("List of Reactions:");
        for (Reaction r : reactionsForPublication) {
            System.out.println(r);
        }
        */


        // Update a reaction
//reactionService.update(new Reaction(1, userService.getOneByID(30), publicationService.getOneByID(17), Timestamp.valueOf(LocalDateTime.now())));
        // Delete a reaction
    //   reactionService.delete(1);

        // get one reaction by id
     //   System.out.println(reactionService.getOneByID(6));


      /* ServiceMessage sm = new ServiceMessage()   ;


        long currentMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentMillis);
        java.sql.Date sqlDate = new java.sql.Date(currentMillis);

        Message m1 = new Message(36, 37, "blaset l 3ada?", sqlDate);

        sm.send(m1);

        Set<Message> messages = sm.getAll();

        for (Message m : messages) {
            System.out.println(m);
        }
*/

       ServiceReactionCommentaire reactionCommentaireService = new ServiceReactionCommentaire();
       ReactionCommentaire    reactionCommentaire = new ReactionCommentaire(commentaireService.getOneByID(8),userService.getOneByID(30),publicationService.getOneByID(17), Timestamp.valueOf(LocalDateTime.now()));

      //reactionCommentaireService.add(reactionCommentaire);

        // Get all reactions for a comment
        Set<ReactionCommentaire> reactionsForComment = reactionCommentaireService.getAll();

        // Print the reactions
      /*  System.out.println("List of Reactions:");
        for (ReactionCommentaire r : reactionsForComment) {
            System.out.println(r);
        }
        */


        // update a reaction
        //reactionCommentaireService.update(new ReactionCommentaire(1, commentaireService.getOneByID(8),userService.getOneByID(30),publicationService.getOneByID(17), Timestamp.valueOf(LocalDateTime.now())));
        // Delete a reaction
        //reactionCommentaireService.delete(1);

        // get one reaction by id

        //System.out.println(reactionCommentaireService.getOneByID(1));



        
    }
}









