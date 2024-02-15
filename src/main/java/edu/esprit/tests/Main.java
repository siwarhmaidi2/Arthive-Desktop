package edu.esprit.tests;

import edu.esprit.entities.*;
import edu.esprit.services.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Create a user
        //create date of user

        LocalDate birthDate = LocalDate.of(2001, 5, 15);
        User user = new User("ayoub", "toujani", "ayoubtoujani@gmail.com", "1234563", java.sql.Date.valueOf(birthDate), "tunis", 123456789);
        ServiceUser userService = new ServiceUser();

        //Add a user
      // userService.add(user);
      //  userService.getOneByID(30);
     //   userService.getAll();



       /* // Get all users
        Set<User> users = userService.getAll();
        System.out.println("List of Users:");
        for (User u : users) {
            System.out.println(u);
        }
        */

        // Update a user
        //  userService.update(new User(20,"Ayoub", "Toujani", "ayoubtoujani808@gmail.com", "1234563", java.sql.Date.valueOf(birthDate), "tunis", 123456789));

        // Delete a user
        //userService.delete(20);

        // get one user by id
        //  System.out.println(userService.getOneByID(20));



        // Create a publication
      Publication publication = new Publication(18,"Hello World!", Timestamp.valueOf(LocalDateTime.now()), user.getId_user());
       ServicePublication publicationService = new ServicePublication();

        // Add a publication
    //  publicationService.add(publication);

        // Get all publications
        /*Set<Publication> publications = publicationService.getAll();
        System.out.println("List of Publications:");
        for (Publication p : publications) {
            System.out.println(p);
        }
*/


        // Update a publication
        //publicationService.update(new Publication(11,"Hello World!", Timestamp.valueOf(LocalDateTime.now()), user.getId_user()));

        // Delete a publication
        //publicationService.delete(11);

        // get one publication by id
       // System.out.println(publicationService.getOneByID(11));


        // Create a comment
        Commentaire commentaire = new Commentaire("Nice post!", Timestamp.valueOf(LocalDateTime.now()), user.getId_user(), publication.getId_publication());
       ServiceCommentaire commentaireService = new ServiceCommentaire();

        // Add a comment
       // commentaireService.add(commentaire);

        // Get all comments for a publication
       /* Set<Commentaire> commentsForPublication = commentaireService.getAll();

        // Print the comments
        System.out.println("List of Comments:");
        for (Commentaire c : commentsForPublication) {
            System.out.println(c);
        }


*/
        // Update a comment
       // commentaireService.update(new Commentaire(1, "Nice post!", Timestamp.valueOf(LocalDateTime.now()), user.getId_user(), publication.getId_publication()));

        // Delete a comment
        //commentaireService.delete(1);

        // get one comment by id
       // System.out.println(commentaireService.getOneByID(1));


        // Create a reaction

      Reaction reaction = new Reaction(user.getId_user(), publication.getId_publication(), commentaire.getIdCommentaire(), Timestamp.valueOf(LocalDateTime.now()));
     ServiceReaction reactionService = new ServiceReaction();

        // Add a reaction
     // reactionService.add(reaction);

        // Get all reactions for a publication
      //  Set<Reaction> reactionsForPublication = reactionService.getAll();

        // Print the reactions
       /* System.out.println("List of Reactions:");
        for (Reaction r : reactionsForPublication) {
            System.out.println(r);
        }
        */


        // Update a reaction
       // reactionService.update(new Reaction(1, user.getId_user(), publication.getId_publication(), commentaire.getIdCommentaire(), Timestamp.valueOf(LocalDateTime.now())));

        // Delete a reaction
       // reactionService.delete(1);

        // get one reaction by id
        //System.out.println(reactionService.getOneByID(1));


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
        ReactionCommentaire reactionCommentaire = new ReactionCommentaire(36, 18, 9);
       // reactionCommentaireService.add(reactionCommentaire);

    }
}









