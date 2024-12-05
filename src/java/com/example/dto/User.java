package com.example.dto;

public class User
{
    // props
    private String userID;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String roleID;
    private String password;

    // constructor
    public User(String userID, String fullName, String email,
        String phoneNumber, String roleID, String password)
    {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleID = roleID;
        this.password = password;
    }

    // getters 
    public String getUserID()
    {
        return userID;
    }

    public String getFullName()
    {
        return fullName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getRoleID()
    {
        return roleID;
    }

    public String getPassword()
    {
        return password;
    }

    // setters
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setRoleID(String roleID)
    {
        this.roleID = roleID;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    // toString
    @Override
    public String toString()
    {
        return String.format("UserID: %s, FullName: %s, Email: %s, "
            + "PhoneNumber: %s, RoleID: %s, Password: %s",
            userID, fullName, email, phoneNumber, roleID, password);
    }
}
