package org.example.example;

import java.net.HttpCookie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationUtil
{
	private static final Logger logger = Logger.getLogger("AuthenticationUtil.class");
	public static boolean isAuthenticate(HttpServletRequest req,boolean isAdmin){
		HttpSession session = req.getSession();
		try(Connection con=ConnectionUtil.getConnection())
		{

			String apiKey = (String) session.getAttribute("Auth-Token");
			String apiKeyFromHeader= req.getHeader("Auth-Token");
			logger.log(Level.INFO,"auth values:"+apiKeyFromHeader+"  "+apiKey);
			if(apiKeyFromHeader!=null)
				apiKey=apiKeyFromHeader;

			PreparedStatement pstm= con.prepareStatement("select * from usertable where apikey=? ");
			pstm.setString(1,apiKey);
			ResultSet rs=pstm.executeQuery();
			while(rs.next())
			{
				if(rs.getString("role").equals("customer") && isAdmin)
				return false;

				return true;
			}
		}
		catch(Exception e){
			logger.log(Level.INFO, "Exception during Authentication ", e);
			return false;
		}

		return false;
	}
}
