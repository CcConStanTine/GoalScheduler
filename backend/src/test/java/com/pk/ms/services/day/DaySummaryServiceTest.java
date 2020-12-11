package com.pk.ms.services.day;

import com.pk.ms.constants.DayName;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.MonthName;
import com.pk.ms.dao.day.DaySummaryRepository;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.entities.day.DaySummary;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.schedule.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class DaySummaryServiceTest {

    @Mock
    DaySummaryRepository repository;

    @Mock
    ScheduleService scheduleService;

    @Mock
    DayService dayService;

    MyScheduleUser user1;
    MyScheduleUser user2;
    Schedule schedule1;
    Schedule schedule2;

    Month month1;

    Day day1;
    Day day2;

    DayPlan dayPlan1;
    DayPlan dayPlan2;
    DayPlan dayPlan3;
    DayPlan dayPlan4;

    DaySummary daySummary1;
    DaySummary daySummary2;

    DaySummaryService daySummaryService;

    @BeforeEach
    void setUp() {
        user1 = new MyScheduleUser();
        user2 = new MyScheduleUser();
        schedule1 = new Schedule(user1);
        schedule2 = new Schedule(user2);
        schedule1.setScheduleId(1L);
        schedule2.setScheduleId(2L);

        month1 = new Month(MonthName.JANUARY, null);

        day1 = new Day(LocalDate.of(2020, 1, 1), DayName.WEDNESDAY, month1);
        day2 = new Day(LocalDate.of(2020, 1, 2), DayName.THURSDAY, month1);
        day1.setDayId(1L);
        day2.setDayId(2L);

        dayPlan1 = new DayPlan("Do some stuff 1",
                LocalTime.of(9, 0, 0),
                LocalTime.of(15, 0, 0),
                schedule1, day1);
        dayPlan2 = new DayPlan("Do some stuff 2",
                LocalTime.of(10, 0, 0),
                LocalTime.of(11, 30, 0),
                schedule1, day1);
        dayPlan3 = new DayPlan("Do some stuff 3",
                LocalTime.of(9, 0, 0),
                LocalTime.of(15, 0, 0),
                schedule1, day2);
        dayPlan4 = new DayPlan("Do some stuff 1",
                LocalTime.of(9, 0, 0),
                LocalTime.of(15, 0, 0),
                schedule2, day1);
        dayPlan1.setDayPlanId(1L);
        dayPlan2.setDayPlanId(2L);
        dayPlan3.setDayPlanId(3L);
        dayPlan4.setDayPlanId(4L);

        schedule1.addDayPlan(dayPlan1);
        schedule1.addDayPlan(dayPlan2);
        schedule1.addDayPlan(dayPlan3);
        schedule2.addDayPlan(dayPlan4);

        dayPlan1.setDayPlanId(1L);
        dayPlan1.setImportance(Importance.IMPORTANT);
        dayPlan1.setFulfilled(true);
        dayPlan2.setDayPlanId(2L);
        dayPlan2.setFulfilled(false);

        dayPlan3.setDayPlanId(3L);
        dayPlan4.setDayPlanId(4L);

        daySummary1 = new DaySummary(schedule1, day1);
        daySummary2 = new DaySummary(schedule2, day1);
        daySummary1.setDaySummaryId(1L);
        daySummary2.setDaySummaryId(2L);

        daySummaryService = new DaySummaryService(repository, dayService, scheduleService);
    }

    @Test
    @DisplayName("Check if method getDaySummaryById() calls repository's method findById()")
    void should_MethodGetDaySummaryByIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(daySummary1));
        //when
        daySummaryService.getDaySummaryById(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getDaySummaryById() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodGetDaySummaryByIdThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> daySummaryService.getDaySummaryById(1L, 1L));
    }

    @Test
    @DisplayName("Check if method getDaySummaryById() throws AccessDeniedException when given user has no access to the summary")
    void should_MethodGetDaySummaryByIdThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenSummary() {
        //given
        given(repository.findById(2L)).willReturn(Optional.of(daySummary2));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> daySummaryService.getDaySummaryById(1L, 2L));
    }

    @Test
    @DisplayName("Check if method getDaySummaryByScheduleIdAndDayId() calls repository's method findByScheduleIdAndDayId()")
    void should_MethodGetDaySummaryByScheduleIdAndDayIdCallsRepositoryMethodFindByScheduleIdAndDayId() {
        //given
        given(repository.findByScheduleIdAndDayId(1L, 1L)).willReturn(Optional.of(daySummary1));
        //when
        daySummaryService.getDaySummaryByScheduleIdAndDayId(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findByScheduleIdAndDayId(1L, 1L);
    }

    @Test
    @DisplayName("Check if method getDaySummaryByScheduleIdAndDayId() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodGetDaySummaryByScheduleIdAndDayIdThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findByScheduleIdAndDayId(1L, 1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> daySummaryService.getDaySummaryByScheduleIdAndDayId(1L, 1L));
    }

    @Test
    @DisplayName("Check if method createDaySummary() calls dayService's method getDayById()")
    void should_MethodCreateDaySummaryCallsDayServiceMethodGetDayById() {
        //given
        given(dayService.getDayById(1L)).willReturn(day1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        //when
        daySummaryService.createDaySummary(1L, 1L);
        //then
        verify(dayService, Mockito.times(1)).getDayById(1L);
    }

    @Test
    @DisplayName("Check if method createDaySummary() calls scheduleService's method getScheduleById()")
    void should_MethodCreateDaySummaryCallsScheduleServiceMethodGetScheduleById() {
        //given
        given(dayService.getDayById(1L)).willReturn(day1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        //when
        daySummaryService.createDaySummary(1L, 1L);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method createDaySummary() calls repository's method save()")
    void should_MethodCreateDaySummaryCallsScheduleRepositoryMethodSave() {
        //given
        given(dayService.getDayById(1L)).willReturn(day1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        given(repository.save(any())).willReturn(daySummary1);
        //when
        daySummaryService.createDaySummary(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method createDaySummary() returns saved summary")
    void should_MethodCreateDaySummaryReturnsSavedSummary() {
        //given
        given(dayService.getDayById(1L)).willReturn(day1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        given(repository.save(any())).willReturn(daySummary1);
        //when
        DaySummary actual = daySummaryService.createDaySummary(1L, 1L);
        //then
        assertSame(daySummary1, actual);
    }

    @Test
    @DisplayName("Check if method updateDaySummary() calls repository's method findById()")
    void should_MethodUpdateDaySummaryCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(daySummary1));
        //when
        daySummaryService.updateDaySummary(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateDaySummary() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodUpdateDaySummaryThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> daySummaryService.updateDaySummary(1L, 1L));
    }

    @Test
    @DisplayName("Check if method updateDaySummary() throws AccessDeniedException when given user has no access to the summary")
    void should_MethodUpdateDaySummaryThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenSummary() {
        //given
        given(repository.findById(2L)).willReturn(Optional.of(daySummary2));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> daySummaryService.updateDaySummary(1L, 2L));
    }

    @Test
    @DisplayName("Check if method updateDaySummary() updates summary fields")
    void should_MethodUpdateDaySummaryUpdateSummaryFields() {
        //given
        int fulfilledAmountBeforeUpdate = daySummary1.getFulfilledAmount();
        int failedAmountBeforeUpdate = daySummary1.getFailedAmount();
        int percentagePlansRatingBeforeUpdate = daySummary1.getPercentagePlansRating();
        dayPlan2.setFulfilled(true);
        given(repository.findById(1L)).willReturn(Optional.of(daySummary1));
        //when
        daySummaryService.updateDaySummary(1L, 1L);
        //then
        assertAll(
                () -> assertNotEquals(fulfilledAmountBeforeUpdate, daySummary1.getFulfilledAmount()),
                () -> assertEquals(1, fulfilledAmountBeforeUpdate),
                () -> assertEquals(2, daySummary1.getFulfilledAmount()),
                () -> assertNotEquals(failedAmountBeforeUpdate, daySummary1.getFailedAmount()),
                () -> assertEquals(1, failedAmountBeforeUpdate),
                () -> assertEquals(0, daySummary1.getFailedAmount()),
                () -> assertNotEquals(percentagePlansRatingBeforeUpdate, daySummary1.getPercentagePlansRating())
        );
    }

    @Test
    @DisplayName("Check if method updateDaySummary() calls repository's method save() ")
    void should_MethodUpdateDaySummaryCallsRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(daySummary1));
        given(repository.save(daySummary1)).willReturn(daySummary1);
        //when
        daySummaryService.updateDaySummary(1L, 1);
        //then
        verify(repository, Mockito.times(1)).save(daySummary1);
    }

    @Test
    @DisplayName("Check if method updateDaySummary() returns saved summary")
    void should_MethodUpdateDaySummaryReturnsSavedSummary() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(daySummary1));
        given(repository.save(daySummary1)).willReturn(daySummary1);
        //when
        DaySummary actual = daySummaryService.updateDaySummary(1L, 1L);
        //then
        assertSame(daySummary1, actual);
    }
}