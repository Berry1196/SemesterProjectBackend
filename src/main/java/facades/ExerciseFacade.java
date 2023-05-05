package facades;

import entities.Exercise;
import entities.Workout;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ExerciseFacade {
    private static EntityManagerFactory emf;
    private static ExerciseFacade instance;

    private ExerciseFacade() {
    }

    public static ExerciseFacade getExerciseFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ExerciseFacade();
        }
        return instance;
    }
    // Gets exercises by workout
    public List<Exercise> getExercisesByWorkout(Workout workout) {
        EntityManager em = emf.createEntityManager();
        List<Exercise> exercises;
        try {
            // creates a query that gets all exercises by workout
            exercises = em.createQuery("SELECT e FROM Exercise e WHERE e.workoutList = :workout", Exercise.class)
                    .setParameter("workout", workout)
                    .getResultList();
        } finally {
            em.close();
        }
        return exercises;
    }

}
