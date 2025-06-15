package com.clarium.controller;

import com.clarium.dto.EmployeeLoginDTO;
import com.clarium.dto.WeeklyReportDTO;
import com.clarium.entity.Employee;
import com.clarium.service.EmployeeLoginService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

        if (isLogin) {
            return "Employee Login Successful";
        } else {
            return "Employee Logout Successful";
        }
    }

    @GetMapping("LoginDetailswithempId")
    public ResponseEntity<EmployeeLoginDTO> getEmployeeLoginDetails(
            @RequestParam("empId") Integer empId,
            @RequestParam("loginDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date loginDate
            ) {

        EmployeeLoginDTO loginDetails = employeeLoginService.getEmployeeLoginByEmpIdAndDate(empId, loginDate);
        return ResponseEntity.ok(loginDetails);

    }

    @GetMapping("/DetailsWithDate")
    public List<EmployeeLoginDTO> getEmployeeLoginDetailswithDate(
            @RequestParam("loginDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date loginDate
    ){
        List<EmployeeLoginDTO> employeeLoginDTOS = employeeLoginService.getListOfEmployeeLogin(loginDate);
        return  employeeLoginDTOS;
    }

    @GetMapping("/getLast5Days")
    public List<EmployeeLoginDTO> getEmployeeLoginsForLast5Days(){
        List<EmployeeLoginDTO> employeeLoginDTOS = employeeLoginService.getEmployeeLoginsForLast5Days();
        return employeeLoginDTOS;
    }

    @GetMapping("/weekly-report")
    public ResponseEntity<List<WeeklyReportDTO>> getWeeklyReport() {
        try {
            List<WeeklyReportDTO> weeklyReport = employeeLoginService.getWeeklyReport();
            return ResponseEntity.ok(weeklyReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("sendmail")
    public String sendmail(){
        return employeeLoginService.sendSimpleMail();
    }

}
