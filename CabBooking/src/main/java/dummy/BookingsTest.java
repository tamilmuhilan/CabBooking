package dummy;
/*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.example.ConnectionUtil;


public class BookingsDao
{

	public static Response processBooking(BookingFilter bookingFilter) throws Exception{
		String a=bookingFilter.getDropPoint();
		try(Connection con= ConnectionUtil.getConnection())
		{
			updateCabDetails(con,bookingFilter);
		}
		catch(Exception e)
		{
			throw e;
		}
		return Response.ok().build();
	}

	public static void updateCabDetails(Connection con,BookingFilter bookingFilter) throws Exception
	{
		try
		{
			String gender = bookingFilter.getEmployeeData().getGender();
			PreparedStatement pr = con.prepareStatement("select * from cabdetails orderby availabilty ");
			ResultSet rs = pr.executeQuery();
			Boolean isNewEntry=true;
			while(rs.next())
			{
				isNewEntry=false;
				int availableCeat=rs.getInt("availableCeat");
				int securityId=rs.getInt("securityId");
				int cabId=rs.getInt("cabId");
				if(securityId!=0 && gender.equals("Female"))
				{
					updateCabEntry(con,bookingFilter,cabId,true);
				}
				else{
					if(availableCeat == 1) { updateCabEntry(con,bookingFilter,cabId,true);}
					else{ updateCabEntry(con,bookingFilter,cabId,false);}
				}
			}
			if(isNewEntry){
				if(gender.equals("Female"))
				insertCabEntry(con,bookingFilter,true);
				else insertCabEntry(con,bookingFilter,false);
			}

		}
		catch(Exception e){
			throw e ;
		}

	}

	public static void updateCabEntry(Connection con,BookingFilter bookingFilter,int cabId,Boolean isCabFull){

	}
	public static void insertCabEntry(Connection con,BookingFilter bookingFilter,Boolean isFemale) throws Exception {
		String empInsQry="insert into employeedetails(bookid,employeeid,gender,phoneno,droppoint) values (?,?,?,?,?)";
		String cabDtlInsQry="insert into cabdetails(cabid,securityid,availableseat,bookid) values (?,?,?,?) ";
		int secId=getSecurityId();
		HashMap cabData=getNewCabData();
		int bookId= (int) cabData.get("ride");
		int cabId= (int) cabData.get("cabid");
		int availableCeat= (int) cabData.get("availableceat");

		PreparedStatement pr = null;
		try{
			pr=con.prepareStatement(empInsQry);
			pr.setInt(1,bookId);
			pr.setInt(2,bookingFilter.getEmployeeData().getEmployeeId());
			pr.setString(3,bookingFilter.getEmployeeData().getGender());
			pr.setLong(4,bookingFilter.getEmployeeData().getPhoneNo());
			pr.setString(5,bookingFilter.getDropPoint());
			pr.executeUpdate();

			pr=con.prepareStatement(cabDtlInsQry);
			pr.clearParameters();
			pr.setInt(1,cabId);
			if(isFemale)
			pr.setInt(2,secId);
			else pr.setInt(2,0);
			pr.setInt(3,availableCeat-1);
			pr.setInt(4,bookId);
			pr.executeUpdate();




		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static HashMap getNewCabData() throws Exception
	{
		HashMap<String,Object> map=new HashMap<>();
		Connection con=ConnectionUtil.getConnection();
		PreparedStatement pstm= con.prepareStatement("select * from cab where availability=1 order by cabid,ride limit 1");
		ResultSet rs=pstm.executeQuery();
		while(rs.next())
		{
			map.put("cabid",rs.getInt("cabid"));
			map.put("ride",rs.getInt("ride")+1);
			map.put("availableceat",rs.getInt("availableceat"));
		}
		return map;

	}
	public static int getSecurityId() throws Exception
	{
		int securityId=0;
		Connection con=ConnectionUtil.getConnection();
		PreparedStatement pstm= con.prepareStatement("select * from securityTable where availability=1 order by securityid,raidCount limit 1");
		ResultSet rs=pstm.executeQuery();
		while(rs.next()){
			securityId=rs.getInt("securityid");
		}
		return securityId;
	}


}
*/

import java.util.UUID;

public class BookingsTest
{

	public static void main(String[] args) {
		String apiKey = generateApiKey();
		System.out.println("Generated API Key: " + apiKey);
	}

	public static String generateApiKey() {
		// Generate a random UUID (Universally Unique Identifier)
		UUID uuid = UUID.randomUUID();
		// Convert UUID to String and remove hyphens
		String apiKey = uuid.toString().replace("-", "");
		// You can also truncate or modify the key as needed

		return apiKey;
	}
}

