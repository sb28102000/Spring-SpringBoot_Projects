package com.shubh.EmployeeAPICRUD.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "po_filetracking")
public class POFileTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;
    @Column(name = "total_records")
    private int totalRecords;
    @Column(name = "processed_records")
    private int processedRecords;
    @Column(name = "error_count")
    private int errorCount;
    @Column(name = "error_message")
    private String errorMsg;

}
