package org.example.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import org.example.Model.User;
import org.example.example.ConnectionUtil;
import org.example.example.EntityToJson;

public class UserDao
{
	private static final Logger logger = Logger.getLogger("UserDao.class");
	public static String USERINSERTQRY="insert into usertable(username,password,gender,phoneno,role,apikey) values (?,?,?,?,?,?)";
	public static String USERUPDATEQRY="update usertable set username=?,password=?,gender=?,phoneno=?,role=? where userid=? ";

	public static Response getUserDetails() throws Exception
	{
		ArrayList<User> userList=new ArrayList<>();
		try(Connection con= ConnectionUtil.getConnection();)
		{

			PreparedStatement pr = con.prepareStatement("select * from usertable");
			ResultSet rs= pr.executeQuery();
			while(rs.next()){
				User user=new User();
				user.setId(rs.getInt("userid"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setGender(rs.getString("gender"));
				user.setRole(rs.getString("role"));
				user.setPhoneNo(rs.getLong("phoneno"));
				userList.add(user);

			}
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Exception in user details: "+e);
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to get user detail: " + e.getMessage()).build();
		}
		return Response.ok(Response.Status.OK).entity("List:"+userList).build();
	}
	public static Response addProcess(User user) throws Exception
	{
		String apiKey = generateApiKey();
		if(!isValidMobileNumber(String.valueOf(user.getPhoneNo())) || !isValidPassword(user.getPassword()) )
			return Response.status(Response.Status.BAD_REQUEST).entity(EntityToJson.jsonResponse("Failed","Invalid MobileNo or password!")).build();

		try(Connection con= ConnectionUtil.getConnection();)
	   {
		   PreparedStatement pr=con.prepareStatement(USERINSERTQRY);
		   pr.setString(1,user.getUsername());
		   pr.setString(2, user.getPassword());
		   pr.setString(3, user.getGender());
		   pr.setLong(4,user.getPhoneNo());
		   pr.setString(5, user.getRole());
		   pr.setString(6,apiKey);
		   pr.executeUpdate();
	   }
	   catch(Exception e)
	   {
		   logger.log(Level.SEVERE,"Exception in user creation: "+e);
		   return Response.status(Response.Status.BAD_REQUEST).entity(EntityToJson.jsonResponse("Failed","Failed to create user")).build();
	   }
	  return Response.ok(Response.Status.CREATED).entity(EntityToJson.jsonResponse("Success","User Created Successfully")).build();
	}


	public static Response editProcess(User user,int id) throws Exception
	{
		try(Connection con= ConnectionUtil.getConnection();)
		{
			PreparedStatement pr=con.prepareStatement(USERUPDATEQRY);
			pr.setString(1,user.getUsername());
			pr.setString(2, user.getPassword());
			pr.setString(3, user.getGender());
			pr.setLong(4,user.getPhoneNo());
			pr.setString(5,user.getRole());
			pr.setInt(6,id);
			pr.executeUpdate();
		}
		catch(Exception e)
		{
			logger.log(Level.SEVERE,"Exception in user edit:"+e);
			return Response.status(Response.Status.BAD_REQUEST).entity("Exception came in edit the user").build();
		}
		return Response.ok(Response.Status.OK).entity("User Edited Successfully").build();
	}

	public static Response deleteProcess(int id)
	{
		try(Connection con= ConnectionUtil.getConnection();)
		{
			PreparedStatement pr=con.prepareStatement("delete from usertable where userid=?");
			pr.setInt(1,id);
			pr.executeUpdate();
			return Response.status(Response.Status.OK).entity("user entry deleted successfully").build();
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Exception in user deletion"+e);
			return Response.status(Response.Status.BAD_REQUEST).entity("Exception came in delete the user").build();
		}
	}

	public static String generateApiKey() {
		// Generate a random UUID (Universally Unique Identifier)
		UUID uuid = UUID.randomUUID();
		// Convert UUID to String and remove hyphens
		String apiKey = uuid.toString().replace("-", "");
		// You can also truncate or modify the key as needed

		return apiKey;
	}
	public static boolean isValidMobileNumber(String mobileNo) {
		return mobileNo != null && mobileNo.matches("^[0-9]{10}$");
	}

	public static boolean isValidPassword(String password) {
		return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,16}$");
	}
}
