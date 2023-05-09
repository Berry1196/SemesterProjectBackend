package facades;

import dtos.ExerciseDTO;
import dtos.WorkoutDTO;
import entities.Exercise;
import entities.User;
import entities.Workout;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
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

    public int workoutCount() {
        EntityManager em = emf.createEntityManager();
        try {
            ArrayList<Workout> workouts = new ArrayList<>(em.createQuery("SELECT w FROM Workout w", Workout.class).getResultList());
            return workouts.size();
        } finally {
            em.close();
        }
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


    public List<WorkoutDTO> getWorkoutsByMuscleGroup(String muscleGroup) {
        EntityManager em = emf.createEntityManager();
        ExerciseFacade exerciseFacade = ExerciseFacade.getExerciseFacade(emf);

        // Retrieve workouts from db that contains exercises with the given muscle group
        List<Workout> workouts = em.createQuery("SELECT w FROM Workout w JOIN w.exercisesList e WHERE e.muscle = :muscleGroup", Workout.class)
                .setParameter("muscleGroup", muscleGroup)
                .getResultList();

        // Converting to DTOs and returning WorkoutDTOs
        return WorkoutDTO.getDTOs(workouts);
    }

    public List<WorkoutDTO> linkWorkoutToUser(String username, WorkoutDTO workoutDTO) {
        EntityManager em = emf.createEntityManager();
        List<Workout> workouts = new ArrayList<>();
        try {
            em.getTransaction().begin();
            // Find user from username
            User user = em.find(User.class, username);
            // Find workout from workoutDTO
            Workout workout = em.find(Workout.class, workoutDTO.getId());
            // Add workout to user
            user.getWorkoutList().add(workout);
            // Add user to workout
            workout.getUserList().add(user);
            // Persist changes
            em.merge(user);
            em.merge(workout);

            // Retrieve all workouts from user
            workouts = em.createQuery("SELECT w FROM Workout w JOIN w.userList u WHERE u.userName = :username", Workout.class)
                    .setParameter("username", username)
                    .getResultList();

            em.getTransaction().commit();

            return WorkoutDTO.getDTOs(workouts);
        } finally {
            em.close();
        }
    }

    //Create a workout
    public WorkoutDTO createWorkout(WorkoutDTO workoutDTO) {
        EntityManager em = emf.createEntityManager();
        Workout workout = new Workout(workoutDTO.getName());
        try {
            em.getTransaction().begin();
            em.persist(workout);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new WorkoutDTO(workout);
    }
    
    public WorkoutDTO addExercisesToWorkout(Long workoutId, Long[] ids) {
        EntityManager em = emf.createEntityManager();
        ExerciseFacade exerciseFacade = ExerciseFacade.getExerciseFacade(emf);
        Workout workout = em.find(Workout.class, workoutId);
        try {
            em.getTransaction().begin();
            for (Long id : ids) {
                Exercise exercise = exerciseFacade.getExerciseById(id);
                workout.getExercisesList().add(exercise);
                exercise.getWorkoutList().add(workout);
            }
            em.merge(workout);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new WorkoutDTO(workout);
    }

    public List<WorkoutDTO> getWorkoutsByUser(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Workout> workouts = em.createQuery("SELECT w FROM Workout w JOIN w.userList u WHERE u.userName = :username", Workout.class)
                    .setParameter("username", username)
                    .getResultList();
            return WorkoutDTO.getDTOs(workouts);
        } finally {
            em.close();
        }
    }
}

