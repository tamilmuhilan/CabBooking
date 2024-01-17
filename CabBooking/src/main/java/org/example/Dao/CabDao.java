package org.example.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import org.example.Model.Cab;
import org.example.example.ConnectionUtil;
import org.example.example.EntityToJson;

public class CabDao
{
	    private static final Logger logger = Logger.getLogger("CabDao.class");
         public static String ADD_CAB="insert into cabtable(cabno,colour,seatingcapacity,availableseat,availableforbooking,driverassigned) values(?,?,?,?,?,false)";
		 public static String EDIT_CAB="update cabtable set cabno=?,colour=?,seatingcapacity=?,availableseat=?,availableforbooking=? where cabid=?";
		 public static String EDIT_DRIVERASSIGNED="update cabtable set driverassigned=? where cabid=?";
	public static  ArrayList<Cab> getCabDetails() throws Exception{
		     ArrayList<Cab> cabList=new ArrayList<>();

		    try(Connection con= ConnectionUtil.getConnection())
			{
				PreparedStatement pstm= con.prepareStatement("select * from cabtable");
				ResultSet rs=pstm.executeQuery();
				while(rs.next()){
					Cab cab=new Cab();
					cab.setId(rs.getInt("cabid"));
					cab.setCabNo(rs.getString("cabno"));
					cab.setColour(rs.getString("colour"));
					cab.setSeatingCapacity(rs.getInt("seatingcapacity"));
					cab.setAvailableSeat(rs.getInt("availableseat"));
					cab.setAvailableForBooking(rs.getBoolean("availableforbooking"));
					cabList.add(cab);
				}
				return cabList;
			}
			catch(Exception e){
				logger.log(Level.SEVERE,"Exception in cab details: "+e);
				//return Response.status(Response.Status.BAD_REQUEST).entity("Failed to get cab details: " + e.getMessage()).build();
			}
          return null;
	}

//	public static Response getAvailableCabDetails()
//	{
//	}

	public static  Response createNewCab(Cab cab) throws Exception
	{
		PreparedStatement pstm= null;
		try(Connection con=ConnectionUtil.getConnection();)
		{

			pstm=con.prepareStatement(ADD_CAB);
			pstm.setString(1,cab.getCabNo());
			pstm.setString(2,cab.getColour());
			pstm.setInt(3,cab.getSeatingCapacity());
			pstm.setInt(4,cab.getAvailableSeat());
			pstm.setBoolean(5, cab.isAvailableForBooking());
			pstm.executeUpdate();
			return Response.ok(Response.Status.CREATED).entity(EntityToJson.jsonResponse("Success","Cab created Successfully")).build();
		}
		catch(Exception e){
			logger.log(Level.INFO,"cab exception"+e);
			return Response.status(Response.Status.BAD_REQUEST).entity(EntityToJson.jsonResponse("Failed","Failed to create cab")).build();
		}
	}

	public static Response editProcess(Cab cab,int id)
	{
		PreparedStatement pstm= null;
		try(Connection con=ConnectionUtil.getConnection();)
		{
			pstm=con.prepareStatement(EDIT_CAB);
			pstm.setString(1,cab.getCabNo());
			pstm.setString(2,cab.getColour());
			pstm.setInt(3,cab.getSeatingCapacity());
			pstm.setInt(4,cab.getAvailableSeat());
			pstm.setBoolean(5, cab.isAvailableForBooking());
			pstm.setInt(6,id);
			pstm.executeUpdate();
			return Response.ok().entity(EntityToJson.jsonResponse("Success","Cab edited Successfully")).build();
		}
		catch(Exception e){
			logger.log(Level.INFO,"Exception in cab edit: "+e);
			return Response.status(Response.Status.BAD_REQUEST).entity(EntityToJson.jsonResponse("Failed","Exception came in cab edit")).build();
		}
	}

	public static Response deleteProcess(int id)
	{
		try(Connection con= ConnectionUtil.getConnection();)
		{
			PreparedStatement pr=con.prepareStatement("delete from cabtable where cabid=?");
			pr.setInt(1,id);
			pr.executeUpdate();
			return Response.status(Response.Status.OK).entity("cab entry deleted successfully").build();

		}
		catch(Exception e){
			logger.log(Level.INFO,"Exception in cab delete"+e);
			return Response.status(Response.Status.BAD_REQUEST).entity("Exception came in delete the cab").build();
		}
	}

	public static ArrayList<Cab> getAvailableCabDetails()
	{
		ArrayList<Cab> cabList=new ArrayList<>();

		try(Connection con= ConnectionUtil.getConnection())
		{
			PreparedStatement pstm= con.prepareStatement("select * from cabtable where driverassigned=false");
			ResultSet rs=pstm.executeQuery();
			while(rs.next()){
				Cab cab=new Cab();
				cab.setId(rs.getInt("cabid"));
				cab.setCabNo(rs.getString("cabno"));
				cab.setColour(rs.getString("colour"));
				cab.setSeatingCapacity(rs.getInt("seatingcapacity"));
				cab.setAvailableSeat(rs.getInt("availableseat"));
				cab.setAvailableForBooking(rs.getBoolean("availableforbooking"));
				cabList.add(cab);
			}
			return cabList;
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Exception in cab details: "+e);
				}
		return null;
	}

	public static void updateAssignedDriver(Connection con, int assignedCabId) throws SQLException
	{
		try
		{
			PreparedStatement pr=con.prepareStatement(EDIT_DRIVERASSIGNED);
			pr.setBoolean(1,true);
			pr.setInt(2,assignedCabId);
			pr.executeUpdate();
		}
		catch(Exception e){
			throw e;
		}
	}
}
