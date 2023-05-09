package facades;

import dtos.ExerciseDTO;
import dtos.WorkoutDTO;
import entities.Exercise;
import entities.Workout;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
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

    public List<ExerciseDTO> getExercises() {
        EntityManager em = emf.createEntityManager();
        List<Exercise> exercises;
        try {
            exercises = em.createQuery("SELECT e FROM Exercise e", Exercise.class).getResultList();
        } finally {
            em.close();
        }
        return ExerciseDTO.getDTOs(exercises);
    }

    public ExerciseDTO addExercise(ExerciseDTO exerciseDTO) {
        EntityManager em = emf.createEntityManager();
        Exercise exercise = new Exercise(exerciseDTO);
        try {
            em.getTransaction().begin();
            em.persist(exercise);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new ExerciseDTO(exercise);
    }

    public ExerciseDTO deleteExercise(Long id) {
        EntityManager em = emf.createEntityManager();
        Exercise exercise = em.find(Exercise.class, id);
        try {
            em.getTransaction().begin();
            em.remove(exercise);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new ExerciseDTO(exercise);
    }
}
