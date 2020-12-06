package com.pk.ms.entities.month;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.MonthName;
import com.pk.ms.constants.Urgency;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.year.Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonthSummaryTest {

    MyScheduleUser user;
    Schedule schedule;
    Year year2021;
    Month month_1_2021;
    Month month_2_2021;
    MonthPlan monthPlan1;
    MonthPlan monthPlan2;
    MonthPlan monthPlan3;
    MonthPlan monthPlan4;
    MonthPlan monthPlan5;

    @BeforeEach
    void setUp() {
        user = new MyScheduleUser();
        schedule = new Schedule(user);
        year2021 = new Year(2021);
        year2021.setYearId(1L);
        month_1_2021 = new Month(MonthName.JANUARY, year2021);
        month_2_2021 = new Month(MonthName.FEBRUARY, year2021);
        month_1_2021.setMonthId(1L);
        month_2_2021.setMonthId(2L);
        monthPlan1 = new MonthPlan("Test content 1",
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 15),
                schedule, month_1_2021);
        monthPlan1.setImportance(Importance.IMPORTANT);
        monthPlan1.setUrgency(Urgency.VERYURGENT);
        monthPlan1.setFulfilled(true);

        monthPlan2 = new MonthPlan("Test content 2",
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 15),
                schedule, month_1_2021);
        monthPlan2.setFulfilled(true);

        monthPlan3 = new MonthPlan("Test content 3",
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 15),
                schedule, month_1_2021);
        monthPlan3.setFulfilled(true);
        monthPlan3.setImportance(Importance.NOTIMPORTANT);
        monthPlan3.setUrgency(Urgency.NONURGENT);

        monthPlan4 = new MonthPlan("Test content 4",
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 15),
                schedule, month_1_2021);

        monthPlan5 = new MonthPlan("Test content 5",
                LocalDate.of(2021, 2, 1),
                LocalDate.of(2021, 2, 12),
                schedule, month_2_2021);
    }

    @Test
    @DisplayName("Check if MonthSummary args constructor call the method countSummary()")
    void should_CountTheMonthSummaryFields_When_ArgsConstructorCalled() {
        //given
        schedule.addMonthPlan(monthPlan1);
        schedule.addMonthPlan(monthPlan2);
        schedule.addMonthPlan(monthPlan4);
        //when
        MonthSummary monthSummary = new MonthSummary(schedule, month_1_2021);
        //then
        assertTrue(monthSummary.getFulfilledAmount()>0);
        assertTrue(monthSummary.getFailedAmount()>0);
        assertTrue(monthSummary.getPercentagePlansRating()>0);
    }

    @Test
    @DisplayName("Check if MonthSummary args constructor count fulfilledAmount field correctly")
    void should_MonthSummaryArgsConstructorCountTheFulfilledAmountFieldCorrectly_When_ThereAreFulfilledPlans() {
        //given
        schedule.addMonthPlan(monthPlan1);
        schedule.addMonthPlan(monthPlan2);
        schedule.addMonthPlan(monthPlan4);
        //when
        MonthSummary monthSummary = new MonthSummary(schedule, month_1_2021);
        //then
        assertEquals(2, monthSummary.getFulfilledAmount());
    }

    @Test
    @DisplayName("Check if MonthSummary args constructor count fulfilledAmount field correctly")
    void should_MonthSummaryArgsConstructorCountTheFulfilledAmountFieldCorrectly_When_ThereAreNoFulfilledPlans() {
        //given
        schedule.addMonthPlan(monthPlan4);
        //when
        MonthSummary monthSummary = new MonthSummary(schedule, month_1_2021);
        //then
        assertEquals(0, monthSummary.getFulfilledAmount());
    }

    @Test
    @DisplayName("Check if MonthSummary args constructor count failedAmount field correctly")
    void should_MonthSummaryArgsConstructorCountTheFailedAmountFieldCorrectly_When_ThereAreFailedPlans() {
        //given
        schedule.addMonthPlan(monthPlan1);
        schedule.addMonthPlan(monthPlan2);
        schedule.addMonthPlan(monthPlan4);
        //when
        MonthSummary monthSummary = new MonthSummary(schedule, month_1_2021);
        //then
        assertEquals(1, monthSummary.getFailedAmount());
    }

    @Test
    @DisplayName("Check if MonthSummary args constructor count failedAmount field correctly")
    void should_MonthSummaryArgsConstructorCountTheFailedAmountFieldCorrectly_When_ThereAreNoFailedPlans() {
        //given
        schedule.addMonthPlan(monthPlan1);
        schedule.addMonthPlan(monthPlan2);
        //when
        MonthSummary monthSummary = new MonthSummary(schedule, month_1_2021);
        //then
        assertEquals(0, monthSummary.getFailedAmount());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field equals 100 when there are only fulfilled plans")
    void should_CountedPercentagePlansFieldEquals100_When_ThereAreOnlyFulfilledPlans() {
        //given
        schedule.addMonthPlan(monthPlan1);
        schedule.addMonthPlan(monthPlan2);
        //when
        MonthSummary monthSummary = new MonthSummary(schedule, month_1_2021);
        //then
        assertEquals(100, monthSummary.getPercentagePlansRating());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field equals 0 when there are only failed plans")
    void should_CountedPercentagePlansFieldEquals0_When_ThereAreOnlyFailedPlans() {
        //given
        schedule.addMonthPlan(monthPlan4);
        //when
        MonthSummary monthSummary = new MonthSummary(schedule, month_1_2021);
        //then
        assertEquals(0, monthSummary.getPercentagePlansRating());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field is between 0 and 100 when there are fulfilled and failed plans")
    void should_CountedPercentagePlansFieldBeBetween0And100_When_ThereAreFulfilledAndFailedPlans() {
        //given
        schedule.addMonthPlan(monthPlan1);
        schedule.addMonthPlan(monthPlan2);
        schedule.addMonthPlan(monthPlan4);
        //when
        MonthSummary monthSummary = new MonthSummary(schedule, month_1_2021);
        //then
        assertTrue(monthSummary.getPercentagePlansRating()>0);
        assertTrue(monthSummary.getPercentagePlansRating()<100);
    }

}