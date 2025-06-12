//package com.shubh.EmployeeAPICRUD.controller;
//
//import com.shubh.EmployeeAPICRUD.dto.POReferenceDTO;
//import com.shubh.EmployeeAPICRUD.util.ExcelGenerator;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.ByteArrayInputStream;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/download")
//public class ExcelDownloadController {
//
//    private final ExcelGenerator excelGenerator;
//
//    public ExcelDownloadController(ExcelGenerator excelGenerator) {
//        this.excelGenerator = excelGenerator;
//    }
//
//    @PostMapping("/errors")
//    public ResponseEntity<Resource> downloadErrorExcel(@RequestBody(required = false) List<Map<String, String>> errorList) {
//        if (errorList == null || errorList.isEmpty()) {
//            return ResponseEntity.badRequest().body(null); // Add log if needed
//        }
//
//        ByteArrayInputStream in = excelGenerator.generateExcel(errorList);
//        InputStreamResource file = new InputStreamResource(in);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=error_report.xlsx")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(file);
//    }
//
//    @PostMapping("/success")
//    public ResponseEntity<Resource> downloadSuccessExcel(@RequestBody List<POReferenceDTO> successList) {
//        if (successList == null || successList.isEmpty()) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        ByteArrayInputStream in = excelGenerator.generateSuccessExcel(successList);
//        InputStreamResource file = new InputStreamResource(in);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=success_report.xlsx")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(file);
//    }
//
//}
