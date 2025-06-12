package com.shubh.EmployeeAPICRUD.service;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.EmployeeAPICRUD.dto.EmployeeDTO;
import com.shubh.EmployeeAPICRUD.entity.Employee;
import com.shubh.EmployeeAPICRUD.entity.FileTracking;
import com.shubh.EmployeeAPICRUD.helper.ExcelHelper;
import com.shubh.EmployeeAPICRUD.helper.JsonHelper;
import com.shubh.EmployeeAPICRUD.repository.EmployeeRepository;
import com.shubh.EmployeeAPICRUD.repository.FileTrackingRepository;
import com.shubh.EmployeeAPICRUD.util.EmployeeMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {



    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FileTrackingRepository fileTrackingRepository;

    @Autowired
    private ExcelHelper excelHelper;

    @Autowired
    private Validator validator;

    @Autowired
    private JsonHelper jsonHelper;



    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ExcelHelper excelHelper, Validator validator) {
        this.employeeRepository = employeeRepository;
        this.excelHelper = excelHelper;
        this.validator = validator;
    }


    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Employee employee = EmployeeMapper.toEntity(dto);
        return EmployeeMapper.toDTO(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .toList();
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(EmployeeMapper::toDTO).orElse(null);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Optional<Employee> empOpt = employeeRepository.findById(id);
        if (empOpt.isPresent()) {
            Employee employee = empOpt.get();
            employee.setEmpName(dto.getEmpName());
            employee.setEmpSalary(Float.parseFloat(String.valueOf(dto.getEmpSalary())));
            employee.setEmpAge(Integer.parseInt(String.valueOf(dto.getEmpAge())));
            employee.setDepName(dto.getDepName());
            employee.setEmpMob(dto.getEmpMob());
            employee.setEmpAdhar(dto.getEmpAdhar());
            return EmployeeMapper.toDTO(employeeRepository.save(employee));
        }
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveFromExcel(MultipartFile file) {
        int total = 0, processed = 0, error = 0;
        String fileName = file.getOriginalFilename();
        StringBuilder errorMessages = new StringBuilder();
        List<Employee> successRecords = new ArrayList<>();
        List<EmployeeDTO> errorRecords = new ArrayList<>();

        List<EmployeeDTO> dtos;
        try {
            dtos = excelHelper.convertExcelToEmployeeDTOList(file.getInputStream());
        } catch (Exception e) {
            saveTracking(fileName,0,0,0,"Failed to read Excel: " + e.getMessage());
            throw new RuntimeException("Failed to read Excel: " + e.getMessage());
        }

        total = dtos.size();

        for (EmployeeDTO dto : dtos) {
            Set<ConstraintViolation<EmployeeDTO>> voilations = validator.validate(dto);

            if (voilations.isEmpty()) {
                Employee employee = EmployeeMapper.toEntity(dto);
                successRecords.add(employee);
                processed++;
            } else {
                error++;

                String errorMsg = voilations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage())
                        .collect((Collectors.joining("; ")));

                errorMessages.append("validation failed for records: ").append(errorMsg).append("\n");
                dto.setErrorMessage(errorMsg);
                errorRecords.add(dto);
            }
        }

        if(!successRecords.isEmpty()){
            employeeRepository.saveAll(successRecords);
        }
        saveTracking(fileName,total,processed,error,errorMessages.toString());

//        Store error and success records for download
        excelHelper.setSuccessRecords(successRecords);
        excelHelper.setErrorRecords(errorRecords);
    }


    @Transactional
    @Override
    public void saveFromJson(MultipartFile file) {
        int total = 0, processed = 0, error = 0;
        String fileName = file.getOriginalFilename();
        StringBuilder errorMessages = new StringBuilder();

        List<EmployeeDTO> dtos;
        List<EmployeeDTO> errorRecords = new ArrayList<>();
        List<Employee> successRecords = new ArrayList<>();

        try {
            dtos = jsonHelper.convertJsonToEmployeeDTOList(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + e.getMessage());
        }

        total = dtos.size();

        for (EmployeeDTO dto : dtos) {
            Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(dto);

            if (violations.isEmpty()) {
                Employee employee = EmployeeMapper.toEntity(dto);
                employeeRepository.save(employee);
                successRecords.add(employee);
                processed++;
            } else {
                error++;
                StringBuilder errorMessage = new StringBuilder();

                for (ConstraintViolation<EmployeeDTO> violation : violations) {
                    errorMessage.append(violation.getPropertyPath()).append(" ")
                            .append(violation.getMessage()).append("; ");
                }

                dto.setErrorMessage(errorMessage.toString());
                errorRecords.add(dto);

                errorMessages.append("Record: ").append(dto.getEmpName())
                        .append(" - ").append(errorMessage.toString())
                        .append("\n");
            }
        }

        saveTracking(fileName, total, processed, error, errorMessages.toString());

        // Set the success and error records in excelHelper for download
        excelHelper.setSuccessRecords(successRecords);
        excelHelper.setErrorRecords(errorRecords);
    }


    private void saveTracking(String fileName, int total, int processed, int error,String errorMessage){
        FileTracking tracking = new FileTracking();
        tracking.setFileName(fileName);
        tracking.setTotal(total);
        tracking.setProcessed(processed);
        tracking.setError(error);
        tracking.setErrorMessage(errorMessage);
        fileTrackingRepository.save(tracking);
    }
}
