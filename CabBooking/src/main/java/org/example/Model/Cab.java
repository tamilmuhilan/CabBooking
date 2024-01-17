package org.example.Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cab
{
	private int id;
	private String cabNo;
	private String colour;
	private int seatingCapacity;
	private int availableSeat;
	private boolean availableForBooking;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCabNo()
	{
		return cabNo;
	}

	public void setCabNo(String cabNo)
	{
		this.cabNo = cabNo;
	}

	public String getColour()
	{
		return colour;
	}

	public void setColour(String colour)
	{
		this.colour = colour;
	}

	public int getSeatingCapacity()
	{
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity)
	{
		this.seatingCapacity = seatingCapacity;
	}

	public int getAvailableSeat()
	{
		return availableSeat;
	}

	public void setAvailableSeat(int availableSeat)
	{
		this.availableSeat = availableSeat;
	}
	public boolean isAvailableForBooking()
	{
		return availableForBooking;
	}

	public void setAvailableForBooking(boolean availableForBooking)
	{
		this.availableForBooking = availableForBooking;
	}

	@Override public String toString()
	{
		return "{" +
			"id:" + id +
			", cabNo:'" + cabNo + '\'' +
			", colour:'" + colour + '\'' +
			", seatingCapacity:" + seatingCapacity +
			", availableSeat:" + availableSeat +
			", availableForBooking:" + availableForBooking +
			" }";
	}
}
