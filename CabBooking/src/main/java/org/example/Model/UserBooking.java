package org.example.Model;

public class UserBooking
{
	private int userId;
	private String userName;

	private int pickPoint;
	private int dropPoint;

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public int getPickPoint()
	{
		return pickPoint;
	}

	public void setPickPoint(int pickPoint)
	{
		this.pickPoint = pickPoint;
	}

	public int getDropPoint()
	{
		return dropPoint;
	}

	public void setDropPoint(int dropPoint)
	{
		this.dropPoint = dropPoint;
	}

	@Override public String toString()
	{
		return "{" +
			"userId=" + userId +
			", userName='" + userName + '\'' +
			", pickPoint=" + pickPoint +
			", dropPoint=" + dropPoint +
			'}';
	}
}
