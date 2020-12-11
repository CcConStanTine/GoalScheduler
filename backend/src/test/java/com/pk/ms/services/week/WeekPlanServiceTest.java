package com.pk.ms.services.week;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.dao.week.WeekPlanRepository;
import com.pk.ms.dto.week.WeekPlanInputDTO;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.week.WeekPlan;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class WeekPlanServiceTest {

    @Mock
    WeekPlanRepository repository;

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

    WeekPlanInputDTO weekPlanInputDTO;

    WeekPlanService weekPlanService;

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

        weekPlanInputDTO = new WeekPlanInputDTO("Do some stuff 1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 4),
                Importance.REGULAR, Urgency.REGULAR, false);

        weekPlanService = new WeekPlanService(repository, weekService, scheduleService);
    }

    @Test
    @DisplayName("Check if method getWeekPlansByScheduleIdAndWeekId() calls repository's method findWeekPlansByScheduleIdAndWeekId()")
    void should_MethodGetWeekPlansByScheduleIdAndWeekIdCallsRepositoryMethodFindWeekPlansByScheduleIdAndWeekId() {
        //given
        given(repository.findWeekPlansByScheduleIdAndWeekId(1L, 1L))
                .willReturn(Arrays.asList(weekPlan1, weekPlan2));
        //when
        weekPlanService.getWeekPlansByScheduleIdAndWeekId(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findWeekPlansByScheduleIdAndWeekId(1L, 1L);
    }

    @Test
    @DisplayName("Check if method getWeekPlansByScheduleIdAndWeekId() return list of plans given from repository")
    void should_MethodGetWeekPlansByScheduleIdAndWeekIdReturnsListOfPlansGivenFromRepository() {
        //given
        List<WeekPlan> expected = Arrays.asList(weekPlan1, weekPlan2);
        given(repository.findWeekPlansByScheduleIdAndWeekId(1L, 1L))
                .willReturn(expected);
        //when
        List<WeekPlan> actual = weekPlanService.getWeekPlansByScheduleIdAndWeekId(1L, 1L);
        //then
        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertSame(expected.get(0), actual.get(0)),
                () -> assertSame(expected.get(1), actual.get(1))
        );
    }

    @Test
    @DisplayName("Check if method getWeekPlan() calls repository's method findById()")
    void should_MethodGetWeekPlanCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        weekPlanService.getWeekPlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getWeekPlan() throws ResourceNotAvailableException when given plan does not exists")
    void should_MethodGetWeekPlanThrowsResourceNotAvailableException_When_GivenPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekPlanService.getWeekPlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method getWeekPlan() throws AccessDeniedException when given user has no access to the plan")
    void should_MethodGetWeekPlanThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenPlan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(weekPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> weekPlanService.getWeekPlan(1L, 4L));
    }

    @Test
    @DisplayName("Check if method getWeekPlan() return plan given from repository")
    void should_MethodGetWeekPlanReturnPlanGivenFromRepository() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        WeekPlan actual = weekPlanService.getWeekPlan(1L, 1L);
        //then
        assertSame(weekPlan1, actual);
    }

    @Test
    @DisplayName("Check if method createWeekPlan() calls weekService's method getWeekById()")
    void should_MethodCreateWeekPlanCallsWeekServiceMethodGetWeekById() {
        //given
        given(weekService.getWeekById(1L)).willReturn(week1);
        //when
        weekPlanService.createWeekPlan(1L, 1L, weekPlanInputDTO);
        //then
        verify(weekService, Mockito.times(1)).getWeekById(1L);
    }

    @Test
    @DisplayName("Check if method createWeekPlan() throws IllegalArgumentException when startDate input is invalid")
    void should_MethodCreateWeekPlanThrowsIllegalArgumentException_When_StarDateInputIsInValid() {
        //given
        weekPlanInputDTO.setStartDate(LocalDate.of(2020, 3, 1));
        given(weekService.getWeekById(1L)).willReturn(week1);
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> weekPlanService.createWeekPlan(1L, 1L, weekPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method createWeekPlan() throws IllegalArgumentException when endDate input is invalid")
    void should_MethodCreateWeekPlanThrowsIllegalArgumentException_When_EndDateInputIsInValid() {
        //given
        weekPlanInputDTO.setEndDate(LocalDate.of(2020, 3, 1));
        given(weekService.getWeekById(1L)).willReturn(week1);
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> weekPlanService.createWeekPlan(1L, 1L, weekPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method createWeekPlan() calls scheduleService's method getScheduleById()")
    void should_MethodCreateWeekPlanCallsScheduleServiceMethodGetScheduleById() {
        //given
        given(weekService.getWeekById(1L)).willReturn(week1);
        //when
        weekPlanService.createWeekPlan(1L, 1L, weekPlanInputDTO);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method createWeekPlan() calls repository's method save()")
    void should_MethodCreateWeekPlanCallsRepositoryMethodSave() {
        //given
        given(weekService.getWeekById(1L)).willReturn(week1);
        //when
        weekPlanService.createWeekPlan(1L, 1L, weekPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() calls repository's method findById()")
    void should_MethodUpdateWeekPlanCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        weekPlanService.updateWeekPlan(1L, 1L, weekPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() throws ResourceNotAvailableException when called plan does not exist")
    void should_MethodUpdateWeekPlanThrowsResourceNotAvailableExceptionWhenCalledPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekPlanService.updateWeekPlan(1L, 1L, weekPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() throws AccessDeniedException when given user has no access to the plan")
    void should_MethodUpdateWeekPlanThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenPlan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(weekPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> weekPlanService.updateWeekPlan(1L, 4L, weekPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() throws IllegalArgumentException when startDate input is invalid")
    void should_MethodUpdateWeekPlanThrowsIllegalArgumentException_When_StarDateInputIsInValid() {
        //given
        weekPlanInputDTO.setStartDate(LocalDate.of(2020, 3, 1));
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> weekPlanService.updateWeekPlan(1L, 1L, weekPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() throws IllegalArgumentException when endDate input is invalid")
    void should_MethodUpdateWeekPlanThrowsIllegalArgumentException_When_EndDateInputIsInValid() {
        //given
        weekPlanInputDTO.setEndDate(LocalDate.of(2020, 3, 1));
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> weekPlanService.updateWeekPlan(1L, 1L, weekPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() update week plan fields")
    void should_MethodUpdateWeekPlanUpdateWeekPlanFields() {
        //given
        weekPlanInputDTO.setContent("updated content");
        weekPlanInputDTO.setStartDate(LocalDate.of(2020, 1, 2));
        weekPlanInputDTO.setEndDate(LocalDate.of(2020, 1, 3));
        weekPlanInputDTO.setImportance(Importance.VERYIMPORTANT);
        weekPlanInputDTO.setUrgency(Urgency.VERYURGENT);
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        weekPlanService.updateWeekPlan(1L, 1, weekPlanInputDTO);
        //then
        assertAll(
                () -> assertEquals(weekPlanInputDTO.getContent(), weekPlan1.getContent()),
                () -> assertEquals(weekPlanInputDTO.getStartDate(), weekPlan1.getStartDate()),
                () -> assertEquals(weekPlanInputDTO.getEndDate(), weekPlan1.getEndDate()),
                () -> assertEquals(weekPlanInputDTO.getImportance(), weekPlan1.getImportance()),
                () -> assertEquals(weekPlanInputDTO.getUrgency(), weekPlan1.getUrgency())
        );
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() do not update importance when weekPlanInputDTO importance is null")
    void should_MethodUpdateWeekPlanNotUpdateWeekPlanImportanceField_When_WeekPlanInputDTOImportanceIsNull() {
        //given
        weekPlanInputDTO.setImportance(null);
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        weekPlanService.updateWeekPlan(1L, 1, weekPlanInputDTO);
        //then
        assertNotEquals(weekPlanInputDTO.getImportance(), weekPlan1.getImportance());
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() do not update urgency when weekPlanInputDTO urgency is null")
    void should_MethodUpdateWeekPlanNotUpdateWeekPlanImportanceField_When_WeekPlanInputDTOUrgencyIsNull() {
        //given
        weekPlanInputDTO.setUrgency(null);
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        weekPlanService.updateWeekPlan(1L, 1, weekPlanInputDTO);
        //then
        assertNotEquals(weekPlanInputDTO.getUrgency(), weekPlan1.getUrgency());
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() do not update fulfilled field")
    void should_MethodUpdateWeekPlanNotUpdateWeekPlanFulfilledField() {
        //given
        weekPlan1.setFulfilled(true);
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        weekPlanService.updateWeekPlan(1L, 1, weekPlanInputDTO);
        //then
        assertNotEquals(weekPlanInputDTO.isFulfilled(), weekPlan1.isFulfilled());
    }

    @Test
    @DisplayName("Check if method updateWeekPlan() call repository method save() ")
    void should_MethodUpdateWeekPlanCallRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        given(repository.save(weekPlan1)).willReturn(weekPlan1);
        //when
        weekPlanService.updateWeekPlan(1L, 1, weekPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any(WeekPlan.class));
    }

    @Test
    @DisplayName("Check if method deleteWeekPlan() call repository method findById() ")
    void should_MethodDeleteWeekPlanCallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        weekPlanService.deleteWeekPlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method deleteWeekPlan() throws ResourceNotAvailableException when called plan does not exists")
    void should_MethodDeleteWeekPlanThrowResourceNotAvailableException_When_CalledPlanDoesNotExists() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekPlanService.deleteWeekPlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method deleteWeekPlan() throws AccessDeniedException given User has no access to the plan")
    void should_MethodDeleteWeekPlanThrowAccessDeniedException_When_UserHasNoAccessToThePLan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(weekPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> weekPlanService.deleteWeekPlan(1L, 4L));
    }

    @Test
    @DisplayName("Check if method deleteWeekPlan() returns proper message when the plan has been deleted")
    void should_ReturnProperMessage_When_PlanHasBeenDeleted() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        String message = weekPlanService.deleteWeekPlan(1L, 1L);
        //then
        assertEquals("Plan deleted successfully. ", message);
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method findById() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        weekPlanService.updateFulfilledStatus(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() throws ResourceNotAvailableException when then plan does not exists")
    void should_MethodUpdateFulfilledStatusThrowResourceNotAvailableException_When_ThePlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekPlanService.updateFulfilledStatus(1L, 1L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() throws AccessDeniedException given User has no access to the plan with given id")
    void should_MethodUpdateFulfilledStatusThrowAccessDeniedException_When_UserHasNoAccessToThePLanWithGivenId() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(weekPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> weekPlanService.updateFulfilledStatus(1L, 4L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() updates fulfilled field")
    void should_MethodUpdateFulfilledStatusUpdateWeekPlanFulFilledField() {
        //given
        weekPlan1.setFulfilled(false);
        boolean expected1 = weekPlan1.isFulfilled();
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        //when
        weekPlanService.updateFulfilledStatus(1L, 1);
        //then
        assertAll(
                () -> assertFalse(expected1),
                () -> assertTrue(weekPlan1.isFulfilled())
        );
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method save() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(weekPlan1));
        given(repository.save(weekPlan1)).willReturn(weekPlan1);
        //when
        weekPlanService.updateFulfilledStatus(1L, 1);
        //then
        verify(repository, Mockito.times(1)).save(any(WeekPlan.class));
    }

}