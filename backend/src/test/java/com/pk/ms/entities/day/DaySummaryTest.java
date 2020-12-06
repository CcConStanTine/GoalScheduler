package com.pk.ms.entities.day;

import com.pk.ms.constants.DayName;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.MonthName;
import com.pk.ms.constants.Urgency;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.year.Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DaySummaryTest {

    MyScheduleUser user;
    Schedule schedule;
    Year year2021;
    Month month_1_2021;
    Day day1;
    Day day2;
    DayPlan dayPlan1;
    DayPlan dayPlan2;
    DayPlan dayPlan3;
    DayPlan dayPlan4;
    DayPlan dayPlan5;

    @BeforeEach
    void setUp() {
        user = new MyScheduleUser();
        schedule = new Schedule(user);
        year2021 = new Year(2021);
        year2021.setYearId(1L);
        month_1_2021 = new Month(MonthName.JANUARY, year2021);
        day1 = new Day(LocalDate.of(2021, 1, 1), DayName.FRIDAY, month_1_2021);
        day2 = new Day(LocalDate.of(2021, 1, 2), DayName.SATURDAY, month_1_2021);
        day1.setDayId(1L);
        day1.setDayId(2L);

        dayPlan1 = new DayPlan("Test content 1",
                LocalTime.of(18, 30, 0),
                LocalTime.of(19, 30, 0),
                schedule, day1);
        dayPlan1.setImportance(Importance.IMPORTANT);
        dayPlan1.setUrgency(Urgency.VERYURGENT);
        dayPlan1.setFulfilled(true);

        dayPlan2 = new DayPlan("Test content 2",
                LocalTime.of(18, 30, 0),
                LocalTime.of(19, 30, 0),
                schedule, day1);
        dayPlan2.setFulfilled(true);

        dayPlan3 = new DayPlan("Test content 3",
                LocalTime.of(18, 30, 0),
                LocalTime.of(19, 30, 0),
                schedule, day1);
        dayPlan3.setFulfilled(true);
        dayPlan3.setImportance(Importance.NOTIMPORTANT);
        dayPlan3.setUrgency(Urgency.NONURGENT);

        dayPlan4 = new DayPlan("Test content 4",
                LocalTime.of(18, 30, 0),
                LocalTime.of(19, 30, 0),
                schedule, day1);

        dayPlan5 = new DayPlan("Test content 5",
                LocalTime.of(18, 30, 0),
                LocalTime.of(19, 30, 0),
                schedule, day1);
    }

    @Test
    @DisplayName("Check if DaySummary args constructor call the method countSummary()")
    void should_CountTheDaySummaryFields_When_ArgsConstructorCalled() {
        //given
        schedule.addDayPlan(dayPlan1);
        schedule.addDayPlan(dayPlan2);
        schedule.addDayPlan(dayPlan4);
        //when
        DaySummary daySummary = new DaySummary(schedule, day1);
        //then
        assertTrue(daySummary.getFulfilledAmount()>0);
        assertTrue(daySummary.getFailedAmount()>0);
        assertTrue(daySummary.getPercentagePlansRating()>0);
    }

    @Test
    @DisplayName("Check if DaySummary args constructor count fulfilledAmount field correctly")
    void should_DaySummaryArgsConstructorCountTheFulfilledAmountFieldCorrectly_When_ThereAreFulfilledPlans() {
        //given
        schedule.addDayPlan(dayPlan1);
        schedule.addDayPlan(dayPlan2);
        schedule.addDayPlan(dayPlan4);
        //when
        DaySummary daySummary = new DaySummary(schedule, day1);
        //then
        assertEquals(2, daySummary.getFulfilledAmount());
    }

    @Test
    @DisplayName("Check if DaySummary args constructor count fulfilledAmount field correctly")
    void should_DaySummaryArgsConstructorCountTheFulfilledAmountFieldCorrectly_When_ThereAreNoFulfilledPlans() {
        //given
        schedule.addDayPlan(dayPlan4);
        //when
        DaySummary daySummary = new DaySummary(schedule, day1);
        //then
        assertEquals(0, daySummary.getFulfilledAmount());
    }

    @Test
    @DisplayName("Check if DaySummary args constructor count failedAmount field correctly")
    void should_DaySummaryArgsConstructorCountTheFailedAmountFieldCorrectly_When_ThereAreFailedPlans() {
        //given
        schedule.addDayPlan(dayPlan1);
        schedule.addDayPlan(dayPlan2);
        schedule.addDayPlan(dayPlan4);
        //when
        DaySummary daySummary = new DaySummary(schedule, day1);
        //then
        assertEquals(1, daySummary.getFailedAmount());
    }

    @Test
    @DisplayName("Check if DaySummary args constructor count failedAmount field correctly")
    void should_DaySummaryArgsConstructorCountTheFailedAmountFieldCorrectly_When_ThereAreNoFailedPlans() {
        //given
        schedule.addDayPlan(dayPlan1);
        schedule.addDayPlan(dayPlan2);
        //when
        DaySummary daySummary = new DaySummary(schedule, day1);
        //then
        assertEquals(0, daySummary.getFailedAmount());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field equals 100 when there are only fulfilled plans")
    void should_CountedPercentagePlansFieldEquals100_When_ThereAreOnlyFulfilledPlans() {
        //given
        schedule.addDayPlan(dayPlan1);
        schedule.addDayPlan(dayPlan2);
        //when
        DaySummary daySummary = new DaySummary(schedule, day1);
        //then
        assertEquals(100, daySummary.getPercentagePlansRating());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field equals 0 when there are only failed plans")
    void should_CountedPercentagePlansFieldEquals0_When_ThereAreOnlyFailedPlans() {
        //given
        schedule.addDayPlan(dayPlan4);
        //when
        DaySummary daySummary = new DaySummary(schedule, day1);
        //then
        assertEquals(0, daySummary.getPercentagePlansRating());
    }

    @Test
    @DisplayName("Check if counted percentagePlansRating field is between 0 and 100 when there are fulfilled and failed plans")
    void should_CountedPercentagePlansFieldBeBetween0And100_When_ThereAreFulfilledAndFailedPlans() {
        //given
        schedule.addDayPlan(dayPlan1);
        schedule.addDayPlan(dayPlan2);
        schedule.addDayPlan(dayPlan4);
        //when
        DaySummary daySummary = new DaySummary(schedule, day1);
        //then
        assertTrue(daySummary.getPercentagePlansRating()>0);
        assertTrue(daySummary.getPercentagePlansRating()<100);
    }


}