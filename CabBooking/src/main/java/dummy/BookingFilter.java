package dummy;

import dummy.RegisterFilter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookingFilter
{
	private int cabId;
	private int driverId;
	private int securityId;
	private String pickupPoint;
	private String dropPoint;
	private int startTime;
	private int endTime;
	private RegisterFilter employeeData;
	private boolean availability;
	private int cabCapacity;

	public int getCabId()
	{
		return cabId;
	}

	public int getDriverId()
	{
		return driverId;
	}

	public int getStartTime()
	{
		return startTime;
	}

	public int getEndTime()
	{
		return endTime;
	}

	public int getSecurityId()
	{
		return securityId;
	}

	public String getPickupPoint()
	{
		return pickupPoint;
	}

	public String getDropPoint()
	{
		return dropPoint;
	}

	public boolean isAvailability()
	{
		return availability;
	}

	public int getCabCapacity()
	{
		return cabCapacity;
	}

	public void setAvailability(boolean availability)
	{
		this.availability = availability;
	}

	public void setCabCapacity(int cabCapacity)
	{
		this.cabCapacity = cabCapacity;
	}

	public void setCabId(int cabId)
	{
		this.cabId = cabId;
	}

	public void setDriverId(int driverId)
	{
		this.driverId = driverId;
	}

	public void setDropPoint(String dropPoint)
	{
		this.dropPoint = dropPoint;
	}

	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}

	public void setEndTime(int endTime)
	{
		this.endTime = endTime;
	}

	public RegisterFilter getEmployeeData()
	{
		return employeeData;
	}

	public void setEmployeeList(RegisterFilter employeeData)
	{
		this.employeeData = employeeData;
	}
}
