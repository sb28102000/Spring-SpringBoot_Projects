package com.shubh.EmployeeAPICRUD.repository;

import com.shubh.EmployeeAPICRUD.entity.OtherCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtherChargesRepository extends JpaRepository<OtherCharges,Long> {

//    @Query("SELECT oc FROM OtherCharges oc WHERE oc.poReference.poSaReferenceNumber = :poSaReferenceNumber")
//    List<OtherCharges> findByPoReferenceNumber(@Param("poSaReferenceNumber") String poSaReferenceNumber);

}
