//package com.shubh.EmployeeAPICRUD.util;
//
//import com.shubh.EmployeeAPICRUD.dto.OtherChargesDTO;
//import com.shubh.EmployeeAPICRUD.dto.POReferenceDTO;
//import com.shubh.EmployeeAPICRUD.entity.OtherCharges;
//import com.shubh.EmployeeAPICRUD.entity.POReference;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class POReferenceMapper {
//
//    private static OtherChargesMapper otherChargesMapper;
//
//    @Autowired
//    public POReferenceMapper(OtherChargesMapper otherChargesMapper) {
//        this.otherChargesMapper = otherChargesMapper;
//    }
//
//    public static POReferenceDTO toDTO(POReference entity) {
//        if (entity == null) {
//            return null;
//        }
//        POReferenceDTO dto = new POReferenceDTO();
//        dto.setRecipientGstin(entity.getRecipientGstin());
//        dto.setRecipientLegalName(entity.getRecipientLegalName());
//        dto.setRecipientTradeName(entity.getRecipientTradeName());
//        dto.setRecipientAddr1(entity.getRecipientAddr1());
//        dto.setRecipientAddr2(entity.getRecipientAddr2());
//        dto.setRecipientLocation(entity.getRecipientLocation());
//        dto.setRecipientPinCode(entity.getRecipientPinCode());
//        dto.setRecipientStateCode(entity.getRecipientStateCode());
//        dto.setRecipientPhoneNumber(entity.getRecipientPhoneNumber());
//        dto.setRecipientEmailId(entity.getRecipientEmailId());
//        dto.setSupplierGstin(entity.getSupplierGstin());
//        dto.setSupplierPan(entity.getSupplierPan());
//        dto.setSupplierAadhaar(entity.getSupplierAadhaar());
//        dto.setSupplierCode(entity.getSupplierCode());
//        dto.setSupplierLegalName(entity.getSupplierLegalName());
//        dto.setSupplierTradeName(entity.getSupplierTradeName());
//        dto.setSupplierAddr1(entity.getSupplierAddr1());
//        dto.setSupplierAddr2(entity.getSupplierAddr2());
//        dto.setSupplierLocation(entity.getSupplierLocation());
//        dto.setSupplierPincode(entity.getSupplierPincode());
//        dto.setSupplierStateCode(entity.getSupplierStateCode());
//        dto.setSupplierPhoneNumber(entity.getSupplierPhoneNumber());
//        dto.setSupplierEmailId(entity.getSupplierEmailId());
//        dto.setPos(entity.getPos());
//        dto.setPoSaReferenceNumber(entity.getPoSaReferenceNumber());
//        dto.setPoSaReferenceDate(entity.getPoSaReferenceDate());
//        dto.setPoSaValidityDate(entity.getPoSaValidityDate());
//        dto.setPoOtherReferenceNumber(entity.getPoOtherReferenceNumber());
//        dto.setPoOtherReferenceDate(entity.getPoOtherReferenceDate());
//        dto.setDeliveryStartDate(entity.getDeliveryStartDate());
//        dto.setDeliveryEndDate(entity.getDeliveryEndDate());
//        dto.setDeliveryTerms(entity.getDeliveryTerms());
//        dto.setSalesOrderNumber(entity.getSalesOrderNumber());
//        dto.setSalesOrderDate(entity.getSalesOrderDate());
//        dto.setCompanyCode(entity.getCompanyCode());
//        dto.setPurchasingOrganisation(entity.getPurchasingOrganisation());
//        dto.setPurchaseGroup(entity.getPurchaseGroup());
//        dto.setIncoterms(entity.getIncoterms());
//        dto.setIncoterms2(entity.getIncoterms2());
//        dto.setCurrency(entity.getCurrency());
//        dto.setPaymentInDays(entity.getPaymentInDays());
//        dto.setPaymentInDaysNet(entity.getPaymentInDaysNet());
//        dto.setExchangeRate(entity.getExchangeRate());
//        dto.setFixExchangeRate(entity.getFixExchangeRate());
//        dto.setGrMessage(entity.getGrMessage());
//        dto.setCountry(entity.getCountry());
//        dto.setCollectiveNumber(entity.getCollectiveNumber());
//        dto.setWarrantyDays(entity.getWarrantyDays());
//        dto.setConsignmentNetPrice(entity.getConsignmentNetPrice());
//        dto.setRetention(entity.getRetention());
//        dto.setDownpaymentCategory(entity.getDownpaymentCategory());
//        dto.setIncotermsVersion(entity.getIncotermsVersion());
//        dto.setIncotermsLocation1(entity.getIncotermsLocation1());
//        dto.setIncotermsLocation2(entity.getIncotermsLocation2());
//        dto.setShiptoGstin(entity.getShiptoGstin());
//        dto.setShiptoLegalName(entity.getShiptoLegalName());
//        dto.setShiptoTradeName(entity.getShiptoTradeName());
//        dto.setShiptoAddr1(entity.getShiptoAddr1());
//        dto.setShiptoAddr2(entity.getShiptoAddr2());
//        dto.setShiptoLocation(entity.getShiptoLocation());
//        dto.setShiptoPinCode(entity.getShiptoPinCode());
//        dto.setShiptoStateCode(entity.getShiptoStateCode());
//        dto.setPlantCode(entity.getPlantCode());
//
//        dto.setItemTotal(entity.getItemTotal());
//        dto.setOrderLineReference(entity.getOrderLineReference());
//        dto.setOriginCountry(entity.getOriginCountry());
//        dto.setUniqueItemSlNo(entity.getUniqueItemSlNo());
//        dto.setDeliveryScheduleIdentifier(entity.getDeliveryScheduleIdentifier());
//        dto.setQuantity(entity.getQuantity());
//        dto.setBatchName(entity.getBatchName());
//        dto.setBatchExpiryDate(entity.getBatchExpiryDate());
//        dto.setWarrantyDate(entity.getWarrantyDate());
//        dto.setAttributeDetails(entity.getAttributeDetails());
//        dto.setAttributeValue(entity.getAttributeValue());
//        dto.setTotalTaxableValue(entity.getTotalTaxableValue());
//        dto.setTotalSgstAmount(entity.getTotalSgstAmt());
//        dto.setTotalCgstAmount(entity.getTotalCgstAmt());
//        dto.setTotalIgstAmount(entity.getTotalIgstAmt());
//        dto.setTotalCessAmount(entity.getTotalCessAmt());
//        dto.setTotalStateCessAmount(entity.getTotalStateCessAmt());
//        dto.setTotalDiscount(entity.getTotalDiscount());
//        dto.setTotalOtherCharges(entity.getTotalOtherCharges());
//        dto.setTotalRoundOff(entity.getTotalRoundOff());
//        dto.setTotalDocumentValue(entity.getTotalDocumentValue());
//        dto.setTotalInvoiceValueForeignCurrency(entity.getTotalInvoiceValueInForeignCurrency());
//        dto.setPayeeName(entity.getPayeeName());
//        dto.setAccountNumber(entity.getAccountNumber());
//        dto.setMode(entity.getPaymentMode());
//        dto.setBranchIfscCode(entity.getBranchIfscCode());
//        dto.setTermsOfPayment(entity.getTermsOfPayment());
//        dto.setPaymentInstruction(entity.getPaymentInstruction());
//        dto.setCreditTransfer(String.valueOf(entity.getCreditTransfer()));
//        dto.setDirectDebit(String.valueOf(entity.getDirectDebit()));
//        dto.setCreditDays(entity.getCreditDays());
//        dto.setPaidAmount(entity.getPaidAmount());
//        dto.setDueAmount(entity.getDueAmount());
//        dto.setTendRefr(entity.getTendRefr());
//        dto.setContrRefr(entity.getContrRefr());
//        dto.setExtRefr(entity.getExtRefr());
//        dto.setProjRefr(entity.getProjRefr());
//        dto.setUrl(entity.getUrl());
//        dto.setDocs(entity.getDocs());
//        dto.setInfo(entity.getInfo());
//        dto.setAmendPo(String.valueOf(entity.getAmendPo()));
//        dto.setSiteCode(entity.getSiteCode());
//        dto.setDepartment(entity.getDepartment());
//        dto.setLocation(entity.getLocation());
//
//        // Mapping User Defined Fields
//        dto.setUserDefinedField1(entity.getUserDefinedField1());
//        dto.setUserDefinedField2(entity.getUserDefinedField2());
//        dto.setUserDefinedField3(entity.getUserDefinedField3());
//        dto.setUserDefinedField4(entity.getUserDefinedField4());
//        dto.setUserDefinedField5(entity.getUserDefinedField5());
//        dto.setUserDefinedField6(entity.getUserDefinedField6());
//        dto.setUserDefinedField7(entity.getUserDefinedField7());
//        dto.setUserDefinedField8(entity.getUserDefinedField8());
//        dto.setUserDefinedField9(entity.getUserDefinedField9());
//        dto.setUserDefinedField10(entity.getUserDefinedField10());
//        dto.setUserDefinedField11(entity.getUserDefinedField11());
//        dto.setUserDefinedField12(entity.getUserDefinedField12());
//        dto.setUserDefinedField13(entity.getUserDefinedField13());
//        dto.setUserDefinedField14(entity.getUserDefinedField14());
//        dto.setUserDefinedField15(entity.getUserDefinedField15());
//
//
//
//        // Map OtherCharges list to DTO
//        if (entity.getOtherChargesList() != null) {
//            List<OtherChargesDTO> otherChargesDTOList = entity.getOtherChargesList()
//                    .stream()
//                    .map(otherChargesMapper::toDTO)
//                    .collect(Collectors.toList());
//            dto.setOtherChargesList(otherChargesDTOList);
//        }
//        return dto;
//    }
//
//    public static POReference toEntity(POReferenceDTO dto) {
//        if (dto == null) {
//            return null;
//        }
//        POReference entity = new POReference();
//        entity.setRecipientGstin(dto.getRecipientGstin());
//        entity.setRecipientLegalName(dto.getRecipientLegalName());
//        entity.setRecipientTradeName(dto.getRecipientTradeName());
//        entity.setRecipientAddr1(dto.getRecipientAddr1());
//        entity.setRecipientAddr2(dto.getRecipientAddr2());
//        entity.setRecipientLocation(dto.getRecipientLocation());
//        entity.setRecipientPinCode(dto.getRecipientPinCode());
//        entity.setRecipientStateCode(dto.getRecipientStateCode());
//        entity.setRecipientPhoneNumber(dto.getRecipientPhoneNumber());
//        entity.setRecipientEmailId(dto.getRecipientEmailId());
//        entity.setSupplierGstin(dto.getSupplierGstin());
//        entity.setSupplierPan(dto.getSupplierPan());
//        entity.setSupplierAadhaar(dto.getSupplierAadhaar());
//        entity.setSupplierCode(dto.getSupplierCode());
//        entity.setSupplierLegalName(dto.getSupplierLegalName());
//        entity.setSupplierTradeName(dto.getSupplierTradeName());
//        entity.setSupplierAddr1(dto.getSupplierAddr1());
//        entity.setSupplierAddr2(dto.getSupplierAddr2());
//        entity.setSupplierLocation(dto.getSupplierLocation());
//        entity.setSupplierPincode(dto.getSupplierPincode());
//        entity.setSupplierStateCode(dto.getSupplierStateCode());
//        entity.setSupplierPhoneNumber(dto.getSupplierPhoneNumber());
//        entity.setSupplierEmailId(dto.getSupplierEmailId());
//        entity.setPos(dto.getPos());
//        entity.setPoSaReferenceNumber(dto.getPoSaReferenceNumber());
//        entity.setPoSaReferenceDate(dto.getPoSaReferenceDate());
//        entity.setPoSaValidityDate(dto.getPoSaValidityDate());
//        entity.setPoOtherReferenceNumber(dto.getPoOtherReferenceNumber());
//        entity.setPoOtherReferenceDate(dto.getPoOtherReferenceDate());
//        entity.setDeliveryStartDate(dto.getDeliveryStartDate());
//        entity.setDeliveryEndDate(dto.getDeliveryEndDate());
//        entity.setDeliveryTerms(dto.getDeliveryTerms());
//        entity.setSalesOrderNumber(dto.getSalesOrderNumber());
//        entity.setSalesOrderDate(dto.getSalesOrderDate());
//        entity.setCompanyCode(dto.getCompanyCode());
//        entity.setPurchasingOrganisation(dto.getPurchasingOrganisation());
//        entity.setPurchaseGroup(dto.getPurchaseGroup());
//        entity.setIncoterms(dto.getIncoterms());
//        entity.setIncoterms2(dto.getIncoterms2());
//        entity.setCurrency(dto.getCurrency());
//        entity.setPaymentInDays(dto.getPaymentInDays());
//        entity.setPaymentInDaysNet(dto.getPaymentInDaysNet());
//        entity.setExchangeRate(dto.getExchangeRate());
//        entity.setFixExchangeRate(dto.getFixExchangeRate());
//        entity.setGrMessage(dto.getGrMessage());
//        entity.setCountry(dto.getCountry());
//        entity.setCollectiveNumber(dto.getCollectiveNumber());
//        entity.setWarrantyDays(dto.getWarrantyDays());
//        entity.setConsignmentNetPrice(dto.getConsignmentNetPrice());
//        entity.setRetention(dto.getRetention());
//        entity.setDownpaymentCategory(dto.getDownpaymentCategory());
//        entity.setIncotermsVersion(dto.getIncotermsVersion());
//        entity.setIncotermsLocation1(dto.getIncotermsLocation1());
//        entity.setIncotermsLocation2(dto.getIncotermsLocation2());
//        entity.setShiptoGstin(dto.getShiptoGstin());
//        entity.setShiptoLegalName(dto.getShiptoLegalName());
//        entity.setShiptoTradeName(dto.getShiptoTradeName());
//        entity.setShiptoAddr1(dto.getShiptoAddr1());
//        entity.setShiptoAddr2(dto.getShiptoAddr2());
//        entity.setShiptoLocation(dto.getShiptoLocation());
//        entity.setShiptoPinCode(dto.getShiptoPinCode());
//        entity.setShiptoStateCode(dto.getShiptoStateCode());
//        entity.setPlantCode(dto.getPlantCode());
//
//        entity.setItemTotal(dto.getItemTotal());
//        entity.setOrderLineReference(dto.getOrderLineReference());
//        entity.setOriginCountry(dto.getOriginCountry());
//        entity.setUniqueItemSlNo(dto.getUniqueItemSlNo());
//        entity.setDeliveryScheduleIdentifier(dto.getDeliveryScheduleIdentifier());
//        entity.setQuantity(dto.getQuantity());
//        entity.setBatchName(dto.getBatchName());
//        entity.setBatchExpiryDate(dto.getBatchExpiryDate());
//        entity.setWarrantyDate(dto.getWarrantyDate());
//        entity.setAttributeDetails(dto.getAttributeDetails());
//        entity.setAttributeValue(dto.getAttributeValue());
//
//        entity.setTotalTaxableValue(dto.getTotalTaxableValue());
//        entity.setTotalSgstAmt(dto.getTotalSgstAmount());
//        entity.setTotalCgstAmt(dto.getTotalCgstAmount());
//        entity.setTotalIgstAmt(dto.getTotalIgstAmount());
//        entity.setTotalCessAmt(dto.getTotalCessAmount());
//        entity.setTotalStateCessAmt(dto.getTotalStateCessAmount());
//        entity.setTotalDiscount(dto.getTotalDiscount());
//        entity.setTotalOtherCharges(dto.getTotalOtherCharges());
//        entity.setTotalRoundOff(dto.getTotalRoundOff());
//        entity.setTotalDocumentValue(dto.getTotalDocumentValue());
//        entity.setTotalInvoiceValueInForeignCurrency(dto.getTotalInvoiceValueForeignCurrency());
//
//        entity.setPayeeName(dto.getPayeeName());
//        entity.setAccountNumber(dto.getAccountNumber());
//        entity.setPaymentMode(dto.getMode());
//        entity.setBranchIfscCode(dto.getBranchIfscCode());
//        entity.setTermsOfPayment(dto.getTermsOfPayment());
//        entity.setPaymentInstruction(dto.getPaymentInstruction());
//        entity.setCreditTransfer(String.valueOf(dto.getCreditTransfer()));
//        entity.setDirectDebit(String.valueOf(dto.getDirectDebit()));
//        entity.setCreditDays(dto.getCreditDays());
//        entity.setPaidAmount(dto.getPaidAmount());
//        entity.setDueAmount(dto.getDueAmount());
//        entity.setTendRefr(dto.getTendRefr());
//        entity.setContrRefr(dto.getContrRefr());
//        entity.setExtRefr(dto.getExtRefr());
//        entity.setProjRefr(dto.getProjRefr());
//        entity.setUrl(dto.getUrl());
//        entity.setDocs(dto.getDocs());
//        entity.setInfo(dto.getInfo());
//        entity.setAmendPo(String.valueOf(dto.getAmendPo()));
//        entity.setSiteCode(dto.getSiteCode());
//        entity.setDepartment(dto.getDepartment());
//        entity.setLocation(dto.getLocation());
//
//        // User Defined Fields
//        entity.setUserDefinedField1(dto.getUserDefinedField1());
//        entity.setUserDefinedField2(dto.getUserDefinedField2());
//        entity.setUserDefinedField3(dto.getUserDefinedField3());
//        entity.setUserDefinedField4(dto.getUserDefinedField4());
//        entity.setUserDefinedField5(dto.getUserDefinedField5());
//        entity.setUserDefinedField6(dto.getUserDefinedField6());
//        entity.setUserDefinedField7(dto.getUserDefinedField7());
//        entity.setUserDefinedField8(dto.getUserDefinedField8());
//        entity.setUserDefinedField9(dto.getUserDefinedField9());
//        entity.setUserDefinedField10(dto.getUserDefinedField10());
//        entity.setUserDefinedField11(dto.getUserDefinedField11());
//        entity.setUserDefinedField12(dto.getUserDefinedField12());
//        entity.setUserDefinedField13(dto.getUserDefinedField13());
//        entity.setUserDefinedField14(dto.getUserDefinedField14());
//        entity.setUserDefinedField15(dto.getUserDefinedField15());
//
//
//
//        // Map OtherChargesDTO list to Entity
//        if (dto.getOtherChargesList() != null) {
//            List<OtherCharges> otherChargesList = dto.getOtherChargesList()
//                    .stream()
//                    .map(otherChargesMapper::toEntity)
//                    .collect(Collectors.toList());
//            entity.setOtherChargesList(otherChargesList);
//        }
//        return entity;
//    }
//}
