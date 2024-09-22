package com.example.vacationPayCalculator.controller;

import com.example.vacationPayCalculator.service.VacationPayCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VacationPayCalculatorController {

    @Autowired
    private VacationPayCalculatorService calculatorService;
    @GetMapping("/calculacte")
    public ResponseEntity<Map<String, Double>> getVacationPay(
            @RequestParam double salary,
            @RequestParam int vacationDays,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDay) {

        if (salary <= 0 || vacationDays <= 0) {
            throw new IllegalArgumentException("Salary and vacation days must be greater than zero.");
        }
        double vacationPay;
        if (firstDay == null){
            vacationPay = calculatorService.calculateVacationPay(salary, vacationDays);
        } else {
            vacationPay = calculatorService.calculateDetailedVacationPay(salary, vacationDays, firstDay);
        }

        Map<String, Double> response = new HashMap<>();
        response.put("vacationPay", vacationPay);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
