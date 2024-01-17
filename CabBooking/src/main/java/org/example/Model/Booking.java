package org.example.Model;

import java.sql.Time;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Booking
{
	private int bookId;
	private int cabId;
	private int driverId;
	private int pickPoint;
	private int dropPoint;

	public int getBookId()
	{
		return bookId;
	}

	public void setBookId(int bookId)
	{
		this.bookId = bookId;
	}

	public int getCabId()
	{
		return cabId;
	}

	public void setCabId(int cabId)
	{
		this.cabId = cabId;
	}

	public int getDriverId()
	{
		return driverId;
	}

	public void setDriverId(int driverId)
	{
		this.driverId = driverId;
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
			"bookId=" + bookId +
			", cabId=" + cabId +
			", driverId=" + driverId +
			", pickPoint=" + pickPoint +
			", dropPoint=" + dropPoint + '}';
	}
}
