package com.clarium.service.impl;
import com.clarium.dto.EmployeeDTO;
import com.clarium.entity.Employee;
import com.clarium.dao.EmployeeRepository;
import com.clarium.mapper.EmployeeMapper;
import com.clarium.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.clarium.constants.ApplicationConstants.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;



    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .filter(Employee::getActive)
                .map(employee -> employeeMapper.EmployeeEntityToModel(employee))
                .collect(Collectors.toList());
    }



    public Optional<EmployeeDTO> getEmployeeById(int Id){
        Optional<Employee> employee = employeeRepository.findById(Id);
        if(employee.isPresent() && employee.get().getActive()) {
            return Optional.of(employeeMapper.EmployeeEntityToModel(employee.get()));
        }
        return Optional.empty();
    }


    public String CreateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.EmployeeModelToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        Optional<Employee> savedEmployeeExists = employeeRepository.findById(savedEmployee.getEmpId());
        if(savedEmployeeExists.isPresent()){
            return EMPLOYEE_CREATION_SUCCESSFUL;
        } else {
            return EMPLOYEE_CREATION_UNSUCCESSFUL;
        }
    }

    @Override
    public String updateEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsById(employeeDTO.getEmpId())) {
            Optional<Employee> existingEmployeeDetails = employeeRepository.findById(employeeDTO.getEmpId());
            if (existingEmployeeDetails.isPresent()) {
                Employee existingEmployee = existingEmployeeDetails.get();
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
                return EMPLOYEE_UPDATE_SUCCESSFUL;
            }
        }
        return EMPLOYEE_UPDATE_UNSUCCESSFUL;
    }

    public String deleteEmployee(int Id){
        Optional<Employee> employee = employeeRepository.findById(Id);
        if (employee.isPresent()){
            Employee existingEmployee = employee.get();
            existingEmployee.setActive(false);
            employeeRepository.save(existingEmployee);
            return Id + EMPLOYEE_DELETION_SUCCESSFUL;
        } else {
            return EMPLOYEE_DELETION_UNSUCCESSFUL;
        }
    }
}
