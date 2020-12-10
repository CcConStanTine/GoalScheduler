package com.pk.ms.services.week;

import com.pk.ms.constants.Importance;
import com.pk.ms.dao.week.WeekSummaryRepository;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.entities.week.WeekSummary;
import com.pk.ms.entities.year.Year;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class WeekSummaryServiceTest {

    @Mock
    WeekSummaryRepository repository;

    @Mock
    ScheduleService scheduleService;

    @Mock
    WeekService weekService;

    MyScheduleUser user1;
    MyScheduleUser user2;
    Schedule schedule1;
    Schedule schedule2;

    Year year2020;

    Week week1;
    Week week2;

    WeekPlan weekPlan1;
    WeekPlan weekPlan2;
    WeekPlan weekPlan3;
    WeekPlan weekPlan4;

    WeekSummary weekSummary1;
    WeekSummary weekSummary2;

    WeekSummaryService weekSummaryService;

    @BeforeEach
    void setUp() {
        user1 = new MyScheduleUser();
        user2 = new MyScheduleUser();
        schedule1 = new Schedule(user1);
        schedule2 = new Schedule(user2);
        schedule1.setScheduleId(1L);
        schedule2.setScheduleId(2L);

        year2020 = new Year(2020);
        year2020.setYearId(1L);

        week1 = new Week(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), year2020);
        week2 = new Week(LocalDate.of(2020, 1, 6), LocalDate.of(2020, 1, 12), year2020);
        week1.setWeekId(1L);
        week2.setWeekId(2L);

        weekPlan1 = new WeekPlan("Do some stuff 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 5),
                schedule1, week1);
        weekPlan2 = new WeekPlan("Do some stuff 2",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 4),
                schedule1, week1);
        weekPlan3 = new WeekPlan("Do some stuff 3",
                LocalDate.of(2020, 1, 7),
                LocalDate.of(2020, 1, 10),
                schedule1, week2);
        weekPlan4 = new WeekPlan("Do some stuff 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 4),
                schedule2, week1);
        weekPlan1.setWeekPlanId(1L);
        weekPlan2.setWeekPlanId(2L);
        weekPlan3.setWeekPlanId(3L);
        weekPlan4.setWeekPlanId(4L);

        schedule1.addWeekPlan(weekPlan1);
        schedule1.addWeekPlan(weekPlan2);
        schedule1.addWeekPlan(weekPlan3);
        schedule2.addWeekPlan(weekPlan4);

        weekPlan1.setWeekPlanId(1L);
        weekPlan1.setImportance(Importance.IMPORTANT);
        weekPlan1.setFulfilled(true);
        weekPlan2.setWeekPlanId(2L);
        weekPlan2.setFulfilled(false);

        weekPlan3.setWeekPlanId(3L);
        weekPlan4.setWeekPlanId(4L);

        weekSummary1 = new WeekSummary(schedule1, week1);
        weekSummary2 = new WeekSummary(schedule2, week1);
        weekSummary1.setWeekSummaryId(1L);
        weekSummary2.setWeekSummaryId(2L);

        weekSummaryService = new WeekSummaryService(repository, weekService, scheduleService);
    }

    @Test
    @DisplayName("Check if method getWeekSummaryById() calls repository's method findById()")
    void should_MethodGetWeekSummaryByIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekSummary1));
        //when
        weekSummaryService.getWeekSummaryById(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getWeekSummaryById() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodGetWeekSummaryByIdThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekSummaryService.getWeekSummaryById(1L, 1L));
    }

    @Test
    @DisplayName("Check if method getWeekSummaryById() throws AccessDeniedException when given user has no access to the summary")
    void should_MethodGetWeekSummaryByIdThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenSummary() {
        //given
        given(repository.findById(2L)).willReturn(Optional.of(weekSummary2));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> weekSummaryService.getWeekSummaryById(1L, 2L));
    }

    @Test
    @DisplayName("Check if method getWeekSummaryByScheduleIdAndWeekId() calls repository's method findByScheduleIdAndWeekId()")
    void should_MethodGetWeekSummaryByScheduleIdAndWeekIdCallsRepositoryMethodFindByScheduleIdAndWeekId() {
        //given
        given(repository.findByScheduleIdAndWeekId(1L, 1L)).willReturn(Optional.of(weekSummary1));
        //when
        weekSummaryService.getWeekSummaryByScheduleIdAndWeekId(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findByScheduleIdAndWeekId(1L, 1L);
    }

    @Test
    @DisplayName("Check if method getWeekSummaryByScheduleIdAndWeekId() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodGetWeekSummaryByScheduleIdAndWeekIdThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findByScheduleIdAndWeekId(1L, 1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekSummaryService.getWeekSummaryByScheduleIdAndWeekId(1L, 1L));
    }

    @Test
    @DisplayName("Check if method createWeekSummary() calls weekService's method getWeekById()")
    void should_MethodCreateWeekSummaryCallsWeekServiceMethodGetWeekById() {
        //given
        given(weekService.getWeekById(1L)).willReturn(week1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        //when
        weekSummaryService.createWeekSummary(1L, 1L);
        //then
        verify(weekService, Mockito.times(1)).getWeekById(1L);
    }

    @Test
    @DisplayName("Check if method createWeekSummary() calls scheduleService's method getScheduleById()")
    void should_MethodCreateWeekSummaryCallsScheduleServiceMethodGetScheduleById() {
        //given
        given(weekService.getWeekById(1L)).willReturn(week1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        //when
        weekSummaryService.createWeekSummary(1L, 1L);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method createWeekSummary() calls repository's method save()")
    void should_MethodCreateWeekSummaryCallsScheduleRepositoryMethodSave() {
        //given
        given(weekService.getWeekById(1L)).willReturn(week1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        given(repository.save(any())).willReturn(weekSummary1);
        //when
        weekSummaryService.createWeekSummary(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method createWeekSummary() returns saved summary")
    void should_MethodCreateWeekSummaryReturnsSavedSummary() {
        //given
        given(weekService.getWeekById(1L)).willReturn(week1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        given(repository.save(any())).willReturn(weekSummary1);
        //when
        WeekSummary actual = weekSummaryService.createWeekSummary(1L, 1L);
        //then
        assertSame(weekSummary1, actual);
    }

    @Test
    @DisplayName("Check if method updateWeekSummary() calls repository's method findById()")
    void should_MethodUpdateWeekSummaryCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekSummary1));
        //when
        weekSummaryService.updateWeekSummary(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateWeekSummary() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodUpdateWeekSummaryThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekSummaryService.updateWeekSummary(1L, 1L));
    }

    @Test
    @DisplayName("Check if method updateWeekSummary() throws AccessDeniedException when given user has no access to the summary")
    void should_MethodUpdateWeekSummaryThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenSummary() {
        //given
        given(repository.findById(2L)).willReturn(Optional.of(weekSummary2));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> weekSummaryService.updateWeekSummary(1L, 2L));
    }

    @Test
    @DisplayName("Check if method updateWeekSummary() updates summary fields")
    void should_MethodUpdateWeekSummaryUpdateSummaryFields() {
        //given
        int fulfilledAmountBeforeUpdate = weekSummary1.getFulfilledAmount();
        int failedAmountBeforeUpdate = weekSummary1.getFailedAmount();
        int percentagePlansRatingBeforeUpdate = weekSummary1.getPercentagePlansRating();
        weekPlan2.setFulfilled(true);
        given(repository.findById(1L)).willReturn(Optional.of(weekSummary1));
        //when
        weekSummaryService.updateWeekSummary(1L, 1L);
        //then
        assertAll(
                () -> assertNotEquals(fulfilledAmountBeforeUpdate, weekSummary1.getFulfilledAmount()),
                () -> assertEquals(1, fulfilledAmountBeforeUpdate),
                () -> assertEquals(2, weekSummary1.getFulfilledAmount()),
                () -> assertNotEquals(failedAmountBeforeUpdate, weekSummary1.getFailedAmount()),
                () -> assertEquals(1, failedAmountBeforeUpdate),
                () -> assertEquals(0, weekSummary1.getFailedAmount()),
                () -> assertNotEquals(percentagePlansRatingBeforeUpdate, weekSummary1.getPercentagePlansRating())
        );
    }

    @Test
    @DisplayName("Check if method updateWeekSummary() calls repository's method save() ")
    void should_MethodUpdateWeekSummaryCallsRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekSummary1));
        given(repository.save(weekSummary1)).willReturn(weekSummary1);
        //when
        weekSummaryService.updateWeekSummary(1L, 1);
        //then
        verify(repository, Mockito.times(1)).save(weekSummary1);
    }

    @Test
    @DisplayName("Check if method updateWeekSummary() returns saved summary")
    void should_MethodUpdateWeekSummaryReturnsSavedSummary() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekSummary1));
        given(repository.save(weekSummary1)).willReturn(weekSummary1);
        //when
        WeekSummary actual = weekSummaryService.updateWeekSummary(1L, 1L);
        //then
        assertSame(weekSummary1, actual);
    }
}