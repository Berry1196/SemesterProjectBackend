package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDTO;
import entities.User;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("application/json")
    public String getAllUsers() {
        return "Hello from the other side";
    }

    @POST
    @Path("create")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(String user) {
        UserDTO userDTO = GSON.fromJson(user, UserDTO.class);
        FACADE.createUser(userDTO);
        return Response.ok().build();
    }
}
