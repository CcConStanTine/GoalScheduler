package com.pk.ms.entities.day;

import com.pk.ms.constants.DayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class DayNameTest {
    HashMap<Integer, String> dayNamesMap;

    @BeforeEach
    void setUp() {
        dayNamesMap = new HashMap<>();
        dayNamesMap.put(0, "EMPTY");
        dayNamesMap.put(1, "MONDAY");
        dayNamesMap.put(2, "TUESDAY");
        dayNamesMap.put(3, "WEDNESDAY");
        dayNamesMap.put(4, "THURSDAY");
        dayNamesMap.put(5, "FRIDAY");
        dayNamesMap.put(6, "SATURDAY");
        dayNamesMap.put(7, "SUNDAY");
    }

    @ParameterizedTest
    @EnumSource(DayName.class)
    @DisplayName("Check if enum DayName contains proper constants in proper order")
    void should_DayNameEnumValuesBeInProperOrderAndEqualsProperValues(DayName dayName) {
        assertThat(dayName.toString()).isEqualTo(dayNamesMap.get(dayName.getDayNumber()));
    }
}
