package com.shubh.EmployeeAPICRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponse {

    private String status;
    private String message;
}
