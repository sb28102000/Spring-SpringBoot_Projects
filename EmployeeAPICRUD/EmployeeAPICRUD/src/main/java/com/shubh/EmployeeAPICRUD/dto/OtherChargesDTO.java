package com.shubh.EmployeeAPICRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherChargesDTO {

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
    private String sgstAmtRs;
    private String cgstAmtRs;
    private String igstAmtRs;
    private String cessRate;
    private String cessAdvalAmtRs;
    private String cessNonAdvalAmtRs;
    private String stateCessRate;
    private String stateCessAdvalAmtRs;
    private String stateCessNonAdvalAmtRs;
    private String otherCharges;
}
