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
        ConnectToSQL conn = DBConfig.quickConnect();
        User output = conn.getUser(Integer.parseInt(id));
        User authenticated = Authenticator.authenticateUser(headers);
        if (output.getName().equals(authenticated.getName()) || authenticated.getStatus() == DBConfig.ADMIN_STATUS) {
            return Response.status(200).entity(output).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(null).build();
        }
        
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        ConnectToSQL conn = DBConfig.quickConnect();
        ArrayList<User> output = conn.getAllUsers();
        return Response.status(200).entity(new GenericEntity<ArrayList<User>>(output){}).build();
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User u){
        ConnectToSQL conn = DBConfig.quickConnect();
        String output = conn.addUser(u.getId(), u.getUsername(), u.getPassword(), u.getEmail(), u.getStatus(), u.getName());
        System.out.println(output);
        return Response.status(200).entity(output).build();
    }
}
