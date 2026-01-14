package com.shubh.EmployeeAPICRUD.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "filetracking")
@AllArgsConstructor
@NoArgsConstructor
public class FileTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "total")
    private Integer total;
    @Column(name = "processed")
    private Integer processed;
    @Column(name = "error")
    private Integer error;
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
}

