package com.viraloab.notificationService.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class NotificationControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> sendMail(Exception e) {
        Map<String, Object> responseEntityMap = new LinkedHashMap<>();
        responseEntityMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseEntityMap.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseEntityMap.put("message", e.getMessage());
        responseEntityMap.put("timestamp", new Date());
        return new ResponseEntity<>(responseEntityMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
