package com.clarium.service.impl;

import com.clarium.dao.EmployeeLoginRepository;
import com.clarium.dao.EmployeeRepository;
import com.clarium.dto.EmployeeLoginDTO;
import com.clarium.entity.Employee;
import com.clarium.entity.EmployeeLogin;
import com.clarium.mapper.EmployeeMapper;
import com.clarium.service.EmployeeLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeLoginServiceImpl implements EmployeeLoginService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeLoginRepository employeeLoginRepository;

    @Override
    public Optional<Employee> createEmployeeLoginTime(int id, boolean isLogin) {
        Optional<Employee> employee = employeeRepository.findById(id);
        EmployeeLogin employeeLogin = new EmployeeLogin();
        employeeLogin.setLoginDate(java.sql.Date.valueOf(LocalDate.now()));
        employeeLogin.setEmployee(employee.get());
        if(isLogin) {
            employeeLogin.setLoginTime(Timestamp.valueOf(LocalDateTime.now()));
        } else {
            employeeLogin.setLogoutTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        employeeLogin.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
        employeeLogin.setUpdatedAt(new Timestamp(new java.util.Date().getTime()));
        employeeLoginRepository.save(employeeLogin);
        return Optional.empty();
    }

    /**
     *
     * @param employeeLogin
     * @return
     */

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

    public List<EmployeeLoginDTO> getAllEmployeeLoginDetails(Integer empId) {
        List<EmployeeLogin> employeeLoginDto = employeeLoginRepository.findByEmployee_EmpId(empId);
        return employeeLoginDto.stream()
                .map(this::employeeLoginEntitytoModel)
                .collect(Collectors.toList());
    }

    public EmployeeLoginDTO getEmployeeLoginByEmpIdAndDate(Integer empId, Date loginDate) {
        EmployeeLogin employeeLogin = employeeLoginRepository.findByEmployee_EmpIdAndLoginDate(empId, loginDate);
        return employeeLoginEntitytoModel(employeeLogin);
    }

}
