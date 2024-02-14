package edu.esprit.tests;


import edu.esprit.services.ServiceUser;

import java.time.LocalDate;

import edu.esprit.entities.User;

public class Main {
    public static void main(String[] args) {


        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        User u1 = new User(12,"zied", "zhiri", "medali@gmail.com", "1234", java.sql.Date.valueOf(birthDate), "tunis", 12345678);

        ServiceUser su = new ServiceUser() ;
        su.add(u1);
         System.out.println(su.getAll());
         su.update(u1);

    }
}



