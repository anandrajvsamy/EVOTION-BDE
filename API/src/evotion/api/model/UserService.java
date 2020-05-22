package evotion.api;


import it.unimi.evotion.api.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/users")
/*
@Api(value = "/analytic", description = "Operations about pets")
@Produces({"application/json"})*/
public class UserService {

   
	@GET
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
	public User login(@QueryParam("username") String username,@QueryParam("password") String password,@Context HttpServletRequest request) {
        
		
		User user=new User();
		user.setToken("123456");
		HttpSession session = request.getSession();
		session.setAttribute("logined","1");
		session.setAttribute("token","123456");
		return user;
    }

    
}
