package com.shubh.EmployeeAPICRUD.service;

import com.shubh.EmployeeAPICRUD.dto.EmployeeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO dto);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO dto);
    void deleteEmployee(Long id);
    void saveFromExcel(MultipartFile file);
    void saveFromJson(MultipartFile file);
}
