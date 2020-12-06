package com.pk.ms.entities.year;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YearSummaryTest {

    MyScheduleUser user;
    Schedule schedule;
    Year year2020;
    Year year2021;
    YearPlan yearPlan1;
    YearPlan yearPlan2;
    YearPlan yearPlan3;
    YearPlan yearPlan4;
    YearPlan yearPlan5;

    @BeforeEach
    void setUp() {
        user = new MyScheduleUser();
        schedule = new Schedule(user);
        year2020 = new Year(2020);
        year2021 = new Year(2021);
        year2020.setYearId(1L);
        year2021.setYearId(2L);

        yearPlan1 = new YearPlan("Test content 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 5, 10),
                schedule, year2020);
        yearPlan1.setImportance(Importance.IMPORTANT);
        yearPlan1.setUrgency(Urgency.VERYURGENT);
        yearPlan1.setFulfilled(true);

        yearPlan2 = new YearPlan("Test content 2",
                LocalDate.of(2020, 2, 1),
                LocalDate.of(2020, 10, 1),
                schedule, year2020);
        yearPlan2.setFulfilled(true);

        yearPlan3 = new YearPlan("Test content 3",
                LocalDate.of(2020, 2, 1),
                LocalDate.of(2020, 10, 1),
                schedule, year2020);
        yearPlan3.setFulfilled(true);
        yearPlan3.setImportance(Importance.NOTIMPORTANT);
        yearPlan3.setUrgency(Urgency.NONURGENT);

        yearPlan4 = new YearPlan("Test content 4",
                LocalDate.of(2020, 2, 1),
                LocalDate.of(2020, 10, 1),
                schedule, year2020);

        yearPlan5 = new YearPlan("Test content 5",
                LocalDate.of(2021, 2, 1),
                LocalDate.of(2021, 10, 1),
                schedule, year2020);
    }

    @Test
    @DisplayName("Check if YearSummary args constructor call the method countSummary()")
    void should_CountTheYearSummaryFields_When_ArgsConstructorCalled() {
        //given
        schedule.addYearPlan(yearPlan1);
        schedule.addYearPlan(yearPlan2);
        schedule.addYearPlan(yearPlan4);
        //when
        YearSummary yearSummary = new YearSummary(schedule, year2020);
        //then
        assertTrue(yearSummary.getFulfilledAmount()>0);
        assertTrue(yearSummary.getFailedAmount()>0);
        assertTrue(yearSummary.getPercentagePlansRating()>0);
    }

    @Test
    @DisplayName("Check if YearSummary args constructor count fulfilledAmount field correctly")
    void should_YearSummaryArgsConstructorCountTheFulfilledAmountFieldCorrectly_When_ThereAreFulfilledPlans() {
        //given
        schedule.addYearPlan(yearPlan1);
        schedule.addYearPlan(yearPlan2);
        schedule.addYearPlan(yearPlan4);
        //when
        YearSummary yearSummary = new YearSummary(schedule, year2020);
        //then
        assertEquals(2, yearSummary.getFulfilledAmount());
    }

    @Test
    @DisplayName("Check if YearSummary args constructor count fulfilledAmount field correctly")
    void should_YearSummaryArgsConstructorCountTheFulfilledAmountFieldCorrectly_When_ThereAreNoFulfilledPlans() {
        //given
        schedule.addYearPlan(yearPlan4);
        //when
        YearSummary yearSummary = new YearSummary(schedule, year2020);
        //then
        assertEquals(0, yearSummary.getFulfilledAmount());
    }

    @Test
    @DisplayName("Check if YearSummary args constructor count failedAmount field correctly")
    void should_YearSummaryArgsConstructorCountTheFailedAmountFieldCorrectly_When_ThereAreFailedPlans() {
        //given
        schedule.addYearPlan(yearPlan1);
        schedule.addYearPlan(yearPlan2);
        schedule.addYearPlan(yearPlan4);
        //when
        YearSummary yearSummary = new YearSummary(schedule, year2020);
        //then
        assertEquals(1, yearSummary.getFailedAmount());
    }

    @Test
    @DisplayName("Check if YearSummary args constructor count failedAmount field correctly")
    void should_YearSummaryArgsConstructorCountTheFailedAmountFieldCorrectly_When_ThereAreNoFailedPlans() {
        //given
        schedule.addYearPlan(yearPlan1);
        schedule.addYearPlan(yearPlan2);
        //when
        YearSummary yearSummary = new YearSummary(schedule, year2020);
        //then
        assertEquals(0, yearSummary.getFailedAmount());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field equals 100 when there are only fulfilled plans")
    void should_CountedPercentagePlansFieldEquals100_When_ThereAreOnlyFulfilledPlans() {
        //given
        schedule.addYearPlan(yearPlan1);
        schedule.addYearPlan(yearPlan2);
        //when
        YearSummary yearSummary = new YearSummary(schedule, year2020);
        //then
        assertEquals(100, yearSummary.getPercentagePlansRating());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field equals 0 when there are only failed plans")
    void should_CountedPercentagePlansFieldEquals0_When_ThereAreOnlyFailedPlans() {
        //given
        schedule.addYearPlan(yearPlan4);
        //when
        YearSummary yearSummary = new YearSummary(schedule, year2020);
        //then
        assertEquals(0, yearSummary.getPercentagePlansRating());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field is between 0 and 100 when there are fulfilled and failed plans")
    void should_CountedPercentagePlansFieldBeBetween0And100_When_ThereAreFulfilledAndFailedPlans() {
        //given
        schedule.addYearPlan(yearPlan1);
        schedule.addYearPlan(yearPlan2);
        schedule.addYearPlan(yearPlan4);
        //when
        YearSummary yearSummary = new YearSummary(schedule, year2020);
        //then
        assertTrue(yearSummary.getPercentagePlansRating()>0);
        assertTrue(yearSummary.getPercentagePlansRating()<100);
    }
}