package vn.khmt.restful;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
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
        ConnectToSQL conn = DBConfig.quickConnect();
        User output = conn.getUser(Integer.parseInt(id));
        User authenticated = AuthorizationChecker.checkFromHeaders(headers);
        if (output.getUsername().equals(authenticated.getUsername()) || authenticated.getStatus() == DBConfig.ADMIN_STATUS) {
            return Response.status(200).entity(output).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(null).build();
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(@Context HttpHeaders headers){
        User authenticated = AuthorizationChecker.checkFromHeaders(headers);
        if (authenticated.getStatus() == DBConfig.ADMIN_STATUS) {
            ConnectToSQL conn = DBConfig.quickConnect();
            ArrayList<User> output = conn.getAllUsers();
            return Response.status(200).entity(new GenericEntity<ArrayList<User>>(output){}).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(null).build();
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User u){
        ConnectToSQL conn = DBConfig.quickConnect();
        String output = conn.addUser(u.getUsername(), u.getPassword(), u.getEmail(), u.getStatus(), u.getName());
        System.out.println(output);
        return Response.status(200).entity("{\"result\":\""+output+"\"}").build();
    }
    
    @PUT
    @Path("/rename")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeName(User u, @Context HttpHeaders headers){
        System.out.println(u.getUsername() + " " + u.getName());
        User authenticated = AuthorizationChecker.checkFromHeaders(headers);
        ConnectToSQL conn = DBConfig.quickConnect();
        if (authenticated.getUsername().equals(u.getUsername())) {
            String output = conn.renameUser(authenticated.getId(), u.getName());
            System.out.println(output);
            return Response.status(200).entity("{\"result\":\""+output+"\"}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(null).build();
    }
}
