package vn.khmt.restful;

import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
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
    public Response getUser(@PathParam("param") String id){
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        String output = conn.getUser(Integer.parseInt(id));
        System.out.println(output);
        return Response.status(200).entity(output).build();
    }
    @POST
    @Path("/add")
    public Response addUser(@QueryParam("username") String username, @QueryParam("password") String password
                            , @QueryParam("email") String email, @QueryParam("name") String name){
        ConnectToSQL conn = new ConnectToSQL("POSTGRESQL", "ec2-54-227-253-228.compute-1.amazonaws.com:5432", "d8viikojj42e3b", "uzufecmqojhnyx", "WPJGueUbd3npLKslU2BEUOmMHx");
        String output = conn.addUser(username, password, email, name);
        System.out.println(output);
        return Response.status(200).entity(output).build();
    }
}
