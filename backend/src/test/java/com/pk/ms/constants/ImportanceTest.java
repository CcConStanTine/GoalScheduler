package com.pk.ms.constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ImportanceTest {

    Set<String> importanceSet;

    @BeforeEach
    void setUp() {
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

}