package com.shubh.EmployeeAPICRUD.util;

import com.shubh.EmployeeAPICRUD.entity.POReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelGenerator {

    public static ByteArrayInputStream generateExcelFromEntities(List<POReference> dataList, boolean isErrorFile) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(isErrorFile ? "Errors" : "Success");

            // Create header
            Row header = sheet.createRow(0);
            int i = 0;
            header.createCell(i++).setCellValue("Recipient GSTIN");
            header.createCell(i++).setCellValue("Recipient Legal Name");
            header.createCell(i++).setCellValue("Recipient Trade Name");
            header.createCell(i++).setCellValue("Recipient Addr1");
            header.createCell(i++).setCellValue("Recipient Addr2");
            header.createCell(i++).setCellValue("Recipient Location");
            header.createCell(i++).setCellValue("Recipient Pin Code");
            header.createCell(i++).setCellValue("Recipient State Code");
            header.createCell(i++).setCellValue("Recipient Phone Number");
            header.createCell(i++).setCellValue("Recipient Email Id");
            header.createCell(i++).setCellValue("Supplier GSTIN");
            header.createCell(i++).setCellValue("Supplier PAN");
            header.createCell(i++).setCellValue("Supplier Aadhaar");
            header.createCell(i++).setCellValue("Supplier Code");
            header.createCell(i++).setCellValue("Supplier Legal Name");
            header.createCell(i++).setCellValue("Supplier Trade Name");
            header.createCell(i++).setCellValue("Supplier Addr1");
            header.createCell(i++).setCellValue("Supplier Addr2");
            header.createCell(i++).setCellValue("Supplier Location");
            header.createCell(i++).setCellValue("Supplier Pincode");
            header.createCell(i++).setCellValue("Supplier State Code");
            header.createCell(i++).setCellValue("Supplier Phone  Number");
            header.createCell(i++).setCellValue("Supplier Email ID");
            header.createCell(i++).setCellValue("POS");
            header.createCell(i++).setCellValue("PO / SA Reference Number");
            header.createCell(i++).setCellValue("PO / SA Reference Date");
            header.createCell(i++).setCellValue("PO / SA Validity Date");
            header.createCell(i++).setCellValue("PO other reference Number");
            header.createCell(i++).setCellValue("PO other reference Date");
            header.createCell(i++).setCellValue("Delivery Start Date");
            header.createCell(i++).setCellValue("Delivery End Date");
            header.createCell(i++).setCellValue("Delivery Terms");
            header.createCell(i++).setCellValue("Sales Order Number");
            header.createCell(i++).setCellValue("Sales Order Date");
            header.createCell(i++).setCellValue("Company Code");
            header.createCell(i++).setCellValue("Purchasing Organisation");
            header.createCell(i++).setCellValue("Purchase group");
            header.createCell(i++).setCellValue("Incoterms");
            header.createCell(i++).setCellValue("Incoterms 2");
            header.createCell(i++).setCellValue("Currency");
            header.createCell(i++).setCellValue("Payment in days");
            header.createCell(i++).setCellValue("Payment in days net");
            header.createCell(i++).setCellValue("Exchange rate");
            header.createCell(i++).setCellValue("Fix Exchange Rate");
            header.createCell(i++).setCellValue("GR Message");
            header.createCell(i++).setCellValue("Country");
            header.createCell(i++).setCellValue("Collective Number");
            header.createCell(i++).setCellValue("Warranty Days");
            header.createCell(i++).setCellValue("Consignment Net Price");
            header.createCell(i++).setCellValue("Retention");
            header.createCell(i++).setCellValue("Downpayment Category");
            header.createCell(i++).setCellValue("Incoterms Version");
            header.createCell(i++).setCellValue("Incoterms Location1");
            header.createCell(i++).setCellValue("Incoterms Location2");
            header.createCell(i++).setCellValue("Shipto GSTIN");
            header.createCell(i++).setCellValue("Shipto Legal Name");
            header.createCell(i++).setCellValue("Shipto Trade Name");
            header.createCell(i++).setCellValue("Shipto Addr1");
            header.createCell(i++).setCellValue("Shipto Addr2");
            header.createCell(i++).setCellValue("Shipto Location");
            header.createCell(i++).setCellValue("Shipto Pin Code");
            header.createCell(i++).setCellValue("Ship to State Code");
            header.createCell(i++).setCellValue("Plant Code");

            header.createCell(i++).setCellValue("Item Total");
            header.createCell(i++).setCellValue("Order line reference");
            header.createCell(i++).setCellValue("Origin Country");
            header.createCell(i++).setCellValue("Unique item Sl. No.");
            header.createCell(i++).setCellValue("Delivery Schedule Identifier");
            header.createCell(i++).setCellValue("Quantity");
            header.createCell(i++).setCellValue("Delivery Start Date");
            header.createCell(i++).setCellValue("Delivery End Date");
            header.createCell(i++).setCellValue("Batch Name");
            header.createCell(i++).setCellValue("Batch Expiry Date");
            header.createCell(i++).setCellValue("Warranty Date");
            header.createCell(i++).setCellValue("Attribute Details of the items");
            header.createCell(i++).setCellValue("Attribute Value of the Items");
            header.createCell(i++).setCellValue("Total Taxable value");
            header.createCell(i++).setCellValue("Total Sgst Amt");
            header.createCell(i++).setCellValue("Total Cgst Amt");
            header.createCell(i++).setCellValue("Total Igst Amt");
            header.createCell(i++).setCellValue("Total Cess Amt");
            header.createCell(i++).setCellValue("Total State Cess Amt");
            header.createCell(i++).setCellValue("Total Discount");
            header.createCell(i++).setCellValue("Total Other charges");
            header.createCell(i++).setCellValue("Total Round off");
            header.createCell(i++).setCellValue("Total Document value");
            header.createCell(i++).setCellValue("Total Invoice value in Foreign Currency");
            header.createCell(i++).setCellValue("Payee Name");
            header.createCell(i++).setCellValue("Account Number");
            header.createCell(i++).setCellValue("Mode");
            header.createCell(i++).setCellValue("Branch/IFSC Code");
            header.createCell(i++).setCellValue("Terms of Payment");
            header.createCell(i++).setCellValue("Payment instruction");
            header.createCell(i++).setCellValue("Credit Transfer");
            header.createCell(i++).setCellValue("Direct Debit");
            header.createCell(i++).setCellValue("Credit Days");
            header.createCell(i++).setCellValue("Paided amount");
            header.createCell(i++).setCellValue("Due amount");
            header.createCell(i++).setCellValue("TendRefr");
            header.createCell(i++).setCellValue("ContrRefr");
            header.createCell(i++).setCellValue("ExtRefr");
            header.createCell(i++).setCellValue("ProjRefr");
            header.createCell(i++).setCellValue("Url");
            header.createCell(i++).setCellValue("Docs");
            header.createCell(i++).setCellValue("Info");
            header.createCell(i++).setCellValue("Amend PO (Y/N)");
            header.createCell(i++).setCellValue("Site Code");
            header.createCell(i++).setCellValue("Department");
            header.createCell(i++).setCellValue("Location");
            header.createCell(i++).setCellValue("User Defined Field - 1");
            header.createCell(i++).setCellValue("User Defined Field - 2");
            header.createCell(i++).setCellValue("User Defined Field - 3");
            header.createCell(i++).setCellValue("User Defined Field - 4");
            header.createCell(i++).setCellValue("User Defined Field - 5");
            header.createCell(i++).setCellValue("User Defined Field - 6");
            header.createCell(i++).setCellValue("User Defined Field - 7");
            header.createCell(i++).setCellValue("User Defined Field - 8");
            header.createCell(i++).setCellValue("User Defined Field - 9");
            header.createCell(i++).setCellValue("User Defined Field - 10");
            header.createCell(i++).setCellValue("User Defined Field - 11");
            header.createCell(i++).setCellValue("User Defined Field - 12");
            header.createCell(i++).setCellValue("User Defined Field - 13");
            header.createCell(i++).setCellValue("User Defined Field - 14");
            header.createCell(i++).setCellValue("User Defined Field - 15");
            header.createCell(i++).setCellValue("Total Quantity");
//            // Add more fields as needed...
            if (isErrorFile) {
                header.createCell(i++).setCellValue("Error Message");
            }

            // Populate data
            int rowIdx = 1;
            for (POReference entity : dataList) {
                Row row = sheet.createRow(rowIdx++);
                int j = 0;

                row.createCell(0).setCellValue(entity.getRecipientGstin());
                row.createCell(1).setCellValue(entity.getRecipientLegalName());
                row.createCell(2).setCellValue(entity.getRecipientTradeName());
                row.createCell(3).setCellValue(entity.getRecipientAddr1());
                row.createCell(4).setCellValue(entity.getRecipientAddr2());
                row.createCell(5).setCellValue(entity.getRecipientLocation());
                row.createCell(6).setCellValue(entity.getRecipientPinCode());
                row.createCell(7).setCellValue(entity.getRecipientStateCode());
                row.createCell(8).setCellValue(entity.getRecipientPhoneNumber());
                row.createCell(9).setCellValue(entity.getRecipientEmailId());
                row.createCell(10).setCellValue(entity.getSupplierGstin());
                row.createCell(11).setCellValue(entity.getSupplierPan());
                row.createCell(12).setCellValue(entity.getSupplierAadhaar());
                row.createCell(13).setCellValue(entity.getSupplierCode());
                row.createCell(14).setCellValue(entity.getSupplierLegalName());
                row.createCell(15).setCellValue(entity.getSupplierTradeName());
                row.createCell(16).setCellValue(entity.getSupplierAddr1());
                row.createCell(17).setCellValue(entity.getSupplierAddr2());
                row.createCell(18).setCellValue(entity.getSupplierLocation());
                row.createCell(19).setCellValue(entity.getSupplierPincode());
                row.createCell(20).setCellValue(entity.getSupplierStateCode());
                row.createCell(21).setCellValue(entity.getSupplierPhoneNumber());
                row.createCell(22).setCellValue(entity.getSupplierEmailId());
                row.createCell(23).setCellValue(entity.getPos());
                row.createCell(24).setCellValue(entity.getPoSaReferenceNumber());
                row.createCell(25).setCellValue(entity.getPoSaReferenceDate());
                row.createCell(26).setCellValue(entity.getPoSaValidityDate());
                row.createCell(27).setCellValue(entity.getPoOtherReferenceNumber());
                row.createCell(28).setCellValue(entity.getPoOtherReferenceDate());
                row.createCell(29).setCellValue(entity.getDeliveryStartDate());
                row.createCell(30).setCellValue(entity.getDeliveryEndDate());
                row.createCell(31).setCellValue(entity.getDeliveryTerms());
                row.createCell(32).setCellValue(entity.getSalesOrderNumber());
                row.createCell(33).setCellValue(entity.getSalesOrderDate());
                row.createCell(34).setCellValue(entity.getCompanyCode());
                row.createCell(35).setCellValue(entity.getPurchasingOrganisation());
                row.createCell(36).setCellValue(entity.getPurchaseGroup());
                row.createCell(37).setCellValue(entity.getIncoterms());
                row.createCell(38).setCellValue(entity.getIncoterms2());
                row.createCell(39).setCellValue(entity.getCurrency());
                row.createCell(40).setCellValue(entity.getPaymentInDays());
                row.createCell(41).setCellValue(entity.getPaymentInDaysNet());
                row.createCell(42).setCellValue(entity.getExchangeRate());
                row.createCell(43).setCellValue(entity.getFixExchangeRate());
                row.createCell(44).setCellValue(entity.getGrMessage());
                row.createCell(45).setCellValue(entity.getCountry());
                row.createCell(46).setCellValue(entity.getCollectiveNumber());
                row.createCell(47).setCellValue(entity.getWarrantyDays());
                row.createCell(48).setCellValue(entity.getConsignmentNetPrice());
                row.createCell(49).setCellValue(entity.getRetention());
                row.createCell(50).setCellValue(entity.getDownpaymentCategory());
                row.createCell(51).setCellValue(entity.getIncotermsVersion());
                row.createCell(52).setCellValue(entity.getIncotermsLocation1());
                row.createCell(53).setCellValue(entity.getIncotermsLocation2());
                row.createCell(54).setCellValue(entity.getShiptoGstin());
                row.createCell(55).setCellValue(entity.getShiptoLegalName());
                row.createCell(56).setCellValue(entity.getShiptoTradeName());
                row.createCell(57).setCellValue(entity.getShiptoAddr1());
                row.createCell(58).setCellValue(entity.getShiptoAddr2());
                row.createCell(58).setCellValue(entity.getShiptoLocation());
                row.createCell(60).setCellValue(entity.getShiptoPinCode());
                row.createCell(61).setCellValue(entity.getShiptoStateCode());
                row.createCell(62).setCellValue(entity.getPlantCode());

                row.createCell(63).setCellValue(entity.getItemTotal());
                row.createCell(64).setCellValue(entity.getOrderLineReference());
                row.createCell(65).setCellValue(entity.getOriginCountry());
                row.createCell(66).setCellValue(entity.getUniqueItemSlNo());
                row.createCell(67).setCellValue(entity.getDeliveryScheduleIdentifier());
                row.createCell(68).setCellValue(entity.getQuantity());
                row.createCell(69).setCellValue(entity.getDeliveryStartDate());
                row.createCell(70).setCellValue(entity.getDeliveryEndDate());
                row.createCell(71).setCellValue(entity.getBatchName());
                row.createCell(72).setCellValue(entity.getBatchExpiryDate());
                row.createCell(73).setCellValue(entity.getWarrantyDate());
                row.createCell(74).setCellValue(entity.getAttributeDetails());
                row.createCell(75).setCellValue(entity.getAttributeValue());
                row.createCell(76).setCellValue(entity.getTotalTaxableValue());
                row.createCell(77).setCellValue(entity.getTotalSgstAmt());
                row.createCell(78).setCellValue(entity.getTotalCgstAmt());
                row.createCell(79).setCellValue(entity.getTotalIgstAmt());
                row.createCell(80).setCellValue(entity.getTotalCessAmt());
                row.createCell(81).setCellValue(entity.getTotalStateCessAmt());
                row.createCell(82).setCellValue(entity.getTotalDiscount());
                row.createCell(83).setCellValue(entity.getTotalOtherCharges());
                row.createCell(84).setCellValue(entity.getTotalRoundOff());
                row.createCell(85).setCellValue(entity.getTotalDocumentValue());
                row.createCell(86).setCellValue(entity.getTotalInvoiceValueInForeignCurrency());
                row.createCell(87).setCellValue(entity.getPayeeName());
                row.createCell(88).setCellValue(entity.getAccountNumber());
                row.createCell(89).setCellValue(entity.getPaymentMode());
                row.createCell(90).setCellValue(entity.getBranchIfscCode());
                row.createCell(91).setCellValue(entity.getTermsOfPayment());
                row.createCell(92).setCellValue(entity.getPaymentInstruction());
                row.createCell(93).setCellValue(entity.getCreditTransfer());
                row.createCell(94).setCellValue(entity.getDirectDebit());
                row.createCell(95).setCellValue(entity.getCreditDays());
                row.createCell(96).setCellValue(entity.getPaidAmount());
                row.createCell(97).setCellValue(entity.getDueAmount());
                row.createCell(98).setCellValue(entity.getTendRefr());
                row.createCell(99).setCellValue(entity.getContrRefr());
                row.createCell(100).setCellValue(entity.getExtRefr());
                row.createCell(101).setCellValue(entity.getProjRefr());
                row.createCell(102).setCellValue(entity.getUrl());
                row.createCell(103).setCellValue(entity.getDocs());
                row.createCell(104).setCellValue(entity.getInfo());
                row.createCell(105).setCellValue(entity.getAmendPo());
                row.createCell(106).setCellValue(entity.getSiteCode());
                row.createCell(107).setCellValue(entity.getDepartment());
                row.createCell(108).setCellValue(entity.getLocation());
                row.createCell(109).setCellValue(entity.getUserDefinedField1());
                row.createCell(110).setCellValue(entity.getUserDefinedField2());
                row.createCell(111).setCellValue(entity.getUserDefinedField3());
                row.createCell(112).setCellValue(entity.getUserDefinedField4());
                row.createCell(113).setCellValue(entity.getUserDefinedField5());
                row.createCell(114).setCellValue(entity.getUserDefinedField6());
                row.createCell(115).setCellValue(entity.getUserDefinedField7());
                row.createCell(116).setCellValue(entity.getUserDefinedField8());
                row.createCell(117).setCellValue(entity.getUserDefinedField9());
                row.createCell(118).setCellValue(entity.getUserDefinedField10());
                row.createCell(119).setCellValue(entity.getUserDefinedField11());
                row.createCell(120).setCellValue(entity.getUserDefinedField12());
                row.createCell(121).setCellValue(entity.getUserDefinedField13());
                row.createCell(123).setCellValue(entity.getUserDefinedField14());
                row.createCell(124).setCellValue(entity.getUserDefinedField15());
                row.createCell(125).setCellValue(entity.getTotalQuantity() != null ? entity.getTotalQuantity().toString() : "");

//                // Add more fields...

                if (isErrorFile) {
                    row.createCell(126).setCellValue(entity.getErrorMessage());
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }

        }
    }


//    public static ByteArrayInputStream generateExcel(List<POReferenceDTO> poReferenceDTOList, boolean isErrorFile) {
//
//        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//            Sheet sheet = workbook.createSheet(isErrorFile ? "Errors" : "Success");
//            Row header = sheet.createRow(0);
//
//            Field[] fields = POReferenceDTO.class.getDeclaredFields();
//            int col = 0;
//
//            for (Field field : fields) {
//                field.setAccessible(true);
//                header.createCell(col++).setCellValue(field.getName());
//            }
//
//
//            if (isErrorFile) {
//                header.createCell(col).setCellValue("Error Message");
//            }
//
//
//            int rowIdx = 1;
//            for (POReferenceDTO dto : poReferenceDTOList) {
//                boolean hasError = dto.getErrorMessage() != null && !dto.getErrorMessage().isEmpty();
//
//                // Filter records based on success or error flag
//                if ((isErrorFile && !hasError) || (!isErrorFile && hasError)) {
//                    continue;
//                }
//
//                Row row = sheet.createRow(rowIdx++);
//                col = 0;
//                for (Field field : fields) {
//                    field.setAccessible(true);
//                    Object value = field.get(dto);
//                    row.createCell(col++).setCellValue(value != null ? value.toString() : "");
//                }
//
//                if (isErrorFile) {
//                    row.createCell(col).setCellValue(String.join(", ", dto.getErrorMessage()));
//                }
//            }
//            workbook.write(out);
//            return new ByteArrayInputStream(out.toByteArray());
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate Excel file: " + e.getMessage());
//        }
//    }


//    public static ByteArrayInputStream generatePOReferenceExcel(List<POReference> records) throws IOException {
//        String[] columns = {"ID", "PO/SA Reference Number", "Total Quantity", "Status"};
//
//        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//            Sheet sheet = workbook.createSheet("PO Reference");
//            Row headerRow = sheet.createRow(0);
//            for (int col = 0; col < columns.length; col++) {
//                Cell cell = headerRow.createCell(col);
//                cell.setCellValue(columns[col]);
//            }
//
//            int rowIdx = 1;
//            for (POReference ref : records) {
//                Row row = sheet.createRow(rowIdx++);
//                row.createCell(0).setCellValue(ref.getId());
//                row.createCell(1).setCellValue(ref.getPoSaReferenceNumber());
//                row.createCell(2).setCellValue(ref.getTotalQuantity()); // Example field
//                row.createCell(3).setCellValue(ref.getStatus()); // Assuming you track success/error
//            }
//            workbook.write(out);
//            return new ByteArrayInputStream(out.toByteArray());
//            }
//
//        }
//
//    }

//    public static ByteArrayInputStream generateExcel(List<POReferenceDTO> poReferenceDTOList, boolean isErrorFile) {
//        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//            Sheet sheet = workbook.createSheet(isErrorFile ? "errors" : "success");
//            Row header = sheet.createRow(0);
//
//            Field[] fields = POReferenceDTO.class.getDeclaredFields();
//
//            int col = 0;
//            for (Field field : fields) {
//                field.setAccessible(true);
//                header.createCell(col++).setCellValue(field.getName());
//            }
//
//
//            int rowIdx = 1;
//            for (POReferenceDTO dto : poReferenceDTOList) {
//                Row row = sheet.createRow(rowIdx++);
//                col = 0;
//                for (Field field : fields) {
//                    field.setAccessible(true);
//                    Object value = field.get(dto);
//                    row.createCell(col++).setCellValue(value != null ? value.toString() : "");
//                }
//                if (isErrorFile) {
//                    row.createCell(col).setCellValue(String.join(", ", dto.getErrorMessages()));
//                }
//            }
//            workbook.write(out);
//            return new ByteArrayInputStream(out.toByteArray());
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate Excel file: " + e.getMessage());
//        }
//    }

//    public ByteArrayInputStream generateSuccessExcel(List<POReferenceDTO> successList) {
//        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//            Sheet sheet = workbook.createSheet("Success");
//
//            // Set headers
//            int i = -1;
//            Row header = sheet.createRow(i++);
//            header.createCell(i++).setCellValue("Recipient GSTIN");
//            header.createCell(i++).setCellValue("Recipient Legal Name");
//            header.createCell(i++).setCellValue("Recipient Trade Name");
//            header.createCell(i++).setCellValue("Recipient Addr1");
//            header.createCell(i++).setCellValue("Recipient Addr2");
//            header.createCell(i++).setCellValue("Recipient Location");
//            header.createCell(i++).setCellValue("Recipient Pin Code");
//            header.createCell(i++).setCellValue("Recipient State Code");
//            header.createCell(i++).setCellValue("Recipient Phone Number");
//            header.createCell(i++).setCellValue("Recipient Email Id");
//            header.createCell(i++).setCellValue("Supplier GSTIN");
//            header.createCell(i++).setCellValue("Supplier PAN");
//            header.createCell(i++).setCellValue("Supplier Aadhaar");
//            header.createCell(i++).setCellValue("Supplier Code");
//            header.createCell(i++).setCellValue("Supplier Legal Name");
//            header.createCell(i++).setCellValue("Supplier Trade Name");
//            header.createCell(i++).setCellValue("Supplier Addr1");
//            header.createCell(i++).setCellValue("Supplier Addr2");
//            header.createCell(i++).setCellValue("Supplier Location");
//            header.createCell(i++).setCellValue("Supplier Pincode");
//            header.createCell(i++).setCellValue("Supplier State Code");
//            header.createCell(i++).setCellValue("Supplier Phone  Number");
//            header.createCell(i++).setCellValue("Supplier Email ID");
//            header.createCell(i++).setCellValue("POS");
//            header.createCell(i++).setCellValue("PO / SA Reference Number");
//            header.createCell(i++).setCellValue("PO / SA Reference Date");
//            header.createCell(i++).setCellValue("PO / SA Validity Date");
//            header.createCell(i++).setCellValue("PO other reference Number");
//            header.createCell(i++).setCellValue("PO other reference Date");
//            header.createCell(i++).setCellValue("Delivery Start Date");
//            header.createCell(i++).setCellValue("Delivery End Date");
//            header.createCell(i++).setCellValue("Delivery Terms");
//            header.createCell(i++).setCellValue("Sales Order Number");
//            header.createCell(i++).setCellValue("Sales Order Date");
//            header.createCell(i++).setCellValue("Company Code");
//            header.createCell(i++).setCellValue("Purchasing Organisation");
//            header.createCell(i++).setCellValue("Purchase group");
//            header.createCell(i++).setCellValue("Incoterms");
//            header.createCell(i++).setCellValue("Incoterms 2");
//            header.createCell(i++).setCellValue("Currency");
//            header.createCell(i++).setCellValue("Payment in days");
//            header.createCell(i++).setCellValue("Payment in days net");
//            header.createCell(i++).setCellValue("Exchange rate");
//            header.createCell(i++).setCellValue("Fix Exchange Rate");
//            header.createCell(i++).setCellValue("GR Message");
//            header.createCell(i++).setCellValue("Country");
//            header.createCell(i++).setCellValue("Collective Number");
//            header.createCell(i++).setCellValue("Warranty Days");
//            header.createCell(i++).setCellValue("Consignment Net Price");
//            header.createCell(i++).setCellValue("Retention");
//            header.createCell(i++).setCellValue("Downpayment Category");
//            header.createCell(i++).setCellValue("Incoterms Version");
//            header.createCell(i++).setCellValue("Incoterms Location1");
//            header.createCell(i++).setCellValue("Incoterms Location2");
//            header.createCell(i++).setCellValue("Shipto GSTIN");
//            header.createCell(i++).setCellValue("Shipto Legal Name");
//            header.createCell(i++).setCellValue("Shipto Trade Name");
//            header.createCell(i++).setCellValue("Shipto Addr1");
//            header.createCell(i++).setCellValue("Shipto Addr2");
//            header.createCell(i++).setCellValue("Shipto Location");
//            header.createCell(i++).setCellValue("Shipto Pin Code");
//            header.createCell(i++).setCellValue("Ship to State Code");
//            header.createCell(i++).setCellValue("Plant Code");
//
//            header.createCell(i++).setCellValue("Item Total");
//            header.createCell(i++).setCellValue("Order line reference");
//            header.createCell(i++).setCellValue("Origin Country");
//            header.createCell(i++).setCellValue("Unique item Sl. No.");
//            header.createCell(i++).setCellValue("Delivery Schedule Identifier");
//            header.createCell(i++).setCellValue("Quantity");
//            header.createCell(i++).setCellValue("Delivery Start Date");
//            header.createCell(i++).setCellValue("Delivery End Date");
//            header.createCell(i++).setCellValue("Batch Name");
//            header.createCell(i++).setCellValue("Batch Expiry Date");
//            header.createCell(i++).setCellValue("Warranty Date");
//            header.createCell(i++).setCellValue("Attribute Details of the items");
//            header.createCell(i++).setCellValue("Attribute Value of the Items");
//            header.createCell(i++).setCellValue("Total Taxable value");
//            header.createCell(i++).setCellValue("Total Sgst Amt");
//            header.createCell(i++).setCellValue("Total Cgst Amt");
//            header.createCell(i++).setCellValue("Total Igst Amt");
//            header.createCell(i++).setCellValue("Total Cess Amt");
//            header.createCell(i++).setCellValue("Total State Cess Amt");
//            header.createCell(i++).setCellValue("Total Discount");
//            header.createCell(i++).setCellValue("Total Other charges");
//            header.createCell(i++).setCellValue("Total Round off");
//            header.createCell(i++).setCellValue("Total Document value");
//            header.createCell(i++).setCellValue("Total Invoice value in Foreign Currency");
//            header.createCell(i++).setCellValue("Payee Name");
//            header.createCell(i++).setCellValue("Account Number");
//            header.createCell(i++).setCellValue("Mode");
//            header.createCell(i++).setCellValue("Branch/IFSC Code");
//            header.createCell(i++).setCellValue("Terms of Payment");
//            header.createCell(i++).setCellValue("Payment instruction");
//            header.createCell(i++).setCellValue("Credit Transfer");
//            header.createCell(i++).setCellValue("Direct Debit");
//            header.createCell(i++).setCellValue("Credit Days");
//            header.createCell(i++).setCellValue("Paided amount");
//            header.createCell(i++).setCellValue("Due amount");
//            header.createCell(i++).setCellValue("TendRefr");
//            header.createCell(i++).setCellValue("ContrRefr");
//            header.createCell(i++).setCellValue("ExtRefr");
//            header.createCell(i++).setCellValue("ProjRefr");
//            header.createCell(i++).setCellValue("Url");
//            header.createCell(i++).setCellValue("Docs");
//            header.createCell(i++).setCellValue("Info");
//            header.createCell(i++).setCellValue("Amend PO (Y/N)");
//            header.createCell(i++).setCellValue("Site Code");
//            header.createCell(i++).setCellValue("Department");
//            header.createCell(i++).setCellValue("Location");
//            header.createCell(i++).setCellValue("User Defined Field - 1");
//            header.createCell(i++).setCellValue("User Defined Field - 2");
//            header.createCell(i++).setCellValue("User Defined Field - 3");
//            header.createCell(i++).setCellValue("User Defined Field - 4");
//            header.createCell(i++).setCellValue("User Defined Field - 5");
//            header.createCell(i++).setCellValue("User Defined Field - 6");
//            header.createCell(i++).setCellValue("User Defined Field - 7");
//            header.createCell(i++).setCellValue("User Defined Field - 8");
//            header.createCell(i++).setCellValue("User Defined Field - 9");
//            header.createCell(i++).setCellValue("User Defined Field - 10");
//            header.createCell(i++).setCellValue("User Defined Field - 11");
//            header.createCell(i++).setCellValue("User Defined Field - 12");
//            header.createCell(i++).setCellValue("User Defined Field - 13");
//            header.createCell(i++).setCellValue("User Defined Field - 14");
//            header.createCell(i++).setCellValue("User Defined Field - 15");
//            header.createCell(i++).setCellValue("Total Quantity");
//            // Add more fields as needed...
//
//            int rowIdx = 1;
//            for (POReferenceDTO dto : successList) {
//                Row row = sheet.createRow(rowIdx++);
//                row.createCell(0).setCellValue(dto.getRecipientGstin());
//                row.createCell(1).setCellValue(dto.getRecipientLegalName());
//                row.createCell(2).setCellValue(dto.getRecipientTradeName());
//                row.createCell(3).setCellValue(dto.getRecipientAddr1());
//                row.createCell(4).setCellValue(dto.getRecipientAddr2());
//                row.createCell(5).setCellValue(dto.getRecipientLocation());
//                row.createCell(6).setCellValue(dto.getRecipientPinCode());
//                row.createCell(7).setCellValue(dto.getRecipientStateCode());
//                row.createCell(8).setCellValue(dto.getRecipientPhoneNumber());
//                row.createCell(9).setCellValue(dto.getRecipientEmailId());
//                row.createCell(10).setCellValue(dto.getSupplierGstin());
//                row.createCell(11).setCellValue(dto.getSupplierPan());
//                row.createCell(12).setCellValue(dto.getSupplierAadhaar());
//                row.createCell(13).setCellValue(dto.getSupplierCode());
//                row.createCell(14).setCellValue(dto.getSupplierLegalName());
//                row.createCell(15).setCellValue(dto.getSupplierTradeName());
//                row.createCell(16).setCellValue(dto.getSupplierAddr1());
//                row.createCell(17).setCellValue(dto.getSupplierAddr2());
//                row.createCell(18).setCellValue(dto.getSupplierLocation());
//                row.createCell(19).setCellValue(dto.getSupplierPincode());
//                row.createCell(20).setCellValue(dto.getSupplierStateCode());
//                row.createCell(21).setCellValue(dto.getSupplierPhoneNumber());
//                row.createCell(22).setCellValue(dto.getSupplierEmailId());
//                row.createCell(23).setCellValue(dto.getPos());
//                row.createCell(24).setCellValue(dto.getPoSaReferenceNumber());
//                row.createCell(25).setCellValue(dto.getPoSaReferenceDate());
//                row.createCell(26).setCellValue(dto.getPoSaValidityDate());
//                row.createCell(27).setCellValue(dto.getPoOtherReferenceNumber());
//                row.createCell(28).setCellValue(dto.getPoOtherReferenceDate());
//                row.createCell(29).setCellValue(dto.getDeliveryStartDate());
//                row.createCell(30).setCellValue(dto.getDeliveryEndDate());
//                row.createCell(31).setCellValue(dto.getDeliveryTerms());
//                row.createCell(32).setCellValue(dto.getSalesOrderNumber());
//                row.createCell(33).setCellValue(dto.getSalesOrderDate());
//                row.createCell(34).setCellValue(dto.getCompanyCode());
//                row.createCell(35).setCellValue(dto.getPurchasingOrganisation());
//                row.createCell(36).setCellValue(dto.getPurchaseGroup());
//                row.createCell(37).setCellValue(dto.getIncoterms());
//                row.createCell(38).setCellValue(dto.getIncoterms2());
//                row.createCell(39).setCellValue(dto.getCurrency());
//                row.createCell(40).setCellValue(dto.getPaymentInDays());
//                row.createCell(41).setCellValue(dto.getPaymentInDaysNet());
//                row.createCell(42).setCellValue(dto.getExchangeRate());
//                row.createCell(43).setCellValue(dto.getFixExchangeRate());
//                row.createCell(44).setCellValue(dto.getGrMessage());
//                row.createCell(45).setCellValue(dto.getCountry());
//                row.createCell(46).setCellValue(dto.getCollectiveNumber());
//                row.createCell(47).setCellValue(dto.getWarrantyDays());
//                row.createCell(48).setCellValue(dto.getConsignmentNetPrice());
//                row.createCell(49).setCellValue(dto.getRetention());
//                row.createCell(50).setCellValue(dto.getDownpaymentCategory());
//                row.createCell(51).setCellValue(dto.getIncotermsVersion());
//                row.createCell(52).setCellValue(dto.getIncotermsLocation1());
//                row.createCell(53).setCellValue(dto.getIncotermsLocation2());
//                row.createCell(54).setCellValue(dto.getShiptoGstin());
//                row.createCell(55).setCellValue(dto.getShiptoLegalName());
//                row.createCell(56).setCellValue(dto.getShiptoTradeName());
//                row.createCell(57).setCellValue(dto.getShiptoAddr1());
//                row.createCell(58).setCellValue(dto.getShiptoAddr2());
//                row.createCell(58).setCellValue(dto.getShiptoLocation());
//                row.createCell(60).setCellValue(dto.getShiptoPinCode());
//                row.createCell(61).setCellValue(dto.getShiptoStateCode());
//                row.createCell(62).setCellValue(dto.getPlantCode());
//
//                row.createCell(63).setCellValue(dto.getItemTotal());
//                row.createCell(64).setCellValue(dto.getOrderLineReference());
//                row.createCell(65).setCellValue(dto.getOriginCountry());
//                row.createCell(66).setCellValue(dto.getUniqueItemSlNo());
//                row.createCell(67).setCellValue(dto.getDeliveryScheduleIdentifier());
//                row.createCell(68).setCellValue(dto.getQuantity());
//                row.createCell(69).setCellValue(dto.getDeliveryStartDate());
//                row.createCell(70).setCellValue(dto.getDeliveryEndDate());
//                row.createCell(71).setCellValue(dto.getBatchName());
//                row.createCell(72).setCellValue(dto.getBatchExpiryDate());
//                row.createCell(73).setCellValue(dto.getWarrantyDate());
//                row.createCell(74).setCellValue(dto.getAttributeDetails());
//                row.createCell(75).setCellValue(dto.getAttributeValue());
//                row.createCell(76).setCellValue(dto.getTotalTaxableValue());
//                row.createCell(77).setCellValue(dto.getTotalSgstAmount());
//                row.createCell(78).setCellValue(dto.getTotalCgstAmount());
//                row.createCell(79).setCellValue(dto.getTotalIgstAmount());
//                row.createCell(80).setCellValue(dto.getTotalCessAmount());
//                row.createCell(81).setCellValue(dto.getTotalStateCessAmount());
//                row.createCell(82).setCellValue(dto.getTotalDiscount());
//                row.createCell(83).setCellValue(dto.getTotalOtherCharges());
//                row.createCell(84).setCellValue(dto.getTotalRoundOff());
//                row.createCell(85).setCellValue(dto.getTotalDocumentValue());
//                row.createCell(86).setCellValue(dto.getTotalInvoiceValueForeignCurrency());
//                row.createCell(87).setCellValue(dto.getPayeeName());
//                row.createCell(88).setCellValue(dto.getAccountNumber());
//                row.createCell(89).setCellValue(dto.getMode());
//                row.createCell(90).setCellValue(dto.getBranchIfscCode());
//                row.createCell(91).setCellValue(dto.getTermsOfPayment());
//                row.createCell(92).setCellValue(dto.getPaymentInstruction());
//                row.createCell(93).setCellValue(dto.getCreditTransfer());
//                row.createCell(94).setCellValue(dto.getDirectDebit());
//                row.createCell(95).setCellValue(dto.getCreditDays());
//                row.createCell(96).setCellValue(dto.getPaidAmount());
//                row.createCell(97).setCellValue(dto.getDueAmount());
//                row.createCell(98).setCellValue(dto.getTendRefr());
//                row.createCell(99).setCellValue(dto.getContrRefr());
//                row.createCell(100).setCellValue(dto.getExtRefr());
//                row.createCell(101).setCellValue(dto.getProjRefr());
//                row.createCell(102).setCellValue(dto.getUrl());
//                row.createCell(103).setCellValue(dto.getDocs());
//                row.createCell(104).setCellValue(dto.getInfo());
//                row.createCell(105).setCellValue(dto.getAmendPo());
//                row.createCell(106).setCellValue(dto.getSiteCode());
//                row.createCell(107).setCellValue(dto.getDepartment());
//                row.createCell(108).setCellValue(dto.getLocation());
//                row.createCell(109).setCellValue(dto.getUserDefinedField1());
//                row.createCell(110).setCellValue(dto.getUserDefinedField2());
//                row.createCell(111).setCellValue(dto.getUserDefinedField3());
//                row.createCell(112).setCellValue(dto.getUserDefinedField4());
//                row.createCell(113).setCellValue(dto.getUserDefinedField5());
//                row.createCell(114).setCellValue(dto.getUserDefinedField6());
//                row.createCell(115).setCellValue(dto.getUserDefinedField7());
//                row.createCell(116).setCellValue(dto.getUserDefinedField8());
//                row.createCell(117).setCellValue(dto.getUserDefinedField9());
//                row.createCell(118).setCellValue(dto.getUserDefinedField10());
//                row.createCell(119).setCellValue(dto.getUserDefinedField11());
//                row.createCell(120).setCellValue(dto.getUserDefinedField12());
//                row.createCell(121).setCellValue(dto.getUserDefinedField13());
//                row.createCell(123).setCellValue(dto.getUserDefinedField14());
//                row.createCell(124).setCellValue(dto.getUserDefinedField15());
//                row.createCell(125).setCellValue(dto.getTotalQuantity());
//                // Add more fields...
//            }
//
//            workbook.write(out);
//            return new ByteArrayInputStream(out.toByteArray());
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate Excel file: " + e.getMessage());
//        }
//    }


