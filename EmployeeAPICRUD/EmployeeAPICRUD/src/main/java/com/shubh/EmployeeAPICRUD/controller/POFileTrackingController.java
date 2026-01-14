//package com.shubh.EmployeeAPICRUD.controller;
//
//import com.shubh.EmployeeAPICRUD.entity.POFileTracking;
//import com.shubh.EmployeeAPICRUD.repository.POFileTrackingRepository;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/po-tracking")
//public class POFileTrackingController {
//
//    private final POFileTrackingRepository poFileTrackingRepository;
//
//    public POFileTrackingController(POFileTrackingRepository fileTrackingRepository) {
//        this.poFileTrackingRepository = fileTrackingRepository;
//    }
//
//    @GetMapping
//    public List<POFileTracking> getAllFileTracking() {
//        return poFileTrackingRepository.findAll();
//    }
//}
