package com.clarium.controller;

import com.clarium.dto.EmployeeLoginDTO;
import com.clarium.entity.Employee;
import com.clarium.service.EmployeeLoginService;
import com.clarium.service.EmployeeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employeemanagement")
public class EmployeeLoginController {

    private final EmployeeLoginService employeeLoginService;

    public EmployeeLoginController(EmployeeLoginService employeeLoginService) {
        this.employeeLoginService = employeeLoginService;
    }

    @GetMapping("employeeLogin/{Id}")
    public String getEmployeeLoginById(@PathVariable("Id") int Id, @RequestParam("login") boolean isLogin){
        Optional<Employee> employeeDTO = employeeLoginService.createEmployeeLoginTime(Id,isLogin);
        return "LoginDetailsCreatedSuccessfully";
    }

    @GetMapping("LoginDetailswithempId")
    public ResponseEntity<EmployeeLoginDTO> getEmployeeLoginDetails(
            @RequestParam("empId") Integer empId,
            @RequestParam("loginDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date loginDate
            ) {

        EmployeeLoginDTO loginDetails = employeeLoginService.getEmployeeLoginByEmpIdAndDate(empId, loginDate);
        return ResponseEntity.ok(loginDetails);

    }

    @GetMapping("LoginDetails/{empId}")
    public List<EmployeeLoginDTO> getAllLoginsById(@PathVariable("empId") Integer empId) {
        List<EmployeeLoginDTO> loginDetails = employeeLoginService.getAllEmployeeLoginDetails(empId);
        return loginDetails;
    }



}
