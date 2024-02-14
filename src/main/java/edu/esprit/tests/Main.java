package edu.esprit.tests;



import edu.esprit.entities.Publication;
import edu.esprit.entities.User;

import edu.esprit.services.ServicePublication;
import edu.esprit.services.ServiceUser;


import java.sql.Timestamp;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {


        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        User u1 = new User(12,"med", "ali", "medali@gmail.com", "1234", java.sql.Date.valueOf(birthDate), "tunis", 12345678);

        ServiceUser su = new ServiceUser() ;
        //su.add(u1);
       // System.out.println(su.getAll());

       // System.out.println("**************************************************");


       // System.out.println(su.getOneByID(1));


      //  System.out.println("**************************************************");


       ServicePublication sp = new ServicePublication();

// Assuming you have a constructor for Publication that takes content, timestamp, and user as parameters
      Publication newPublication = new Publication("hello", new Timestamp(System.currentTimeMillis()), u1.getId_user());

      // sp.add(newPublication);

      //  sp.add(newPublication);

        System.out.println("liste des publications" +sp.getAll());

      //  sp.update(new Publication(5,"hii", new Timestamp(System.currentTimeMillis()), u1.getId_user()));

       //sp.getOneByID(6);



        



    }
        }

//su.add(u1);
//System.out.println(su.getAll());








