package com.example.vacationPayCalculator.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class VacationPayCalculatorService {
    private final int workingDaysPerYear = 247;
    private static Set<MonthDay> holidays = new HashSet<>();

    static {
        holidays.add(MonthDay.of(1, 1));
        holidays.add(MonthDay.of(1, 2));
        holidays.add(MonthDay.of(1, 3));
        holidays.add(MonthDay.of(1, 4));
        holidays.add(MonthDay.of(1, 5));
        holidays.add(MonthDay.of(1, 6));
        holidays.add(MonthDay.of(1, 7));
        holidays.add(MonthDay.of(1, 8));
        holidays.add(MonthDay.of(5, 1));
        holidays.add(MonthDay.of(5, 9));
        holidays.add(MonthDay.of(2, 23));
        holidays.add(MonthDay.of(3, 8));
        holidays.add(MonthDay.of(6, 12));
        holidays.add(MonthDay.of(11, 6));
    }
    public double calculateVacationPay(double salary, int vacationDays){
        double averageDailySalary = salary / workingDaysPerYear;
        return averageDailySalary * vacationDays;
    }

    public double calculateDetailedVacationPay(double salary, int vacationDays, LocalDate firstDay){
        double averageDailySalary = salary / workingDaysPerYear;
        int workingDays = getWorkingDays(vacationDays, firstDay);
        return averageDailySalary * workingDays;
    }

    private int getWorkingDays(int vacationDays, LocalDate firstDay){
        int workingDays = 0;
        LocalDate day = firstDay;
        for (int i=0; i<vacationDays; i++){
            if (day.getDayOfWeek() != DayOfWeek.SATURDAY &&
                    day.getDayOfWeek() != DayOfWeek.SUNDAY &&
                    !holidays.contains(MonthDay.from(day)))
                workingDays++;
            day = day.plusDays(1);
        }
        return workingDays;
    }
}
