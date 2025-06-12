package com.clarium.controller;


import com.clarium.dto.EmployeeDTO;
import com.clarium.entity.Employee;
import com.clarium.service.EmployeeLoginService;
import com.clarium.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employeeManagement")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeLoginService employeeLoginService;

    public EmployeeController(EmployeeService empService, EmployeeLoginService employeeLoginService) {
        employeeService = empService;
        this.employeeLoginService = employeeLoginService;
    }

    @GetMapping("{Id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("Id") int Id){
        Optional<EmployeeDTO> employee = employeeService.getEmployeeById(Id);

        if (employee.isPresent()){
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllEmployees")
    public List<EmployeeDTO> getEmployees(){
        return employeeService.getAllEmployees();
    }


    @PostMapping()
    public String createEmployee(@RequestBody EmployeeDTO employeeDTO){

        Optional<EmployeeDTO> employee_ = employeeService.CreateEmployee(employeeDTO);
        if(employee_.isPresent()){
            return "The Employee Data has been Saved Successfully";
        } else {
            return "Employee already Exists in Records";
        }
    }

    @PatchMapping()
    public String updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Optional<EmployeeDTO> _employee = employeeService.updateEmployee(employeeDTO);
        if (_employee.isEmpty()){
            return "The Employee does not Exists in Table.";
        } else {
            return "The Employee Data has been successfully Updated.";
        }
    }

    @DeleteMapping("{Id}")
    public String deleteEmployeeById(@PathVariable("Id") int Id) {
        return employeeService.deleteEmployee(Id);
    }
}
