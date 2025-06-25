package com.clarium.service;

import com.clarium.dto.EmployeeDTO;
import com.clarium.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {



    List<EmployeeDTO> getAllEmployees();
    Optional<EmployeeDTO> getEmployeeById(int Id);
    String CreateEmployee(EmployeeDTO employeeDTO);
    String updateEmployee(EmployeeDTO employeeDTO);
    String deleteEmployee(int Id);


}
