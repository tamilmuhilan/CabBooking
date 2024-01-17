package org.example.Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Driver
{
	private int driverId;
	private String driverName;
	private int assignedCabId;
	private long driverNo;

	public int getDriverId()
	{
		return driverId;
	}

	public void setDriverId(int driverId)
	{
		this.driverId = driverId;
	}

	public String getDriverName()
	{
		return driverName;
	}

	public void setDriverName(String driverName)
	{
		this.driverName = driverName;
	}

	public int getAssignedCabId()
	{
		return assignedCabId;
	}

	public void setAssignedCabId(int assignedCabId)
	{
		this.assignedCabId = assignedCabId;
	}

	public long getDriverNo()
	{
		return driverNo;
	}

	public void setDriverNo(long driverNo)
	{
		this.driverNo = driverNo;
	}

	@Override public String toString()
	{
		return "{" +
			"driverId=" + driverId +
			", driverName='" + driverName + '\'' +
			", assignedCabId=" + assignedCabId +
			", driverNo=" + driverNo +
			" }";
	}
}
