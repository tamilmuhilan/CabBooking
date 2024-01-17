package org.example.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.example.Model.Locations;
import org.example.example.ConnectionUtil;


public class LocationDao
{
	public static String GET_LOCATION_INFO="select * from locations";

	public static ArrayList<Locations> getLocationdetails() throws Exception
	{
		ArrayList<Locations> locationsList = new ArrayList<>();
		try(Connection con = ConnectionUtil.getConnection();)
		{
			PreparedStatement pstm = con.prepareStatement(GET_LOCATION_INFO);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				Locations loc=new Locations();
				loc.setId(rs.getInt("id"));
				loc.setLocationName(rs.getString("location_name"));
				locationsList.add(loc);
			}
			return locationsList;
		}
		catch(Exception e){
                throw e;
		}
	}
}
