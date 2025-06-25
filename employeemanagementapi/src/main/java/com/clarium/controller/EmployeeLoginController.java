package com.clarium.controller;

import com.clarium.dto.EmployeeLoginDTO;
import com.clarium.dto.WeeklyReportDTO;
import com.clarium.service.EmployeeLoginService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.clarium.constants.ApplicationConstants.*;

@RestController
@RequestMapping("/employeeManagement")
public class EmployeeLoginController {

    private final EmployeeLoginService employeeLoginService;

    public EmployeeLoginController(EmployeeLoginService employeeLoginService) {
        this.employeeLoginService = employeeLoginService;
    }

    @GetMapping("employeeLogin/{Id}")
    public ResponseEntity<String> createEmployeeLoginById(@PathVariable("Id") int Id, @RequestParam("login") boolean isLogin){
        return ResponseEntity.ok(employeeLoginService.createEmployeeLoginTime(Id,isLogin));
    }

    @GetMapping("loginDetailsWithEmpId")
    public ResponseEntity<EmployeeLoginDTO> getEmployeeLoginDetails(@RequestParam("empId") Integer empId, @RequestParam("loginDate") @DateTimeFormat(pattern = DATE_FORMAT_PATTERN) Date loginDate) {
        return ResponseEntity.ok(employeeLoginService.getEmployeeLoginByEmpIdAndDate(empId, loginDate));
    }

    @GetMapping("loginDetailsWithDate")
    public ResponseEntity<List<EmployeeLoginDTO>> getEmployeeLoginDetailsWithDate(@RequestParam("loginDate") @DateTimeFormat(pattern = DATE_FORMAT_PATTERN) Date loginDate){
        return ResponseEntity.ok(employeeLoginService.getListOfEmployeeLogin(loginDate));
    }

    @GetMapping("weekly-report")
    public ResponseEntity<List<WeeklyReportDTO>> getWeeklyReport() {
        return ResponseEntity.ok(employeeLoginService.getWeeklyReport());
    }

    @GetMapping("sendmail")
    public ResponseEntity<String> sendmail() {
        return ResponseEntity.ok(employeeLoginService.sendSimpleMail());
    }

}
