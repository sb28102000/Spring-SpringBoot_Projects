package com.shubh.EmployeeAPICRUD.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table(name="employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;
    @Column(name = "emp_name")
    private String empName;
    @Column(name = "emp_salary")
    private float empSalary;
    @Column(name = "emp_age")
    private int empAge;
    @Column(name = "dep_name")
    private String depName;
    @Column(name = "dep_id")
    private int depId;
    @Column(name = "emp_add")
    private String empAdd;
    @Column(name = "emp_mob")
    private String empMob;
    @Column(name = "emp_adhar")
    private String empAdhar;
}
