package com.pk.ms.constants;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ImportanceTest {

    static Set<String> importanceSet;

    @BeforeAll
    static void setUp() {
        importanceSet = new HashSet<>();
        importanceSet.add("NOTIMPORTANT");
        importanceSet.add("REGULAR");
        importanceSet.add("IMPORTANT");
        importanceSet.add("VERYIMPORTANT");
    }

    @ParameterizedTest
    @EnumSource(Importance.class)
    @DisplayName("Check if enum Importance contains proper constants")
    void should_ImportanceConstantsEqualsProperValues(Importance importance) {
        assertThat(importanceSet.contains(importance.toString())).isEqualTo(true);
    }

    @Test
    @DisplayName("Check if enum Importance.NOTIMPORTANT contains proper number constants")
    void should_ImportanceNOTIMPORTANTNumberConstantsEqualsProperValues() {
        assertThat(Importance.NOTIMPORTANT.getYearImportanceWeight()).isEqualTo(6);
        assertThat(Importance.NOTIMPORTANT.getMonthImportanceWeight()).isEqualTo(7);
        assertThat(Importance.NOTIMPORTANT.getWeekImportanceWeight()).isEqualTo(8);
        assertThat(Importance.NOTIMPORTANT.getDayImportanceWeight()).isEqualTo(10);
    }

    @Test
    @DisplayName("Check if enum Importance.REGULAR contains proper number constants")
    void should_ImportanceREGULARNumberConstantsEqualsProperValues() {
        assertThat(Importance.REGULAR.getYearImportanceWeight()).isEqualTo(15);
        assertThat(Importance.REGULAR.getMonthImportanceWeight()).isEqualTo(18);
        assertThat(Importance.REGULAR.getWeekImportanceWeight()).isEqualTo(17);
        assertThat(Importance.REGULAR.getDayImportanceWeight()).isEqualTo(20);
    }

    @Test
    @DisplayName("Check if enum Importance.IMPORTANT contains proper number constants")
    void should_ImportanceIMPORTANTNumberConstantsEqualsProperValues() {
        assertThat(Importance.IMPORTANT.getYearImportanceWeight()).isEqualTo(40);
        assertThat(Importance.IMPORTANT.getMonthImportanceWeight()).isEqualTo(40);
        assertThat(Importance.IMPORTANT.getWeekImportanceWeight()).isEqualTo(38);
        assertThat(Importance.IMPORTANT.getDayImportanceWeight()).isEqualTo(35);
    }

    @Test
    @DisplayName("Check if enum Importance.VERYIMPORTANT contains proper number constants")
    void should_ImportanceVERYIMPORTANTNumberConstantsEqualsProperValues() {
        assertThat(Importance.VERYIMPORTANT.getYearImportanceWeight()).isEqualTo(58);
        assertThat(Importance.VERYIMPORTANT.getMonthImportanceWeight()).isEqualTo(58);
        assertThat(Importance.VERYIMPORTANT.getWeekImportanceWeight()).isEqualTo(55);
        assertThat(Importance.VERYIMPORTANT.getDayImportanceWeight()).isEqualTo(50);
    }

}