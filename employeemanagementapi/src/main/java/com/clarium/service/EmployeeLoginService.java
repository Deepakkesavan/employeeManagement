package com.clarium.service;

import com.clarium.dto.EmployeeLoginDTO;
import com.clarium.dto.WeeklyReportDTO;
import com.clarium.entity.Employee;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeLoginService {

    Optional<Employee> createEmployeeLoginTime(int id, boolean isLogin);
    List<EmployeeLoginDTO> getListOfEmployeeLogin(Date loginDate);
    EmployeeLoginDTO getEmployeeLoginByEmpIdAndDate(Integer empId, Date loginDate);
    List<EmployeeLoginDTO> getEmployeeLoginsForLast5Days();
    List<WeeklyReportDTO> getWeeklyReport();
    public String sendSimpleMail();


}
