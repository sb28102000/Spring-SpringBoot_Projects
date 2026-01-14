//package com.shubh.EmployeeAPICRUD.service;
//
//import com.shubh.EmployeeAPICRUD.entity.POFileTracking;
//import com.shubh.EmployeeAPICRUD.repository.FileTrackingRepository;
//import com.shubh.EmployeeAPICRUD.repository.POFileTrackingRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class POFileTrackingService {
//
//    private final POFileTrackingRepository poFileTrackingRepository;
//
//    public POFileTrackingService(POFileTrackingRepository fileTrackingRepository) {
//        this.poFileTrackingRepository = fileTrackingRepository;
//    }
//
//    public void saveFileTracking(String fileName, int totalRecords, int processedRecords, int errorCount, String errorMessage) {
//        POFileTracking tracking = new POFileTracking();
//        tracking.setFileName(fileName);
//        tracking.setTotalRecords(totalRecords);
//        tracking.setProcessedRecords(processedRecords);
//        tracking.setErrorCount(errorCount);
//        tracking.setErrorMessage(errorMessage.isEmpty() ? "No Error" : errorMessage);
//
//        poFileTrackingRepository.save(tracking);
//    }
//}
