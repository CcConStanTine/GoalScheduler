package com.pk.ms.entities.month;

import com.pk.ms.constants.MonthName;
import com.pk.ms.entities.year.Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonthTest {

    Year year2020;
    Year year2021;
    Month month2020_1;
    Month month2020_2;
    Month month2020_3;
    Month month2020_4;
    Month month2020_5;
    Month month2020_6;
    Month month2020_7;
    Month month2020_8;
    Month month2020_9;
    Month month2020_10;
    Month month2020_11;
    Month month2020_12;
    Month month2021_2;

    @BeforeEach
    void setUp() {
        year2020 = new Year(2020);
        year2021 = new Year(2021);
        month2020_1 = new Month(MonthName.JANUARY, year2020);
        month2020_2 = new Month(MonthName.FEBRUARY, year2020);
        month2020_3 = new Month(MonthName.MARCH, year2020);
        month2020_4 = new Month(MonthName.APRIL, year2020);
        month2020_5 = new Month(MonthName.MAY, year2020);
        month2020_6 = new Month(MonthName.JUNE, year2020);
        month2020_7 = new Month(MonthName.JULY, year2020);
        month2020_8 = new Month(MonthName.AUGUST, year2020);
        month2020_9 = new Month(MonthName.SEPTEMBER, year2020);
        month2020_10 = new Month(MonthName.OCTOBER, year2020);
        month2020_11 = new Month(MonthName.NOVEMBER, year2020);
        month2020_12 = new Month(MonthName.DECEMBER, year2020);
        month2021_2 = new Month(MonthName.FEBRUARY, year2021);
    }

    @Test
    @DisplayName("Check if months have 31 days when they are months that should have 31 days")
    void should_MonthsHave31Days_WhenTheyAreTheOnesThatShouldHave() {
        assertEquals(31, month2020_1.getDaysAmount());
        assertEquals(31, month2020_3.getDaysAmount());
        assertEquals(31, month2020_5.getDaysAmount());
        assertEquals(31, month2020_7.getDaysAmount());
        assertEquals(31, month2020_8.getDaysAmount());
        assertEquals(31, month2020_10.getDaysAmount());
        assertEquals(31, month2020_12.getDaysAmount());
    }

    @Test
    @DisplayName("Check if months have 30 days when they are months that should have 30 days")
    void should_MonthsHave30Days_WhenTheyAreTheOnesThatShouldHave() {
        assertEquals(30, month2020_4.getDaysAmount());
        assertEquals(30, month2020_6.getDaysAmount());
        assertEquals(30, month2020_9.getDaysAmount());
        assertEquals(30, month2020_11.getDaysAmount());
    }

    @Test
    @DisplayName("Check if february has 29 days when year is leap")
    void should_FebruaryHave29Days_WhenTheYearIsLeap() {
        assertEquals(29, month2020_2.getDaysAmount());
    }

    @Test
    @DisplayName("Check if february has 28 days when year is not leap")
    void should_FebruaryHave28Days_WhenTheYearIsNotLeap() {
        assertEquals(28, month2021_2.getDaysAmount());
    }

}