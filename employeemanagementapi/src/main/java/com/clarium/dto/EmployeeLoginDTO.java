package com.clarium.dto;

import com.clarium.entity.Employee;

import java.sql.Timestamp;

public class EmployeeLoginDTO {


    private Integer empId;
    private String empName;
    private Timestamp loginTime;
    private Timestamp logoutTime;
    private Timestamp loginHours;

    public EmployeeLoginDTO() {
    }

    public EmployeeLoginDTO(Integer empId, String empName, Timestamp loginTime, Timestamp logoutTime, Timestamp loginHours) {
        this.empId = empId;
        this.empName = empName;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.loginHours = loginHours;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Timestamp getLoginHours() {
        return loginHours;
    }

    public void setLoginHours(Timestamp loginHours) {
        this.loginHours = loginHours;
    }
}
