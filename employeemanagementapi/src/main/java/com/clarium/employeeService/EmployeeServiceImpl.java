package com.clarium.employeeService;
import com.clarium.employeeDTO.EmployeeDTO;
import com.clarium.entity.Employee;
import com.clarium.employeeRepository.EmployeeRepository;
import com.clarium.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository EmpRepo;

    @Autowired
    private EmployeeMapper employeeMapper;


    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = EmpRepo.findAll();
        return employees.stream()
                .map(employee -> employeeMapper.EmployeeEntitytoModel(employee))
                .collect(Collectors.toList());
    }



    public Optional<EmployeeDTO> getEmployeeById(int Id){
        Optional<Employee> employee = EmpRepo.findById(Id);
        if(employee.isPresent()) {
            return Optional.of(employeeMapper.EmployeeEntitytoModel(employee.get()));
        }
        return Optional.empty();
    }


    public Optional<EmployeeDTO> CreateEmployee(EmployeeDTO employeeDTO) {
        // For new employees, don't check by ID since it should be auto-generated
        // You can check by email instead to avoid duplicates

        Employee employee = employeeMapper.EmployeeModeltoEntity(employeeDTO);
        // Set ID to 0 or null for new employees so Hibernate generates it
        employee.setId(null);

        Employee savedEmployee = EmpRepo.save(employee);
        return Optional.of(employeeMapper.EmployeeEntitytoModel(savedEmployee));
    }

    @Override
    public Optional<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO) {
        if (EmpRepo.existsById(employeeDTO.getId())) {
            Optional<Employee> existingEmployeeOpt = EmpRepo.findById(employeeDTO.getId());
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
                existingEmployee.setUpdatedTime(new Timestamp(new Date().getTime()));
                Employee updatedEmployee = EmpRepo.save(existingEmployee);
                return Optional.of(employeeMapper.EmployeeEntitytoModel(updatedEmployee));
            }
        }
        return Optional.empty();
    }



    public String deleteEmployee(int Id){
        if (EmpRepo.existsById(Id)){
            EmpRepo.deleteById(Id);
            return Id + " Has been Deleted Successfully.";
        } else {
            return "The Employee does not exists in the Table.";
        }
    }
}
