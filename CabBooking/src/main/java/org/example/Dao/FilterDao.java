package org.example.Dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.example.example.ConnectionUtil;
import org.example.example.EntityToJson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FilterDao implements Filter
{
	Logger logger=Logger.getLogger("FilterDao.class");

	@Override public void init(FilterConfig filterConfig) throws ServletException
	{

	}

	@Override
	public void doFilter(@Context ServletRequest servletRequest,@Context ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{

		HttpServletRequest req=(HttpServletRequest) servletRequest;
		HttpServletResponse res=(HttpServletResponse) servletResponse;
		HttpSession session = req.getSession();

		StringBuilder sb = new StringBuilder();
		BufferedReader reader = req.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();

		String mobileNo = jsonObject.get("Mobileno").getAsString();
		String password = jsonObject.get("Password").getAsString();

		if(!UserDao.isValidMobileNumber(mobileNo) || !UserDao.isValidPassword(password) ){
			res.getWriter().write(EntityToJson.jsonResponse("Failed","Invalid Mobile or Password"));
			return;
		}

		logger.log(Level.INFO,"request Data: "+mobileNo+"  "+password);
		boolean isCustomer=false;
		boolean isAdmin=false;
		try(Connection con= ConnectionUtil.getConnection();)
		{
			PreparedStatement pr=con.prepareStatement("select * from usertable where phoneno=? and password=? ");
			pr.setLong(1,Long.parseLong(mobileNo));
			pr.setString(2,password);
			ResultSet rs=pr.executeQuery();
			while(rs.next())
			{
				String role= rs.getString("role");

				session.setAttribute("Login_Id",rs.getInt("userid"));
				session.setAttribute("username",rs.getString("username"));
				session.setAttribute("Auth-Token",rs.getString("apikey"));

				Cookie cookie=new Cookie("Login_Id",""+rs.getInt("userid"));
				cookie.setPath("/");
				res.addCookie(cookie);
				Cookie cookie1=new Cookie("username",rs.getString("username"));
				cookie1.setPath("/");
				res.addCookie(cookie1);
				Cookie cookie2=new Cookie("Auth-Token",rs.getString("apikey"));
				cookie2.setPath("/");
				res.addCookie(cookie2);
				Cookie cookie3=new Cookie("role",rs.getString("role"));
				cookie3.setPath("/");
				res.addCookie(cookie3);

				if(role.equals("customer"))
				isCustomer=true;
				else if(role.equals("admin"))
					isAdmin=true;

			}
			if(isCustomer){
				res.getWriter().write(EntityToJson.jsonResponse("Success","Welcome customer!"));
			}
			else if(isAdmin){
				res.getWriter().write(EntityToJson.jsonResponse("Success","Welcome admin!"));
			}
			else {
				res.getWriter().write(EntityToJson.jsonResponse("Failed","Invalid Mobile or Password"));
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
		catch(ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}




	}

	@Override public void destroy()
	{

	}
}
