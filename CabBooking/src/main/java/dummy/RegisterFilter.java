package dummy;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterFilter
{
	private int employeeId;
	private String employeeName;
	private String gender;
	private long phoneNo;

	public void setEmployeeId(int employeeId)
	{
		this.employeeId = employeeId;
	}

	public void setPhoneNo(long phoneNo)
	{
		this.phoneNo = phoneNo;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}

	public int getEmployeeId()
	{
		return employeeId;
	}

	public String getEmployeeName()
	{
		return employeeName;
	}

	public String getGender()
	{
		return gender;
	}

	public long getPhoneNo()
	{
		return phoneNo;
	}
}
