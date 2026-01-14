package com.shubh.EmployeeAPICRUD.emailService;

import com.shubh.EmployeeAPICRUD.entity.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);
}
