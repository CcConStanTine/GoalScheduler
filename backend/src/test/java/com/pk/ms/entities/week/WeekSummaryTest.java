package com.pk.ms.entities.week;

import com.pk.ms.constants.Importance;
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

class WeekSummaryTest {

    MyScheduleUser user;
    Schedule schedule;
    Year year2021;
    Week week1;
    Week week2;
    WeekPlan weekPlan1;
    WeekPlan weekPlan2;
    WeekPlan weekPlan3;
    WeekPlan weekPlan4;
    WeekPlan weekPlan5;

    @BeforeEach
    void setUp() {
        user = new MyScheduleUser();
        schedule = new Schedule(user);
        year2021 = new Year(2021);
        year2021.setYearId(1L);
        week1 = new Week(LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 3), year2021);
        week2 = new Week(LocalDate.of(2021, 1, 4),
                LocalDate.of(2021, 1, 10), year2021);
        week1.setWeekId(1L);
        week2.setWeekId(2L);

        weekPlan1 = new WeekPlan("Test content 1",
                LocalDate.of(2021, 1, 3),
                LocalDate.of(2021, 1, 3),
                schedule, week1);
        weekPlan1.setImportance(Importance.IMPORTANT);
        weekPlan1.setUrgency(Urgency.VERYURGENT);
        weekPlan1.setFulfilled(true);

        weekPlan2 = new WeekPlan("Test content 2",
                LocalDate.of(2021, 1, 3),
                LocalDate.of(2021, 1, 3),
                schedule, week1);
        weekPlan2.setFulfilled(true);

        weekPlan3 = new WeekPlan("Test content 3",
                LocalDate.of(2021, 1, 3),
                LocalDate.of(2021, 1, 3),
                schedule, week1);
        weekPlan3.setFulfilled(true);
        weekPlan3.setImportance(Importance.NOTIMPORTANT);
        weekPlan3.setUrgency(Urgency.NONURGENT);

        weekPlan4 = new WeekPlan("Test content 4",
                LocalDate.of(2021, 1, 3),
                LocalDate.of(2021, 1, 3),
                schedule, week1);

        weekPlan5 = new WeekPlan("Test content 5",
                LocalDate.of(2021, 1, 4),
                LocalDate.of(2021, 1, 9),
                schedule, week2);
    }

    @Test
    @DisplayName("Check if WeekSummary args constructor call the method countSummary()")
    void should_CountTheWeekSummaryFields_When_ArgsConstructorCalled() {
        //given
        schedule.addWeekPlan(weekPlan1);
        schedule.addWeekPlan(weekPlan2);
        schedule.addWeekPlan(weekPlan4);
        //when
        WeekSummary weekSummary = new WeekSummary(schedule, week1);
        //then
        assertTrue(weekSummary.getFulfilledAmount()>0);
        assertTrue(weekSummary.getFailedAmount()>0);
        assertTrue(weekSummary.getPercentagePlansRating()>0);
    }

    @Test
    @DisplayName("Check if WeekSummary args constructor count fulfilledAmount field correctly")
    void should_WeekSummaryArgsConstructorCountTheFulfilledAmountFieldCorrectly_When_ThereAreFulfilledPlans() {
        //given
        schedule.addWeekPlan(weekPlan1);
        schedule.addWeekPlan(weekPlan2);
        schedule.addWeekPlan(weekPlan4);
        //when
        WeekSummary weekSummary = new WeekSummary(schedule, week1);
        //then
        assertEquals(2, weekSummary.getFulfilledAmount());
    }

    @Test
    @DisplayName("Check if WeekSummary args constructor count fulfilledAmount field correctly")
    void should_WeekSummaryArgsConstructorCountTheFulfilledAmountFieldCorrectly_When_ThereAreNoFulfilledPlans() {
        //given
        schedule.addWeekPlan(weekPlan4);
        //when
        WeekSummary weekSummary = new WeekSummary(schedule, week1);
        //then
        assertEquals(0, weekSummary.getFulfilledAmount());
    }

    @Test
    @DisplayName("Check if WeekSummary args constructor count failedAmount field correctly")
    void should_WeekSummaryArgsConstructorCountTheFailedAmountFieldCorrectly_When_ThereAreFailedPlans() {
        //given
        schedule.addWeekPlan(weekPlan1);
        schedule.addWeekPlan(weekPlan2);
        schedule.addWeekPlan(weekPlan4);
        //when
        WeekSummary weekSummary = new WeekSummary(schedule, week1);
        //then
        assertEquals(1, weekSummary.getFailedAmount());
    }

    @Test
    @DisplayName("Check if WeekSummary args constructor count failedAmount field correctly")
    void should_WeekSummaryArgsConstructorCountTheFailedAmountFieldCorrectly_When_ThereAreNoFailedPlans() {
        //given
        schedule.addWeekPlan(weekPlan1);
        schedule.addWeekPlan(weekPlan2);
        //when
        WeekSummary weekSummary = new WeekSummary(schedule, week1);
        //then
        assertEquals(0, weekSummary.getFailedAmount());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field equals 100 when there are only fulfilled plans")
    void should_CountedPercentagePlansFieldEquals100_When_ThereAreOnlyFulfilledPlans() {
        //given
        schedule.addWeekPlan(weekPlan1);
        schedule.addWeekPlan(weekPlan2);
        //when
        WeekSummary weekSummary = new WeekSummary(schedule, week1);
        //then
        assertEquals(100, weekSummary.getPercentagePlansRating());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field equals 0 when there are only failed plans")
    void should_CountedPercentagePlansFieldEquals0_When_ThereAreOnlyFailedPlans() {
        //given
        schedule.addWeekPlan(weekPlan4);
        //when
        WeekSummary weekSummary = new WeekSummary(schedule, week1);
        //then
        assertEquals(0, weekSummary.getPercentagePlansRating());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field is between 0 and 100 when there are fulfilled and failed plans")
    void should_CountedPercentagePlansFieldBeBetween0And100_When_ThereAreFulfilledAndFailedPlans() {
        //given
        schedule.addWeekPlan(weekPlan1);
        schedule.addWeekPlan(weekPlan2);
        schedule.addWeekPlan(weekPlan4);
        //when
        WeekSummary weekSummary = new WeekSummary(schedule, week1);
        //then
        assertTrue(weekSummary.getPercentagePlansRating()>0);
        assertTrue(weekSummary.getPercentagePlansRating()<100);
    }

}