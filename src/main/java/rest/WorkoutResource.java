package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Workout;
import facades.CarFacade;
import facades.WorkoutFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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
}
