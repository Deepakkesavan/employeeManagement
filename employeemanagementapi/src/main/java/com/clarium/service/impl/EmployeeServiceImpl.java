package com.clarium.service.impl;
import com.clarium.dao.EmployeeLoginRepository;
import com.clarium.dto.EmployeeDTO;
import com.clarium.entity.Employee;
import com.clarium.dao.EmployeeRepository;
import com.clarium.entity.EmployeeLogin;
import com.clarium.mapper.EmployeeMapper;
import com.clarium.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeLoginRepository employeeLoginRepository;



    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .filter(employee -> employee.getActive())
                .map(employee -> employeeMapper.EmployeeEntitytoModel(employee))
                .collect(Collectors.toList());
    }



    public Optional<EmployeeDTO> getEmployeeById(int Id){
        Optional<Employee> employee = employeeRepository.findById(Id);
        if(employee.isPresent() && employee.get().getActive()) {
            return Optional.of(employeeMapper.EmployeeEntitytoModel(employee.get()));
        }
        return Optional.empty();
    }


    public Optional<EmployeeDTO> CreateEmployee(EmployeeDTO employeeDTO) {
        // For new employees, don't check by ID since it should be auto-generated
        // You can check by email instead to avoid duplicates

        Employee employee = employeeMapper.EmployeeModeltoEntity(employeeDTO);
        // Set ID to 0 or null for new employees so Hibernate generates it
        employee.setEmpId(null);

        Employee savedEmployee = employeeRepository.save(employee);
        return Optional.of(employeeMapper.EmployeeEntitytoModel(savedEmployee));
    }

    @Override
    public Optional<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsById(employeeDTO.getEmpid())) {
            Optional<Employee> existingEmployeeOpt = employeeRepository.findById(employeeDTO.getEmpid());
            if (existingEmployeeOpt.isPresent()) {
                Employee existingEmployee = existingEmployeeOpt.get();
                existingEmployee.setFirstName(employeeDTO.getFirstName());
                existingEmployee.setLastName(employeeDTO.getLastName());
                existingEmployee.setEmail(employeeDTO.getEmail());
                existingEmployee.setPhoneNo(employeeDTO.getPhoneNo());
                existingEmployee.setDob(employeeDTO.getDob());
                existingEmployee.setGender(employeeDTO.getGender());
                existingEmployee.setDesignation(employeeDTO.getDesignation());
                existingEmployee.setJoiningDate(employeeDTO.getJoiningDate());
                existingEmployee.setEmploymentType(employeeDTO.getEmploymentType());
                existingEmployee.setCtc(employeeDTO.getCtc());
                Employee updatedEmployee = employeeRepository.save(existingEmployee);
                return Optional.of(employeeMapper.EmployeeEntitytoModel(updatedEmployee));
            }
        }
        return Optional.empty();
    }

    public String deleteEmployee(int Id){
        Optional<Employee> employee = employeeRepository.findById(Id);
        if (employee.isPresent()){
            Employee existingEmployee = employee.get();
            existingEmployee.setActive(false);
            employeeRepository.save(existingEmployee);
            return Id + " Has been Deleted Successfully.";
        } else {
            return "The Employee does not exists in the Table.";
        }
    }
}
