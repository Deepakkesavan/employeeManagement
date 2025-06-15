package com.clarium.service.impl;

import com.clarium.dao.EmployeeLoginRepository;
import com.clarium.dao.EmployeeRepository;
import com.clarium.dto.DailyWorkReportDTO;
import com.clarium.dto.EmployeeLoginDTO;
import com.clarium.dto.WeeklyReportDTO;
import com.clarium.entity.Employee;
import com.clarium.entity.EmployeeLogin;
import com.clarium.mapper.EmployeeMapper;
import com.clarium.service.EmployeeLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeLoginServiceImpl implements EmployeeLoginService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeLoginRepository employeeLoginRepository;

    @Override
    public Optional<Employee> createEmployeeLoginTime(int id, boolean isLogin) {
        Optional<Employee> employee = employeeRepository.findById(id);

        // Check if employee exists
        if (!employee.isPresent()) {
            return Optional.empty();
        }

        if (isLogin) {
            // Check if employee already has an active login (no logout time)
            Optional<EmployeeLogin> existingLogin = employeeLoginRepository.findByEmployee_EmpIdAndLogoutTimeIsNull(id);

            if (existingLogin.isPresent()) {
                // Employee is already logged in, don't create duplicate login
                return employee;
            }

            // Create new login record
            EmployeeLogin employeeLogin = new EmployeeLogin();
            employeeLogin.setEmployee(employee.get());
            employeeLogin.setLoginDate(java.sql.Date.valueOf(LocalDate.now()));
            employeeLogin.setLoginTime(Timestamp.valueOf(LocalDateTime.now()));
            employeeLoginRepository.save(employeeLogin);

        } else {
            // Find the most recent login without logout time
            Optional<EmployeeLogin> employeeLogin = employeeLoginRepository.findTopByEmployee_EmpIdAndLogoutTimeIsNullOrderByLoginTimeDesc(id);

            if (employeeLogin.isPresent()) {
                employeeLogin.get().setLogoutTime(Timestamp.valueOf(LocalDateTime.now()));
                employeeLoginRepository.save(employeeLogin.get());
            } else {
                // No active login found to logout
                return Optional.empty();
            }
        }

        return employee;
    }



    public EmployeeLoginDTO employeeLoginEntitytoModel(EmployeeLogin employeeLogin){

        EmployeeLoginDTO employeeLoginDTO = new EmployeeLoginDTO();

        employeeLoginDTO.setEmpId(employeeLogin.getEmployee().getEmpId());
        employeeLoginDTO.setEmpName(employeeLogin.getEmployee().getFirstName());
        employeeLoginDTO.setLoginTime(employeeLogin.getLoginTime());
        employeeLoginDTO.setLogoutTime(employeeLogin.getLogoutTime());

        Timestamp calculatedHours = calculateLoginHours(employeeLogin.getLoginTime(),employeeLogin.getLogoutTime());
        employeeLoginDTO.setLoginHours(calculatedHours);

        return employeeLoginDTO;
    }

    public Timestamp calculateLoginHours(Timestamp loginTime, Timestamp logoutTime) {

        if (loginTime == null || logoutTime == null) return null;

        LocalDateTime login = loginTime.toLocalDateTime();
        LocalDateTime logout = logoutTime.toLocalDateTime();

        Duration duration;

        if (logout.isBefore(login)) {
            return null;
        } else {
            duration = Duration.between(login,logout);
        }

        long durationMillis =  duration.toMillis();
        return new Timestamp(durationMillis);
    }

    public EmployeeLoginDTO getEmployeeLoginByEmpIdAndDate(Integer empId, Date loginDate) {
        EmployeeLogin employeeLogin = employeeLoginRepository.findByEmployee_EmpIdAndLoginDate(empId, loginDate);
        return employeeLoginEntitytoModel(employeeLogin);
    }


    //This Method I am Trying to fetch ListOfEmployeeDTO from EmployeeLogin By LoginDate
    public List<EmployeeLoginDTO> getListOfEmployeeLogin(Date loginDate) {
        List<EmployeeLogin> employeeLogins = employeeLoginRepository.findAllByLoginDate(loginDate);
        return employeeLogins.stream()
                .map(this::employeeLoginEntitytoModel)
                .collect(Collectors.toList());
    }

//  @Scheduled(cron = "0 0/1 * * * FRI")
    public List<EmployeeLoginDTO> getEmployeeLoginsForLast5Days() {

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1); // minusDays(4) gives us 5 days total including today

        Date sqlStartDate = java.sql.Date.valueOf(startDate);
        Date sqlEndDate = java.sql.Date.valueOf(endDate);

        // Query from endDate to startDate (5 days back to today)
        List<EmployeeLogin> employeeLogins = employeeLoginRepository.findAllByLoginDateBetween(sqlEndDate, sqlStartDate);

        return employeeLogins.stream()
                .map(this::employeeLoginEntitytoModel)
                .collect(Collectors.toList());
//        System.out.println("TEst");
//        return null;
    }



    public List<WeeklyReportDTO> getWeeklyReport() {
        // Get current week dates (Monday to Sunday)
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.FRIDAY);

        Date sqlStartDate = java.sql.Date.valueOf(startOfWeek);
        Date sqlEndDate = java.sql.Date.valueOf(endOfWeek);

        // Get all employee logins for the current week
        List<EmployeeLogin> weeklyLogins = employeeLoginRepository.findAllByLoginDateBetween(sqlStartDate, sqlEndDate);

        // Group by employee ID
        Map<Integer, List<EmployeeLogin>> loginsByEmployee = weeklyLogins.stream()
                .collect(Collectors.groupingBy(login -> login.getEmployee().getEmpId()));

        List<WeeklyReportDTO> weeklyReports = new ArrayList<>();

        for (Map.Entry<Integer, List<EmployeeLogin>> entry : loginsByEmployee.entrySet()) {
            Integer empId = entry.getKey();
            List<EmployeeLogin> employeeLogins = entry.getValue();

            // Group by date for each employee
            Map<LocalDate, List<EmployeeLogin>> loginsByDate = employeeLogins.stream()
                    .collect(Collectors.groupingBy(login -> login.getLoginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));



            List<DailyWorkReportDTO> dailyReports = new ArrayList<>();
            double totalWeeklyHours = 0.0;

            for (Map.Entry<LocalDate, List<EmployeeLogin>> dateEntry : loginsByDate.entrySet()) {
                LocalDate date = dateEntry.getKey();
                List<EmployeeLogin> dailyLogins = dateEntry.getValue();

                // Calculate total work hours for the day
                double dailyWorkHours = 0.0;
                for (EmployeeLogin login : dailyLogins) {
                    if (login.getLoginTime() != null && login.getLogoutTime() != null) {
                        long workMillis = login.getLogoutTime().getTime() - login.getLoginTime().getTime();
                        double workHours = workMillis / (1000.0 * 60 * 60); // Convert to hours
                        dailyWorkHours += workHours;
                    }
                }

                if (dailyWorkHours > 0) {
                    DailyWorkReportDTO dailyReport = new DailyWorkReportDTO(
                            java.sql.Date.valueOf(date),
                            Math.round(dailyWorkHours * 100.0) / 100.0 // Round to 2 decimal places
                    );
                    dailyReports.add(dailyReport);
                    totalWeeklyHours += dailyWorkHours;
                }
            }

            // Sort daily reports by date
            dailyReports.sort(Comparator.comparing(DailyWorkReportDTO::getDate));

            WeeklyReportDTO weeklyReport = new WeeklyReportDTO(
                    empId,
                    dailyReports,
                    Math.round(totalWeeklyHours * 100.0) / 100.0 // Round to 2 decimal places
            );

            weeklyReports.add(weeklyReport);
        }

        // Sort by employee ID
        weeklyReports.sort(Comparator.comparing(WeeklyReportDTO::getEmpId));


        return weeklyReports;
    }


    public String sendSimpleMail(){

        StringBuilder jsonLike = new StringBuilder("[");
        for (int i = 0; i < getWeeklyReport().size(); i++) {
            WeeklyReportDTO report = getWeeklyReport().get(i);
            jsonLike.append("{\"EmpId\":").append(report.getEmpId())
                    .append(",\"TotalWorkHours\":").append(report.getTotalWorkHours()).append("}");
            if (i < getWeeklyReport().size() - 1) jsonLike.append(",");
        }
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("deepakramoorthy@gmail.com");
            simpleMailMessage.setTo("deepakk@clarium.tech");
            simpleMailMessage.setText(jsonLike.toString());
            simpleMailMessage.setSubject("Weekly Report Of Employees");

            javaMailSender.send(simpleMailMessage);
            return "Mail Sent Successfully";
    }




}
