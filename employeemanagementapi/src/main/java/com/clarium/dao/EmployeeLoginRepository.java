package com.clarium.dao;

import com.clarium.entity.EmployeeLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeLoginRepository extends JpaRepository<EmployeeLogin,Integer> {

    EmployeeLogin findByEmployee_EmpIdAndLoginDate(Integer empId, Date loginDate);

    List<EmployeeLogin> findAllByLoginDate(Date loginDate);

    List<EmployeeLogin> findAllByLoginDateBetween(Date startDate, Date endDate);

    Optional<EmployeeLogin> findByEmployee_EmpIdAndLogoutTimeIsNull(Integer empId);

    Optional<EmployeeLogin> findTopByEmployee_EmpIdAndLogoutTimeIsNullOrderByLoginTimeDesc(Integer empId);
}
