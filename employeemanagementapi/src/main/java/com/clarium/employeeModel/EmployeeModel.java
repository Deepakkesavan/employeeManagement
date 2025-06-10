package com.clarium.employeeModel;


import java.sql.Timestamp;
import java.util.Date;

public class EmployeeModel {

    private int Id;
    private String FirstName;
    private String LastName;
    private String email;
    private String PhoneNo;
    private String DOB;
    private String Gender;
    private String Designation;
    private Date JoiningDate;
    private String EmploymentType;
    private String CTC;
    private Boolean IsActive;
    private Timestamp CreatedTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public Date getJoiningDate() {
        return JoiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        JoiningDate = joiningDate;
    }

    public String getEmploymentType() {
        return EmploymentType;
    }

    public void setEmploymentType(String employmentType) {
        EmploymentType = employmentType;
    }

    public String getCTC() {
        return CTC;
    }

    public void setCTC(String CTC) {
        this.CTC = CTC;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public Timestamp getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        CreatedTime = createdTime;
    }


    public EmployeeModel() {
    }

    public EmployeeModel(int id, String firstName, String lastName, String email, String phoneNo, String DOB, String gender, String designation, Date joiningDate, String employmentType, String CTC, Boolean isActive, Timestamp createdTime) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        this.email = email;
        PhoneNo = phoneNo;
        this.DOB = DOB;
        Gender = gender;
        Designation = designation;
        JoiningDate = joiningDate;
        EmploymentType = employmentType;
        this.CTC = CTC;
        IsActive = isActive;
        CreatedTime = createdTime;
    }
}
