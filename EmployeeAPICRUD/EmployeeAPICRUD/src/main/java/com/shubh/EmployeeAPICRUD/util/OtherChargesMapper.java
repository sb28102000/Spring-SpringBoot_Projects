//package com.shubh.EmployeeAPICRUD.util;
//
//import com.shubh.EmployeeAPICRUD.dto.OtherChargesDTO;
//import com.shubh.EmployeeAPICRUD.entity.OtherCharges;
//import org.springframework.stereotype.Component;
//
//@Component
//public class OtherChargesMapper {
//
//    // Entity to DTO
//    public OtherChargesDTO toDTO(OtherCharges entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        OtherChargesDTO dto = new OtherChargesDTO();
//        dto.setSlNo(entity.getSlNo());
//        dto.setProductDescription(entity.getProductDescription());
//        dto.setIsService(entity.getIsService());
//        dto.setHsnCode(entity.getHsnCode());
//        dto.setItemBuyerReferenceNumber(entity.getItemBuyerReferenceNumber());
//        dto.setItemSellerReferenceNumber(entity.getItemSellerReferenceNumber());
//        dto.setBarCode(entity.getBarCode());
//        dto.setQuantity(String.valueOf(entity.getQuantity()));
//        dto.setFreeQuantity(String.valueOf(entity.getFreeQuantity()));
//        dto.setUnit(entity.getUnit());
//        dto.setUnitPrice(String.valueOf(entity.getUnitPrice()));
//        dto.setGrossAmount(String.valueOf(entity.getGrossAmount()));
//        dto.setDiscount(String.valueOf(entity.getDiscount()));
//        dto.setPreTaxValue(String.valueOf(entity.getPreTaxValue()));
//        dto.setTaxableValue(String.valueOf(entity.getTaxableValue()));
//        dto.setGstRate(String.valueOf(entity.getGstRate()));
//        dto.setSgstAmtRs(String.valueOf(entity.getSgstAmt()));
//        dto.setCgstAmtRs(String.valueOf(entity.getCgstAmt()));
//        dto.setIgstAmtRs(String.valueOf(entity.getIgstAmt()));
//        dto.setCessRate(String.valueOf(entity.getCessRate()));
//        dto.setCessAdvalAmtRs(String.valueOf(entity.getCessAmtAdval()));
//        dto.setCessNonAdvalAmtRs(String.valueOf(entity.getCessNonAdvalAmt()));
//        dto.setStateCessRate(String.valueOf(entity.getStateCessRate()));
//        dto.setStateCessAdvalAmtRs(String.valueOf(entity.getStateCessAdvalAmt()));
//        dto.setStateCessNonAdvalAmtRs(entity.getStateCessNonAdvalAmt());
//        dto.setOtherCharges(entity.getOtherCharges());
//        return dto;
//    }
//
//    // DTO to Entity
//    public OtherCharges toEntity(OtherChargesDTO dto) {
//        if (dto == null) {
//            return null;
//        }
//
//        OtherCharges entity = new OtherCharges();
//        entity.setSlNo(dto.getSlNo());
//        entity.setProductDescription(dto.getProductDescription());
//        entity.setIsService(String.valueOf(dto.getIsService()));
//        entity.setHsnCode(dto.getHsnCode());
//        entity.setItemBuyerReferenceNumber(dto.getItemBuyerReferenceNumber());
//        entity.setItemSellerReferenceNumber(dto.getItemSellerReferenceNumber());
//        entity.setBarCode(dto.getBarCode());
//        entity.setQuantity(String.valueOf(dto.getQuantity()));
//        entity.setFreeQuantity(String.valueOf(dto.getFreeQuantity()));
//        entity.setUnit(dto.getUnit());
//        entity.setUnitPrice(String.valueOf(dto.getUnitPrice()));
//        entity.setGrossAmount(String.valueOf(dto.getGrossAmount()));
//        entity.setDiscount(String.valueOf(dto.getDiscount()));
//        entity.setPreTaxValue(String.valueOf(dto.getPreTaxValue()));
//        entity.setTaxableValue(String.valueOf(dto.getTaxableValue()));
//        entity.setGstRate(String.valueOf(dto.getGstRate()));
//        entity.setSgstAmt(String.valueOf(dto.getSgstAmtRs()));
//        entity.setCgstAmt(String.valueOf(dto.getCgstAmtRs()));
//        entity.setIgstAmt(String.valueOf(dto.getIgstAmtRs()));
//        entity.setCessRate(String.valueOf(dto.getCessRate()));
//        entity.setCessAmtAdval(String.valueOf(dto.getCessAdvalAmtRs()));
//        entity.setCessNonAdvalAmt(String.valueOf(dto.getCessNonAdvalAmtRs()));
//        entity.setStateCessRate(String.valueOf(dto.getStateCessRate()));
//        entity.setStateCessAdvalAmt(String.valueOf(dto.getStateCessAdvalAmtRs()));
//        entity.setStateCessNonAdvalAmt(String.valueOf(dto.getStateCessNonAdvalAmtRs()));
//        entity.setOtherCharges(String.valueOf(dto.getOtherCharges()));
//        return entity;
//    }
//
//}
