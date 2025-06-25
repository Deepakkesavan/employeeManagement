package com.clarium.mapper;

import com.clarium.dto.EmployeeDTO;
import com.clarium.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDTO EmployeeEntityToModel(Employee employee) {
        return new EmployeeDTO(
                employee.getEmpId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhoneNo(),
                employee.getDob(),
                employee.getGender(),
                employee.getDesignation(),
                employee.getJoiningDate(),
                employee.getEmploymentType(),
                employee.getCtc()

        );
    }

    public Employee EmployeeModelToEntity(EmployeeDTO employeeDTO) {
        return new Employee(
                employeeDTO.getEmpId(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail(),
                employeeDTO.getPhoneNo(),
                employeeDTO.getDob(),
                employeeDTO.getGender(),
                employeeDTO.getDesignation(),
                employeeDTO.getJoiningDate(),
                employeeDTO.getEmploymentType(),
                employeeDTO.getCtc(),
                true
        );
    }
}
