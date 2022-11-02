package com.luis.nosqli;


import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class UserEndpoint {
    
    @Inject 
    UserService service;
        
    @GET
    public List<User> list() {
        return service.list();
    }
    
    @POST
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {       
        User user = service.login(username, password);
        String token = "Auth failed";
        if (user != null)  token = "token";
        return Response.ok(token).build();
    }
    
}
