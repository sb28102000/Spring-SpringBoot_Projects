package com.shubh.EmployeeAPICRUD.controller;

import com.shubh.EmployeeAPICRUD.dto.EmailResponse;
import com.shubh.EmployeeAPICRUD.emailService.EmailService;
import com.shubh.EmployeeAPICRUD.entity.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details){
        String status = emailService.sendSimpleMail(details);
        return status;
    }

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details){
        String status = emailService.sendMailWithAttachment(details);
        return status;
    }

//    @PostMapping("/sendMail")
//    public ResponseEntity<EmailResponse> sendMail(@RequestBody EmailDetails details){
//
//        String result = emailService.sendSimpleMail(details);
//
//        EmailResponse response = new EmailResponse();
//        if("Success".equalsIgnoreCase(result)){
//            response.setStatus("success");
//            response.setMessage("Email sent successfully.");
//            return ResponseEntity.ok(response);
//        }
//        else{
//            response.setStatus("failure");
//            response.setMessage("Email sending failed: " + result);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
}
