package com.clarium.mapper;

import com.clarium.dto.EmployeeDTO;
import com.clarium.entity.Employee;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class EmployeeMapper {

    public EmployeeDTO EmployeeEntitytoModel(Employee employee) {
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



    public Employee EmployeeModeltoEntity(EmployeeDTO employeeDTO) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Employee employee = new Employee(
                0,
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
                true,
                timestamp,
                timestamp

        );
        return employee;
    }

}
