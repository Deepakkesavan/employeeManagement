package com.clarium.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;


@Entity
@Table(name = "Employee_Login_Details",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"emp_id", "login_date"}
        )
})
public class EmployeeLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loginId;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @Column(name = "login_date")
    private Date loginDate;

    @Column(name = "login_time")
    private Timestamp loginTime;

    @Column(name = "logout_time")
    private Timestamp logoutTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public EmployeeLogin() {
    }

    public EmployeeLogin(Integer loginId, Employee employee, Date loginDate, Timestamp loginTime, Timestamp logoutTime, Timestamp createdAt, Timestamp updatedAt) {
        this.loginId = loginId;
        this.employee = employee;
        this.loginDate = loginDate;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
