package com.example.demo.utils;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class response {
    public static ResponseEntity<Map<String, Object>> createResponse(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    public static boolean isValidParam(String param) {
        return !Pattern.compile("([';]+|(--)+|\\b(delete|drop|truncate)\\b)", Pattern.CASE_INSENSITIVE)
                .matcher(param).find();
    }
}
