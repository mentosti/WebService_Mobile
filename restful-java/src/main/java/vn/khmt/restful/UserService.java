package vn.khmt.restful;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import vn.khmt.db.ConnectToSQL;
/**
 * REST Web Service
 *
 * @author TheNhan
 */
@Path("user")
public class UserService {

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("param") String id, @Context HttpHeaders headers){
        System.out.println("Get User");
        String authenticationHeader = headers.getRequestHeader("authentication").get(0);
        System.out.println("Authentication header = " + authenticationHeader);
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        User output = conn.getUser(Integer.parseInt(id));
        return Response.status(200).entity(output).build();
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        ArrayList<User> output = conn.getAllUsers();
        return Response.status(200).entity(new GenericEntity<ArrayList<User>>(output){}).build();
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User u){
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        String output = conn.addUser(u.getId(), u.getUsername(), u.getPassword(), u.getEmail(), u.getStatus(), u.getName());
        System.out.println(output);
        return Response.status(200).entity(output).build();
    }
}
