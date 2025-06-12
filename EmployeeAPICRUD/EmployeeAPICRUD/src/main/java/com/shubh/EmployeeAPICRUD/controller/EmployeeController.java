// File: controller/EmployeeController.java
package com.shubh.EmployeeAPICRUD.controller;

import com.shubh.EmployeeAPICRUD.dto.EmployeeDTO;
import com.shubh.EmployeeAPICRUD.helper.ExcelHelper;
import com.shubh.EmployeeAPICRUD.service.EmployeeService;
import com.shubh.EmployeeAPICRUD.service.EmployeeServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExcelHelper excelHelper;
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO dto = employeeService.getEmployeeById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        EmployeeDTO updated = employeeService.updateEmployee(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().body(Map.of("message:","Employee deleted successfully"));
    }

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            employeeServiceImpl.saveFromExcel(file);
            return ResponseEntity.ok().body(
                    Map.of(
                            "message", "Excel File Uploaded."
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(
                            "message", "Failed to upload file: " + e.getMessage()
                    )
                    );
        }
    }

    @GetMapping("/download/success")
    public ResponseEntity<?> downloadSuccessRecords() {
        try {
            ByteArrayInputStream inputStream = excelHelper.generateSuccessExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=success_records.xlsx")
                    .body(inputStream.readAllBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate success records Excel: " + e.getMessage());
        }
    }

    @GetMapping("/download/error")
    public ResponseEntity<?> downloadErrorRecords() {
        try {
            ByteArrayInputStream inputStream = excelHelper.generateErrorExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=error_records.xlsx")
                    .body(inputStream.readAllBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate error records Excel: " + e.getMessage());
        }
    }
    @PostMapping("/upload-json")
    public ResponseEntity<Map> uploadJson(@RequestParam("file") MultipartFile file) {
        try {
            employeeServiceImpl.saveFromJson(file);
            return ResponseEntity.ok().body(Map.of("message","JSON File uploaded."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message","Failed to upload JSON File."));
        }
    }
}



