package com.clarium.service;

import com.clarium.dto.EmployeeLoginDTO;
import com.clarium.entity.Employee;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeLoginService {

    Optional<Employee> createEmployeeLoginTime(int id, boolean isLogin);

    List<EmployeeLoginDTO> getAllEmployeeLoginDetails(Integer empId);

    public EmployeeLoginDTO getEmployeeLoginByEmpIdAndDate(Integer empId, Date loginDate);

}
