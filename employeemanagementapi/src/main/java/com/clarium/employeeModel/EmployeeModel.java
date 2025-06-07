package com.clarium.employeeModel;

import jakarta.persistence.Column;

import java.security.Timestamp;
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
    private Timestamp UpdatedTime;

}
