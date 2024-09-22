package com.example.vacationPayCalculator.controller;

import com.example.vacationPayCalculator.service.VacationPayCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class VacationPayCalculatorControllerTests {
    @Mock
    private VacationPayCalculatorService calculatorService;

    @InjectMocks
    private VacationPayCalculatorController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetVacationPay() {
        double salary = 30000;
        int vacationDays = 10;
        double expectedVacationPay = 1214.58;

        when(calculatorService.calculateVacationPay(salary, vacationDays)).thenReturn(expectedVacationPay);

        ResponseEntity<Map<String, Double>> response = controller.getVacationPay(salary, vacationDays, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedVacationPay, response.getBody().get("vacationPay"));
    }

    @Test
    public void testGetVacationPayWithFirstDay() {
        double salary = 30000;
        int vacationDays = 10;
        LocalDate firstDay = LocalDate.of(2023, 1, 2);
        double expectedVacationPay = 364.37;

        when(calculatorService.calculateDetailedVacationPay(salary, vacationDays, firstDay)).thenReturn(expectedVacationPay);

        ResponseEntity<Map<String, Double>> response = controller.getVacationPay(salary, vacationDays, firstDay);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedVacationPay, response.getBody().get("vacationPay"));
    }

    @Test
    public void testHandleIllegalArgumentException() {
        double salary = -1;
        int vacationDays = 10;

        ResponseEntity<Map<String, String>> response = controller.handleIllegalArgumentException(
                new IllegalArgumentException("Salary and vacation days must be greater than zero.")
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Salary and vacation days must be greater than zero.", response.getBody().get("error"));
    }
}
