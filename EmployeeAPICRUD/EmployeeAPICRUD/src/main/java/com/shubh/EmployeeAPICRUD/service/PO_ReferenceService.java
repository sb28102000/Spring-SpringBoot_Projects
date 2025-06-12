//package com.shubh.EmployeeAPICRUD.service;
//
//import com.shubh.EmployeeAPICRUD.dto.OtherChargesDTO;
//import com.shubh.EmployeeAPICRUD.dto.POReferenceDTO;
//import com.shubh.EmployeeAPICRUD.entity.OtherCharges;
//import com.shubh.EmployeeAPICRUD.entity.POReference;
//import com.shubh.EmployeeAPICRUD.repository.OtherChargesRepository;
//import com.shubh.EmployeeAPICRUD.repository.POReferenceRepository;
//import com.shubh.EmployeeAPICRUD.util.OtherChargesMapper;
//import com.shubh.EmployeeAPICRUD.util.POReferenceMapper;
//import jakarta.transaction.Transactional;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.shubh.EmployeeAPICRUD.util.ExcelUtils.getCellStringValue;
//
//@Service
//public class PO_ReferenceService {
//
//    @Autowired
//    private POReferenceRepository poReferenceRepository;
//
//    @Autowired
//    private OtherChargesRepository otherChargesRepository;
//
//    @Autowired
//    private POReferenceMapper poReferenceMapper;
//
//    @Autowired
//    private OtherChargesMapper otherChargesMapper;
//
//
//    @Transactional
//    public String processExcelFile(MultipartFile file) {
//
//        Map<String, POReference> existingPORefs = new HashMap<>();
//
//        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
//            Sheet sheet = workbook.getSheetAt(0);
//            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//                Row row = sheet.getRow(rowIndex);
//                if (row == null) continue;
//
//                try {
//                    // Read and populate DTOs
//                    POReferenceDTO poDto = new POReferenceDTO();
//                    OtherChargesDTO ocDto = new OtherChargesDTO();
//
//                    poDto.setRecipientGstin(getCellStringValue(row.getCell(0)));
//                    poDto.setRecipientLegalName(getCellStringValue(row.getCell(1)));
//                    poDto.setRecipientTradeName(getCellStringValue(row.getCell(2)));
//                    poDto.setRecipientAddr1(getCellStringValue(row.getCell(3)));
//                    poDto.setRecipientAddr2(getCellStringValue(row.getCell(4)));
//                    poDto.setRecipientLocation(getCellStringValue(row.getCell(5)));
//                    poDto.setRecipientPinCode(getCellStringValue(row.getCell(6)));
//                    poDto.setRecipientStateCode(getCellStringValue(row.getCell(7)));
//                    poDto.setRecipientPhoneNumber(getCellStringValue(row.getCell(8)));
//                    poDto.setRecipientEmailId(getCellStringValue(row.getCell(9)));
//
//                    poDto.setSupplierGstin(getCellStringValue(row.getCell(10)));
//                    poDto.setSupplierPan(getCellStringValue(row.getCell(11)));
//                    poDto.setSupplierAadhaar(getCellStringValue(row.getCell(12)));
//                    poDto.setSupplierCode(getCellStringValue(row.getCell(13)));
//                    poDto.setSupplierLegalName(getCellStringValue(row.getCell(14)));
//                    poDto.setSupplierTradeName(getCellStringValue(row.getCell(15)));
//                    poDto.setSupplierAddr1(getCellStringValue(row.getCell(16)));
//                    poDto.setSupplierAddr2(getCellStringValue(row.getCell(17)));
//                    poDto.setSupplierLocation(getCellStringValue(row.getCell(18)));
//                    poDto.setSupplierPincode(getCellStringValue(row.getCell(19)));
//                    poDto.setSupplierStateCode(getCellStringValue(row.getCell(20)));
//                    poDto.setSupplierPhoneNumber(getCellStringValue(row.getCell(21)));
//                    poDto.setSupplierEmailId(getCellStringValue(row.getCell(22)));
//
//                    poDto.setPos(getCellStringValue(row.getCell(23)));
//                    poDto.setPoSaReferenceNumber(getCellStringValue(row.getCell(24)));
//                    poDto.setPoSaReferenceDate(getCellStringValue(row.getCell(25)));
//                    poDto.setPoSaValidityDate(getCellStringValue(row.getCell(26)));
//                    poDto.setPoOtherReferenceNumber(getCellStringValue(row.getCell(27)));
//                    poDto.setPoOtherReferenceDate(getCellStringValue(row.getCell(28)));
//                    poDto.setDeliveryStartDate(getCellStringValue(row.getCell(29)));
//                    poDto.setDeliveryEndDate(getCellStringValue(row.getCell(30)));
//                    poDto.setDeliveryTerms(getCellStringValue(row.getCell(31)));
//                    poDto.setSalesOrderNumber(getCellStringValue(row.getCell(32)));
//                    poDto.setSalesOrderDate(getCellStringValue(row.getCell(33)));
//                    poDto.setCompanyCode(getCellStringValue(row.getCell(34)));
//                    poDto.setPurchasingOrganisation(getCellStringValue(row.getCell(35)));
//                    poDto.setPurchaseGroup(getCellStringValue(row.getCell(36)));
//                    poDto.setIncoterms(getCellStringValue(row.getCell(37)));
//                    poDto.setIncoterms2(getCellStringValue(row.getCell(38)));
//                    poDto.setCurrency(getCellStringValue(row.getCell(39)));
//                    poDto.setPaymentInDays(getCellStringValue(row.getCell(40)));
//                    poDto.setPaymentInDaysNet(getCellStringValue(row.getCell(41)));
//                    poDto.setExchangeRate(getCellStringValue(row.getCell(42)));
//                    poDto.setFixExchangeRate(getCellStringValue(row.getCell(43)));
//                    poDto.setGrMessage(getCellStringValue(row.getCell(44)));
//                    poDto.setCountry(getCellStringValue(row.getCell(45)));
//                    poDto.setCollectiveNumber(getCellStringValue(row.getCell(46)));
//                    poDto.setWarrantyDays(getCellStringValue(row.getCell(47)));
//                    poDto.setConsignmentNetPrice(getCellStringValue(row.getCell(48)));
//                    poDto.setRetention(getCellStringValue(row.getCell(49)));
//                    poDto.setDownpaymentCategory(getCellStringValue(row.getCell(50)));
//                    poDto.setIncotermsVersion(getCellStringValue(row.getCell(51)));
//                    poDto.setIncotermsLocation1(getCellStringValue(row.getCell(52)));
//                    poDto.setIncotermsLocation2(getCellStringValue(row.getCell(53)));
//                    poDto.setShiptoGstin(getCellStringValue(row.getCell(54)));
//                    poDto.setShiptoLegalName(getCellStringValue(row.getCell(55)));
//                    poDto.setShiptoTradeName(getCellStringValue(row.getCell(56)));
//                    poDto.setShiptoAddr1(getCellStringValue(row.getCell(57)));
//                    poDto.setShiptoAddr2(getCellStringValue(row.getCell(58)));
//                    poDto.setShiptoLocation(getCellStringValue(row.getCell(59)));
//                    poDto.setShiptoPinCode(getCellStringValue(row.getCell(60)));
//                    poDto.setShiptoStateCode(getCellStringValue(row.getCell(61)));
//                    poDto.setPlantCode(getCellStringValue(row.getCell(62)));
//
//                    poDto.setItemTotal(getCellStringValue(row.getCell(89)));
//                    poDto.setOrderLineReference(getCellStringValue(row.getCell(90)));
//                    poDto.setOriginCountry(getCellStringValue(row.getCell(91)));
//                    poDto.setUniqueItemSlNo(getCellStringValue(row.getCell(92)));
//                    poDto.setDeliveryScheduleIdentifier(getCellStringValue(row.getCell(93)));
//                    poDto.setQuantity(getCellStringValue(row.getCell(94)));
//                    poDto.setBatchName(getCellStringValue(row.getCell(97)));
//                    poDto.setBatchExpiryDate(getCellStringValue(row.getCell(98)));
//                    poDto.setWarrantyDate(getCellStringValue(row.getCell(99)));
//                    poDto.setAttributeDetails(getCellStringValue(row.getCell(100)));
//                    poDto.setAttributeValue(getCellStringValue(row.getCell(101)));
//                    poDto.setTotalTaxableValue(getCellStringValue(row.getCell(102)));
//                    poDto.setTotalSgstAmount(getCellStringValue(row.getCell(103)));
//                    poDto.setTotalCgstAmount(getCellStringValue(row.getCell(104)));
//                    poDto.setTotalIgstAmount(getCellStringValue(row.getCell(105)));
//                    poDto.setTotalCessAmount(getCellStringValue(row.getCell(106)));
//                    poDto.setTotalStateCessAmount(getCellStringValue(row.getCell(107)));
//                    poDto.setTotalDiscount(getCellStringValue(row.getCell(108)));
//                    poDto.setTotalOtherCharges(getCellStringValue(row.getCell(109)));
//                    poDto.setTotalRoundOff(getCellStringValue(row.getCell(110)));
//                    poDto.setTotalDocumentValue(getCellStringValue(row.getCell(111)));
//                    poDto.setTotalInvoiceValueForeignCurrency(getCellStringValue(row.getCell(112)));
//
//                    poDto.setPayeeName(getCellStringValue(row.getCell(113)));
//                    poDto.setAccountNumber(getCellStringValue(row.getCell(114)));
//                    poDto.setMode(getCellStringValue(row.getCell(115)));
//                    poDto.setBranchIfscCode(getCellStringValue(row.getCell(116)));
//                    poDto.setTermsOfPayment(getCellStringValue(row.getCell(117)));
//                    poDto.setPaymentInstruction(getCellStringValue(row.getCell(118)));
//                    poDto.setCreditTransfer(getCellStringValue(row.getCell(119)));
//                    poDto.setDirectDebit(getCellStringValue(row.getCell(120)));
//                    poDto.setCreditDays(getCellStringValue(row.getCell(121)));
//                    poDto.setPaidAmount(getCellStringValue(row.getCell(122)));
//                    poDto.setDueAmount(getCellStringValue(row.getCell(123)));
//
//                    poDto.setTendRefr(getCellStringValue(row.getCell(124)));
//                    poDto.setContrRefr(getCellStringValue(row.getCell(125)));
//                    poDto.setExtRefr(getCellStringValue(row.getCell(126)));
//                    poDto.setProjRefr(getCellStringValue(row.getCell(127)));
//                    poDto.setUrl(getCellStringValue(row.getCell(128)));
//                    poDto.setDocs(getCellStringValue(row.getCell(129)));
//                    poDto.setInfo(getCellStringValue(row.getCell(130)));
//
//                    poDto.setAmendPo(getCellStringValue(row.getCell(131)));
//                    poDto.setSiteCode(getCellStringValue(row.getCell(132)));
//                    poDto.setDepartment(getCellStringValue(row.getCell(133)));
//                    poDto.setLocation(getCellStringValue(row.getCell(134)));
//
//                    poDto.setUserDefinedField1(getCellStringValue(row.getCell(135)));
//                    poDto.setUserDefinedField2(getCellStringValue(row.getCell(136)));
//                    poDto.setUserDefinedField3(getCellStringValue(row.getCell(137)));
//                    poDto.setUserDefinedField4(getCellStringValue(row.getCell(138)));
//                    poDto.setUserDefinedField5(getCellStringValue(row.getCell(139)));
//                    poDto.setUserDefinedField6(getCellStringValue(row.getCell(140)));
//                    poDto.setUserDefinedField7(getCellStringValue(row.getCell(141)));
//                    poDto.setUserDefinedField8(getCellStringValue(row.getCell(142)));
//                    poDto.setUserDefinedField9(getCellStringValue(row.getCell(143)));
//                    poDto.setUserDefinedField10(getCellStringValue(row.getCell(144)));
//                    poDto.setUserDefinedField11(getCellStringValue(row.getCell(145)));
//                    poDto.setUserDefinedField12(getCellStringValue(row.getCell(146)));
//                    poDto.setUserDefinedField13(getCellStringValue(row.getCell(147)));
//                    poDto.setUserDefinedField14(getCellStringValue(row.getCell(148)));
//                    poDto.setUserDefinedField15(getCellStringValue(row.getCell(149)));
//
//                    ocDto.setSlNo(getCellStringValue(row.getCell(63)));
//                    ocDto.setProductDescription(getCellStringValue(row.getCell(64)));
//                    ocDto.setIsService(getCellStringValue(row.getCell(65)));
//                    ocDto.setHsnCode(getCellStringValue(row.getCell(66)));
//                    ocDto.setItemBuyerReferenceNumber(getCellStringValue(row.getCell(67)));
//                    ocDto.setItemSellerReferenceNumber(getCellStringValue(row.getCell(68)));
//                    ocDto.setBarCode(getCellStringValue(row.getCell(69)));
//                    ocDto.setQuantity(getCellStringValue(row.getCell(70)));
//                    ocDto.setFreeQuantity(getCellStringValue(row.getCell(71)));
//                    ocDto.setUnit(getCellStringValue(row.getCell(72)));
//                    ocDto.setUnitPrice(getCellStringValue(row.getCell(73)));
//                    ocDto.setGrossAmount(getCellStringValue(row.getCell(74)));
//                    ocDto.setDiscount(getCellStringValue(row.getCell(75)));
//                    ocDto.setPreTaxValue(getCellStringValue(row.getCell(76)));
//                    ocDto.setTaxableValue(getCellStringValue(row.getCell(77)));
//                    ocDto.setGstRate(getCellStringValue(row.getCell(78)));
//                    ocDto.setSgstAmtRs(getCellStringValue(row.getCell(79)));
//                    ocDto.setCgstAmtRs(getCellStringValue(row.getCell(80)));
//                    ocDto.setIgstAmtRs(getCellStringValue(row.getCell(81)));
//                    ocDto.setCessRate(getCellStringValue(row.getCell(82)));
//                    ocDto.setCessAdvalAmtRs(getCellStringValue(row.getCell(83)));
//                    ocDto.setCessNonAdvalAmtRs(getCellStringValue(row.getCell(84)));
//                    ocDto.setStateCessRate(getCellStringValue(row.getCell(85)));
//                    ocDto.setStateCessAdvalAmtRs(getCellStringValue(row.getCell(86)));
//                    ocDto.setStateCessNonAdvalAmtRs(getCellStringValue(row.getCell(87)));
//                    ocDto.setOtherCharges(getCellStringValue(row.getCell(88)));
//
//
////                    String quantityStr = getCellValue(row.getCell(70));
////                    ocDto.setQuantity(quantityStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(quantityStr));
//
//                    if (!isValidPOReferenceDTO(poDto)) continue;
//                    if (!isValidOtherChargesDTO(ocDto)) continue;
//
//                    // Fetch existing or create new POReference entity
//                    POReference poRef = existingPORefs.get(poDto.getPoSaReferenceNumber());
//                    if (poRef == null) {
//                        poRef = poReferenceRepository.findByPoSaReferenceNumber(poDto.getPoSaReferenceNumber()).orElse(null);
//                        if (poRef == null) {
//                            poRef = poReferenceMapper.toEntity(poDto);
////                            poRef.setTotalQuantity(BigDecimal.ZERO);
//                            poRef = poReferenceRepository.save(poRef);
//                        }
//                        existingPORefs.put(poDto.getPoSaReferenceNumber(), poRef);
//                    }
//                    OtherCharges oc = otherChargesMapper.toEntity(ocDto);
//                    oc.setPoReference(poRef);
//                    otherChargesRepository.save(oc);
//
//                    // Update total quantity
////                    poRef.setTotalQuantity(poRef.getTotalQuantity().add(ocDto.getQuantity()));
//                    poReferenceRepository.save(poRef);
//
//                }catch (Exception e) {
//                    System.out.println("Error at row " + (rowIndex + 1) + ": " + e.getMessage());
//                }
//
//            }
//        }
//        catch (IOException e) {
//            throw new RuntimeException("Failed to process Excel file", e);
//        }
//
//        poReferenceRepository.save(entity);
//        return "File processed successfully";
//    }
//
//    private String getCellValue(Cell cell) {
//        return cell == null ? "" : cell.toString().trim();
//    }
//
//    private boolean isValidPOReferenceDTO(POReferenceDTO dto) {
//        return dto.getRecipientGstin() != null && dto.getRecipientGstin().length() == 15 &&
//                dto.getSupplierGstin() != null && dto.getSupplierGstin().length() == 15 &&
//                dto.getRecipientPinCode() != null && dto.getRecipientPinCode().matches("\\d{6}") &&
//                dto.getSupplierPincode() != null && dto.getSupplierPincode().matches("\\d{6}");
//    }
//
//    private boolean isValidOtherChargesDTO(OtherChargesDTO dto) {
//        return dto.getSlNo() != null && !dto.getSlNo().isEmpty();
//    }
//}
