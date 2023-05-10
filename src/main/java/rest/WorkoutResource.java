package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ExerciseDTO;
import dtos.WorkoutDTO;
import facades.WorkoutFacade;
import kong.unirest.Unirest;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import kong.unirest.HttpResponse;

import java.io.File;
import java.util.List;
import java.util.Random;

@Path("workouts")
public class WorkoutResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final WorkoutFacade FACADE =  WorkoutFacade.getWorkoutFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("application/json")
    public String getPredefinedWorkouts() {
        return GSON.toJson(FACADE.getPredefinedWorkouts());
    }

    @GET
    @Produces("application/json")
    @Path("/{muscle}")
    public String getWorkoutsByMuscle(@PathParam("muscle") String muscle) {
        return GSON.toJson(FACADE.getWorkoutsByMuscleGroup(muscle));
    }

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public Response linkWorkoutToUser(@PathParam("username") String username, String workout) {
        WorkoutDTO workoutDTO = GSON.fromJson(workout, WorkoutDTO.class);
        List<WorkoutDTO> workouts = FACADE.linkWorkoutToUser(username, workoutDTO);
        return Response.ok(GSON.toJson(workouts)).build();
    }

    @POST
    @Path("/photo")
    @Produces("image/jpeg")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response generateWorkoutPhoto(String muscles) {
        HttpResponse<File> response = Unirest.get("https://muscle-group-image-generator.p.rapidapi.com/getImage?muscleGroups=" + muscles)
                .header("X-RapidAPI-Key", "04cb178b04msh880aff6d34465f7p118339jsn457ca14b4907")
                .header("X-RapidAPI-Host", "muscle-group-image-generator.p.rapidapi.com")
                .asFile(new Random().nextDouble() + ".jpg");

        return Response.ok(response.getBody()).build();
    }

    @GET
    @Produces("application/json")
    @Path("/user/{username}")
    public String getWorkoutsByUser(@PathParam("username") String username) {
        return GSON.toJson(FACADE.getWorkoutsByUser(username));
    }

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createWorkout(String workout) {
        WorkoutDTO workoutDTO = GSON.fromJson(workout, WorkoutDTO.class);
        return GSON.toJson(FACADE.createWorkout(workoutDTO));
    }

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/exercises/{workoutId}")
    public WorkoutDTO addExerciseToWorkout(@PathParam("workoutId") Long workoutId, String exerciseIDs) {
        Long[] ids = GSON.fromJson(exerciseIDs, Long[].class);
        return FACADE.addExercisesToWorkout(workoutId, ids);
    }
}
