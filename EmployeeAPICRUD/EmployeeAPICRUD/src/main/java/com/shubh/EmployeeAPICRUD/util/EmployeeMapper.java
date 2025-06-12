package com.shubh.EmployeeAPICRUD.util;

import com.shubh.EmployeeAPICRUD.dto.EmployeeDTO;
import com.shubh.EmployeeAPICRUD.entity.Employee;

public final class EmployeeMapper {

    private EmployeeMapper() {
        // Prevent instantiation
    }

    public static Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setEmpName(dto.getEmpName());
        employee.setEmpSalary(Float.parseFloat(String.valueOf(dto.getEmpSalary())));
        employee.setEmpAge(Integer.parseInt(String.valueOf(dto.getEmpAge())));
        employee.setDepName(dto.getDepName());
        employee.setDepId(Integer.parseInt(String.valueOf(dto.getDepId())));
        employee.setEmpAdd(dto.getEmpAdd());
        employee.setEmpMob(dto.getEmpMob());
        employee.setEmpAdhar(dto.getEmpAdhar());
        return employee;
    }

    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmpName(employee.getEmpName());
        dto.setEmpSalary(Float.valueOf(String.valueOf(employee.getEmpSalary())));
        dto.setEmpAge(Integer.valueOf(String.valueOf(employee.getEmpAge())));
        dto.setDepName(employee.getDepName());
        dto.setDepId(Integer.valueOf(String.valueOf(employee.getDepId())));
        dto.setEmpAdd(employee.getEmpAdd());
        dto.setEmpMob(employee.getEmpMob());
        dto.setEmpAdhar(employee.getEmpAdhar());
        return dto;
    }
}
