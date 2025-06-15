package com.clarium.dao;

import com.clarium.entity.Employee;
import com.clarium.entity.EmployeeLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeLoginRepository extends JpaRepository<EmployeeLogin,Integer> {


    Optional<EmployeeLogin> findByEmployee_EmpId(Integer empId);

    EmployeeLogin findByEmployeeAndLoginDate(Optional<Employee> employee, LocalDate now);

    EmployeeLogin findByEmployee_EmpIdAndLoginDate(Integer empId, Date loginDate);

    List<EmployeeLogin> findAllByLoginDate(Date loginDate);

    List<EmployeeLogin> findAllByLoginDateBetween(Date startDate, Date endDate);

    // Find employee login where logout time is null (active login)
    Optional<EmployeeLogin> findByEmployee_EmpIdAndLogoutTimeIsNull(Integer empId);

    // Find the most recent login without logout time (for logout operation)
    Optional<EmployeeLogin> findTopByEmployee_EmpIdAndLogoutTimeIsNullOrderByLoginTimeDesc(Integer empId);

}
