package com.clarium.employeeService;


import com.clarium.entity.Employee;
import com.clarium.employeeRepository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository EmpRepo;

    public List<Employee> getAllEmployees(){
        return EmpRepo.findAll();
    }

    public Optional<Employee> getEmployeeById(int Id){

        return EmpRepo.findById(Id);
    }

    public Optional<Employee> CreateEmployee(Employee employee) {
        if(EmpRepo.existsById(employee.getId())){
            return Optional.empty();
        } else {
            return Optional.of(EmpRepo.save(employee));
        }
    }

    public Optional<Employee> UpdateEmployee(Employee employee) {
        if(EmpRepo.existsById(employee.getId())){
            return Optional.of(EmpRepo.save(employee));
        } else {
            return Optional.empty();
        }
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
