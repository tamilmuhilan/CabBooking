package dummy;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("check")
public class BookingsResource
{


@GET
@Path("c")
	public String createBooking(@Context HttpServletRequest request) throws Exception
	{
       return request.getHeader("auth-key");
	}



}
