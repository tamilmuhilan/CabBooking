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

import org.example.Dao.CabDao;
import org.example.Dao.UserDao;
import org.example.Model.Cab;
import org.example.example.AuthenticationUtil;

@Path("v1/cabs")
public class CabResource
{
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public static ArrayList<Cab> getCabList(@Context HttpServletRequest req) throws Exception{
		if(!AuthenticationUtil.isAuthenticate(req,true))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		return CabDao.getCabDetails();
	}
	@GET
	@Path("available")
	@Produces({MediaType.APPLICATION_JSON})
	public static ArrayList<Cab> getAvailableCabList(@Context HttpServletRequest req) throws Exception{
		if(!AuthenticationUtil.isAuthenticate(req,true))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		return CabDao.getAvailableCabDetails();
	}



	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static Response addNewCab(Cab cab,@Context HttpServletRequest req) throws Exception {
		if(!AuthenticationUtil.isAuthenticate(req,true))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		return CabDao.createNewCab(cab);
	}

	@PUT
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static Response editCab(@PathParam("id") int id,Cab cab,@Context HttpServletRequest req)  {
		if(!AuthenticationUtil.isAuthenticate(req,true))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		if (id==0 || cab == null ) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Error cames in path or body").build();
		}
		return CabDao.editProcess(cab,id);
	}


//	@DELETE
//	@Path("delete-cab/{id}")
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	public Response deleteUser(@PathParam("id") int id) throws Exception{
//		return CabDao.deleteProcess(id);
//	}

	//	@GET
	//	@Path("list-available-cabs")
	//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	//	public static Response getAvailableCabList(){
	//		return CabDao.getAvailableCabDetails();
	//	}


}
