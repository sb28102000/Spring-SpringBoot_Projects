package com.shubh.EmployeeAPICRUD.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.EmployeeAPICRUD.dto.OtherChargesDTO;
import com.shubh.EmployeeAPICRUD.dto.POReferenceDTO;
import com.shubh.EmployeeAPICRUD.entity.OtherCharges;
import com.shubh.EmployeeAPICRUD.entity.POFileTracking;
import com.shubh.EmployeeAPICRUD.entity.POReference;
import com.shubh.EmployeeAPICRUD.repository.POFileTrackingRepository;
import com.shubh.EmployeeAPICRUD.repository.POReferenceRepository;
import com.shubh.EmployeeAPICRUD.specification.POReferenceSpecification;
import com.shubh.EmployeeAPICRUD.util.ExcelGenerator;
import com.shubh.EmployeeAPICRUD.util.ExcelUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PoReference_Service {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private POReferenceRepository poReferenceRepository;

    @Autowired
    private POFileTrackingRepository poFileTrackingRepository;


    public Optional<POReference> getPOReferenceByPoSaReferenceNumber(String poSaReferenceNumber) {
        Optional<POReference> poReference = poReferenceRepository.findByPoSaReferenceNumber(poSaReferenceNumber);
        return poReference;
    }


//  Upload Excel-File
    public List<POReferenceDTO> processPOReferenceExcel(MultipartFile file) throws IOException {


        log.info("Starting processing to upload Excel File: {}", file.getOriginalFilename());
        InputStream inputStream = file.getInputStream();
        List<POReferenceDTO> poReferenceDTOList = extractDataFromExcel(inputStream);
        System.out.println("DTO size: " + poReferenceDTOList.size());
        log.info("Extracted {} Rows from the excel file.", poReferenceDTOList.size());

        Map<String, POReferenceDTO> uniquePOMap = new HashMap<>();
        Set<String> errorPoNumbers = new HashSet<>();

//        successList.clear();
//        errorList.clear();

        int totalRecords = poReferenceDTOList.size();
        int errorCount = 0;
        int processedCount = 0;
        List<String> allErrors = new ArrayList<>();
        Map<String, POReferenceDTO> poReferenceMap = new HashMap<>();

        int rowIndex = 1;
        for (POReferenceDTO currentDto : poReferenceDTOList) {

            String poSaRefNumber = currentDto.getPoSaReferenceNumber();

            if (!uniquePOMap.containsKey(poSaRefNumber)) {
                if (poSaRefNumber == null || poSaRefNumber.trim().isEmpty()) {
                    currentDto.getErrorMessages().add("PO / SA Reference Number is missing or empty.");
                }

                if (!currentDto.getErrorMessages().isEmpty()) {
                    errorPoNumbers.add(poSaRefNumber);
                    errorCount++;
                    allErrors.add("Row " + rowIndex + ": " + String.join(", ", currentDto.getErrorMessages()));
                    rowIndex++;
                    continue;
                }

                // Manual validations
                if (currentDto.getRecipientGstin() == null || currentDto.getRecipientGstin().length() != 15) {
                    currentDto.getErrorMessages().add("Recipient GSTIN must be 15 characters.");
                }
                if (currentDto.getRecipientPinCode() == null || currentDto.getRecipientPinCode().trim().length() != 8) {
                    currentDto.getErrorMessages().add("Recipient Pin Code must be 6 digits.");
                }
                if (currentDto.getSupplierGstin() == null || currentDto.getSupplierGstin().length() != 15) {
                    currentDto.getErrorMessages().add("Supplier GSTIN must be 15 characters.");
                }
                if (currentDto.getSupplierPincode() == null || currentDto.getSupplierPincode().trim().length() != 8) {
                    currentDto.getErrorMessages().add("Supplier Pin Code must be 6 digits.");
                }

                if (!currentDto.getErrorMessages().isEmpty()) {
                    errorPoNumbers.add(poSaRefNumber);
                    errorCount++;
                    allErrors.add("Row " + rowIndex + ": " + String.join(", ", currentDto.getErrorMessages()));
                    rowIndex++;
                    continue;
                }


                uniquePOMap.put(poSaRefNumber, currentDto);
                processedCount++;
            }

            if (errorPoNumbers.contains(poSaRefNumber)) {
                rowIndex++;
                continue;
            }
            rowIndex++;
        }

        saveDTOsToEntities(poReferenceDTOList);

        POFileTracking poFileTracking = new POFileTracking();
        poFileTracking.setFileName(file.getOriginalFilename());
        poFileTracking.setTotalRecords(totalRecords);
        poFileTracking.setProcessedRecords(processedCount);
        poFileTracking.setErrorCount(errorCount);
        poFileTracking.setErrorMsg(allErrors.isEmpty() ? "No Error" : String.join(" | ", allErrors));
        poFileTrackingRepository.save(poFileTracking);


        poReferenceDTOList.addAll(poReferenceMap.values());
        return poReferenceDTOList;

    }


//    Upload JSON-File
    public List<POReferenceDTO> processPOReferenceJson(MultipartFile file) throws IOException {

        log.info("Starting processing to upload JSON File: {}", file.getOriginalFilename());
        InputStream inputStream = file.getInputStream();
        List<POReferenceDTO> poReferenceDTOList = extractDataFromJson(inputStream);
        System.out.println("DTO size: " + poReferenceDTOList.size());
        log.info("Extracted {} Rows from the JSON file.", poReferenceDTOList.size());

        Map<String, POReferenceDTO> uniquePOMap = new HashMap<>();
        Set<String> errorPoNumbers = new HashSet<>();

        int totalRecords = poReferenceDTOList.size();
        int errorCount = 0;
        int processedCount = 0;
        List<String> allErrors = new ArrayList<>();
        Map<String, POReferenceDTO> poReferenceMap = new HashMap<>();

        int rowIndex = 1;
        for (POReferenceDTO currentDto : poReferenceDTOList) {

            String poSaRefNumber = currentDto.getPoSaReferenceNumber();

            if (!uniquePOMap.containsKey(poSaRefNumber)) {
                if (poSaRefNumber == null || poSaRefNumber.trim().isEmpty()) {
                    currentDto.getErrorMessages().add("PO / SA Reference Number is missing or empty.");
                }

                if (!currentDto.getErrorMessages().isEmpty()) {
                    errorPoNumbers.add(poSaRefNumber);
                    errorCount++;
                    allErrors.add("Row " + rowIndex + ": " + String.join(", ", currentDto.getErrorMessages()));
                    rowIndex++;
                    continue;
                }

                // Manual validations
                if (currentDto.getRecipientGstin() == null || currentDto.getRecipientGstin().length() != 15) {
                    currentDto.getErrorMessages().add("Recipient GSTIN must be 15 characters.");
                }
                if (currentDto.getRecipientPinCode() == null || currentDto.getRecipientPinCode().trim().length() != 6) {
                    currentDto.getErrorMessages().add("Recipient Pin Code must be 6 digits.");
                }
                if (currentDto.getSupplierGstin() == null || currentDto.getSupplierGstin().length() != 15) {
                    currentDto.getErrorMessages().add("Supplier GSTIN must be 15 characters.");
                }
                if (currentDto.getSupplierPincode() == null || currentDto.getSupplierPincode().trim().length() != 6) {
                    currentDto.getErrorMessages().add("Supplier Pin Code must be 6 digits.");
                }

                if (!currentDto.getErrorMessages().isEmpty()) {
                    errorPoNumbers.add(poSaRefNumber);
                    errorCount++;
                    allErrors.add("Row " + rowIndex + ": " + String.join(", ", currentDto.getErrorMessages()));
                    rowIndex++;
                    continue;
                }

                uniquePOMap.put(poSaRefNumber, currentDto);
                processedCount++;
            }

            if (errorPoNumbers.contains(poSaRefNumber)) {
                rowIndex++;
                continue;
            }

            rowIndex++;
        }

        saveDTOsToEntities(poReferenceDTOList);

        POFileTracking poFileTracking = new POFileTracking();
        poFileTracking.setFileName(file.getOriginalFilename());
        poFileTracking.setTotalRecords(totalRecords);
        poFileTracking.setProcessedRecords(processedCount);
        poFileTracking.setErrorCount(errorCount);
        poFileTracking.setErrorMsg(allErrors.isEmpty() ? "No Error" : String.join(" | ", allErrors));
        poFileTrackingRepository.save(poFileTracking);

        poReferenceDTOList.addAll(poReferenceMap.values());
        return poReferenceDTOList;
    }


    @Service
    public static class ExcelDownloadService {

        @Autowired
        private POReferenceRepository poReferenceRepository;

        public ByteArrayInputStream generateErrorExcel() {
            List<POReference> errorList = poReferenceRepository.findByErrorMessageIsNotNull();
            return ExcelGenerator.generateExcelFromEntities(errorList, true);
        }

        public ByteArrayInputStream generateSuccessExcel() {
            List<POReference> successList = poReferenceRepository.findByErrorMessageIsNotNull();
            return ExcelGenerator.generateExcelFromEntities(successList, false);
        }

    }


//  Extract Data from ExcelSheet and Saved it to DTO Classes.
    private List<POReferenceDTO> extractDataFromExcel(InputStream inputStream) throws IOException {
        log.info("Starting to extract data from the Excel InputStream.");
        List<POReferenceDTO> poReferenceDTOList = new ArrayList<>();
        Map<String, POReferenceDTO> poReferenceMap = new HashMap<>();

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        if (!rows.hasNext()) {
            log.warn("ExcelSheet is empty, No header row found.");
            return poReferenceDTOList;
        }

        rows.next(); // Skip header row
        int rowCount = 0;

        while (rows.hasNext()) {
            Row row = rows.next();
            rowCount++;

            String poSaReferenceNumber = getCellStringValue(row.getCell(24)); // Column for po_sa_reference_number

            if (poSaReferenceNumber == null || poSaReferenceNumber.trim().isEmpty()) {
                // skip or log error
                continue;
            }

            POReferenceDTO poDto;

            if (!poReferenceMap.containsKey(poSaReferenceNumber)) {

                poDto = new POReferenceDTO();
                poDto.setPoSaReferenceNumber(poSaReferenceNumber);

                poDto.setRecipientGstin(getCellStringValue(row.getCell(0)));
                poDto.setRecipientLegalName(getCellStringValue(row.getCell(1)));
                poDto.setRecipientTradeName(getCellStringValue(row.getCell(2)));
                poDto.setRecipientAddr1(getCellStringValue(row.getCell(3)));
                poDto.setRecipientAddr2(getCellStringValue(row.getCell(4)));
                poDto.setRecipientLocation(getCellStringValue(row.getCell(5)));
                poDto.setRecipientPinCode(getCellStringValue(row.getCell(6)));
                poDto.setRecipientStateCode(getCellStringValue(row.getCell(7)));
                poDto.setRecipientPhoneNumber(getCellStringValue(row.getCell(8)));
                poDto.setRecipientEmailId(getCellStringValue(row.getCell(9)));

                poDto.setSupplierGstin(getCellStringValue(row.getCell(10)));
                poDto.setSupplierPan(getCellStringValue(row.getCell(11)));
                poDto.setSupplierAadhaar(getCellStringValue(row.getCell(12)));
                poDto.setSupplierCode(getCellStringValue(row.getCell(13)));
                poDto.setSupplierLegalName(getCellStringValue(row.getCell(14)));
                poDto.setSupplierTradeName(getCellStringValue(row.getCell(15)));
                poDto.setSupplierAddr1(getCellStringValue(row.getCell(16)));
                poDto.setSupplierAddr2(getCellStringValue(row.getCell(17)));
                poDto.setSupplierLocation(getCellStringValue(row.getCell(18)));
                poDto.setSupplierPincode(getCellStringValue(row.getCell(19)));
                poDto.setSupplierStateCode(getCellStringValue(row.getCell(20)));
                poDto.setSupplierPhoneNumber(getCellStringValue(row.getCell(21)));
                poDto.setSupplierEmailId(getCellStringValue(row.getCell(22)));

                poDto.setPos(getCellStringValue(row.getCell(23)));
                poDto.setPoSaReferenceNumber(getCellStringValue(row.getCell(24)));
                poDto.setPoSaReferenceDate(getCellStringValue(row.getCell(25)));
                poDto.setPoSaValidityDate(getCellStringValue(row.getCell(26)));
                poDto.setPoOtherReferenceNumber(getCellStringValue(row.getCell(27)));
                poDto.setPoOtherReferenceDate(getCellStringValue(row.getCell(28)));
                poDto.setDeliveryStartDate(getCellStringValue(row.getCell(29)));
                poDto.setDeliveryEndDate(getCellStringValue(row.getCell(30)));
                poDto.setDeliveryTerms(getCellStringValue(row.getCell(31)));
                poDto.setSalesOrderNumber(getCellStringValue(row.getCell(32)));
                poDto.setSalesOrderDate(getCellStringValue(row.getCell(33)));
                poDto.setCompanyCode(getCellStringValue(row.getCell(34)));
                poDto.setPurchasingOrganisation(getCellStringValue(row.getCell(35)));
                poDto.setPurchaseGroup(getCellStringValue(row.getCell(36)));
                poDto.setIncoterms(getCellStringValue(row.getCell(37)));
                poDto.setIncoterms2(getCellStringValue(row.getCell(38)));
                poDto.setCurrency(getCellStringValue(row.getCell(39)));
                poDto.setPaymentInDays(getCellStringValue(row.getCell(40)));
                poDto.setPaymentInDaysNet(getCellStringValue(row.getCell(41)));
                poDto.setExchangeRate(getCellStringValue(row.getCell(42)));
                poDto.setFixExchangeRate(getCellStringValue(row.getCell(43)));
                poDto.setGrMessage(getCellStringValue(row.getCell(44)));
                poDto.setCountry(getCellStringValue(row.getCell(45)));
                poDto.setCollectiveNumber(getCellStringValue(row.getCell(46)));
                poDto.setWarrantyDays(getCellStringValue(row.getCell(47)));
                poDto.setConsignmentNetPrice(getCellStringValue(row.getCell(48)));
                poDto.setRetention(getCellStringValue(row.getCell(49)));
                poDto.setDownpaymentCategory(getCellStringValue(row.getCell(50)));
                poDto.setIncotermsVersion(getCellStringValue(row.getCell(51)));
                poDto.setIncotermsLocation1(getCellStringValue(row.getCell(52)));
                poDto.setIncotermsLocation2(getCellStringValue(row.getCell(53)));
                poDto.setShiptoGstin(getCellStringValue(row.getCell(54)));
                poDto.setShiptoLegalName(getCellStringValue(row.getCell(55)));
                poDto.setShiptoTradeName(getCellStringValue(row.getCell(56)));
                poDto.setShiptoAddr1(getCellStringValue(row.getCell(57)));
                poDto.setShiptoAddr2(getCellStringValue(row.getCell(58)));
                poDto.setShiptoLocation(getCellStringValue(row.getCell(59)));
                poDto.setShiptoPinCode(getCellStringValue(row.getCell(60)));
                poDto.setShiptoStateCode(getCellStringValue(row.getCell(61)));
                poDto.setPlantCode(getCellStringValue(row.getCell(62)));

                poDto.setItemTotal(getCellStringValue(row.getCell(89)));
                poDto.setOrderLineReference(getCellStringValue(row.getCell(90)));
                poDto.setOriginCountry(getCellStringValue(row.getCell(91)));
                poDto.setUniqueItemSlNo(getCellStringValue(row.getCell(92)));
                poDto.setDeliveryScheduleIdentifier(getCellStringValue(row.getCell(93)));
                poDto.setQuantity(getCellStringValue(row.getCell(94)));
                poDto.setDeliveryStartDate(getCellStringValue(row.getCell(95)));
                poDto.setDeliveryEndDate(getCellStringValue(row.getCell(96)));
                poDto.setBatchName(getCellStringValue(row.getCell(97)));
                poDto.setBatchExpiryDate(getCellStringValue(row.getCell(98)));
                poDto.setWarrantyDate(getCellStringValue(row.getCell(99)));
                poDto.setAttributeDetails(getCellStringValue(row.getCell(100)));
                poDto.setAttributeValue(getCellStringValue(row.getCell(101)));
                poDto.setTotalTaxableValue(getCellStringValue(row.getCell(102)));
                poDto.setTotalSgstAmount(getCellStringValue(row.getCell(103)));
                poDto.setTotalCgstAmount(getCellStringValue(row.getCell(104)));
                poDto.setTotalIgstAmount(getCellStringValue(row.getCell(105)));
                poDto.setTotalCessAmount(getCellStringValue(row.getCell(106)));
                poDto.setTotalStateCessAmount(getCellStringValue(row.getCell(107)));
                poDto.setTotalDiscount(getCellStringValue(row.getCell(108)));
                poDto.setTotalOtherCharges(getCellStringValue(row.getCell(109)));
                poDto.setTotalRoundOff(getCellStringValue(row.getCell(110)));
                poDto.setTotalDocumentValue(getCellStringValue(row.getCell(111)));
                poDto.setTotalInvoiceValueForeignCurrency(getCellStringValue(row.getCell(112)));

                poDto.setPayeeName(getCellStringValue(row.getCell(113)));
                poDto.setAccountNumber(getCellStringValue(row.getCell(114)));
                poDto.setMode(getCellStringValue(row.getCell(115)));
                poDto.setBranchIfscCode(getCellStringValue(row.getCell(116)));
                poDto.setTermsOfPayment(getCellStringValue(row.getCell(117)));
                poDto.setPaymentInstruction(getCellStringValue(row.getCell(118)));
                poDto.setCreditTransfer(getCellStringValue(row.getCell(119)));
                poDto.setDirectDebit(getCellStringValue(row.getCell(120)));
                poDto.setCreditDays(getCellStringValue(row.getCell(121)));
                poDto.setPaidAmount(getCellStringValue(row.getCell(122)));
                poDto.setDueAmount(getCellStringValue(row.getCell(123)));

                poDto.setTendRefr(getCellStringValue(row.getCell(124)));
                poDto.setContrRefr(getCellStringValue(row.getCell(125)));
                poDto.setExtRefr(getCellStringValue(row.getCell(126)));
                poDto.setProjRefr(getCellStringValue(row.getCell(127)));
                poDto.setUrl(getCellStringValue(row.getCell(128)));
                poDto.setDocs(getCellStringValue(row.getCell(129)));
                poDto.setInfo(getCellStringValue(row.getCell(130)));

                poDto.setAmendPo(getCellStringValue(row.getCell(131)));
                poDto.setSiteCode(getCellStringValue(row.getCell(132)));
                poDto.setDepartment(getCellStringValue(row.getCell(133)));
                poDto.setLocation(getCellStringValue(row.getCell(134)));

                poDto.setUserDefinedField1(getCellStringValue(row.getCell(135)));
                poDto.setUserDefinedField2(getCellStringValue(row.getCell(136)));
                poDto.setUserDefinedField3(ExcelUtils.getCellStringValue(row.getCell(137)));
                poDto.setUserDefinedField4(getCellStringValue(row.getCell(138)));
                poDto.setUserDefinedField5(getCellStringValue(row.getCell(139)));
                poDto.setUserDefinedField6(getCellStringValue(row.getCell(140)));
                poDto.setUserDefinedField7(getCellStringValue(row.getCell(141)));
                poDto.setUserDefinedField8(getCellStringValue(row.getCell(142)));
                poDto.setUserDefinedField9(getCellStringValue(row.getCell(143)));
                poDto.setUserDefinedField10(getCellStringValue(row.getCell(144)));
                poDto.setUserDefinedField11(getCellStringValue(row.getCell(145)));
                poDto.setUserDefinedField12(getCellStringValue(row.getCell(146)));
                poDto.setUserDefinedField13(getCellStringValue(row.getCell(147)));
                poDto.setUserDefinedField14(getCellStringValue(row.getCell(148)));
                poDto.setUserDefinedField15(getCellStringValue(row.getCell(149)));

                poReferenceMap.put(poSaReferenceNumber, poDto);
            } else {
                poDto = poReferenceMap.get(poSaReferenceNumber);
            }

            OtherChargesDTO chargesDto = new OtherChargesDTO();

            chargesDto.setSlNo(getCellStringValue(row.getCell(63)));
            chargesDto.setProductDescription(getCellStringValue(row.getCell(64)));
            chargesDto.setIsService(getCellStringValue(row.getCell(65)));
            chargesDto.setHsnCode(getCellStringValue(row.getCell(66)));
            chargesDto.setItemBuyerReferenceNumber(getCellStringValue(row.getCell(67)));
            chargesDto.setItemSellerReferenceNumber(getCellStringValue(row.getCell(68)));
            chargesDto.setBarCode(getCellStringValue(row.getCell(69)));
            chargesDto.setQuantity(getCellStringValue(row.getCell(70)));
            chargesDto.setFreeQuantity(getCellStringValue(row.getCell(71)));
            chargesDto.setUnit(getCellStringValue(row.getCell(72)));
            chargesDto.setUnitPrice(getCellStringValue(row.getCell(73)));
            chargesDto.setGrossAmount(getCellStringValue(row.getCell(74)));
            chargesDto.setDiscount(getCellStringValue(row.getCell(75)));
            chargesDto.setPreTaxValue(getCellStringValue(row.getCell(76)));
            chargesDto.setTaxableValue(getCellStringValue(row.getCell(77)));
            chargesDto.setGstRate(getCellStringValue(row.getCell(78)));
            chargesDto.setSgstAmtRs(getCellStringValue(row.getCell(79)));
            chargesDto.setCgstAmtRs(getCellStringValue(row.getCell(80)));
            chargesDto.setIgstAmtRs(getCellStringValue(row.getCell(81)));
            chargesDto.setCessRate(getCellStringValue(row.getCell(82)));
            chargesDto.setCessAdvalAmtRs(getCellStringValue(row.getCell(83)));
            chargesDto.setCessNonAdvalAmtRs(getCellStringValue(row.getCell(84)));
            chargesDto.setStateCessRate(getCellStringValue(row.getCell(85)));
            chargesDto.setStateCessAdvalAmtRs(getCellStringValue(row.getCell(86)));
            chargesDto.setStateCessNonAdvalAmtRs(getCellStringValue(row.getCell(87)));
            chargesDto.setOtherCharges(getCellStringValue(row.getCell(88)));

            if (poDto.getOtherChargesDTOList() == null) {
                poDto.setOtherChargesDTOList(new ArrayList<>());
            }
            poDto.getOtherChargesDTOList().add(chargesDto);


        }
        poReferenceDTOList.addAll(poReferenceMap.values());
        log.info("Data extraction completed with {} PO records.", poReferenceDTOList.size());
        return poReferenceDTOList;
    }

//----------------------------------------------------------------------------------------------------------------------------->

//  Extract Data from the JSON-File and saved it to DTO Classes.
    private List<POReferenceDTO> extractDataFromJson(InputStream inputStream) throws IOException {
        List<POReferenceDTO> poReferenceDTOList = new ArrayList<>();
        Map<String, POReferenceDTO> poReferenceMap = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(inputStream);

        if (!rootNode.isArray()) {
            return poReferenceDTOList;
        }

        for (JsonNode node : rootNode) {
            String poSaReferenceNumber = getJsonText(node, "poSaReferenceNumber");
            if (poSaReferenceNumber == null || poSaReferenceNumber.trim().isEmpty()) {
                continue;
            }

            POReferenceDTO poDto = poReferenceMap.get(poSaReferenceNumber);
            if (poDto == null) {
                poDto = new POReferenceDTO();
                poDto.setPoSaReferenceNumber(poSaReferenceNumber);
                poDto.setRecipientGstin(getJsonText(node, "recipientGstin"));
                poDto.setRecipientLegalName(getJsonText(node, "recipientLegalName"));
                poDto.setRecipientTradeName(getJsonText(node, "recipientTradeName"));
                poDto.setRecipientAddr1(getJsonText(node, "recipientAddr1"));
                poDto.setRecipientAddr2(getJsonText(node, "recipientAddr2"));
                poDto.setRecipientLocation(getJsonText(node, "recipientLocation"));
                poDto.setRecipientPinCode(getJsonText(node, "recipientPinCode"));
                poDto.setRecipientStateCode(getJsonText(node, "recipientStateCode"));
                poDto.setRecipientPhoneNumber(getJsonText(node, "recipientPhoneNumber"));
                poDto.setRecipientEmailId(getJsonText(node, "recipientEmailId"));

                poDto.setSupplierGstin(getJsonText(node, "supplierGstin"));
                poDto.setSupplierPan(getJsonText(node, "supplierPan"));
                poDto.setSupplierAadhaar(getJsonText(node, "supplierAadhaar"));
                poDto.setSupplierCode(getJsonText(node, "supplierCode"));
                poDto.setSupplierLegalName(getJsonText(node, "supplierLegalName"));
                poDto.setSupplierTradeName(getJsonText(node, "supplierTradeName"));
                poDto.setSupplierAddr1(getJsonText(node, "supplierAddr1"));
                poDto.setSupplierAddr2(getJsonText(node, "supplierAddr2"));
                poDto.setSupplierLocation(getJsonText(node, "supplierLocation"));
                poDto.setSupplierPincode(getJsonText(node, "supplierPincode"));
                poDto.setSupplierStateCode(getJsonText(node, "supplierStateCode"));
                poDto.setSupplierPhoneNumber(getJsonText(node, "supplierPhoneNumber"));
                poDto.setSupplierEmailId(getJsonText(node, "supplierEmailId"));

                poDto.setPos(getJsonText(node, "pos"));
                poDto.setPoSaReferenceNumber(getJsonText(node, "poSaReferenceNumber"));
                poDto.setPoSaReferenceDate(getJsonText(node, "poSaReferenceDate"));
                poDto.setPoSaValidityDate(getJsonText(node, "poSaValidityDate"));
                poDto.setPoOtherReferenceNumber(getJsonText(node, "poOtherReferenceNumber"));
                poDto.setPoOtherReferenceDate(getJsonText(node, "poOtherReferenceDate"));
                poDto.setDeliveryStartDate(getJsonText(node, "deliveryStartDate"));
                poDto.setDeliveryEndDate(getJsonText(node, "deliveryEndDate"));
                poDto.setDeliveryTerms(getJsonText(node, "deliveryTerms"));
                poDto.setSalesOrderNumber(getJsonText(node, "salesOrderNumber"));
                poDto.setSalesOrderDate(getJsonText(node, "salesOrderDate"));
                poDto.setCompanyCode(getJsonText(node, "companyCode"));
                poDto.setPurchasingOrganisation(getJsonText(node, "purchasingOrganisation"));
                poDto.setPurchaseGroup(getJsonText(node, "purchaseGroup"));
                poDto.setIncoterms(getJsonText(node, "incoterms"));
                poDto.setIncoterms2(getJsonText(node, "incoterms2"));
                poDto.setCurrency(getJsonText(node, "currency"));
                poDto.setPaymentInDays(getJsonText(node, "paymentInDays"));
                poDto.setPaymentInDaysNet(getJsonText(node, "paymentInDaysNet"));
                poDto.setExchangeRate(getJsonText(node, "exchangeRate"));
                poDto.setFixExchangeRate(getJsonText(node, "fixExchangeRate"));
                poDto.setGrMessage(getJsonText(node, "grMessage"));
                poDto.setCountry(getJsonText(node, "country"));
                poDto.setCollectiveNumber(getJsonText(node, "collectiveNumber"));
                poDto.setWarrantyDays(getJsonText(node, "warrantyDays"));
                poDto.setConsignmentNetPrice(getJsonText(node, "consignmentNetPrice"));
                poDto.setRetention(getJsonText(node, "retention"));
                poDto.setDownpaymentCategory(getJsonText(node, "downpaymentCategory"));
                poDto.setIncotermsVersion(getJsonText(node, "incotermsVersion"));
                poDto.setIncotermsLocation1(getJsonText(node, "incotermsLocation1"));
                poDto.setIncotermsLocation2(getJsonText(node, "incotermsLocation2"));
                poDto.setShiptoGstin(getJsonText(node, "shiptoGstin"));
                poDto.setShiptoLegalName(getJsonText(node, "shiptoLegalName"));
                poDto.setShiptoTradeName(getJsonText(node, "shiptoTradeName"));
                poDto.setShiptoAddr1(getJsonText(node, "shiptoAddr1"));
                poDto.setShiptoAddr2(getJsonText(node, "shiptoAddr2"));
                poDto.setShiptoLocation(getJsonText(node, "shiptoLocation"));
                poDto.setShiptoPinCode(getJsonText(node, "shiptoPinCode"));
                poDto.setShiptoStateCode(getJsonText(node, "shiptoStateCode"));
                poDto.setPlantCode(getJsonText(node, "plantCode"));

                poDto.setItemTotal(getJsonText(node, "itemTotal"));
                poDto.setOrderLineReference(getJsonText(node, "orderLineReference"));
                poDto.setOriginCountry(getJsonText(node, "originCountry"));
                poDto.setUniqueItemSlNo(getJsonText(node, "uniqueItemSlNo"));
                poDto.setDeliveryScheduleIdentifier(getJsonText(node, "deliveryScheduleIdentifier"));
                poDto.setQuantity(getJsonText(node, "quantity"));
                poDto.setDeliveryStartDate(getJsonText(node, "itemDeliveryStartDate"));
                poDto.setDeliveryEndDate(getJsonText(node, "itemDeliveryEndDate"));
                poDto.setBatchName(getJsonText(node, "batchName"));
                poDto.setBatchExpiryDate(getJsonText(node, "batchExpiryDate"));
                poDto.setWarrantyDate(getJsonText(node, "warrantyDate"));
                poDto.setAttributeDetails(getJsonText(node, "attributeDetails"));
                poDto.setAttributeValue(getJsonText(node, "attributeValue"));
                poDto.setTotalTaxableValue(getJsonText(node, "totalTaxableValue"));
                poDto.setTotalSgstAmount(getJsonText(node, "totalSgstAmt"));
                poDto.setTotalCgstAmount(getJsonText(node, "totalCgstAmt"));
                poDto.setTotalIgstAmount(getJsonText(node, "totalIgstAmt"));
                poDto.setTotalCessAmount(getJsonText(node, "totalCessAmt"));
                poDto.setTotalStateCessAmount(getJsonText(node, "totalStateCessAmt"));
                poDto.setTotalDiscount(getJsonText(node, "totalDiscount"));
                poDto.setTotalOtherCharges(getJsonText(node, "totalOtherCharges"));
                poDto.setTotalRoundOff(getJsonText(node, "totalRoundOff"));
                poDto.setTotalDocumentValue(getJsonText(node, "totalDocumentValue"));
                poDto.setTotalInvoiceValueForeignCurrency(getJsonText(node, "totalInvoiceValueInForeignCurrency"));

                poDto.setPayeeName(getJsonText(node, "payeeName"));
                poDto.setAccountNumber(getJsonText(node, "accountNumber"));
                poDto.setMode(getJsonText(node, "paymentMode"));
                poDto.setBranchIfscCode(getJsonText(node, "branchIfscCode"));
                poDto.setTermsOfPayment(getJsonText(node, "termsOfPayment"));
                poDto.setPaymentInstruction(getJsonText(node, "paymentInstruction"));
                poDto.setCreditTransfer(getJsonText(node, "creditTransfer"));
                poDto.setDirectDebit(getJsonText(node, "directDebit"));
                poDto.setCreditDays(getJsonText(node, "creditDays"));
                poDto.setPaidAmount(getJsonText(node, "paidAmount"));
                poDto.setDueAmount(getJsonText(node, "dueAmount"));

                poDto.setTendRefr(getJsonText(node, "tendRefr"));
                poDto.setContrRefr(getJsonText(node, "contrRefr"));
                poDto.setExtRefr(getJsonText(node, "extRefr"));
                poDto.setProjRefr(getJsonText(node, "projRefr"));
                poDto.setUrl(getJsonText(node, "url"));
                poDto.setDocs(getJsonText(node, "docs"));
                poDto.setInfo(getJsonText(node, "info"));

                poDto.setAmendPo(getJsonText(node, "amendPo"));
                poDto.setSiteCode(getJsonText(node, "siteCode"));
                poDto.setDepartment(getJsonText(node, "department"));
                poDto.setLocation(getJsonText(node, "location"));

                poDto.setUserDefinedField1(getJsonText(node, "userDefinedField1"));
                poDto.setUserDefinedField2(getJsonText(node, "userDefinedField2"));
                poDto.setUserDefinedField3(getJsonText(node, "userDefinedField3"));
                poDto.setUserDefinedField4(getJsonText(node, "userDefinedField4"));
                poDto.setUserDefinedField5(getJsonText(node, "userDefinedField5"));
                poDto.setUserDefinedField6(getJsonText(node, "userDefinedField6"));
                poDto.setUserDefinedField7(getJsonText(node, "userDefinedField7"));
                poDto.setUserDefinedField8(getJsonText(node, "userDefinedField8"));
                poDto.setUserDefinedField9(getJsonText(node, "userDefinedField9"));
                poDto.setUserDefinedField10(getJsonText(node, "userDefinedField10"));
                poDto.setUserDefinedField11(getJsonText(node, "userDefinedField11"));
                poDto.setUserDefinedField12(getJsonText(node, "userDefinedField12"));
                poDto.setUserDefinedField13(getJsonText(node, "userDefinedField13"));
                poDto.setUserDefinedField14(getJsonText(node, "userDefinedField14"));
                poDto.setUserDefinedField15(getJsonText(node, "userDefinedField15"));

                poDto.setOtherChargesDTOList(new ArrayList<>());
                poReferenceMap.put(poSaReferenceNumber, poDto);
                poReferenceDTOList.add(poDto);
            }

            OtherChargesDTO chargeDTO = new OtherChargesDTO();

            chargeDTO.setSlNo(getJsonText(node, "slNo"));
            chargeDTO.setProductDescription(getJsonText(node, "productDescription"));
            chargeDTO.setIsService(getJsonText(node, "isService"));
            chargeDTO.setHsnCode(getJsonText(node, "hsnCode"));
            chargeDTO.setItemBuyerReferenceNumber(getJsonText(node, "itemBuyerReferenceNumber"));
            chargeDTO.setItemSellerReferenceNumber(getJsonText(node, "itemSellerReferenceNumber"));
            chargeDTO.setBarCode(getJsonText(node, "barCode"));
            chargeDTO.setQuantity(getJsonText(node, "quantity"));
            chargeDTO.setFreeQuantity(getJsonText(node, "freeQuantity"));
            chargeDTO.setUnit(getJsonText(node, "unit"));
            chargeDTO.setUnitPrice(getJsonText(node, "unitPrice"));
            chargeDTO.setGrossAmount(getJsonText(node, "grossAmount"));
            chargeDTO.setDiscount(getJsonText(node, "discount"));
            chargeDTO.setPreTaxValue(getJsonText(node, "preTaxValue"));
            chargeDTO.setTaxableValue(getJsonText(node, "taxableValue"));
            chargeDTO.setGstRate(getJsonText(node, "gstRate"));
            chargeDTO.setSgstAmtRs(getJsonText(node, "sgstAmtRs"));
            chargeDTO.setCgstAmtRs(getJsonText(node, "cgstAmtRs"));
            chargeDTO.setIgstAmtRs(getJsonText(node, "igstAmtRs"));
            chargeDTO.setCessRate(getJsonText(node, "cessRate"));
            chargeDTO.setCessAdvalAmtRs(getJsonText(node, "cessAdvalAmtRs"));
            chargeDTO.setCessNonAdvalAmtRs(getJsonText(node, "cessNonAdvalAmtRs"));
            chargeDTO.setStateCessRate(getJsonText(node, "stateCessRate"));
            chargeDTO.setStateCessAdvalAmtRs(getJsonText(node, "stateCessAdvalAmtRs"));
            chargeDTO.setStateCessNonAdvalAmtRs(getJsonText(node, "stateCessNonAdvalAmtRs"));
            chargeDTO.setOtherCharges(getJsonText(node, "otherCharges"));

            // Add other fields as needed

            poDto.getOtherChargesDTOList().add(chargeDTO);
        }

        return poReferenceDTOList;
    }


    //<-------------------------------------------------------------------------------------------------->

//  Save Data from DTO TO Entities.
    public void saveDTOsToEntities(List<POReferenceDTO> poReferenceDTOList) {

        log.info("Started saving valid DTOs POReferenceDTO into the Database.");
        Map<String, POReference> poReferenceMap = new HashMap<>();

        for (POReferenceDTO poDto : poReferenceDTOList) {
            try {
                POReference entity = new POReference();

                entity.setRecipientGstin(poDto.getRecipientGstin());
                entity.setRecipientLegalName(poDto.getRecipientLegalName());
                entity.setRecipientTradeName(poDto.getRecipientTradeName());
                entity.setRecipientAddr1(poDto.getRecipientAddr1());
                entity.setRecipientAddr2(poDto.getRecipientAddr2());
                entity.setRecipientLocation(poDto.getRecipientLocation());
                entity.setRecipientPinCode(poDto.getRecipientPinCode());
                entity.setRecipientStateCode(poDto.getRecipientStateCode());
                entity.setRecipientPhoneNumber(poDto.getRecipientPhoneNumber());
                entity.setRecipientEmailId(poDto.getRecipientEmailId());

                entity.setSupplierGstin(poDto.getSupplierGstin());
                entity.setSupplierPan(poDto.getSupplierPan());
                entity.setSupplierAadhaar(poDto.getSupplierAadhaar());
                entity.setSupplierCode(poDto.getSupplierCode());
                entity.setSupplierLegalName(poDto.getSupplierLegalName());
                entity.setSupplierTradeName(poDto.getSupplierTradeName());
                entity.setSupplierAddr1(poDto.getSupplierAddr1());
                entity.setSupplierAddr2(poDto.getSupplierAddr2());
                entity.setSupplierLocation(poDto.getSupplierLocation());
                entity.setSupplierPincode(poDto.getSupplierPincode());
                entity.setSupplierStateCode(poDto.getSupplierStateCode());
                entity.setSupplierPhoneNumber(poDto.getSupplierPhoneNumber());
                entity.setSupplierEmailId(poDto.getSupplierEmailId());

                entity.setPos(poDto.getPos());
                entity.setPoSaReferenceNumber(poDto.getPoSaReferenceNumber());
                entity.setPoSaReferenceDate(poDto.getPoSaReferenceDate());
                entity.setPoSaValidityDate(poDto.getPoSaValidityDate());
                entity.setPoOtherReferenceNumber(poDto.getPoOtherReferenceNumber());
                entity.setPoOtherReferenceDate(poDto.getPoOtherReferenceDate());
                entity.setDeliveryStartDate(poDto.getDeliveryStartDate());
                entity.setDeliveryEndDate(poDto.getDeliveryEndDate());
                entity.setDeliveryTerms(poDto.getDeliveryTerms());
                entity.setSalesOrderNumber(poDto.getSalesOrderNumber());
                entity.setSalesOrderDate(poDto.getSalesOrderDate());
                entity.setCompanyCode(poDto.getCompanyCode());
                entity.setPurchasingOrganisation(poDto.getPurchasingOrganisation());
                entity.setPurchaseGroup(poDto.getPurchaseGroup());
                entity.setIncoterms(poDto.getIncoterms());
                entity.setIncoterms2(poDto.getIncoterms2());
                entity.setCurrency(poDto.getCurrency());
                entity.setPaymentInDays(poDto.getPaymentInDays());
                entity.setPaymentInDaysNet(poDto.getPaymentInDaysNet());
                entity.setExchangeRate(poDto.getExchangeRate());
                entity.setFixExchangeRate(poDto.getFixExchangeRate());
                entity.setGrMessage(poDto.getGrMessage());
                entity.setCountry(poDto.getCountry());
                entity.setCollectiveNumber(poDto.getCollectiveNumber());
                entity.setWarrantyDays(poDto.getWarrantyDays());
                entity.setConsignmentNetPrice(poDto.getConsignmentNetPrice());
                entity.setRetention(poDto.getRetention());
                entity.setDownpaymentCategory(poDto.getDownpaymentCategory());
                entity.setIncotermsVersion(poDto.getIncotermsVersion());
                entity.setIncotermsLocation1(poDto.getIncotermsLocation1());
                entity.setIncotermsLocation2(poDto.getIncotermsLocation2());
                entity.setShiptoGstin(poDto.getShiptoGstin());
                entity.setShiptoLegalName(poDto.getShiptoLegalName());
                entity.setShiptoTradeName(poDto.getShiptoTradeName());
                entity.setShiptoAddr1(poDto.getShiptoAddr1());
                entity.setShiptoAddr2(poDto.getShiptoAddr2());
                entity.setShiptoLocation(poDto.getShiptoLocation());
                entity.setShiptoPinCode(poDto.getShiptoPinCode());
                entity.setShiptoStateCode(poDto.getShiptoStateCode());
                entity.setPlantCode(poDto.getPlantCode());

                entity.setItemTotal(poDto.getItemTotal());
                entity.setOrderLineReference(poDto.getOrderLineReference());
                entity.setOriginCountry(poDto.getOriginCountry());
                entity.setUniqueItemSlNo(poDto.getUniqueItemSlNo());
                entity.setDeliveryScheduleIdentifier(poDto.getDeliveryScheduleIdentifier());
                entity.setQuantity(poDto.getQuantity());
                entity.setBatchName(poDto.getBatchName());
                entity.setBatchExpiryDate(poDto.getBatchExpiryDate());
                entity.setWarrantyDate(poDto.getWarrantyDate());
                entity.setAttributeDetails(poDto.getAttributeDetails());
                entity.setAttributeValue(poDto.getAttributeValue());
                entity.setTotalTaxableValue(poDto.getTotalTaxableValue());
                entity.setTotalSgstAmt(poDto.getTotalSgstAmount());
                entity.setTotalCgstAmt(poDto.getTotalCgstAmount());
                entity.setTotalIgstAmt(poDto.getTotalIgstAmount());
                entity.setTotalCessAmt(poDto.getTotalCessAmount());
                entity.setTotalStateCessAmt(poDto.getTotalStateCessAmount());
                entity.setTotalDiscount(poDto.getTotalDiscount());
                entity.setTotalOtherCharges(poDto.getTotalOtherCharges());
                entity.setTotalRoundOff(poDto.getTotalRoundOff());
                entity.setTotalDocumentValue(poDto.getTotalDocumentValue());
                entity.setTotalInvoiceValueInForeignCurrency(poDto.getTotalInvoiceValueForeignCurrency());

                entity.setPayeeName(poDto.getPayeeName());
                entity.setAccountNumber(poDto.getAccountNumber());
                entity.setPaymentMode(poDto.getMode());
                entity.setBranchIfscCode(poDto.getBranchIfscCode());
                entity.setTermsOfPayment(poDto.getTermsOfPayment());
                entity.setPaymentInstruction(poDto.getPaymentInstruction());
                entity.setCreditTransfer(poDto.getCreditTransfer());
                entity.setDirectDebit(poDto.getDirectDebit());
                entity.setCreditDays(poDto.getCreditDays());
                entity.setPaidAmount(poDto.getPaidAmount());
                entity.setDueAmount(poDto.getDueAmount());

                entity.setTendRefr(poDto.getTendRefr());
                entity.setContrRefr(poDto.getContrRefr());
                entity.setExtRefr(poDto.getExtRefr());
                entity.setProjRefr(poDto.getProjRefr());
                entity.setUrl(poDto.getUrl());
                entity.setDocs(poDto.getDocs());
                entity.setInfo(poDto.getInfo());

                entity.setAmendPo(poDto.getAmendPo());
                entity.setSiteCode(poDto.getSiteCode());
                entity.setDepartment(poDto.getDepartment());
                entity.setLocation(poDto.getLocation());

                entity.setUserDefinedField1(poDto.getUserDefinedField1());
                entity.setUserDefinedField2(poDto.getUserDefinedField2());
                entity.setUserDefinedField3(poDto.getUserDefinedField3());
                entity.setUserDefinedField4(poDto.getUserDefinedField4());
                entity.setUserDefinedField5(poDto.getUserDefinedField5());
                entity.setUserDefinedField6(poDto.getUserDefinedField6());
                entity.setUserDefinedField7(poDto.getUserDefinedField7());
                entity.setUserDefinedField8(poDto.getUserDefinedField8());
                entity.setUserDefinedField9(poDto.getUserDefinedField9());
                entity.setUserDefinedField10(poDto.getUserDefinedField10());
                entity.setUserDefinedField11(poDto.getUserDefinedField11());
                entity.setUserDefinedField12(poDto.getUserDefinedField12());
                entity.setUserDefinedField13(poDto.getUserDefinedField13());
                entity.setUserDefinedField14(poDto.getUserDefinedField14());
                entity.setUserDefinedField15(poDto.getUserDefinedField15());
                entity.setTotalQuantity(poDto.getTotalQuantity());
                entity.setErrorMessage(poDto.getErrorMessages() != null &&
                        !poDto.getErrorMessages().isEmpty() ? poDto.getErrorMessages().stream()
                        .collect(Collectors.joining(", ")) : "No Error");

                entity.setOtherChargesList(new ArrayList<>());

                double totalQnty = 0.0;

                if (poDto.getOtherChargesDTOList() != null) {
                    for (OtherChargesDTO chargeDto : poDto.getOtherChargesDTOList()) {
                        OtherCharges chargesEntity = new OtherCharges();

                        chargesEntity.setSlNo(chargeDto.getSlNo());
                        chargesEntity.setProductDescription(chargeDto.getProductDescription());
                        chargesEntity.setIsService(chargeDto.getIsService());
                        chargesEntity.setHsnCode(chargeDto.getHsnCode());
                        chargesEntity.setItemBuyerReferenceNumber(chargeDto.getItemBuyerReferenceNumber());
                        chargesEntity.setItemSellerReferenceNumber(chargeDto.getItemSellerReferenceNumber());
                        chargesEntity.setBarCode(chargeDto.getBarCode());
                        chargesEntity.setQuantity(chargeDto.getQuantity());
                        chargesEntity.setFreeQuantity(chargeDto.getFreeQuantity());
                        chargesEntity.setUnit(chargeDto.getUnit());
                        chargesEntity.setUnitPrice(chargeDto.getUnitPrice());
                        chargesEntity.setGrossAmount(chargeDto.getGrossAmount());
                        chargesEntity.setDiscount(chargeDto.getDiscount());
                        chargesEntity.setPreTaxValue(chargeDto.getPreTaxValue());
                        chargesEntity.setTaxableValue(chargeDto.getTaxableValue());
                        chargesEntity.setGstRate(chargeDto.getGstRate());
                        chargesEntity.setSgstAmt(chargeDto.getSgstAmtRs());
                        chargesEntity.setCgstAmt(chargeDto.getCgstAmtRs());
                        chargesEntity.setIgstAmt(chargeDto.getIgstAmtRs());
                        chargesEntity.setCessRate(chargeDto.getCessRate());
                        chargesEntity.setStateCessAdvalAmt(chargeDto.getCessAdvalAmtRs());
                        chargesEntity.setCessNonAdvalAmt(chargeDto.getCessNonAdvalAmtRs());
                        chargesEntity.setStateCessRate(chargeDto.getStateCessRate());
                        chargesEntity.setStateCessAdvalAmt(chargeDto.getStateCessAdvalAmtRs());
                        chargesEntity.setStateCessNonAdvalAmt(chargeDto.getStateCessNonAdvalAmtRs());
                        chargesEntity.setOtherCharges(chargeDto.getOtherCharges());

                        chargesEntity.setPoReference(entity);
                        entity.getOtherChargesList().add(chargesEntity);

                        if (chargesEntity.getQuantity() != null && !chargesEntity.getQuantity().isBlank()) {
                            log.info("Quantity value {}:", chargesEntity.getQuantity());
                            log.info("Before total: {}", totalQnty);
                            totalQnty += Double.parseDouble(chargesEntity.getQuantity().trim());
                            log.info("After total: {}", totalQnty);
                        }
                    }
                }
                entity.setTotalQuantity(totalQnty);

                String key = entity.getPoSaReferenceNumber();
                if (key != null && !key.trim().isEmpty()) {
                    poReferenceMap.put(key, entity);
                } else {
                    // If no key, save directly
                    poReferenceRepository.save(entity);
                }

            } catch (Exception e) {
                log.error("Error saving POReferenceDTO: " + poDto.getPoSaReferenceNumber(), e);
            }
        }

        if (!poReferenceMap.isEmpty()) {
            poReferenceRepository.saveAll(poReferenceMap.values());
        }
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private String getJsonText(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asText() : null;
    }

    public Page<POReference> findAll(Pageable pageable) {
        return poReferenceRepository.findAll(pageable);
    }

    public Page<POReference> filterPOReference(String poSaReferenceNumber, int page, int size) {
        Specification<POReference> spec = Specification.where(POReferenceSpecification.hasPoSaReferenceNumber(poSaReferenceNumber));
        Pageable pageable = PageRequest.of(page, size);
        return poReferenceRepository.findAll(spec, pageable);
    }

    public Page<POReference> filterPOReferences(String poSaReferenceNumber, int page, int size) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<POReference> cq = cb.createQuery(POReference.class);
        Root<POReference> root = cq.from(POReference.class);

        List<Predicate> predicates = new ArrayList<>();

        if (poSaReferenceNumber != null && !poSaReferenceNumber.isEmpty()) {
            predicates.add(cb.equal(root.get("poSaReferenceNumber"), poSaReferenceNumber));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<POReference> query = entityManager.createQuery(cq);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<POReference> resultList = query.getResultList();

        // Count query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<POReference> countRoot = countQuery.from(POReference.class);
        countQuery.select(cb.count(countRoot));
        countQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, PageRequest.of(page, size), count);
    }
}
















//    public Page<POReference> filterPOReference(String poSaReferenceNumber, int page, int size) {
//        QPOReference qPO = QPOReference.pOReference;
//
//        BooleanExpression predicate = qPO.isNotNull(); // always true
//
//        if (poSaReferenceNumber != null && !poSaReferenceNumber.isBlank()) {
//            predicate = predicate.and(qPO.poSaReferenceNumber.eq(poSaReferenceNumber));
//        }
//
//        Pageable pageable = PageRequest.of(page, size);
//        return poReferenceRepository.findAll(predicate, pageable);
//    }



