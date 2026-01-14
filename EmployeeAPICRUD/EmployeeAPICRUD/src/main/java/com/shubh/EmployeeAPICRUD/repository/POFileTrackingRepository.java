package com.shubh.EmployeeAPICRUD.repository;

import com.shubh.EmployeeAPICRUD.entity.POFileTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POFileTrackingRepository extends JpaRepository<POFileTracking,Long> {

}
