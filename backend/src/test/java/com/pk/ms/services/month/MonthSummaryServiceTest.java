package com.pk.ms.services.month;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.MonthName;
import com.pk.ms.dao.month.MonthSummaryRepository;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.entities.month.MonthSummary;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
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
class MonthSummaryServiceTest {

    @Mock
    MonthSummaryRepository repository;

    @Mock
    MonthService monthService;

    @Mock
    ScheduleService scheduleService;

    MyScheduleUser user1;
    MyScheduleUser user2;
    Schedule schedule1;
    Schedule schedule2;

    Year year2020;

    Month month1;
    Month month2;

    MonthPlan monthPlan1;
    MonthPlan monthPlan2;
    MonthPlan monthPlan3;
    MonthPlan monthPlan4;

    MonthSummary monthSummary1;
    MonthSummary monthSummary2;

    MonthSummaryService monthSummaryService;

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

        month1 = new Month(MonthName.JANUARY, year2020);
        month2 = new Month(MonthName.FEBRUARY, year2020);
        month1.setMonthId(1L);
        month2.setMonthId(2L);

        monthPlan1 = new MonthPlan("Do some stuff 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 31),
                schedule1, month1);
        monthPlan2 = new MonthPlan("Do some stuff 2",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 25),
                schedule1, month1);
        monthPlan3 = new MonthPlan("Do some stuff 3",
                LocalDate.of(2020, 2, 2),
                LocalDate.of(2020, 2, 25),
                schedule1, month2);
        monthPlan4 = new MonthPlan("Do some stuff 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 10),
                schedule2, month1);
        monthPlan1.setMonthPlanId(1L);
        monthPlan2.setMonthPlanId(2L);
        monthPlan3.setMonthPlanId(3L);
        monthPlan4.setMonthPlanId(4L);

        schedule1.addMonthPlan(monthPlan1);
        schedule1.addMonthPlan(monthPlan2);
        schedule1.addMonthPlan(monthPlan3);
        schedule2.addMonthPlan(monthPlan4);

        monthPlan1.setMonthPlanId(1L);
        monthPlan1.setImportance(Importance.IMPORTANT);
        monthPlan1.setFulfilled(true);
        monthPlan2.setMonthPlanId(2L);
        monthPlan2.setFulfilled(false);

        monthPlan3.setMonthPlanId(3L);
        monthPlan4.setMonthPlanId(4L);

        monthSummary1 = new MonthSummary(schedule1, month1);
        monthSummary2 = new MonthSummary(schedule2, month1);
        monthSummary1.setMonthSummaryId(1L);
        monthSummary2.setMonthSummaryId(2L);

        monthSummaryService = new MonthSummaryService(repository, monthService, scheduleService);
    }

    @Test
    @DisplayName("Check if method getMonthSummaryById() calls repository's method findById()")
    void should_MethodGetMonthSummaryByIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthSummary1));
        //when
        monthSummaryService.getMonthSummaryById(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getMonthSummaryById() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodGetMonthSummaryByIdThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> monthSummaryService.getMonthSummaryById(1L, 1L));
    }

    @Test
    @DisplayName("Check if method getMonthSummaryById() throws AccessDeniedException when given user has no access to the summary")
    void should_MethodGetMonthSummaryByIdThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenSummary() {
        //given
        given(repository.findById(2L)).willReturn(Optional.of(monthSummary2));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> monthSummaryService.getMonthSummaryById(1L, 2L));
    }

    @Test
    @DisplayName("Check if method getMonthSummaryByScheduleIdAndMonthId() calls repository's method findByScheduleIdAndMonthId()")
    void should_MethodGetMonthSummaryByScheduleIdAndMonthIdCallsRepositoryMethodFindByScheduleIdAndMonthId() {
        //given
        given(repository.findByScheduleIdAndMonthId(1L, 1L)).willReturn(Optional.of(monthSummary1));
        //when
        monthSummaryService.getMonthSummaryByScheduleIdAndMonthId(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findByScheduleIdAndMonthId(1L, 1L);
    }

    @Test
    @DisplayName("Check if method getMonthSummaryByScheduleIdAndMonthId() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodGetMonthSummaryByScheduleIdAndMonthIdThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findByScheduleIdAndMonthId(1L, 1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> monthSummaryService.getMonthSummaryByScheduleIdAndMonthId(1L, 1L));
    }

    @Test
    @DisplayName("Check if method createMonthSummary() calls monthService's method getMonthById()")
    void should_MethodCreateMonthSummaryCallsMonthServiceMethodGetMonthById() {
        //given
        given(monthService.getMonthById(1L)).willReturn(month1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        //when
        monthSummaryService.createMonthSummary(1L, 1L);
        //then
        verify(monthService, Mockito.times(1)).getMonthById(1L);
    }

    @Test
    @DisplayName("Check if method createMonthSummary() calls scheduleService's method getScheduleById()")
    void should_MethodCreateMonthSummaryCallsScheduleServiceMethodGetScheduleById() {
        //given
        given(monthService.getMonthById(1L)).willReturn(month1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        //when
        monthSummaryService.createMonthSummary(1L, 1L);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method createMonthSummary() calls repository's method save()")
    void should_MethodCreateMonthSummaryCallsScheduleRepositoryMethodSave() {
        //given
        given(monthService.getMonthById(1L)).willReturn(month1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        given(repository.save(any())).willReturn(monthSummary1);
        //when
        monthSummaryService.createMonthSummary(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method createMonthSummary() returns saved summary")
    void should_MethodCreateMonthSummaryReturnsSavedSummary() {
        //given
        given(monthService.getMonthById(1L)).willReturn(month1);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        given(repository.save(any())).willReturn(monthSummary1);
        //when
        MonthSummary actual = monthSummaryService.createMonthSummary(1L, 1L);
        //then
        assertSame(monthSummary1, actual);
    }

    @Test
    @DisplayName("Check if method updateMonthSummary() calls repository's method findById()")
    void should_MethodUpdateMonthSummaryCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthSummary1));
        //when
        monthSummaryService.updateMonthSummary(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateMonthSummary() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodUpdateMonthSummaryThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> monthSummaryService.updateMonthSummary(1L, 1L));
    }

    @Test
    @DisplayName("Check if method updateMonthSummary() throws AccessDeniedException when given user has no access to the summary")
    void should_MethodUpdateMonthSummaryThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenSummary() {
        //given
        given(repository.findById(2L)).willReturn(Optional.of(monthSummary2));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> monthSummaryService.updateMonthSummary(1L, 2L));
    }

    @Test
    @DisplayName("Check if method updateMonthSummary() updates summary fields")
    void should_MethodUpdateMonthSummaryUpdateSummaryFields() {
        //given
        int fulfilledAmountBeforeUpdate = monthSummary1.getFulfilledAmount();
        int failedAmountBeforeUpdate = monthSummary1.getFailedAmount();
        int percentagePlansRatingBeforeUpdate = monthSummary1.getPercentagePlansRating();
        monthPlan2.setFulfilled(true);
        given(repository.findById(1L)).willReturn(Optional.of(monthSummary1));
        //when
        monthSummaryService.updateMonthSummary(1L, 1L);
        //then
        assertAll(
                () -> assertNotEquals(fulfilledAmountBeforeUpdate, monthSummary1.getFulfilledAmount()),
                () -> assertEquals(1, fulfilledAmountBeforeUpdate),
                () -> assertEquals(2, monthSummary1.getFulfilledAmount()),
                () -> assertNotEquals(failedAmountBeforeUpdate, monthSummary1.getFailedAmount()),
                () -> assertEquals(1, failedAmountBeforeUpdate),
                () -> assertEquals(0, monthSummary1.getFailedAmount()),
                () -> assertNotEquals(percentagePlansRatingBeforeUpdate, monthSummary1.getPercentagePlansRating())
        );
    }

    @Test
    @DisplayName("Check if method updateMonthSummary() calls repository's method save() ")
    void should_MethodUpdateMonthSummaryCallsRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthSummary1));
        given(repository.save(monthSummary1)).willReturn(monthSummary1);
        //when
        monthSummaryService.updateMonthSummary(1L, 1);
        //then
        verify(repository, Mockito.times(1)).save(monthSummary1);
    }

    @Test
    @DisplayName("Check if method updateMonthSummary() returns saved summary")
    void should_MethodUpdateMonthSummaryReturnsSavedSummary() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthSummary1));
        given(repository.save(monthSummary1)).willReturn(monthSummary1);
        //when
        MonthSummary actual = monthSummaryService.updateMonthSummary(1L, 1L);
        //then
        assertSame(monthSummary1, actual);
    }

}