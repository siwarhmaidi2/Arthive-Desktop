package com.arthive.tests;


import com.arthive.services.ServiceUser;

public class Main {
    public static void main(String[] args) {
        ServicePersonne sp = new ServicePersonne();
        System.out.println(sp.getAll());

          ServiceUser su = new ServiceUser() ;
        System.out.println("liste"+ su.getAll());

      // User u1 = new User("ayoub","toujani","toujaniayoub808@gamil.com","1234",2014-02-05,null,12);
      //  su.ajouter(u1);

    }
}



