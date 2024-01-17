package org.example.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import org.example.Model.Driver;
import org.example.example.ConnectionUtil;
import org.example.example.EntityToJson;

public class DriverDao
{
	private static final Logger logger = Logger.getLogger("DriverDao.class");
	public static String GETDRIVERDETAIL="select * from drivertable";
	public static String DRIVERINSERTQRY="insert into drivertable(drivername,driverno,assignedcabid) values(?,?,?)";
	public static String DRIVERUPDATEQRY="update drivertable set drivername=?,driverno=?,assignedcabid=? where driverid=?";
	public static ArrayList<Driver> getDriverDetails() throws Exception
	{
		  ArrayList<Driver> driversList=new ArrayList<>();
       try(Connection con= ConnectionUtil.getConnection();) {
		   PreparedStatement pstm=con.prepareStatement(GETDRIVERDETAIL);
		   ResultSet rs=pstm.executeQuery();
		   while(rs.next()){
			   Driver driver=new Driver();
			   driver.setDriverId(rs.getInt("driverid"));
			   driver.setDriverName(rs.getString("drivername"));
			   driver.setDriverNo(rs.getLong("driverno"));
			   driver.setAssignedCabId(rs.getInt("assignedcabid"));
			   driversList.add(driver);
		   }
          return driversList;
	   }
	   catch(Exception e)
	   {
		   logger.log(Level.SEVERE,"Exception in driver details: "+e);
		   return null;
		  // return Response.ok(Response.Status.BAD_REQUEST).entity("Exception in driver list").build();
	   }
     //  return Response.ok().entity("DriverList :"+driversList).build();

	}

	public static Response createNewDriver(Driver driver) throws Exception
	{
		try(Connection con = ConnectionUtil.getConnection();)
		{

			PreparedStatement pstm =null;
				pstm=con.prepareStatement(DRIVERINSERTQRY);
			pstm.setString(1,driver.getDriverName());
			pstm.setLong(2,driver.getDriverNo());
			pstm.setInt(3,driver.getAssignedCabId());
			int i=pstm.executeUpdate();
           if(i>0){
			   CabDao.updateAssignedDriver(con,driver.getAssignedCabId());
		   }
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Exception in driver creation: "+e);
			return Response.ok(Response.Status.BAD_REQUEST).entity(EntityToJson.jsonResponse("Failed","Exception in driver creation")).build();
		}
		return Response.ok(Response.Status.CREATED).entity(EntityToJson.jsonResponse("Success","New Driver Created Successfully")).build();
	}

	public static Response editProcess(Driver driver,int id) throws Exception
	{
		try(Connection con = ConnectionUtil.getConnection();)
		{

			PreparedStatement pstm =null;
			pstm=con.prepareStatement(DRIVERUPDATEQRY);
			pstm.setString(1,driver.getDriverName());
			pstm.setLong(2,driver.getDriverNo());
			pstm.setInt(3,driver.getAssignedCabId());
			pstm.setInt(4,id);
			pstm.executeUpdate();
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Exception in driver edit: "+e);
			return Response.ok(Response.Status.BAD_REQUEST).entity(EntityToJson.jsonResponse("Failed","Exception in driver edit")).build();
		}
		return Response.ok().entity(EntityToJson.jsonResponse("Success","Driver Edited Successfully")).build();
	}
	public static Response deleteProcess(int id)
	{
		try(Connection con= ConnectionUtil.getConnection();)
		{
			PreparedStatement pr=con.prepareStatement("delete from drivertable where driverid=?");
			pr.setInt(1,id);
			pr.executeUpdate();
			return Response.status(Response.Status.OK).entity("driver entry deleted successfully").build();

		}
		catch(Exception e){
			return Response.status(Response.Status.BAD_REQUEST).entity("Exception came in delete the driver").build();
		}
	}
}
