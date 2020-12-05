package com.pk.ms.entities.schedule;

import com.pk.ms.entities.day.Day;
import com.pk.ms.constants.DayName;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.entities.month.Month;
import com.pk.ms.constants.MonthName;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    MyScheduleUser user;
    Schedule schedule;
    Year year2020;
    Year year2021;
    Month month1;
    Month month2;
    Week week1;
    Week week2;
    Day day1;
    Day day2;

    @BeforeEach
    void setUp() {
        user = new MyScheduleUser();
        schedule = new Schedule(user);
        year2020 = new Year(2020);
        year2021 = new Year(2021);
        month1 = new Month(MonthName.JANUARY, year2020);
        month2 = new Month(MonthName.FEBRUARY, year2020);
        week1 = new Week(LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 5),
                year2020);
        week2 = new Week(LocalDate.of(2020, 1, 6),
                LocalDate.of(2020, 1, 12),
                year2020);
        day1 = new Day(LocalDate.of(2020, 1, 1), DayName.WEDNESDAY, month1);
        day2 = new Day(LocalDate.of(2020, 1, 1), DayName.THURSDAY, month2);
    }

    @Test
    @DisplayName("Check if method getParticularYearPlansList returns only YearPlans assigned to one particular Year")
    void should_ReturnOnlyYearPlansAssignedToOneParticularYear() {
        //given
        year2020.setYearId(1L);
        year2021.setYearId(2L);
        YearPlan yearPlan1 = new YearPlan("Test content 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 5, 10),
                schedule, year2020);
        YearPlan yearPlan2 = new YearPlan("Test content 2",
                LocalDate.of(2020, 2, 1),
                LocalDate.of(2020, 10, 1),
                schedule, year2020);
        YearPlan yearPlan3 = new YearPlan("Test content 3",
                LocalDate.of(2021, 2, 1),
                LocalDate.of(2021, 10, 1),
                schedule, year2021);
        schedule.addYearPlan(yearPlan1);
        schedule.addYearPlan(yearPlan2);
        schedule.addYearPlan(yearPlan3);
        //when
        List<YearPlan> yearPlanList = schedule.getParticularYearPlansList(1L);
        //then
        assertEquals(2, yearPlanList.size());
        assertEquals("Test content 1", yearPlanList.get(0).getContent());
        assertEquals("Test content 2", yearPlanList.get(1).getContent());
    }

    @Test
    @DisplayName("Check if method getParticularYearPlansList returns empty List when no YearPlan is assigned to the Year")
    void should_ReturnEmptyList_When_NoYearPlanIsAssignedToTheYear() {
        //given
        year2020.setYearId(1L);
        year2021.setYearId(2L);
        YearPlan yearPlan1 = new YearPlan("Test content 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 5, 10),
                schedule, year2020);
        YearPlan yearPlan2 = new YearPlan("Test content 2",
                LocalDate.of(2020, 2, 1),
                LocalDate.of(2020, 10, 1),
                schedule, year2020);
        schedule.addYearPlan(yearPlan1);
        schedule.addYearPlan(yearPlan2);
        //when
        List<YearPlan> yearPlanList = schedule.getParticularYearPlansList(2L);
        //then
        assertEquals(0, yearPlanList.size());
    }

    @Test
    @DisplayName("Check if method getParticularMethodPlansList returns only MonthPlans assigned to one particular Month")
    void should_ReturnOnlyMonthPlansAssignedToOneParticularMonth() {
        //given
        month1.setMonthId(1L);
        month2.setMonthId(2L);
        MonthPlan monthPlan1 = new MonthPlan("Test content 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 15),
                schedule, month1);
        MonthPlan monthPlan2 = new MonthPlan("Test content 2",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 31),
                schedule, month1);
        MonthPlan monthPlan3 = new MonthPlan("Test content 3",
                LocalDate.of(2020, 2, 1),
                LocalDate.of(2020, 2, 20),
                schedule, month2);
        schedule.addMonthPlan(monthPlan1);
        schedule.addMonthPlan(monthPlan2);
        schedule.addMonthPlan(monthPlan3);
        //when
        List<MonthPlan> monthPlanList = schedule.getParticularMonthPlansList(1L);
        //then
        assertEquals(2, monthPlanList.size());
        assertEquals("Test content 1", monthPlanList.get(0).getContent());
        assertEquals("Test content 2", monthPlanList.get(1).getContent());
    }

    @Test
    @DisplayName("Check if method getParticularMonthPlansList returns empty List when no MonthPlan is assigned to the Month")
    void should_ReturnEmptyList_When_NoMonthPlanIsAssignedToTheMonth() {
        //given
        month1.setMonthId(1L);
        month2.setMonthId(2L);
        MonthPlan monthPlan1 = new MonthPlan("Test content 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 15),
                schedule, month1);
        MonthPlan monthPlan2 = new MonthPlan("Test content 2",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 31),
                schedule, month1);
        schedule.addMonthPlan(monthPlan1);
        schedule.addMonthPlan(monthPlan2);
        //when
        List<MonthPlan> monthPlanList = schedule.getParticularMonthPlansList(2L);
        //then
        assertEquals(0, monthPlanList.size());
    }

    @Test
    @DisplayName("Check if method getParticularWeekPlansList returns only WeekPlans assigned to one particular Week")
    void should_ReturnOnlyWeekPlansAssignedToOneParticularWeek() {
        //given
        week1.setWeekId(1L);
        week2.setWeekId(2L);
        WeekPlan weekPlan1 = new WeekPlan("Test content 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 4),
                schedule, week1);
        WeekPlan weekPlan2 = new WeekPlan("Test content 2",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 5),
                schedule, week1);
        WeekPlan weekPlan3 = new WeekPlan("Test content 3",
                LocalDate.of(2020, 1, 6),
                LocalDate.of(2020, 1, 12),
                schedule, week2);
        schedule.addWeekPlan(weekPlan1);
        schedule.addWeekPlan(weekPlan2);
        schedule.addWeekPlan(weekPlan3);
        //when
        List<WeekPlan> weekPlanList = schedule.getParticularWeekPlansList(1L);
        //then
        assertEquals(2, weekPlanList.size());
        assertEquals("Test content 1", weekPlanList.get(0).getContent());
        assertEquals("Test content 2", weekPlanList.get(1).getContent());
    }

    @Test
    @DisplayName("Check if method getParticularWeekPlansList returns empty List when no WeekPlan is assigned to the Week")
    void should_ReturnEmptyList_When_NoWeekPlanIsAssignedToTheWeek() {
        //given
        week1.setWeekId(1L);
        week2.setWeekId(2L);
        WeekPlan weekPlan1 = new WeekPlan("Test content 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 4),
                schedule, week1);
        WeekPlan weekPlan2 = new WeekPlan("Test content 2",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 5),
                schedule, week1);
        schedule.addWeekPlan(weekPlan1);
        schedule.addWeekPlan(weekPlan2);
        //when
        List<WeekPlan> weekPlanList = schedule.getParticularWeekPlansList(2L);
        //then
        assertEquals(0, weekPlanList.size());
    }

    @Test
    @DisplayName("Check if method getParticularDayPlansList returns only DayPlans assigned to one particular Day")
    void should_ReturnOnlyDayPlansAssignedToOneParticularDay() {
        //given
        day1.setDayId(1L);
        day2.setDayId(2L);
        DayPlan dayPlan1 = new DayPlan("Test content 1",
                LocalTime.of(6, 30),
                LocalTime.of(8, 30),
                schedule, day1);
        DayPlan dayPlan2 = new DayPlan("Test content 2",
                LocalTime.of(9, 30),
                LocalTime.of(12, 30),
                schedule, day1);
        DayPlan dayPlan3 = new DayPlan("Test content 3",
                LocalTime.of(6, 30),
                LocalTime.of(8, 30),
                schedule, day2);
        schedule.addDayPlan(dayPlan1);
        schedule.addDayPlan(dayPlan2);
        schedule.addDayPlan(dayPlan3);
        //when
        List<DayPlan> dayPlanList = schedule.getParticularDayPlansList(1L);
        //then
        assertEquals(2, dayPlanList.size());
        assertEquals("Test content 1", dayPlanList.get(0).getContent());
        assertEquals("Test content 2", dayPlanList.get(1).getContent());
    }

    @Test
    @DisplayName("Check if method getParticularDayPlansList returns empty List when no DayPlan is assigned to the Day")
    void should_ReturnEmptyList_When_NoDayPlanIsAssignedToTheDay() {
        //given
        day1.setDayId(1L);
        day2.setDayId(2L);
        DayPlan dayPlan1 = new DayPlan("Test content 1",
                LocalTime.of(6, 30),
                LocalTime.of(8, 30),
                schedule, day1);
        DayPlan dayPlan2 = new DayPlan("Test content 2",
                LocalTime.of(9, 30),
                LocalTime.of(12, 30),
                schedule, day1);
        schedule.addDayPlan(dayPlan1);
        schedule.addDayPlan(dayPlan2);
        //when
        List<DayPlan> dayPlanList = schedule.getParticularDayPlansList(2L);
        //then
        assertEquals(0, dayPlanList.size());
    }
}
