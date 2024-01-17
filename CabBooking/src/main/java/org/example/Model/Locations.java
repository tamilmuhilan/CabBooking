package org.example.Model;

public class Locations
{
	int id;
	String locationName;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getLocationName()
	{
		return locationName;
	}

	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}

	@Override public String toString()
	{
		return "{" +
			"id=" + id +
			", locationName='" + locationName + '\'' +
			'}';
	}
}
