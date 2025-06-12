package com.shubh.EmployeeAPICRUD.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "other_charges")
public class OtherCharges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String slNo;
    private String productDescription;
    private String isService;
    private String hsnCode;
    private String itemBuyerReferenceNumber;
    private String itemSellerReferenceNumber;
    private String barCode;
    private String quantity;
    private String freeQuantity;
    private String unit;
    private String unitPrice;
    private String grossAmount;
    private String discount;
    private String preTaxValue;
    private String taxableValue;
    private String gstRate;
    private String sgstAmt;
    private String cgstAmt;
    private String igstAmt;
    private String cessRate;
    private String cessAmtAdval;
    private String cessNonAdvalAmt;
    private String stateCessRate;
    private String stateCessAdvalAmt;
    private String stateCessNonAdvalAmt;
    private String otherCharges;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_sa_reference_number", referencedColumnName = "po_sa_reference_number")
    @JsonIgnore
    private POReference poReference;

}
