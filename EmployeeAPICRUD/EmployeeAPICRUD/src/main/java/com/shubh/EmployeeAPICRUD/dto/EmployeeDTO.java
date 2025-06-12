package com.shubh.EmployeeAPICRUD.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {



    @NotBlank(message = "Name cannot be null or blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 to 50 characters")
    private String empName;

    @NotNull(message = "Salary is required")
    @Min(value = 7000, message = "Salary must be greater than 7000")
    private Float empSalary;

    @Min(value = 18, message = "Age must be at least 18")
    private Integer empAge;

    @NotBlank(message = "Department name is required")
    private String depName;

    private Integer depId;

    private String empAdd;

    @Pattern(regexp = "\\d{10}", message = "Mobile must be 10 digits")
    private String empMob;

    @Pattern(regexp = "\\d{12}", message = "Aadhar must be 12 digits")
    private String empAdhar;

    private String errorMessage;
}
