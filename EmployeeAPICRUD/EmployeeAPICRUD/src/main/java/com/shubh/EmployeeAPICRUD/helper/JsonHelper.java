package com.shubh.EmployeeAPICRUD.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubh.EmployeeAPICRUD.dto.EmployeeDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Component
public class JsonHelper {
    public List<EmployeeDTO> convertJsonToEmployeeDTOList(InputStream is){
        try{
            ObjectMapper mapper  = new ObjectMapper();
            return mapper.readValue(is, new TypeReference<List<EmployeeDTO>>() {});
        }catch (Exception e){
            throw new RuntimeException("Failed to parse JSON: " + e.getMessage());
        }
    }

}
