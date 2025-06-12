package com.shubh.EmployeeAPICRUD.repository;

import com.shubh.EmployeeAPICRUD.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //CharSequence findByempName(String empName);
}
