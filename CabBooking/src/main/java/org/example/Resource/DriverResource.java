package org.example.Resource;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.example.Dao.DriverDao;
import org.example.Dao.UserDao;
import org.example.Model.Driver;
import org.example.example.AuthenticationUtil;

@Path("v1/drivers")
public class DriverResource
{
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static ArrayList<Driver> getDriverList(@Context HttpServletRequest req) throws Exception{
		if(!AuthenticationUtil.isAuthenticate(req,true))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		return DriverDao.getDriverDetails();
	}


	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static Response addNewDriver(Driver driver,@Context HttpServletRequest req) throws Exception{
		if(!AuthenticationUtil.isAuthenticate(req,true))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		return DriverDao.createNewDriver(driver);
	}

	@PUT
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static Response editDriver(@PathParam("id") int id,Driver driver,@Context HttpServletRequest req) throws Exception{
		if(!AuthenticationUtil.isAuthenticate(req,true))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		if (id==0 || driver == null ) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Error cames in path or body").build();
		}
		return DriverDao.editProcess(driver,id);
	}

//	@DELETE
//	@Path("delete-driver/{id}")
//	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	public Response deleteUser(@PathParam("id") int id) throws Exception{
//		return DriverDao.deleteProcess(id);
//	}

}
