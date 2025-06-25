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

    public EmployeeController(EmployeeService empService) {
        employeeService = empService;
    }

    @GetMapping("{Id}")
    public ResponseEntity<Optional<EmployeeDTO>> getEmployeeById(@PathVariable("Id") int Id){
        return ResponseEntity.ok(employeeService.getEmployeeById(Id));
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(employeeService.CreateEmployee(employeeDTO));
    }

    @PatchMapping
    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeDTO));
    }

    @DeleteMapping("{Id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("Id") int Id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(Id));
    }
}
