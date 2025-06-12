//package com.shubh.EmployeeAPICRUD.service;
//
//import com.shubh.EmployeeAPICRUD.entity.OtherCharges;
//import com.shubh.EmployeeAPICRUD.entity.POReference;
//import com.shubh.EmployeeAPICRUD.repository.OtherChargesRepository;
//import com.shubh.EmployeeAPICRUD.repository.POReferenceRepository;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//@Service
//public class POFileDownloadService {
//    @Autowired
//    private POReferenceRepository poReferenceRepository;
//
//    public byte[] generateErrorExcel(String fileName) throws IOException {
//        List<POReference> errors = poReferenceRepository.findByFileNameAndHasErrorTrue(fileName);
//        return createExcelFile(errors, "Error");
//    }
//
//    public byte[] generateSuccessExcel(String fileName) throws IOException {
//        List<POReference> success = poReferenceRepository.findByFileNameAndHasErrorFalse(fileName);
//        return createExcelFile(success, "Success");
//    }
//
//    private byte[] createExcelFile(List<POReference> dataList, String sheetName) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet(sheetName);
//
//        int rowNum = 0;
//        Row header = sheet.createRow(rowNum++);
//        header.createCell(0).setCellValue("Recipient GSTIN");
//        header.createCell(1).setCellValue("PO / SA Reference Number");
//        header.createCell(2).setCellValue("Error Message"); // optional
//
//        for (POReference data : dataList) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(data.getRecipientGstin());
//            row.createCell(1).setCellValue(data.getSupplierGstin());
//            row.createCell(2).setCellValue(data.getErrorMessage() != null ? data.getErrorMessage() : "No Error");
//        }
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        workbook.write(out);
//        workbook.close();
//        return out.toByteArray();
//    }
//
//}
