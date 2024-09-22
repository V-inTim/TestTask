package com.example.vacationPayCalculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VacationPayCalculatorServiceTests {
    private VacationPayCalculatorService service;

    @BeforeEach
    public void setUp() {
        service = new VacationPayCalculatorService();
    }

    @Test
    public void testCalculateVacationPay() {
        double salary = 30000;
        int vacationDays = 10;
        double expectedVacationPay = 1214.58; // 30000 / 247 * 10
        double actualVacationPay = service.calculateVacationPay(salary, vacationDays);
        assertEquals(expectedVacationPay, actualVacationPay, 0.01);
    }

    @Test
    public void testCalculateDetailedVacationPay() {
        double salary = 30000;
        int vacationDays = 10;
        LocalDate firstDay = LocalDate.of(2023, 1, 2); // Assuming no holidays in this period
        double expectedVacationPay = 364.37;
        double actualVacationPay = service.calculateDetailedVacationPay(salary, vacationDays, firstDay);
        assertEquals(expectedVacationPay, actualVacationPay, 0.01);
    }


}
