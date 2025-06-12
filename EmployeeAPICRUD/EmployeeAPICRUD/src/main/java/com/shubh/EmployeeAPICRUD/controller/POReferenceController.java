package com.shubh.EmployeeAPICRUD.controller;


import com.shubh.EmployeeAPICRUD.dto.POReferenceDTO;
import com.shubh.EmployeeAPICRUD.entity.GridResponse;
import com.shubh.EmployeeAPICRUD.entity.POReference;
import com.shubh.EmployeeAPICRUD.service.PoReference_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/po-reference")
@Slf4j
@Validated
public class POReferenceController {

    @Autowired
    private final PoReference_Service poReference_service;


    @Autowired
    private PoReference_Service.ExcelDownloadService excelDownloadService;


    public POReferenceController(PoReference_Service poReference_service) {
        this.poReference_service = poReference_service;
    }


//  Correct code: Upload Excel-File
    @PostMapping("/uploadExcel")
    public ResponseEntity<?> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        // Check if file is empty or not Excel

        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
            return ResponseEntity.badRequest().body(Map.of("Message","Please upload a valid Excel file."));
        }

        try {
            poReference_service.processPOReferenceExcel(file);
            return ResponseEntity.ok().body(Map.of("Message","File processed successfully."));
        } catch (Exception e) {
            // Log exception (not shown here for brevity)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("Message","Error processing the file: " + e.getMessage()));
        }
    }


//  Correct Code: Upload JSON-File
    @PostMapping("/upload-json")
    public ResponseEntity<Map<String, String>> uploadPOReferenceJson(@RequestParam("file") MultipartFile file) {
        try {
            List<POReferenceDTO> processedDtos = poReference_service.processPOReferenceJson(file);
            return ResponseEntity.ok().body(Map.of("Message", "JSON file processed successfully with " + processedDtos.size() + " records."));
        } catch (IOException e) {
            log.error("Error while processing JSON file: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Message","Failed to process JSON file: " + e.getMessage()));
        }
    }

//  Download Error Records
    @GetMapping("/download/errors")
    public ResponseEntity<InputStreamResource> downloadErrorExcel() {
        ByteArrayInputStream in = excelDownloadService.generateErrorExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=errors.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }


//  Download Success Records
    @GetMapping("/download/success")
    public ResponseEntity<InputStreamResource> downloadSuccessExcel() {
        ByteArrayInputStream in = excelDownloadService.generateSuccessExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=success.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }


//  Getting Data using PoSaReferenceNumber
    @GetMapping("/{poSaReferenceNumber}")
    public ResponseEntity<?> getPOReferenceByPoSaReferenceNumber(@PathVariable String poSaReferenceNumber) {
        Optional<POReference> poReference = poReference_service.getPOReferenceByPoSaReferenceNumber(poSaReferenceNumber);

        if (poReference.isPresent()) {
            return ResponseEntity.ok(poReference);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Message","PO Reference with PO/SA Reference Number " + poSaReferenceNumber + " not found."));
        }
    }


//  Using Pageable Method
    @GetMapping
    public Page<POReference> getAllPOReference(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return poReference_service.findAll(pageable);
    }


//    Using Specification Method
    @GetMapping("/filter")
    public GridResponse<POReference> getPOReferences(
            @RequestParam(required = false) String poSaReferenceNumber,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size)  {
        Page<POReference> pageResult = poReference_service.filterPOReference(poSaReferenceNumber, page, size);
        return new GridResponse<>(pageResult);
    }


//  Using Predicate Method
    @GetMapping("/filter-predicate")
    public Page<POReference> filterPOReferences(
            @RequestParam(required = false) String poSaReferenceNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return poReference_service.filterPOReferences(poSaReferenceNumber, page, size);
    }






























//    @GetMapping("/filter")
//    public Page<POReference> getPOReferences(
//            @RequestParam(required = false) String poSaReferenceNumber,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return poReference_service.filterPOReference(poSaReferenceNumber, page, size);
//    }



//    @GetMapping("/{poSaReferenceNumber}")
//    public ResponseEntity<POReferenceDTO> getPOReferenceByNumber(@PathVariable String poSaReferenceNumber) {
//        Optional<POReference> dto = poReference_service.getPOReferenceByPoSaReferenceNumber(poSaReferenceNumber);
//        return ResponseEntity.ok(poReferenceDTO);
//    }
}

//    @GetMapping("/download/{status}")
//    public ResponseEntity<InputStreamResource> downloadPOReferenceByStatus(@PathVariable String status) throws IOException {
//        List<POReference> records;
//
//        if (status.equalsIgnoreCase("success")) {
//            records = poReferenceRepository.findByPoSaReferenceNumber("success");
//        } else if (status.equalsIgnoreCase("error")) {
//            records = poReferenceRepository.findByPoSaReferenceNumber("error");
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//
//        ByteArrayInputStream in = ExcelGenerator.generatePOReferenceExcel(records);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=POReference_" + status + ".xlsx");
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(new InputStreamResource(in));
//    }

//    @GetMapping("/download/{poSaReferenceNumber}")
//    public ResponseEntity<InputStreamResource> downloadByReferenceNumberAndStatus(
//            @PathVariable String poSaReferenceNumber,
//            @PathVariable String status) throws IOException {
//
//        List<POReference> records;
//        if (status.equalsIgnoreCase("all")) {
//            records = poReferenceRepository.findByPoSaReferenceNumber(poSaReferenceNumber);
//        } else {
//            return (ResponseEntity<InputStreamResource>) poReferenceRepository.findByPoSaReferenceNumber(poSaReferenceNumber);
//        }
//
//        if (records.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        ByteArrayInputStream in = ExcelGenerator.generatePOReferenceExcel(records);
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.add("Content-Disposition", "attachment; filename=POReference_" + poSaReferenceNumber + "_" + status + ".xlsx");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(new InputStreamResource(in));
//    }




//    @GetMapping("/download/success")
//    public ResponseEntity<InputStreamResource> downloadSuccess() throws IOException {
//        ByteArrayInputStream in = ExcelGenerator.generateExcel(poReference_service.getSuccessList(), false);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=success.xlsx")
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(new InputStreamResource(in));
//    }
//
//
//    @GetMapping("/download/errors")
//    public ResponseEntity<InputStreamResource> downloadErrors() throws IOException {
//        ByteArrayInputStream in = ExcelGenerator.generateExcel(poReference_service.getErrorList(), true);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=errors.xlsx")
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(new InputStreamResource(in));
//    }

//    @PostMapping("/download/success")
//    public ResponseEntity<Resource> downloadSuccessExcel(@RequestBody List<POReferenceDTO> successList) {
//        if (successList == null || successList.isEmpty()) {
//            return ResponseEntity.badRequest().build();
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
//    // ERROR EXCEL DOWNLOAD
//    @PostMapping("/download/errors")
//    public ResponseEntity<Resource> downloadErrorExcel(@RequestBody List<Map<String, String>> errorList) {
//        if (errorList == null || errorList.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        ByteArrayInputStream in = excelGenerator.generateErrorExcel(errorList);
//        InputStreamResource file = new InputStreamResource(in);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=error_report.xlsx")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(file);
//    }



//    @GetMapping("/download/errors")
//    public ResponseEntity<ByteArrayResource> downloadErrorExcel(@PathVariable String fileName) throws IOException {
//        byte[] data = poFileDownloadService.generateErrorExcel(fileName);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + "_errors.xlsx")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(new ByteArrayResource(data));
//    }





