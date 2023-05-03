/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.CarDTO;
import dtos.RenameMeDTO;
import entities.Exercise;

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

        Exercise exercise = new Exercise();

        em.persist(exercise);

        em.getTransaction().commit();
    }
    
    public static void main(String[] args) {
        populate();
    }
}
