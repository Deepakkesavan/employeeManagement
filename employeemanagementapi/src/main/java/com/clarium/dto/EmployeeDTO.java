package com.clarium.dto;


import java.util.Date;

public class EmployeeDTO {

    private Integer empid;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String dob;
    private String gender;
    private String designation;
    private Date joiningDate;
    private String employmentType;
    private String ctc;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Integer empid, String firstName, String lastName, String email, String phoneNo, String dob, String gender, String designation, Date joiningDate, String employmentType, String ctc) {
        this.empid = empid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.gender = gender;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.employmentType = employmentType;
        this.ctc = ctc;
    }

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }
}

