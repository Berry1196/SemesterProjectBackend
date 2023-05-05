package facades;

import entities.Workout;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@NoArgsConstructor
public class WorkoutFacadeTest {

    private static EntityManagerFactory emf;
    private static WorkoutFacade facade;

    Workout workout1, workout2;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = WorkoutFacade.getWorkoutFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        workout1 = new Workout("Chest workout");
        workout2 = new Workout("Leg workout");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Workout.deleteAllRows").executeUpdate();
            em.persist(workout1);
            em.persist(workout2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void countNumberOfWorkoutsInTestDB() {
        assertEquals(2, facade.workoutCount(), "Expects two rows in the database");
    }
}
