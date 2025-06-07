package com.clarium.employeeController;


import com.clarium.entity.Employee;
import com.clarium.employeeService.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empmngmt/v1/api")
public class EmployeeController {

    @Autowired
    private EmployeeService EmpService;

    @GetMapping("/getEmployee/{Id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("Id") int Id){
        Optional<Employee> employee = EmpService.getEmployeeById(Id);
        if (employee.isPresent()){
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllEmployees")
    public List<Employee> getEmployees(){
        return EmpService.getAllEmployees();
    }
    @PostMapping("/CreateEmployee")
    public String createEmployee(@RequestBody Employee employee){
        Optional<Employee> employee_ = EmpService.CreateEmployee(employee);
        if(employee_.isPresent()){
            return "The Employee Data has been Saved Successfully";
        } else {
            return "Employee already Exists in Records";
        }
    }

    @PostMapping("/UpdateEmployee")
    public String updateEmployee(@RequestBody Employee employee) {
        Optional<Employee> _employee = EmpService.UpdateEmployee(employee);
        if (_employee.isEmpty()){
            return "The Employee does not Exists in Table.";
        } else {
            return "The Employee Data has been successfully Updated.";
        }
    }

    @DeleteMapping("/DeleteEmployee/{Id}")
    public String deleteEmployeeById(@PathVariable("Id") int Id) {
        return EmpService.deleteEmployee(Id);
    }
}
