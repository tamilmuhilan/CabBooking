package org.example.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import org.example.Model.Booking;
import org.example.Model.UserBooking;
import org.example.example.ConnectionUtil;
import org.example.example.EntityToJson;
import org.json.JSONArray;

import com.google.gson.Gson;

public class BookingDao
{
	private static final Logger logger = Logger.getLogger("BookingDao.class");
	private static final int CLOSE_DISTANCE=2;
	public static  String GET_BOOKING_INFO = "select * from bookingtable ";
	public static String BOOKING_INFOWITHCABDETAILS="select a.bookid as bookid,a.pickpoint as pickpoint,a.droppoint as droppoint ,b.cabid as cabid,c.driverid as driverid from bookingtable a left join cabtable b on a.cabid=b.cabid left join drivertable c on a.driverid=c.driverid where b.availableforbooking=true order by b.availableseat desc ";

	public static  String GET_NEXT_BOOKID = "select max(bookid) as id from bookingtable";
	public static  String INSERT_BOOKINGDETAILS = "insert into bookingdetailtable(bookid,cabid,username,userid,pickpoint,droppoint,booktime) values(?,?,?,?,?,?,now()) ";
	public static  String CAB_AVALABILITY = "select * from cabtable a where a.availableforbooking=true and a.driverassigned=true and a.availableseat!=0 and cabid=? ";
	public static  String GET_NEW_CAB = "select a.cabid as cabid,b.driverid as driverid,a.availableseat as availableseat,a.seatingCapacity as seatingCapacity from cabtable a left join drivertable b on b.assignedCabId=a.cabid  where a.availableforbooking=true and a.driverassigned=true and a.availableseat!=0 and a.seatingCapacity=a.availableseat+1 order by a.cabid,a.availableseat limit 1 ";

	public static  String UPDATE_CAB = "update cabtable set availableseat=?,availableforbooking=?  where cabid=? ";
	public static  String INSERTBOOKING = "insert into bookingtable(bookid,cabid,driverid,pickpoint,droppoint) values(?,?,?,?,?) ";
	public static String BOOKING_POINT_UPDATE_QRY="update bookingtable set pickpoint=? ,droppoint=? where bookid=?  ";

	public static String GET_CUSTOMER_BOOKINGDETAILS="\n"
		+ "select a.bookid bookid,c.cabno cabno,d.drivername drivername,d.driverno driverno,pickloc.location_name pickpoint,droploc.location_name droppoint,\n"
		+ "b.booktime bookingtime,b.userid userid\n"
		+ "from bookingtable a \n"
		+ "left join bookingdetailtable b  on a.bookid =b.bookid\n"
		+ "left join cabtable c on a.cabid=c.cabid \n"
		+ "left JOIN drivertable d on a.driverid =d.driverid \n"
		+ "left join locations pickloc on b.pickpoint=pickloc.id\n"
		+ "left join locations droploc on b.droppoint =droploc.id \n"
		+ "where b.userid=? ";


	public static Response getBookingDetails() throws Exception
	{
		ArrayList<Booking> bookingList = new ArrayList<>();
		try(Connection con = ConnectionUtil.getConnection();)
		{
			PreparedStatement pstm = con.prepareStatement(GET_BOOKING_INFO);
			ResultSet rs = pstm.executeQuery();
			while(rs.next())
			{
				Booking booking = new Booking();
				booking.setBookId(rs.getInt("bookid"));
				booking.setCabId(rs.getInt("cabid"));
				booking.setDriverId(rs.getInt("driverid"));
				booking.setPickPoint(rs.getInt("pickpoint"));
				booking.setDropPoint(rs.getInt("droppoint"));
				bookingList.add(booking);
			}
		}
		catch(Exception e){
			throw e;
		}
		return Response.ok().entity("Booking List :"+bookingList).build();

	}

	public static Response bookingProcess(UserBooking userBooking) throws Exception
	{
		Booking book=new Booking();
		int cabid = getClosetCab(userBooking,book);
		logger.log(Level.INFO,"CabId:"+cabid+" "+userBooking);
		Connection con=null;
		PreparedStatement pstm = null;
		try
		{
			con=ConnectionUtil.getConnection();
			con.setAutoCommit(false);
			logger.log(Level.INFO,"check1"+cabid);
            if(cabid==0 || book.getDriverId()==0){
				return Response.ok().entity(EntityToJson.jsonResponse("Failed","Cab not available now!!")).build();
			}
			logger.log(Level.INFO,"check2"+cabid);

			Boolean isCabAvailable = checkCabAvailablityAndUpdateSeat(cabid, con);
			int bookId = getPreviousBookId(cabid, con);
			int uPickPoint=userBooking.getPickPoint();
			int uDropPoint=userBooking.getDropPoint();

			if(isCabAvailable)
			{
				if(bookId == 0)
				{
					bookId = getNextBookId(con);
					logger.log(Level.INFO,"bookid "+bookId+"  "+book);
					pstm = con.prepareStatement(INSERTBOOKING);
					pstm.setInt(1, bookId);
					pstm.setInt(2, cabid);
					pstm.setInt(3, book.getDriverId());
					pstm.setInt(4, uPickPoint);
					pstm.setInt(5, uDropPoint);
					pstm.executeUpdate();

				}
				else{
					pstm = con.prepareStatement(BOOKING_POINT_UPDATE_QRY);
					pstm.setInt(1,uPickPoint > book.getPickPoint() ? uPickPoint : book.getPickPoint());
					pstm.setInt(2,uDropPoint > book.getDropPoint() ? uDropPoint : book.getDropPoint());
					pstm.setInt(3,bookId);
					pstm.executeUpdate();
				}
				pstm = con.prepareStatement(INSERT_BOOKINGDETAILS);
				pstm.setInt(1, bookId);
				pstm.setInt(2, cabid);
				pstm.setString(3, userBooking.getUserName());
				pstm.setInt(4, userBooking.getUserId());
				pstm.setInt(5, userBooking.getPickPoint());
				pstm.setInt(6, userBooking.getDropPoint());
				pstm.executeUpdate();
				con.commit();
				return Response.ok().entity(EntityToJson.jsonResponse("Success","Booking confirmed")).build();
			}
			else
			{
				con.rollback();
				return Response.ok().entity(EntityToJson.jsonResponse("Failed","Cab not available now!!")).build();
			}
		}
		catch(Exception e)
		{
            con.rollback();
			throw e;
		}
		finally
		{
			if(pstm!=null){
				try{pstm.close();} catch(Exception e){logger.log(Level.SEVERE,"Exception came statement close: "+e); }
			}
			if(con!=null){
				try{con.close();} catch(Exception e){logger.log(Level.SEVERE,"Exception came statement close: "+e);}
			}
		}


	}

	public static int getClosetCab(UserBooking userBooking,Booking book) throws Exception
	{
        try
		{
			Connection con = ConnectionUtil.getConnection();
			PreparedStatement pr = null;
			pr=con.prepareStatement(BOOKING_INFOWITHCABDETAILS);
			ResultSet result = pr.executeQuery();
			while(result.next())
			{
				logger.log(Level.INFO,"enter into closet");
				int pickPoint = result.getInt("pickpoint");
				int dropPoint = result.getInt("droppoint");
				int cabId=result.getInt("cabid");
				int driverId=result.getInt("driverid");
				if(distanceBetweenCab(userBooking.getPickPoint(), userBooking.getDropPoint(), pickPoint, dropPoint) <= CLOSE_DISTANCE)
				{
					logger.log(Level.INFO,"closetava"+cabId);
					book.setCabId(cabId);
					book.setDriverId(driverId);
					book.setPickPoint(pickPoint);
					book.setDropPoint(dropPoint);
					 return cabId;
				}
			}
			String qry=GET_NEW_CAB;
			pr=con.prepareStatement(qry);
			ResultSet rs=pr.executeQuery();
			logger.log(Level.INFO,"  "+qry);
            while(rs.next()){
				book.setCabId(rs.getInt("cabid"));
				book.setDriverId(rs.getInt("driverid"));
				book.setBookId(userBooking.getPickPoint());
				book.setDropPoint(userBooking.getDropPoint());
				logger.log(Level.INFO,"newclosetava"+book.getCabId());
				return rs.getInt("cabid");
			}

		}
		catch(Exception e){
			throw e;
		}
        return 0;
	}

	public static int getNextBookId(Connection con) throws SQLException
	{
		int nextBookId = 0;
		PreparedStatement pstm = con.prepareStatement(GET_NEXT_BOOKID);
		ResultSet rs = pstm.executeQuery();
		while(rs.next())
		{
			nextBookId = rs.getInt("id")+1;
			logger.log(Level.INFO,"nextbook"+nextBookId+" "+rs.getInt("id"));
		}
		return nextBookId;
	}

	public static Boolean checkCabAvailablityAndUpdateSeat(int cabid, Connection con)
	{
		try
		{
			PreparedStatement pr = null;
			pr = con.prepareStatement(CAB_AVALABILITY);
			pr.setInt(1, cabid);
			ResultSet rs = pr.executeQuery();
			while(rs.next())
			{

				int seat = rs.getInt("availableseat");
				logger.log(Level.INFO,"available seat "+cabid+" "+seat);
				pr = con.prepareStatement(UPDATE_CAB);
				pr.setInt(1, seat - 1);
				pr.setBoolean(2, (seat - 1 == 0) ? false : true);
				pr.setInt(3, cabid);
				logger.log(Level.INFO,"prep"+pr.toString());
				int i=pr.executeUpdate();
				logger.log(Level.INFO,"update "+i);
				return true;
			}

		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
		logger.log(Level.INFO,"check3"+cabid);
		return false;
	}

	public static int getPreviousBookId(int cabid, Connection con)
	{
		try
		{
			String qry=GET_BOOKING_INFO;
				qry+=" where cabid=? ";
			PreparedStatement pr = con.prepareStatement(qry);
			pr.setInt(1, cabid);
			ResultSet rs = pr.executeQuery();
			while(rs.next())
			{
				return rs.getInt("bookid");
			}
			return 0;
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}


	public static int distanceBetweenCab(int pickPoint, int dropPoint, int pickPoint1, int dropPoint1)
	{
		int distance = Math.abs(pickPoint - pickPoint1) + Math.abs(dropPoint - dropPoint1);
		return distance;
	}

	public static String getCustomerBookingDetails(int id) throws Exception
	{
         ArrayList<HashMap<String,Object>> bookingList=new ArrayList<>();
		 try(Connection con=ConnectionUtil.getConnection())
		 {
			 PreparedStatement pr= con.prepareStatement(GET_CUSTOMER_BOOKINGDETAILS);
			 pr.setInt(1,id);
			 ResultSet rs= pr.executeQuery();
			 while(rs.next()){
				 HashMap<String,Object> bookings=new HashMap<>();
				 bookings.put("Bookid",rs.getInt("bookid"));
				 bookings.put("Cab No",rs.getInt("cabno"));
				 bookings.put("Driver Name",rs.getString("drivername"));
				 bookings.put("Driver No",rs.getLong("driverno"));
				 bookings.put("Pickup Point",rs.getString("pickpoint"));
				 bookings.put("Drop Point",rs.getString("droppoint"));
				 bookings.put("Booking Time",rs.getTimestamp("bookingtime"));
				 bookingList.add(bookings);
			 }
			 Gson gson = new Gson();
			 String jsonString = gson.toJson(bookingList);
			 JSONArray jsonArray = new JSONArray(jsonString);
			return jsonArray.toString();
		 }
		 catch(Exception e){
			 throw e;
		 }

	}
}
