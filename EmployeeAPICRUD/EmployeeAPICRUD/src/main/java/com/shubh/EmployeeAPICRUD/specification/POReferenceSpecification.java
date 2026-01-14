package com.shubh.EmployeeAPICRUD.specification;

import com.shubh.EmployeeAPICRUD.entity.POReference;
import org.springframework.data.jpa.domain.Specification;

public class POReferenceSpecification {

    public static Specification<POReference> hasPoSaReferenceNumber(String number){
        return (root, query, cb) -> {
            if (number == null || number.isEmpty()) return null;
            return cb.equal(root.get("poSaReferenceNumber"), number);
        };

    }
}
