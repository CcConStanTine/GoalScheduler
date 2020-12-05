package com.pk.ms.entities.month;

import com.pk.ms.constants.MonthName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class MonthNameTest {

    HashMap<Integer, String> monthNamesMap;

    @BeforeEach
    void setUp() {
        monthNamesMap = new HashMap<>();
        monthNamesMap.put(0, "EMPTY");
        monthNamesMap.put(1, "JANUARY");
        monthNamesMap.put(2, "FEBRUARY");
        monthNamesMap.put(3, "MARCH");
        monthNamesMap.put(4, "APRIL");
        monthNamesMap.put(5, "MAY");
        monthNamesMap.put(6, "JUNE");
        monthNamesMap.put(7, "JULY");
        monthNamesMap.put(8, "AUGUST");
        monthNamesMap.put(9, "SEPTEMBER");
        monthNamesMap.put(10, "OCTOBER");
        monthNamesMap.put(11, "NOVEMBER");
        monthNamesMap.put(12, "DECEMBER");
    }

    @ParameterizedTest
    @EnumSource(MonthName.class)
    @DisplayName("Check if enum MonthName contains proper constants in proper order")
    void should_MonthNameEnumValuesBeInProperOrderAndEqualsProperValues(MonthName monthName) {
        assertThat(monthName.toString()).isEqualTo(monthNamesMap.get(monthName.getMonthNumber()));
    }

}
