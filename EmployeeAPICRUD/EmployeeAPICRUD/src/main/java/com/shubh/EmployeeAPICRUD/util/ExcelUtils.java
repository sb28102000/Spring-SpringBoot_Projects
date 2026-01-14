package com.shubh.EmployeeAPICRUD.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import static java.sql.JDBCType.BOOLEAN;
import static java.sql.JDBCType.NUMERIC;

@Slf4j
public class ExcelUtils {

    public static String getCellStringValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                String str = cell.getStringCellValue().trim();
                return str.isEmpty() ? null : str;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    log.info("get cell Recipient Pincode: {}", cell.getDateCellValue());
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    try {
                        return String.valueOf(cell.getNumericCellValue());
                    } catch (Exception ex) {
                        return null;
                    }
                }
            default:
                return null;
        }
    }

    private String getJsonText(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asText() : null;
    }
}
