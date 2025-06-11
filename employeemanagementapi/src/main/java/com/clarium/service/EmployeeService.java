package com.clarium.service;

import com.clarium.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();
    Optional<EmployeeDTO> getEmployeeById(int Id);
    Optional<EmployeeDTO> CreateEmployee(EmployeeDTO employeeDTO);
    Optional<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO);
    String deleteEmployee(int Id);

}
