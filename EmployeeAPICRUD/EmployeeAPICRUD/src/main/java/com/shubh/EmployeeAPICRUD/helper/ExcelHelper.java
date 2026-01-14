package com.shubh.EmployeeAPICRUD.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.shubh.EmployeeAPICRUD.dto.EmployeeDTO;
import com.shubh.EmployeeAPICRUD.entity.Employee;
import com.shubh.EmployeeAPICRUD.repository.FileTrackingRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelHelper {
    private List<Employee> successRecords = new ArrayList<>();
    private List<EmployeeDTO> errorRecords = new ArrayList<>();

    public void setSuccessRecords(List<Employee> successRecords) {
        this.successRecords = successRecords;
    }

    public void setErrorRecords(List<EmployeeDTO> errorRecords) {
        this.errorRecords = errorRecords;
    }

    public ByteArrayInputStream generateSuccessExcel() throws IOException {
        return generateExcel(successRecords, false);
    }

    public ByteArrayInputStream generateErrorExcel() throws IOException {
        return generateExcel(errorRecords, true);
    }

    private <T> ByteArrayInputStream generateExcel(List<T> records, boolean isError) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(isError ? "Error Records" : "Success Records");
            Row headerRow = sheet.createRow(0);

            String[] headers = isError
                    ? new String[]{"Error Message" ,"Name", "Salary", "Age", "Department", "Department ID", "Address", "Mobile", "Aadhar"}
                    : new String[]{"ID", "Name", "Salary", "Age", "Department", "Department ID", "Address", "Mobile", "Aadhar"};

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIndex = 1;
            for (T record : records) {
                Row row = sheet.createRow(rowIndex++);

                if (isError && record instanceof EmployeeDTO dto) {
                    row.createCell(0).setCellValue(dto.getErrorMessage());  // Error Message stored in empAdd temporarily
                    row.createCell(1).setCellValue(dto.getEmpName());
                    row.createCell(2).setCellValue(dto.getEmpSalary());
                    row.createCell(3).setCellValue(dto.getEmpAge());
                    row.createCell(4).setCellValue(dto.getDepName());
                    row.createCell(5).setCellValue(dto.getDepId());
                    row.createCell(6).setCellValue(dto.getEmpAdd());
                    row.createCell(7).setCellValue(dto.getEmpMob());
                    row.createCell(8).setCellValue(dto.getEmpAdhar());

                } else if (record instanceof Employee emp) {
                    row.createCell(0).setCellValue(emp.getEmpId());
                    row.createCell(1).setCellValue(emp.getEmpName());
                    row.createCell(2).setCellValue(emp.getEmpSalary());
                    row.createCell(3).setCellValue(emp.getEmpAge());
                    row.createCell(4).setCellValue(emp.getDepName());
                    row.createCell(5).setCellValue(emp.getDepId());
                    row.createCell(6).setCellValue(emp.getEmpAdd());
                    row.createCell(7).setCellValue(emp.getEmpMob());
                    row.createCell(8).setCellValue(emp.getEmpAdhar());
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public List<EmployeeDTO> convertExcelToEmployeeDTOList(InputStream is) {
        List<EmployeeDTO> list = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowNumber = 0;

            for (Row row : sheet) {
                if (rowNumber++ == 0) continue; // Skip header

                EmployeeDTO dto = new EmployeeDTO();

                try {
                    dto.setEmpName(formatter.formatCellValue(row.getCell(0)).trim());
                    dto.setEmpSalary(Float.parseFloat(formatter.formatCellValue(row.getCell(1)).trim()));
                    dto.setEmpAge(Integer.parseInt(formatter.formatCellValue(row.getCell(2)).trim()));
                    dto.setDepName(formatter.formatCellValue(row.getCell(3)).trim());
                    dto.setDepId(Integer.parseInt(formatter.formatCellValue(row.getCell(4)).trim()));
                    dto.setEmpAdd(formatter.formatCellValue(row.getCell(5)).trim());
                    dto.setEmpMob(formatter.formatCellValue(row.getCell(6)).trim());
                    dto.setEmpAdhar(formatter.formatCellValue(row.getCell(7)).trim());

                    list.add(dto);

                } catch (Exception e) {
                    System.err.println("Error parsing row " + rowNumber + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }

        return list;
    }


}










//
//    @Autowired
//    private FileTrackingRepository fileTrackingRepository;
//
//    public List<EmployeeDTO> convertExcelToEmployeeDTOList(InputStream is) {
//        List<EmployeeDTO> list = new ArrayList<>();
//        try (Workbook workbook = WorkbookFactory.create(is)) {
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) continue; // Skip header row
//
//                EmployeeDTO dto = new EmployeeDTO();
//                dto.setEmpName(row.getCell(0).getStringCellValue());
//                dto.setEmpSalary((float) row.getCell(1).getNumericCellValue());
//                dto.setEmpAge((int) row.getCell(2).getNumericCellValue());
//                dto.setDepName(row.getCell(3).getStringCellValue());
//                dto.setEmpMob(row.getCell(4).getStringCellValue());
//                dto.setEmpAdhar(row.getCell(5).getStringCellValue());
//
//                list.add(dto);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }





