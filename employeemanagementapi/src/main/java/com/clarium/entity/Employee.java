package com.clarium.entity;

import jakarta.persistence.*;

import java.security.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "firstName")
    private String FirstName;

    @Column(name = "lastName")
    private String LastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNo")
    private String PhoneNo;

    @Column(name = "dob")
    private String DOB;

    @Column(name = "gender")
    private String Gender;

    @Column(name = "designation")
    private String Designation;

    @Column(name = "joiningDate")
    private Date JoiningDate;

    @Column(name = "employmentType")
    private String EmploymentType;

    @Column(name = "ctc")
    private String CTC;

    @Column(name = "isActive")
    private Boolean IsActive;

    @Column(name = "createdTime")
    private Timestamp CreatedTime;

    @Column(name = "updatedTime")
    private Timestamp UpdatedTime;

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

    public Timestamp getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedAt(Timestamp updatedTime) {
        UpdatedTime = updatedTime;
    }

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, String email, String phoneNo, String DOB, String gender, String designation, Date joiningDate, String employmentType, String CTC, Boolean isActive, Timestamp createdTime, Timestamp updatedTime) {
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
        UpdatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Id=" + Id +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", email='" + email + '\'' +
                ", PhoneNo='" + PhoneNo + '\'' +
                ", DOB='" + DOB + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Designation='" + Designation + '\'' +
                ", JoiningDate=" + JoiningDate +
                ", EmploymentType='" + EmploymentType + '\'' +
                ", CTC='" + CTC + '\'' +
                ", IsActive=" + IsActive +
                ", CreatedAt=" + CreatedTime +
                ", UpdatedAt=" + UpdatedTime +
                '}';
    }
}
