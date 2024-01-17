package org.example.Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User
{
    private int id;
    private String username;
    private String password;
    private String gender;
    private long phoneNo;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender) {this.gender = gender;}

    public long getPhoneNo() {return phoneNo;}

    public void setPhoneNo(long phoneNo) {this.phoneNo = phoneNo;}

    @Override public String toString()
    {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", gender='" + gender + '\'' +
            ", phoneNo=" + phoneNo +
            ", role='" + role + '\'' +
            '}';
    }
}
