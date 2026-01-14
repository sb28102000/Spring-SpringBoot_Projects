package com.shubh.EmployeeAPICRUD.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "po_reference")
public class POReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Recipient Information
    private String recipientGstin;
    private String recipientLegalName;
    private String recipientTradeName;
    private String recipientAddr1;
    private String recipientAddr2;
    private String recipientLocation;
    private String recipientPinCode;
    private String recipientStateCode;
    private String recipientPhoneNumber;
    private String recipientEmailId;

    // Supplier Information
    private String supplierGstin;
    private String supplierPan;
    private String supplierAadhaar;
    private String supplierCode;
    private String supplierLegalName;
    private String supplierTradeName;
    private String supplierAddr1;
    private String supplierAddr2;
    private String supplierLocation;
    private String supplierPincode;
    private String supplierStateCode;
    private String supplierPhoneNumber;
    private String supplierEmailId;

    // PO Information
    private String pos;
    @Column(name = "po_sa_reference_number",  nullable = false, unique = true)
    private String poSaReferenceNumber;
    private String poSaReferenceDate;
    private String poSaValidityDate;
    private String poOtherReferenceNumber;
    private String poOtherReferenceDate;
    private String deliveryStartDate;
    private String deliveryEndDate;
    private String deliveryTerms;
    private String salesOrderNumber;
    private String salesOrderDate;
    private String companyCode;
    private String purchasingOrganisation;
    private String purchaseGroup;
    private String incoterms;
    private String incoterms2;
    private String currency;
    private String paymentInDays;
    private String paymentInDaysNet;
    private String exchangeRate;
    private String fixExchangeRate;
    private String grMessage;
    private String country;
    private String collectiveNumber;
    private String warrantyDays;
    private String consignmentNetPrice;
    private String retention;
    private String downpaymentCategory;
    private String incotermsVersion;
    private String incotermsLocation1;
    private String incotermsLocation2;
    private String shiptoGstin;
    private String shiptoLegalName;
    private String shiptoTradeName;
    private String shiptoAddr1;
    private String shiptoAddr2;
    private String shiptoLocation;
    private String shiptoPinCode;
    private String shiptoStateCode;
    private String plantCode;

    // Item Total and Tax Information
    private String itemTotal;
    private String orderLineReference;
    private String originCountry;
    private String uniqueItemSlNo;
    private String deliveryScheduleIdentifier;
    private String quantity;
    private String itemDeliveryStartDate;
    private String itemDeliveryEndDate;
    private String batchName;
    private String batchExpiryDate;
    private String warrantyDate;
    private String attributeDetails;
    private String attributeValue;
    private String totalTaxableValue;
    private String totalSgstAmt;
    private String totalCgstAmt;
    private String totalIgstAmt;
    private String totalCessAmt;
    private String totalStateCessAmt;
    private String totalDiscount;
    private String totalOtherCharges;
    private String totalRoundOff;
    private String totalDocumentValue;
    private String totalInvoiceValueInForeignCurrency;

    // Payment Information
    private String payeeName;
    private String accountNumber;
    private String paymentMode;
    private String branchIfscCode;
    private String termsOfPayment;
    private String paymentInstruction;
    private String creditTransfer;
    private String directDebit;
    private String creditDays;
    private String paidAmount;
    private String dueAmount;

    // Reference Information
    private String tendRefr;
    private String contrRefr;
    private String extRefr;
    private String projRefr;
    private String url;
    private String docs;
    private String info;

    // Amend PO Information
    private String amendPo;
    private String siteCode;
    private String department;
    private String location;

    // User Defined Fields
    private String userDefinedField1;
    private String userDefinedField2;
    private String userDefinedField3;
    private String userDefinedField4;
    private String userDefinedField5;
    private String userDefinedField6;
    private String userDefinedField7;
    private String userDefinedField8;
    private String userDefinedField9;
    private String userDefinedField10;
    private String userDefinedField11;
    private String userDefinedField12;
    private String userDefinedField13;
    private String userDefinedField14;
    private String userDefinedField15;
    @Column(name = "total_quantity")
    private Double totalQuantity;


    @OneToMany(mappedBy = "poReference", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<OtherCharges> otherChargesList;

    private String errorMessage;

//    private List<String> errorMsg = new ArrayList<>();



}
