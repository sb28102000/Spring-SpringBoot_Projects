package com.shubh.EmployeeAPICRUD.repository;

import com.shubh.EmployeeAPICRUD.entity.OtherCharges;
import com.shubh.EmployeeAPICRUD.entity.POReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface POReferenceRepository extends JpaRepository<POReference,Long>, JpaSpecificationExecutor<POReference> {
    Optional<POReference> findByPoSaReferenceNumber(String poSaReferenceNumber);
    List<POReference> findByErrorMessageIsNotNull();
    List<POReference> findByErrorMessageIsNull();

}
