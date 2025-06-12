//package com.shubh.EmployeeAPICRUD.service;
//
//import com.shubh.EmployeeAPICRUD.dto.OtherChargesDTO;
//import com.shubh.EmployeeAPICRUD.dto.POReferenceDTO;
//import com.shubh.EmployeeAPICRUD.entity.OtherCharges;
//
//import com.shubh.EmployeeAPICRUD.entity.POReference;
//
//import com.shubh.EmployeeAPICRUD.repository.OtherChargesRepository;
//import com.shubh.EmployeeAPICRUD.repository.POReferenceRepository;
//import jakarta.transaction.Transactional;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Service
//public class POReferenceService {
//
//
//    @Autowired
//    private POReferenceRepository poReferenceRepository;
//
//    @Autowired
//    private OtherChargesRepository otherChargesRepository;
//
//    public POReference getPOReferenceByNumber(String poSaReferenceNumber) {
//        return poReferenceRepository.findByPoSaReferenceNumber(poSaReferenceNumber);
//    }
//
//    public List<OtherCharges> getOtherChargesByPoReferenceNumber(String poSaReferenceNumber) {
//        return otherChargesRepository.findByPoReferenceNumber(poSaReferenceNumber);
//    }
//
//
//
//    @Transactional
//    public List<POReferenceDTO> processPOReferenceExcel(InputStream inputStream) throws IOException {
//        List<POReferenceDTO> poReferenceDTOList = new ArrayList<>();
//
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> rows = sheet.iterator();
//
//        rows.next(); // Skip header row
//
//        Map<String, POReference> poReferenceCache = new HashMap<>();
//
//        while (rows.hasNext()) {
//            Row row = rows.next();
//
//            POReferenceDTO poDto = new POReferenceDTO();
//                // Map POReference fields from Excel columns
//                // Adjust column indexes as per your Excel structure
//                poDto.setRecipientGstin(getCellStringValue(row.getCell(0)));
//                poDto.setRecipientLegalName(getCellStringValue(row.getCell(1)));
//                poDto.setRecipientTradeName(getCellStringValue(row.getCell(2)));
//                poDto.setRecipientAddr1(getCellStringValue(row.getCell(3)));
//                poDto.setRecipientAddr2(getCellStringValue(row.getCell(4)));
//                poDto.setRecipientLocation(getCellStringValue(row.getCell(5)));
//                poDto.setRecipientPinCode(getCellStringValue(row.getCell(6)));
//                poDto.setRecipientStateCode(getCellStringValue(row.getCell(7)));
//                poDto.setRecipientPhoneNumber(getCellStringValue(row.getCell(8)));
//                poDto.setRecipientEmailId(getCellStringValue(row.getCell(9)));
//
//                poDto.setSupplierGstin(getCellStringValue(row.getCell(10)));
//                poDto.setSupplierPan(getCellStringValue(row.getCell(11)));
//                poDto.setSupplierAadhaar(getCellStringValue(row.getCell(12)));
//                poDto.setSupplierCode(getCellStringValue(row.getCell(13)));
//                poDto.setSupplierLegalName(getCellStringValue(row.getCell(14)));
//                poDto.setSupplierTradeName(getCellStringValue(row.getCell(15)));
//                poDto.setSupplierAddr1(getCellStringValue(row.getCell(16)));
//                poDto.setSupplierAddr2(getCellStringValue(row.getCell(17)));
//                poDto.setSupplierLocation(getCellStringValue(row.getCell(18)));
//                poDto.setSupplierPincode(getCellStringValue(row.getCell(19)));
//                poDto.setSupplierStateCode(getCellStringValue(row.getCell(20)));
//                poDto.setSupplierPhoneNumber(getCellStringValue(row.getCell(21)));
//                poDto.setSupplierEmailId(getCellStringValue(row.getCell(22)));
//
//                poDto.setPos(getCellStringValue(row.getCell(23)));
//                poDto.setPoSaReferenceNumber(getCellStringValue(row.getCell(24)));
//                poDto.setPoSaReferenceDate(getCellDateValue(row.getCell(25)));
//                poDto.setPoSaValidityDate(getCellDateValue(row.getCell(26)));
//                poDto.setPoOtherReferenceNumber(getCellStringValue(row.getCell(27)));
//                poDto.setPoOtherReferenceDate(getCellDateValue(row.getCell(28)));
//                poDto.setDeliveryStartDate(getCellDateValue(row.getCell(29)));
//                poDto.setDeliveryEndDate(getCellDateValue(row.getCell(30)));
//                poDto.setDeliveryTerms(getCellStringValue(row.getCell(31)));
//                poDto.setSalesOrderNumber(getCellStringValue(row.getCell(32)));
//                poDto.setSalesOrderDate(getCellDateValue(row.getCell(33)));
//                poDto.setCompanyCode(getCellStringValue(row.getCell(34)));
//                poDto.setPurchasingOrganisation(getCellStringValue(row.getCell(35)));
//                poDto.setPurchaseGroup(getCellStringValue(row.getCell(36)));
//                poDto.setIncoterms(getCellStringValue(row.getCell(37)));
//                poDto.setIncoterms2(getCellStringValue(row.getCell(38)));
//                poDto.setCurrency(getCellStringValue(row.getCell(39)));
//                poDto.setPaymentInDays(getCellIntegerValue(row.getCell(40)));
//                poDto.setPaymentInDaysNet(getCellIntegerValue(row.getCell(41)));
//                poDto.setExchangeRate(getCellBooleanValue(row.getCell(42)));
//                poDto.setFixExchangeRate(getCellBooleanValue(row.getCell(43)));
//                poDto.setGrMessage(getCellStringValue(row.getCell(44)));
//                poDto.setCountry(getCellStringValue(row.getCell(45)));
//                poDto.setCollectiveNumber(getCellStringValue(row.getCell(46)));
//                poDto.setWarrantyDays(getCellIntegerValue(row.getCell(47)));
//                poDto.setConsignmentNetPrice(getCellStringValue(row.getCell(48)));
//                poDto.setRetention(getCellStringValue(row.getCell(49)));
//                poDto.setDownpaymentCategory(getCellStringValue(row.getCell(50)));
//                poDto.setIncotermsVersion(getCellStringValue(row.getCell(51)));
//                poDto.setIncotermsLocation1(getCellStringValue(row.getCell(52)));
//                poDto.setIncotermsLocation2(getCellStringValue(row.getCell(53)));
//                poDto.setShiptoGstin(getCellStringValue(row.getCell(54)));
//                poDto.setShiptoLegalName(getCellStringValue(row.getCell(55)));
//                poDto.setShiptoTradeName(getCellStringValue(row.getCell(56)));
//                poDto.setShiptoAddr1(getCellStringValue(row.getCell(57)));
//                poDto.setShiptoAddr2(getCellStringValue(row.getCell(58)));
//                poDto.setShiptoLocation(getCellStringValue(row.getCell(59)));
//                poDto.setShiptoPinCode(getCellStringValue(row.getCell(60)));
//                poDto.setShiptoStateCode(getCellStringValue(row.getCell(61)));
//                poDto.setPlantCode(getCellStringValue(row.getCell(62)));
//
//                poDto.setItemTotal(getCellStringValue(row.getCell(89)));
//                poDto.setOrderLineReference(getCellStringValue(row.getCell(90)));
//                poDto.setOriginCountry(getCellStringValue(row.getCell(91)));
//                poDto.setUniqueItemSlNo(getCellStringValue(row.getCell(92)));
//                poDto.setDeliveryScheduleIdentifier(getCellStringValue(row.getCell(93)));
//                poDto.setQuantity(getCellIntegerValue(row.getCell(94)));
//                poDto.setBatchName(getCellDateValue(row.getCell(97)));
//                poDto.setBatchExpiryDate(getCellDateValue(row.getCell(98)));
//                poDto.setWarrantyDate(getCellDateValue(row.getCell(99)));
//                poDto.setAttributeDetails(getCellStringValue(row.getCell(100)));
//                poDto.setAttributeValue(getCellStringValue(row.getCell(101)));
//                poDto.setTotalTaxableValue(getCellStringValue(row.getCell(102)));
//                poDto.setTotalSgstAmount(getCellStringValue(row.getCell(103)));
//                poDto.setTotalCgstAmount(getCellStringValue(row.getCell(104)));
//                poDto.setTotalIgstAmount(getCellStringValue(row.getCell(105)));
//                poDto.setTotalCessAmount(getCellStringValue(row.getCell(106)));
//                poDto.setTotalStateCessAmount(getCellStringValue(row.getCell(107)));
//                poDto.setTotalDiscount(getCellStringValue(row.getCell(108)));
//                poDto.setTotalOtherCharges(getCellStringValue(row.getCell(109)));
//                poDto.setTotalRoundOff(getCellStringValue(row.getCell(110)));
//                poDto.setTotalDocumentValue(getCellStringValue(row.getCell(111)));
//                poDto.setTotalInvoiceValueForeignCurrency(getCellStringValue(row.getCell(112)));
//
//                poDto.setPayeeName(getCellStringValue(row.getCell(113)));
//                poDto.setAccountNumber(getCellStringValue(row.getCell(114)));
//                poDto.setMode(getCellStringValue(row.getCell(115)));
//                poDto.setBranchIfscCode(getCellStringValue(row.getCell(116)));
//                poDto.setTermsOfPayment(getCellStringValue(row.getCell(117)));
//                poDto.setPaymentInstruction(getCellStringValue(row.getCell(118)));
//                poDto.setCreditTransfer(getCellStringValue(row.getCell(119)));
//                poDto.setDirectDebit(getCellStringValue(row.getCell(120)));
//                poDto.setCreditDays(getCellIntegerValue(row.getCell(121)));
//                poDto.setPaidAmount(getCellStringValue(row.getCell(122)));
//                poDto.setDueAmount(getCellStringValue(row.getCell(123)));
//
//                poDto.setTendRefr(getCellStringValue(row.getCell(124)));
//                poDto.setContrRefr(getCellStringValue(row.getCell(125)));
//                poDto.setExtRefr(getCellStringValue(row.getCell(126)));
//                poDto.setProjRefr(getCellStringValue(row.getCell(127)));
//                poDto.setUrl(getCellStringValue(row.getCell(128)));
//                poDto.setDocs(getCellStringValue(row.getCell(129)));
//                poDto.setInfo(getCellStringValue(row.getCell(130)));
//
//                poDto.setAmendPo(getCellStringValue(row.getCell(131)));
//                poDto.setSiteCode(getCellStringValue(row.getCell(132)));
//                poDto.setDepartment(getCellStringValue(row.getCell(133)));
//                poDto.setLocation(getCellStringValue(row.getCell(134)));
//
//                poDto.setUserDefinedField1(getCellStringValue(row.getCell(135)));
//                poDto.setUserDefinedField2(getCellStringValue(row.getCell(136)));
//                poDto.setUserDefinedField3(getCellStringValue(row.getCell(137)));
//                poDto.setUserDefinedField4(getCellStringValue(row.getCell(138)));
//                poDto.setUserDefinedField5(getCellStringValue(row.getCell(139)));
//                poDto.setUserDefinedField6(getCellStringValue(row.getCell(140)));
//                poDto.setUserDefinedField7(getCellStringValue(row.getCell(141)));
//                poDto.setUserDefinedField8(getCellStringValue(row.getCell(142)));
//                poDto.setUserDefinedField9(getCellStringValue(row.getCell(143)));
//                poDto.setUserDefinedField10(getCellStringValue(row.getCell(144)));
//                poDto.setUserDefinedField11(getCellStringValue(row.getCell(145)));
//                poDto.setUserDefinedField12(getCellStringValue(row.getCell(146)));
//                poDto.setUserDefinedField13(getCellStringValue(row.getCell(147)));
//                poDto.setUserDefinedField14(getCellStringValue(row.getCell(148)));
//                poDto.setUserDefinedField15(getCellStringValue(row.getCell(149)));
//
//
//                List<OtherChargesDTO> otherChargesList = new ArrayList<>();
//                // Now read OtherCharges fields (adjust column indexes accordingly)
//                OtherChargesDTO chargesDto = new OtherChargesDTO();
//                chargesDto.setSlNo(getCellStringValue(row.getCell(63)));
//                chargesDto.setProductDescription(getCellStringValue(row.getCell(64)));
//                chargesDto.setIsService(getCellStringValue(row.getCell(65)));
//                chargesDto.setHsnCode(getCellStringValue(row.getCell(66)));
//                chargesDto.setItemBuyerReferenceNumber(getCellStringValue(row.getCell(67)));
//                chargesDto.setItemSellerReferenceNumber(getCellStringValue(row.getCell(68)));
//                chargesDto.setBarCode(getCellStringValue(row.getCell(69)));
//                chargesDto.setQuantity(getCellStringValue(row.getCell(70)));
//                chargesDto.setFreeQuantity(getCellStringValue(row.getCell(71)));
//                chargesDto.setUnit(getCellStringValue(row.getCell(72)));
//                chargesDto.setUnitPrice(getCellStringValue(row.getCell(73)));
//                chargesDto.setGrossAmount(getCellStringValue(row.getCell(74)));
//                chargesDto.setDiscount(getCellStringValue(row.getCell(75)));
//                chargesDto.setPreTaxValue(getCellStringValue(row.getCell(76)));
//                chargesDto.setTaxableValue(getCellStringValue(row.getCell(77)));
//                chargesDto.setGstRate(getCellStringValue(row.getCell(78)));
//                chargesDto.setSgstAmtRs(getCellStringValue(row.getCell(79)));
//                chargesDto.setCgstAmtRs(getCellStringValue(row.getCell(80)));
//                chargesDto.setIgstAmtRs(getCellStringValue(row.getCell(81)));
//                chargesDto.setCessRate(getCellStringValue(row.getCell(82)));
//                chargesDto.setCessAdvalAmtRs(getCellStringValue(row.getCell(83)));
//                chargesDto.setCessNonAdvalAmtRs(getCellStringValue(row.getCell(84)));
//                chargesDto.setStateCessRate(getCellStringValue(row.getCell(85)));
//                chargesDto.setStateCessAdvalAmtRs(getCellStringValue(row.getCell(86)));
//                chargesDto.setStateCessNonAdvalAmtRs(getCellStringValue(row.getCell(87)));
//                chargesDto.setOtherCharges(getCellStringValue(row.getCell(88)));
//
//
//
//            otherChargesList.add(chargesDto);
//            poDto.setOtherChargesList(otherChargesList);
//
//            poReferenceDTOList.add(poDto);
//        }
//
//        workbook.close();
//        return poReferenceDTOList;
//    }
//
//    @Transactional
//    public void saveFromDTOs(List<POReferenceDTO> dtoList) {
//        for (POReferenceDTO poDto : dtoList) {
//            POReference entity = mapDtoToEntity(poDto);
//            poReferenceRepository.save(entity);
//        }
//    }
//
//    private POReference mapDtoToEntity(POReferenceDTO dto) {
//
//        POReference entity = new POReference();
//
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
//
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
//
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
//        entity.setItemTotal(String.valueOf(dto.getItemTotal()));
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
//        entity.setCreditDays(String.valueOf(dto.getCreditDays()));
//        entity.setPaidAmount(String.valueOf(dto.getPaidAmount()));
//        entity.setDueAmount(String.valueOf(dto.getDueAmount()));
//
//        entity.setTendRefr(dto.getTendRefr());
//        entity.setContrRefr(dto.getContrRefr());
//        entity.setExtRefr(dto.getExtRefr());
//        entity.setProjRefr(dto.getProjRefr());
//        entity.setUrl(dto.getUrl());
//        entity.setDocs(dto.getDocs());
//        entity.setInfo(dto.getInfo());
//
//        entity.setAmendPo(String.valueOf(Boolean.valueOf(dto.getAmendPo())));
//        entity.setSiteCode(dto.getSiteCode());
//        entity.setDepartment(dto.getDepartment());
//        entity.setLocation(dto.getLocation());
//
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
////        entity.setOtherChargesList(dto.getOtherChargesList());
//
//        // Map OtherChargesDTO list to entities
//        List<OtherCharges> chargesList = new ArrayList<>();
//        for (OtherChargesDTO chargeDto : dto.getOtherChargesList()) {
//            OtherCharges charge = new OtherCharges();
//                charge.setSlNo(chargeDto.getSlNo());
//                charge.setProductDescription(chargeDto.getProductDescription());
//                charge.setIsService(chargeDto.getIsService());
//                charge.setHsnCode(chargeDto.getHsnCode());
//                charge.setItemBuyerReferenceNumber(chargeDto.getItemBuyerReferenceNumber());
//                charge.setItemSellerReferenceNumber(chargeDto.getItemSellerReferenceNumber());
//                charge.setBarCode(chargeDto.getBarCode());
//                charge.setQuantity(chargeDto.getQuantity());
//                charge.setFreeQuantity(chargeDto.getFreeQuantity());
//                charge.setUnit(chargeDto.getUnit());
//                charge.setUnitPrice(chargeDto.getUnitPrice());
//                charge.setGrossAmount(chargeDto.getGrossAmount());
//                charge.setDiscount(chargeDto.getDiscount());
//                charge.setPreTaxValue(chargeDto.getPreTaxValue());
//                charge.setTaxableValue(chargeDto.getTaxableValue());
//                charge.setGstRate(chargeDto.getGstRate());
//                charge.setSgstAmt(chargeDto.getSgstAmtRs());
//                charge.setCgstAmt(chargeDto.getCgstAmtRs());
//                charge.setIgstAmt(chargeDto.getIgstAmtRs());
//                charge.setCessRate(chargeDto.getCessRate());
//                charge.setStateCessAdvalAmt(chargeDto.getCessAdvalAmtRs());
//                charge.setCessNonAdvalAmt(chargeDto.getCessNonAdvalAmtRs());
//                charge.setStateCessRate(chargeDto.getStateCessRate());
//                charge.setStateCessAdvalAmt(chargeDto.getStateCessAdvalAmtRs());
//                charge.setStateCessNonAdvalAmt(chargeDto.getStateCessNonAdvalAmtRs());
//                charge.setOtherCharges(chargeDto.getOtherCharges());
//
//
//                charge.setPoReference(entity);  // set back reference
//            chargesList.add(charge);
//        }
//        entity.setOtherChargesList(chargesList);
//
//        return entity;
//    }
//
//    private String getCellStringValue(Cell cell) {
//        if (cell == null || cell.getCellType() == CellType.BLANK) return "";
//
//        switch (cell.getCellType()) {
//            case STRING:
//                String value = cell.getStringCellValue().trim();
//                return value.isEmpty() ? "" : value;
//            case NUMERIC:
//                return String.valueOf(cell.getNumericCellValue()).trim();
//            case BOOLEAN:
//                return String.valueOf(cell.getBooleanCellValue());
//            case FORMULA:
//                try {
//                    return cell.getStringCellValue().trim();
//                } catch (IllegalStateException e) {
//                    return String.valueOf(cell.getNumericCellValue()).trim();
//                }
//            default:
//                return "";
//        }
//    }
//
//    private String getCellDateValue(Cell cell) {
//        if (cell == null || cell.getCellType() == CellType.BLANK) return "";
//
//        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            return dateFormat.format(cell.getDateCellValue());
//        }
//
//        return getCellStringValue(cell);
//    }
//
//    private String getCellDoubleValue(Cell cell) {
//        String value = getCellStringValue(cell);
//        if (value != null && !value.equalsIgnoreCase("null")) {
//            try {
//                Double.parseDouble(value); // Validate if it's a valid double
//                return value;
//            } catch (NumberFormatException e) {
//                return "";
//            }
//        }
//        return "";
//    }
//
//    private String getCellIntegerValue(Cell cell) {
//        String value = getCellStringValue(cell);
//        if (value != null && !value.equalsIgnoreCase("null")) {
//            try {
//                Double numericValue = Double.parseDouble(value);
//                return String.valueOf(numericValue.intValue());
//            } catch (NumberFormatException e) {
//                return "";
//            }
//        }
//        return "";
//    }
//
//    private String getCellBooleanValue(Cell cell) {
//        String value = getCellStringValue(cell);
//        if (value != null && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
//            return value;
//        }
//        return "";
//    }
//
//
//
//
//}