package com.clarium.service.impl;

import com.clarium.dao.EmployeeLoginRepository;
import com.clarium.dao.EmployeeRepository;
import com.clarium.dto.DailyWorkReportDTO;
import com.clarium.dto.EmailConfigurations;
import com.clarium.dto.EmployeeLoginDTO;
import com.clarium.dto.WeeklyReportDTO;
import com.clarium.entity.Employee;
import com.clarium.entity.EmployeeLogin;
import com.clarium.mapper.EmployeeMapper;
import com.clarium.service.EmployeeLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import static com.clarium.constants.ApplicationConstants.*;

@Service
public class EmployeeLoginServiceImpl implements EmployeeLoginService {

    @Autowired
    private EmailConfigurations emailConfigurations;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeLoginRepository employeeLoginRepository;

    @Override
    public String createEmployeeLoginTime(int id, boolean isLogin) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            return EMPLOYEE_LOGIN_NOT_FOUND;
        }
        if (isLogin) {
            Optional<EmployeeLogin> existingLogin = employeeLoginRepository.findByEmployee_EmpIdAndLogoutTimeIsNull(id);
            if (existingLogin.isPresent()) {
                return EMPLOYEE_LOGIN_IS_PRESENT;
            }
            EmployeeLogin employeeLogin = new EmployeeLogin();
            employeeLogin.setEmployee(employee.get());
            employeeLogin.setLoginDate(java.sql.Date.valueOf(LocalDate.now()));
            employeeLogin.setLoginTime(Timestamp.valueOf(LocalDateTime.now()));
            employeeLoginRepository.save(employeeLogin);
            return EMPLOYEE_LOGIN_SUCCESSFUL;
        } else {
            Optional<EmployeeLogin> employeeLogin = employeeLoginRepository.findTopByEmployee_EmpIdAndLogoutTimeIsNullOrderByLoginTimeDesc(id);
            if (employeeLogin.isPresent()) {
                employeeLogin.get().setLogoutTime(Timestamp.valueOf(LocalDateTime.now()));
                employeeLoginRepository.save(employeeLogin.get());
            }
        }
        return EMPLOYEE_LOGOUT_SUCCESSFUL;
    }

    public EmployeeLoginDTO employeeLoginEntityToDTO(EmployeeLogin employeeLogin){
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
        if (Objects.isNull(loginTime) ||  Objects.isNull(logoutTime)) return null;
        LocalDateTime login = loginTime.toLocalDateTime();
        LocalDateTime logout = logoutTime.toLocalDateTime();
        Duration duration = !logout.isBefore(login)?Duration.between(login,logout):Duration.ZERO;
        long durationMillis =  duration.toMillis();
        return new Timestamp(durationMillis);
    }

    public EmployeeLoginDTO getEmployeeLoginByEmpIdAndDate(Integer empId, Date loginDate) {
        EmployeeLogin employeeLogin = employeeLoginRepository.findByEmployee_EmpIdAndLoginDate(empId, loginDate);
        return employeeLoginEntityToDTO(employeeLogin);
    }


    public List<EmployeeLoginDTO> getListOfEmployeeLogin(Date loginDate) {
        List<EmployeeLogin> employeeLogins = employeeLoginRepository.findAllByLoginDate(loginDate);
        return employeeLogins.stream()
                .map(this::employeeLoginEntityToDTO)
                .collect(Collectors.toList());
    }

    public List<WeeklyReportDTO> getWeeklyReport() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(WEEK_START_DAY);
        LocalDate endOfWeek = today.with(WEEK_END_DAY);
        Date sqlStartDate = java.sql.Date.valueOf(startOfWeek);
        Date sqlEndDate = java.sql.Date.valueOf(endOfWeek);

        List<EmployeeLogin> weeklyLogins = employeeLoginRepository.findAllByLoginDateBetween(sqlStartDate, sqlEndDate);

        Map<Integer, List<EmployeeLogin>> loginsByEmployee = weeklyLogins.stream()
                .collect(Collectors.groupingBy(login -> login.getEmployee().getEmpId()));

        return loginsByEmployee.entrySet().stream()
                .map(entry -> {
                    Integer empId = entry.getKey();
                    List<EmployeeLogin> employeeLogins = entry.getValue();

                    Map<LocalDate, List<EmployeeLogin>> loginsByDate = employeeLogins.stream()
                            .collect(Collectors.groupingBy(login ->
                                    login.getLoginDate().toInstant().atZone(SYSTEM_DEFAULT_ZONE).toLocalDate()));

                    List<DailyWorkReportDTO> dailyReports = loginsByDate.entrySet().stream()
                            .map(dateEntry -> {
                                LocalDate date = dateEntry.getKey();
                                double dailyWorkHours = getDailyWorkHours(dateEntry);
                                return dailyWorkHours > MIN_WORK_HOURS_THRESHOLD ?
                                        new DailyWorkReportDTO(
                                                java.sql.Date.valueOf(date),
                                                Math.round(dailyWorkHours * PRECISION_MULTIPLIER) / PRECISION_MULTIPLIER
                                        ) : null;
                            })
                            .filter(Objects::nonNull)
                            .sorted(Comparator.comparing(DailyWorkReportDTO::getDate))
                            .collect(Collectors.toList());

                    double totalWeeklyHours = dailyReports.stream()
                            .mapToDouble(DailyWorkReportDTO::getWorkHours)
                            .sum();

                    return new WeeklyReportDTO(
                            empId,
                            dailyReports,
                            Math.round(totalWeeklyHours * PRECISION_MULTIPLIER) / PRECISION_MULTIPLIER
                    );
                })
                .sorted(Comparator.comparing(WeeklyReportDTO::getEmpId))
                .collect(Collectors.toList());
    }

    private static double getDailyWorkHours(Map.Entry<LocalDate, List<EmployeeLogin>> dateEntry) {
        return dateEntry.getValue().stream()
                .filter(login -> Objects.nonNull(login.getLoginTime()) && Objects.nonNull(login.getLogoutTime()))
                .mapToDouble(login -> {
                    long workMillis = login.getLogoutTime().getTime() - login.getLoginTime().getTime();
                    return workMillis / MILLISECONDS_TO_HOURS; // Convert to hours using constant
                })
                .sum();
    }

    @Scheduled(cron = WEEKLY_REPORT_CRON)
    public String sendSimpleMail(){
        String jsonLike = getWeeklyReport()
                .stream()
                .map(report -> String.format(JSON_TEMPLATE, report.getEmpId(), report.getTotalWorkHours()))
                .collect(Collectors.joining(JSON_SEPARATOR, JSON_ARRAY_START, JSON_ARRAY_END));

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailConfigurations.getFromAddress());
        simpleMailMessage.setTo(emailConfigurations.getToAddress());
        simpleMailMessage.setText(jsonLike);
        simpleMailMessage.setSubject(emailConfigurations.getSubject());
        javaMailSender.send(simpleMailMessage);
        System.out.println(MAIL_SENT_CONSOLE_MESSAGE);
        return MAIL_SENT_SUCCESS_MESSAGE;
    }


}
