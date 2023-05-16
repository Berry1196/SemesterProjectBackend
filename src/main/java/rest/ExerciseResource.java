package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ExerciseDTO;
import dtos.WorkoutDTO;
import facades.ExerciseFacade;
import facades.WorkoutFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("exercises")
public class ExerciseResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ExerciseFacade FACADE =  ExerciseFacade.getExerciseFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("application/json")
    public String getExercises() {
        return GSON.toJson(FACADE.getExercises());
    }

    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addExercise(String exercise) {
        ExerciseDTO exerciseDTO = GSON.fromJson(exercise, ExerciseDTO.class);
        ExerciseDTO returnedDTO = FACADE.addExercise(exerciseDTO);
        return Response.ok(GSON.toJson(returnedDTO)).build();
    }

    @DELETE
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteExercise(@PathParam("id") Long id) {
        ExerciseDTO exerciseDTO = FACADE.deleteExercise(id);
        return Response.ok(GSON.toJson(exerciseDTO)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{name}")
    public String getExercisesByName(@PathParam("name") String name) {
        return GSON.toJson(FACADE.getExercisesByName(name));
    }
}
