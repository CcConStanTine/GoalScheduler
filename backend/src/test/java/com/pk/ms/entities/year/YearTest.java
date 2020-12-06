package com.pk.ms.entities.year;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YearTest {

    Year year2020;
    Year year2021;

    @BeforeEach
    void setUp() {
        year2020 = new Year(2020);
        year2021 = new Year(2021);
    }

    @Test
    @DisplayName("Check if new created leap Year isLeapYear field is true")
    void should_IsLeapYearFlagBeTrue_When_YearIsLeap() {
        assertTrue(year2020.isLeapYear());
    }

    @Test
    @DisplayName("Check if new created non leap Year isLeapYear field is false ")
    void should_IsLeapYearFlagBeFalse_When_YearIsNotLeap() {
        assertFalse(year2021.isLeapYear());
    }

    @Test
    @DisplayName("Check if new created leap Year has proper amount of days")
    void should_Have366Days_When_YearIsLeap() {
        assertEquals(366, year2020.getDaysAmount());
    }

    @Test
    @DisplayName("Check if new created non leap Year has proper amount of days")
    void should_Have365Days_When_YearIsNotLeap() {
        assertEquals(365, year2021.getDaysAmount());
    }

}