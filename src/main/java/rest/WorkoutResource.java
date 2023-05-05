package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Workout;
import facades.CarFacade;
import facades.WorkoutFacade;
import kong.unirest.Unirest;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import kong.unirest.HttpResponse;

import java.io.File;
import java.util.Random;

@Path("workouts")
public class WorkoutResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final WorkoutFacade FACADE =  WorkoutFacade.getWorkoutFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("{muscle}")
    public String getAllWorkouts(@PathParam("muscle") String muscle) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.api-ninjas.com/v1/exercises?muscle="+muscle))
                .header("X-Api-Key", "QutD+llzBmpLntkJrjdTUg==7uizzo9ZIyZ0YzqL")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assert response != null;
        return response.body();
    }

    @GET
    @Produces("application/json")
    public String getPredefinedWorkouts() {
        return GSON.toJson(FACADE.getPredefinedWorkouts());
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
}
