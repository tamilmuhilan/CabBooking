package org.example.Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Login
{
	    private long phoneNo;
		private String password;


	public long getPhoneNo()
	{
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo)
	{
		this.phoneNo = phoneNo;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	@Override public String toString()
	{
		return "{" +
			"phoneNo=" + phoneNo +
			", password='" + password + '\'' +
			'}';
	}
}
