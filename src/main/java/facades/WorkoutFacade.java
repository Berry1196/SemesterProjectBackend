package facades;

import dtos.WorkoutDTO;
import entities.Exercise;
import entities.Workout;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class WorkoutFacade {
    private static EntityManagerFactory emf;
    private static WorkoutFacade instance;

    private WorkoutFacade() {
    }

    public static WorkoutFacade getWorkoutFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new WorkoutFacade();
        }
        return instance;
    }

    public List<WorkoutDTO> getPredefinedWorkouts() {
        EntityManager em = emf.createEntityManager();
        ExerciseFacade exerciseFacade = ExerciseFacade.getExerciseFacade(emf);

        // Retrieving all workouts from the database
        List<Workout> workouts = em.createQuery("SELECT w FROM Workout w", Workout.class).getResultList();

        // Adding exercises to each workout
        workouts.forEach(workout -> {
            List<Exercise> exercises = exerciseFacade.getExercisesByWorkout(workout);
            workout.setExercisesList(exercises);
        });

        // Converting to DTOs and returning WorkoutDTOs
        return WorkoutDTO.getDTOs(workouts);
    }
}
