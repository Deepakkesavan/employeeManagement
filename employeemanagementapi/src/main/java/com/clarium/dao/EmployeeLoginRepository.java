package com.clarium.dao;

import com.clarium.entity.EmployeeLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeLoginRepository extends JpaRepository<EmployeeLogin,Integer> {

    List<EmployeeLogin> findByEmployee_EmpId(Integer empId);

    EmployeeLogin findByEmployee_EmpIdAndLoginDate(Integer empId, Date loginDate);




}
