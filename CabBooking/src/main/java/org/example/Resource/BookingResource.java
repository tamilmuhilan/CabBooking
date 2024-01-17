package org.example.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.example.Dao.BookingDao;
import org.example.Model.Booking;
import org.example.Model.UserBooking;
import org.example.example.AuthenticationUtil;


@Path("v1/bookings")
public class BookingResource
{
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static Response getBookingList(@Context HttpServletRequest req) throws Exception{
		if(!AuthenticationUtil.isAuthenticate(req,true))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		return BookingDao.getBookingDetails();
	}
	@GET
	@Path("customer/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static String getCustomerBookingList(@PathParam("id") int id,@Context HttpServletRequest req) throws Exception{
		int loginId=(int)req.getSession().getAttribute("Login_Id");
		if(!AuthenticationUtil.isAuthenticate(req,false) || loginId!=id )
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		return BookingDao.getCustomerBookingDetails(id);
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public static Response bookCab(UserBooking book,@Context HttpServletRequest req) throws Exception{
		if(!AuthenticationUtil.isAuthenticate(req,false))
		{
			throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		HttpSession session = req.getSession();
		book.setUserId((int)session.getAttribute("Login_Id"));
		book.setUserName((String)session.getAttribute("username"));
		return BookingDao.bookingProcess(book);
	}


//	@PUT
//	@Path("cancel-book/{id}")
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	public static Response cancelBook(@PathParam("id") int id){
//		return BookingDao.bookingCancelProcess(id);
//	}
}
