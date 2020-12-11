package com.pk.ms.services.month;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.MonthName;
import com.pk.ms.constants.Urgency;
import com.pk.ms.dao.month.MonthPlanRepository;
import com.pk.ms.dto.month.MonthPlanInputDTO;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.month.MonthPlan;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class MonthPlanServiceTest {

    @Mock
    MonthPlanRepository repository;

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

    MonthPlanInputDTO monthPlanInputDTO;

    MonthPlanService monthPlanService;

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

        monthPlanInputDTO = new MonthPlanInputDTO("Do some stuff 1",
                LocalDate.of(2020, 1, 5),
                LocalDate.of(2020, 1, 20),
                Importance.REGULAR, Urgency.REGULAR, false);

        monthPlanService = new MonthPlanService(repository, monthService, scheduleService);
    }

    @Test
    @DisplayName("Check if method getMonthPlansByScheduleIdAndMonthId() calls repository's method findMonthPlansByScheduleIdAndMonthId()")
    void should_MethodGetMonthPlansByScheduleIdAndMonthIdCallsRepositoryMethodFindMonthPlansByScheduleIdAndMonthId() {
        //given
        given(repository.findMonthPlansByScheduleIdAndMonthId(1L, 1L))
                .willReturn(Arrays.asList(monthPlan1, monthPlan2));
        //when
        monthPlanService.getMonthPlansByScheduleIdAndMonthId(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findMonthPlansByScheduleIdAndMonthId(1L, 1L);
    }

    @Test
    @DisplayName("Check if method getMonthPlansByScheduleIdAndMonthId() return list of plans given from repository")
    void should_MethodGetMonthPlansByScheduleIdAndMonthIdReturnsListOfPlansGivenFromRepository() {
        //given
        List<MonthPlan> expected = Arrays.asList(monthPlan1, monthPlan2);
        given(repository.findMonthPlansByScheduleIdAndMonthId(1L, 1L))
                .willReturn(expected);
        //when
        List<MonthPlan> actual = monthPlanService.getMonthPlansByScheduleIdAndMonthId(1L, 1L);
        //then
        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertSame(expected.get(0), actual.get(0)),
                () -> assertSame(expected.get(1), actual.get(1))
        );
    }

    @Test
    @DisplayName("Check if method getMonthPlan() calls repository's method findById()")
    void should_MethodGetMonthPlanCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        monthPlanService.getMonthPlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getMonthPlan() throws ResourceNotAvailableException when given plan does not exists")
    void should_MethodGetMonthPlanThrowsResourceNotAvailableException_When_GivenPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> monthPlanService.getMonthPlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method getMonthPlan() throws AccessDeniedException when given user has no access to the plan")
    void should_MethodGetMonthPlanThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenPlan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(monthPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> monthPlanService.getMonthPlan(1L, 4L));
    }

    @Test
    @DisplayName("Check if method getMonthPlan() return plan given from repository")
    void should_MethodGetMonthPlanReturnPlanGivenFromRepository() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        MonthPlan actual = monthPlanService.getMonthPlan(1L, 1L);
        //then
        assertSame(monthPlan1, actual);
    }

    @Test
    @DisplayName("Check if method createMonthPlan() calls monthService's method getMonthById()")
    void should_MethodCreateMonthPlanCallsMonthServiceMethodGetMonthById() {
        //given
        given(monthService.getMonthById(1L)).willReturn(month1);
        //when
        monthPlanService.createMonthPlan(1L, 1L, monthPlanInputDTO);
        //then
        verify(monthService, Mockito.times(1)).getMonthById(1L);
    }

    @Test
    @DisplayName("Check if method createMonthPlan() throws IllegalArgumentException when startDate input is invalid")
    void should_MethodCreateMonthPlanThrowsIllegalArgumentException_When_StarDateInputIsInValid() {
        //given
        monthPlanInputDTO.setStartDate(LocalDate.of(2020, 3, 1));
        given(monthService.getMonthById(1L)).willReturn(month1);
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> monthPlanService.createMonthPlan(1L, 1L, monthPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method createMonthPlan() throws IllegalArgumentException when endDate input is invalid")
    void should_MethodCreateMonthPlanThrowsIllegalArgumentException_When_EndDateInputIsInValid() {
        //given
        monthPlanInputDTO.setEndDate(LocalDate.of(2020, 3, 1));
        given(monthService.getMonthById(1L)).willReturn(month1);
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> monthPlanService.createMonthPlan(1L, 1L, monthPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method createMonthPlan() calls scheduleService's method getScheduleById()")
    void should_MethodCreateMonthPlanCallsScheduleServiceMethodGetScheduleById() {
        //given
        given(monthService.getMonthById(1L)).willReturn(month1);
        //when
        monthPlanService.createMonthPlan(1L, 1L, monthPlanInputDTO);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method createMonthPlan() calls repository's method save()")
    void should_MethodCreateMonthPlanCallsRepositoryMethodSave() {
        //given
        given(monthService.getMonthById(1L)).willReturn(month1);
        //when
        monthPlanService.createMonthPlan(1L, 1L, monthPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() calls repository's method findById()")
    void should_MethodUpdateMonthPlanCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        monthPlanService.updateMonthPlan(1L, 1L, monthPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() throws ResourceNotAvailableException when called plan does not exist")
    void should_MethodUpdateMonthPlanThrowsResourceNotAvailableExceptionWhenCalledPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> monthPlanService.updateMonthPlan(1L, 1L, monthPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() throws AccessDeniedException when given user has no access to the plan")
    void should_MethodUpdateMonthPlanThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenPlan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(monthPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> monthPlanService.updateMonthPlan(1L, 4L, monthPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() throws IllegalArgumentException when startDate input is invalid")
    void should_MethodUpdateMonthPlanThrowsIllegalArgumentException_When_StarDateInputIsInValid() {
        //given
        monthPlanInputDTO.setStartDate(LocalDate.of(2020, 3, 1));
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> monthPlanService.updateMonthPlan(1L, 1L, monthPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() throws IllegalArgumentException when endDate input is invalid")
    void should_MethodUpdateMonthPlanThrowsIllegalArgumentException_When_EndDateInputIsInValid() {
        //given
        monthPlanInputDTO.setEndDate(LocalDate.of(2020, 3, 1));
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> monthPlanService.updateMonthPlan(1L, 1L, monthPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() update month plan fields")
    void should_MethodUpdateMonthPlanUpdateMonthPlanFields() {
        //given
        monthPlanInputDTO.setContent("updated content");
        monthPlanInputDTO.setStartDate(LocalDate.of(2020, 1, 15));
        monthPlanInputDTO.setEndDate(LocalDate.of(2020, 1, 16));
        monthPlanInputDTO.setImportance(Importance.VERYIMPORTANT);
        monthPlanInputDTO.setUrgency(Urgency.VERYURGENT);
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        monthPlanService.updateMonthPlan(1L, 1, monthPlanInputDTO);
        //then
        assertAll(
                () -> assertEquals(monthPlanInputDTO.getContent(), monthPlan1.getContent()),
                () -> assertEquals(monthPlanInputDTO.getStartDate(), monthPlan1.getStartDate()),
                () -> assertEquals(monthPlanInputDTO.getEndDate(), monthPlan1.getEndDate()),
                () -> assertEquals(monthPlanInputDTO.getImportance(), monthPlan1.getImportance()),
                () -> assertEquals(monthPlanInputDTO.getUrgency(), monthPlan1.getUrgency())
        );
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() do not update importance when monthPlanInputDTO importance is null")
    void should_MethodUpdateMonthPlanNotUpdateMonthPlanImportanceField_When_MonthPlanInputDTOImportanceIsNull() {
        //given
        monthPlanInputDTO.setImportance(null);
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        monthPlanService.updateMonthPlan(1L, 1, monthPlanInputDTO);
        //then
        assertNotEquals(monthPlanInputDTO.getImportance(), monthPlan1.getImportance());
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() do not update urgency when monthPlanInputDTO urgency is null")
    void should_MethodUpdateMonthPlanNotUpdateMonthPlanImportanceField_When_MonthPlanInputDTOUrgencyIsNull() {
        //given
        monthPlanInputDTO.setUrgency(null);
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        monthPlanService.updateMonthPlan(1L, 1, monthPlanInputDTO);
        //then
        assertNotEquals(monthPlanInputDTO.getUrgency(), monthPlan1.getUrgency());
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() do not update fulfilled field")
    void should_MethodUpdateMonthPlanNotUpdateMonthPlanFulfilledField() {
        //given
        monthPlan1.setFulfilled(true);
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        monthPlanService.updateMonthPlan(1L, 1, monthPlanInputDTO);
        //then
        assertNotEquals(monthPlanInputDTO.isFulfilled(), monthPlan1.isFulfilled());
    }

    @Test
    @DisplayName("Check if method updateMonthPlan() call repository method save() ")
    void should_MethodUpdateMonthPlanCallRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        given(repository.save(monthPlan1)).willReturn(monthPlan1);
        //when
        monthPlanService.updateMonthPlan(1L, 1, monthPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any(MonthPlan.class));
    }

    @Test
    @DisplayName("Check if method deleteMonthPlan() call repository method findById() ")
    void should_MethodDeleteYearPlanCallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        monthPlanService.deleteMonthPlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method deleteMonthPlan() throws ResourceNotAvailableException when called plan does not exists")
    void should_MethodDeleteMonthPlanThrowResourceNotAvailableException_When_CalledPlanDoesNotExists() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> monthPlanService.deleteMonthPlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method deleteMonthPlan() throws AccessDeniedException given User has no access to the plan")
    void should_MethodDeleteMonthPlanThrowAccessDeniedException_When_UserHasNoAccessToThePLan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(monthPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> monthPlanService.deleteMonthPlan(1L, 4L));
    }

    @Test
    @DisplayName("Check if method deleteMonthPlan() returns proper message when the plan has been deleted")
    void should_ReturnProperMessage_When_PlanHasBeenDeleted() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        String message = monthPlanService.deleteMonthPlan(1L, 1L);
        //then
        assertEquals("Plan deleted successfully. ", message);
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method findById() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        monthPlanService.updateFulfilledStatus(1L, 1L);
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
                () -> monthPlanService.updateFulfilledStatus(1L, 1L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() throws AccessDeniedException given User has no access to the plan with given id")
    void should_MethodUpdateFulfilledStatusThrowAccessDeniedException_When_UserHasNoAccessToThePLanWithGivenId() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(monthPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> monthPlanService.updateFulfilledStatus(1L, 4L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() updates fulfilled field")
    void should_MethodUpdateFulfilledStatusUpdateMonthPlanFulFilledField() {
        //given
        monthPlan1.setFulfilled(false);
        boolean expected1 = monthPlan1.isFulfilled();
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        //when
        monthPlanService.updateFulfilledStatus(1L, 1);
        //then
        assertAll(
                () -> assertFalse(expected1),
                () -> assertTrue(monthPlan1.isFulfilled())
        );
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method save() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(monthPlan1));
        given(repository.save(monthPlan1)).willReturn(monthPlan1);
        //when
        monthPlanService.updateFulfilledStatus(1L, 1);
        //then
        verify(repository, Mockito.times(1)).save(any(MonthPlan.class));
    }

}