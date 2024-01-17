package org.example.Resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.example.Dao.UserDao;
import org.example.Model.User;

@Path("v1/users")
public class UserResource
{
	private static final Logger logger = Logger.getLogger("UserResource.class");
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getUser() throws Exception{
		return UserDao.getUserDetails();

	}

	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response addUser(User user) throws Exception{
		return UserDao.addProcess(user);
	}

	@PUT
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})

	public Response editUser(@PathParam("id") int id,User user) throws Exception{
		if (id==0 || user == null ) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Error cames in path or body").build();
		}
		return UserDao.editProcess(user,id);
	}

	@DELETE
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response deleteUser(@PathParam("id") int id) throws Exception{
		logger.log(Level.INFO,"id!!!"+id);
		return UserDao.deleteProcess(id);
	}

}
