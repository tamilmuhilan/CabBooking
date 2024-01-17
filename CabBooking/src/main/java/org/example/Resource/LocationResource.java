package org.example.Resource;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.example.Dao.LocationDao;
import org.example.Model.Locations;
import org.example.example.AuthenticationUtil;

@Path("v1/locations")
public class LocationResource
{
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public static ArrayList<Locations> getLocationList(@Context HttpServletRequest req) throws Exception{
		if(!AuthenticationUtil.isAuthenticate(req,false))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		return LocationDao.getLocationdetails();
	}
}
