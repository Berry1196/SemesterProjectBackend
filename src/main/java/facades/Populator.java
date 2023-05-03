/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.CarDTO;
import dtos.RenameMeDTO;
import entities.RenameMe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.User;
import entities.Workout;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        User user = new User("user", "test");
        Workout workout = new Workout();
        workout.setName("Test");

        user.getWorkoutList().add(workout);
        workout.getUserList().add(user);

        em.persist(user);
        em.persist(workout);

        em.getTransaction().commit();
    }
    
    public static void main(String[] args) {
        populate();
    }
}
