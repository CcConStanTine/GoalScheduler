package com.pk.ms.services.year;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.dao.year.YearPlanRepository;
import com.pk.ms.dao.year.YearSummaryRepository;
import com.pk.ms.dto.year.YearPlanInputDTO;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.entities.year.YearSummary;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class YearSummaryServiceTest {

    @Mock
    YearSummaryRepository repository;

    @Mock
    YearService yearService;

    @Mock
    ScheduleService scheduleService;

    MyScheduleUser user1;
    MyScheduleUser user2;
    Schedule schedule1;
    Schedule schedule2;

    Year year2020;
    Year year2021;

    YearPlan yearPlan1;
    YearPlan yearPlan2;
    YearPlan yearPlan3;
    YearPlan yearPlan4;

    YearSummary yearSummary1;
    YearSummary yearSummary2;

    YearSummaryService yearSummaryService;

    @BeforeEach
    void setUp() {
        user1 = new MyScheduleUser();
        user2 = new MyScheduleUser();
        schedule1 = new Schedule(user1);
        schedule2 = new Schedule(user2);
        schedule1.setScheduleId(1L);
        schedule2.setScheduleId(2L);

        year2020 = new Year(2020);
        year2021 = new Year(2021);
        year2020.setYearId(1L);
        year2021.setYearId(2L);

        yearPlan1 = new YearPlan("Do some stuff 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 12, 31),
                schedule1, year2020);
        yearPlan2 = new YearPlan("Do some stuff 2",
                LocalDate.of(2020, 1, 2),
                LocalDate.of(2020, 10, 25),
                schedule1, year2020);
        yearPlan3 = new YearPlan("Do some stuff 3",
                LocalDate.of(2021, 1, 2),
                LocalDate.of(2021, 10, 25),
                schedule1, year2021);
        yearPlan4 = new YearPlan("Do some stuff 1",
                LocalDate.of(2020, 1, 3),
                LocalDate.of(2020, 5, 12),
                schedule2, year2020);

        schedule1.addYearPlan(yearPlan1);
        schedule1.addYearPlan(yearPlan2);
        schedule1.addYearPlan(yearPlan3);
        schedule2.addYearPlan(yearPlan4);

        yearPlan1.setYearPlanId(1L);
        yearPlan1.setImportance(Importance.IMPORTANT);
        yearPlan1.setFulfilled(true);
        yearPlan2.setYearPlanId(2L);
        yearPlan2.setFulfilled(false);

        yearPlan3.setYearPlanId(3L);
        yearPlan4.setYearPlanId(4L);

        yearSummary1 = new YearSummary(schedule1, year2020);
        yearSummary2 = new YearSummary(schedule2, year2020);
        yearSummary1.setYearSummaryId(1L);
        yearSummary2.setYearSummaryId(2L);

        yearSummaryService = new YearSummaryService(repository, yearService, scheduleService);
    }

    @Test
    @DisplayName("Check if method getYearSummaryById() calls repository's method findById()")
    void should_MethodGetYearSummaryByIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearSummary1));
        //when
        yearSummaryService.getYearSummaryById(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getYearSummaryById() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodGetYearSummaryByIdThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> yearSummaryService.getYearSummaryById(1L, 1L));
    }

    @Test
    @DisplayName("Check if method getYearSummaryById() throws AccessDeniedException when given user has no access to the summary")
    void should_MethodGetYearSummaryByIdThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenSummary() {
        //given
        given(repository.findById(2L)).willReturn(Optional.of(yearSummary2));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> yearSummaryService.getYearSummaryById(1L, 2L));
    }

    @Test
    @DisplayName("Check if method getYearSummaryByScheduleIdAndYearId() calls repository's method findByScheduleIdAndYearId()")
    void should_MethodGetYearSummaryByScheduleIdAndYearIdCallsRepositoryMethodFindByScheduleIdAndYearId() {
        //given
        given(repository.findByScheduleIdAndYearId(1L, 1L)).willReturn(Optional.of(yearSummary1));
        //when
        yearSummaryService.getYearSummaryByScheduleIdAndYearId(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findByScheduleIdAndYearId(1L, 1L);
    }

    @Test
    @DisplayName("Check if method getYearSummaryByScheduleIdAndYearId() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodGetYearSummaryByScheduleIdAndYearIdThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findByScheduleIdAndYearId(1L, 1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> yearSummaryService.getYearSummaryByScheduleIdAndYearId(1L, 1L));
    }

    @Test
    @DisplayName("Check if method createYearSummary() calls yearService's method getYearById()")
    void should_MethodCreateYearSummaryCallsYearServiceMethodGetYearById() {
        //given
        given(yearService.getYearById(1L)).willReturn(year2020);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        //when
        yearSummaryService.createYearSummary(1L, 1L);
        //then
        verify(yearService, Mockito.times(1)).getYearById(1L);
    }

    @Test
    @DisplayName("Check if method createYearSummary() calls scheduleService's method getScheduleById()")
    void should_MethodCreateYearSummaryCallsScheduleServiceMethodGetScheduleById() {
        //given
        given(yearService.getYearById(1L)).willReturn(year2020);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        //when
        yearSummaryService.createYearSummary(1L, 1L);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method createYearSummary() calls repository's method save()")
    void should_MethodCreateYearSummaryCallsScheduleRepositoryMethodSave() {
        //given
        given(yearService.getYearById(1L)).willReturn(year2020);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        given(repository.save(any())).willReturn(yearSummary1);
        //when
        yearSummaryService.createYearSummary(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method createYearSummary() returns saved summary")
    void should_MethodCreateYearSummaryReturnsSavedSummary() {
        //given
        given(yearService.getYearById(1L)).willReturn(year2020);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule1);
        given(repository.save(any())).willReturn(yearSummary1);
        //when
        YearSummary actual = yearSummaryService.createYearSummary(1L, 1L);
        //then
        assertSame(yearSummary1, actual);
    }

    @Test
    @DisplayName("Check if method updateYearSummary() calls repository's method findById()")
    void should_MethodUpdateYearSummaryCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearSummary1));
        //when
        yearSummaryService.updateYearSummary(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateYearSummary() throws ResourceNotAvailableException when given summary does not exists")
    void should_MethodUpdateYearSummaryThrowsResourceNotAvailableException_When_GivenSummaryDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> yearSummaryService.updateYearSummary(1L, 1L));
    }

    @Test
    @DisplayName("Check if method updateYearSummary() throws AccessDeniedException when given user has no access to the summary")
    void should_MethodUpdateYearSummaryThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenSummary() {
        //given
        given(repository.findById(2L)).willReturn(Optional.of(yearSummary2));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> yearSummaryService.updateYearSummary(1L, 2L));
    }

    @Test
    @DisplayName("Check if method updateYearSummary() updates summary fields")
    void should_MethodUpdateYearSummaryUpdateSummaryFields() {
        //given
        int fulfilledAmountBeforeUpdate = yearSummary1.getFulfilledAmount();
        int failedAmountBeforeUpdate = yearSummary1.getFailedAmount();
        int percentagePlansRatingBeforeUpdate = yearSummary1.getPercentagePlansRating();
        yearPlan2.setFulfilled(true);
        given(repository.findById(1L)).willReturn(Optional.of(yearSummary1));
        //when
        yearSummaryService.updateYearSummary(1L, 1L);
        //then
        assertAll(
                () -> assertNotEquals(fulfilledAmountBeforeUpdate, yearSummary1.getFulfilledAmount()),
                () -> assertEquals(1, fulfilledAmountBeforeUpdate),
                () -> assertEquals(2, yearSummary1.getFulfilledAmount()),
                () -> assertNotEquals(failedAmountBeforeUpdate, yearSummary1.getFailedAmount()),
                () -> assertEquals(1, failedAmountBeforeUpdate),
                () -> assertEquals(0, yearSummary1.getFailedAmount()),
                () -> assertNotEquals(percentagePlansRatingBeforeUpdate, yearSummary1.getPercentagePlansRating())
        );
    }

    @Test
    @DisplayName("Check if method updateYearSummary() calls repository's method save() ")
    void should_MethodUpdateYearSummaryCallsRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearSummary1));
        given(repository.save(yearSummary1)).willReturn(yearSummary1);
        //when
        yearSummaryService.updateYearSummary(1L, 1);
        //then
        verify(repository, Mockito.times(1)).save(yearSummary1);
    }

    @Test
    @DisplayName("Check if method updateYearSummary() returns saved summary")
    void should_MethodUpdateYearSummaryReturnsSavedSummary() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearSummary1));
        given(repository.save(yearSummary1)).willReturn(yearSummary1);
        //when
        YearSummary actual = yearSummaryService.updateYearSummary(1L, 1L);
        //then
        assertSame(yearSummary1, actual);
    }

}