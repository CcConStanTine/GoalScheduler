package com.pk.ms.constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class UrgencyTest {

    Set<String> urgencySet;

    @BeforeEach
    void setUp() {
        urgencySet = new HashSet<>();
        urgencySet.add("NONURGENT");
        urgencySet.add("REGULAR");
        urgencySet.add("URGENT");
        urgencySet.add("VERYURGENT");
    }

    @ParameterizedTest
    @EnumSource(Urgency.class)
    @DisplayName("Check if enum Urgency contains proper constants")
    void should_UrgencyConstantsEqualsProperValues(Urgency urgency) {
        assertThat(urgencySet.contains(urgency.toString())).isEqualTo(true);
    }

}