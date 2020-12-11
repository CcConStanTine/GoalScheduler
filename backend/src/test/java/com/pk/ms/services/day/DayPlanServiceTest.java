package com.pk.ms.services.day;

import com.pk.ms.constants.DayName;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.dao.day.DayPlanRepository;
import com.pk.ms.dto.day.DayPlanInputDTO;
import com.pk.ms.entities.day.DailyRoutinePlan;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.day.DayPlan;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class DayPlanServiceTest {

    @Mock
    DayPlanRepository repository;

    @Mock
    DayService dayService;

    @Mock
    ScheduleService scheduleService;

    @Mock
    DailyRoutinePlanService dailyRoutinePlanService;

    MyScheduleUser user1;
    MyScheduleUser user2;
    Schedule schedule1;
    Schedule schedule2;

    Day day1;
    Day day2;

    DayPlan dayPlan1;
    DayPlan dayPlan2;
    DayPlan dayPlan3;
    DayPlan dayPlan4;

    DayPlanInputDTO dayPlanInputDTO;

    DayPlanService dayPlanService;

    @BeforeEach
    void setUp() {
        user1 = new MyScheduleUser();
        user2 = new MyScheduleUser();
        schedule1 = new Schedule(user1);
        schedule2 = new Schedule(user2);
        schedule1.setScheduleId(1L);
        schedule2.setScheduleId(2L);

        day1 = new Day(LocalDate.of(2020, 1, 1), DayName.WEDNESDAY, null);
        day2 = new Day(LocalDate.of(2020, 1, 2), DayName.THURSDAY, null);
        day1.setDayId(1L);
        day1.setDayId(2L);

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

        dayPlanInputDTO = new DayPlanInputDTO(dayPlan1.getContent(), dayPlan2.getStartDate(), dayPlan1.getEndDate(),
                dayPlan1.getImportance(), dayPlan1.getUrgency(), dayPlan1.isFulfilled());

        dayPlanService = new DayPlanService(repository, dayService, scheduleService,dailyRoutinePlanService);
    }

    @Test
    @DisplayName("Check if method addDailyRoutinePlansToTheDayPlans() calls dailyRoutinePlanService's method getDailyRoutinePlans()")
    void should_CallDailyRoutinePlanServiceMethodGetDailyRoutinePlans() {
        //given
        given(dailyRoutinePlanService.getDailyRoutinePlans(1L)).willReturn(Collections.emptyList());
        //when
        dayPlanService.addDailyRoutinePlansToTheDayPlans(1L, 1L);
        //then
        verify(dailyRoutinePlanService, Mockito.times(1)).getDailyRoutinePlans(1L);
    }

    @Test
    @DisplayName("Check if method addDailyRoutinePlansToTheDayPlans() calls repository's method save()")
    void should_CallRepositoryMethodSaveManyTimes() {
        //given
        given(dailyRoutinePlanService.getDailyRoutinePlans(1L)).willReturn(Arrays.asList(
                new DailyRoutinePlan("some content", LocalTime.of(8, 30, 0),
                        LocalTime.of(9, 0, 0), user1),
                new DailyRoutinePlan("some content 2", LocalTime.of(15, 30, 0),
                        LocalTime.of(17, 0, 0), user1)
        ));
        //when
        dayPlanService.addDailyRoutinePlansToTheDayPlans(1L, 1L);
        //then
        verify(repository, Mockito.times(2)).save(any());
    }

    @Test
    @DisplayName("Check if method getDayPlansByScheduleIdAndDayId() calls repository's method findDayPlansByScheduleIdAndDayId()")
    void should_MethodGetDayPlansByScheduleIdAndDayIdCallsRepositoryMethodFindDayPlansByScheduleIdAndDayId() {
        //given
        given(repository.findDayPlansByScheduleIdAndDayId(1L, 1L))
                .willReturn(Arrays.asList(dayPlan1, dayPlan2));
        //when
        dayPlanService.getDayPlansByScheduleIdAndDayId(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findDayPlansByScheduleIdAndDayId(1L, 1L);
    }

    @Test
    @DisplayName("Check if method getDayPlansByScheduleIdAndDayId() return list of plans given from repository")
    void should_MethodGetDayPlansByScheduleIdAndDayIdReturnsListOfPlansGivenFromRepository() {
        //given
        List<DayPlan> expected = Arrays.asList(dayPlan1, dayPlan2);
        given(repository.findDayPlansByScheduleIdAndDayId(1L, 1L))
                .willReturn(expected);
        //when
        List<DayPlan> actual = dayPlanService.getDayPlansByScheduleIdAndDayId(1L, 1L);
        //then
        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertSame(expected.get(0), actual.get(0)),
                () -> assertSame(expected.get(1), actual.get(1))
        );
    }

    @Test
    @DisplayName("Check if method getDayPlan() calls repository's method findById()")
    void should_MethodGetDayPlanCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        dayPlanService.getDayPlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getDayPlan() throws ResourceNotAvailableException when given plan does not exists")
    void should_MethodGetDayPlanThrowsResourceNotAvailableException_When_GivenPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> dayPlanService.getDayPlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method getDayPlan() throws AccessDeniedException when given user has no access to the plan")
    void should_MethodGetDayPlanThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenPlan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(dayPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> dayPlanService.getDayPlan(1L, 4L));
    }

    @Test
    @DisplayName("Check if method getDayPlan() return plan given from repository")
    void should_MethodGetDayPlanReturnPlanGivenFromRepository() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        DayPlan actual = dayPlanService.getDayPlan(1L, 1L);
        //then
        assertSame(dayPlan1, actual);
    }

    @Test
    @DisplayName("Check if method createDayPlan() calls dayService's method getDayById()")
    void should_MethodCreateDayPlanCallsDayServiceMethodGetDayById() {
        //given
        given(dayService.getDayById(1L)).willReturn(day1);
        //when
        dayPlanService.createDayPlan(1L, 1L, dayPlanInputDTO);
        //then
        verify(dayService, Mockito.times(1)).getDayById(1L);
    }

    @Test
    @DisplayName("Check if method createDayPlan() calls scheduleService's method getScheduleById()")
    void should_MethodCreateDayPlanCallsScheduleServiceMethodGetScheduleById() {
        //given
        given(dayService.getDayById(1L)).willReturn(day1);
        //when
        dayPlanService.createDayPlan(1L, 1L, dayPlanInputDTO);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method createDayPlan() calls repository's method save()")
    void should_MethodCreateDayPlanCallsRepositoryMethodSave() {
        //given
        given(dayService.getDayById(1L)).willReturn(day1);
        //when
        dayPlanService.createDayPlan(1L, 1L, dayPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method updateDayPlan() calls repository's method findById()")
    void should_MethodUpdateDayPlanCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        dayPlanService.updateDayPlan(1L, 1L, dayPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateDayPlan() throws ResourceNotAvailableException when called plan does not exist")
    void should_MethodUpdateDayPlanThrowsResourceNotAvailableExceptionWhenCalledPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> dayPlanService.updateDayPlan(1L, 1L, dayPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateDayPlan() update day plan fields")
    void should_MethodUpdateDayPlanUpdateDayPlanFields() {
        //given
        dayPlanInputDTO.setContent("updated content");
        dayPlanInputDTO.setStartDate(LocalTime.of(1, 1, 1));
        dayPlanInputDTO.setEndDate(LocalTime.of(2, 2, 2));
        dayPlanInputDTO.setImportance(Importance.VERYIMPORTANT);
        dayPlanInputDTO.setUrgency(Urgency.VERYURGENT);
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        dayPlanService.updateDayPlan(1L, 1, dayPlanInputDTO);
        //then
        assertAll(
                () -> assertEquals(dayPlanInputDTO.getContent(), dayPlan1.getContent()),
                () -> assertEquals(dayPlanInputDTO.getStartDate(), dayPlan1.getStartDate()),
                () -> assertEquals(dayPlanInputDTO.getEndDate(), dayPlan1.getEndDate()),
                () -> assertEquals(dayPlanInputDTO.getImportance(), dayPlan1.getImportance()),
                () -> assertEquals(dayPlanInputDTO.getUrgency(), dayPlan1.getUrgency())
        );
    }

    @Test
    @DisplayName("Check if method updateDayPlan() do not update importance when dayPlanInputDTO importance is null")
    void should_MethodUpdateDayPlanNotUpdateDayPlanImportanceField_When_DayPlanInputDTOImportanceIsNull() {
        //given
        dayPlanInputDTO.setImportance(null);
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        dayPlanService.updateDayPlan(1L, 1, dayPlanInputDTO);
        //then
        assertNotEquals(dayPlanInputDTO.getImportance(), dayPlan1.getImportance());
    }

    @Test
    @DisplayName("Check if method updateDayPlan() do not update urgency when dayPlanInputDTO urgency is null")
    void should_MethodUpdateDayPlanNotUpdateDayPlanImportanceField_When_DayPlanInputDTOUrgencyIsNull() {
        //given
        dayPlanInputDTO.setUrgency(null);
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        dayPlanService.updateDayPlan(1L, 1, dayPlanInputDTO);
        //then
        assertNotEquals(dayPlanInputDTO.getUrgency(), dayPlan1.getUrgency());
    }

    @Test
    @DisplayName("Check if method updateDayPlan() do not update fulfilled field")
    void should_MethodUpdateDayPlanNotUpdateDayPlanFulfilledField() {
        //given
        dayPlan1.setFulfilled(true);
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        dayPlanService.updateDayPlan(1L, 1, dayPlanInputDTO);
        //then
        assertNotEquals(dayPlanInputDTO.isFulfilled(), dayPlan1.isFulfilled());
    }

    @Test
    @DisplayName("Check if method updateDayPlan() call repository method save() ")
    void should_MethodUpdateDayPlanCallRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        given(repository.save(dayPlan1)).willReturn(dayPlan1);
        //when
        dayPlanService.updateDayPlan(1L, 1, dayPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any(DayPlan.class));
    }

    @Test
    @DisplayName("Check if method deleteDayPlan() call repository method findById() ")
    void should_MethodDeleteDayPlanCallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        dayPlanService.deleteDayPlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method deleteDayPlan() throws ResourceNotAvailableException when called plan does not exists")
    void should_MethodDeleteDayPlanThrowResourceNotAvailableException_When_CalledPlanDoesNotExists() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> dayPlanService.deleteDayPlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method deleteDayPlan() throws AccessDeniedException given User has no access to the plan")
    void should_MethodDeleteDayPlanThrowAccessDeniedException_When_UserHasNoAccessToThePLan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(dayPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> dayPlanService.deleteDayPlan(1L, 4L));
    }

    @Test
    @DisplayName("Check if method deleteDayPlan() returns proper message when the plan has been deleted")
    void should_ReturnProperMessage_When_PlanHasBeenDeleted() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        String message = dayPlanService.deleteDayPlan(1L, 1L);
        //then
        assertEquals("Plan deleted successfully. ", message);
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method findById() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        dayPlanService.updateFulfilledStatus(1L, 1L);
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
                () -> dayPlanService.updateFulfilledStatus(1L, 1L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() throws AccessDeniedException given User has no access to the plan with given id")
    void should_MethodUpdateFulfilledStatusThrowAccessDeniedException_When_UserHasNoAccessToThePLanWithGivenId() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(dayPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> dayPlanService.updateFulfilledStatus(1L, 4L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() updates fulfilled field")
    void should_MethodUpdateFulfilledStatusUpdateDayPlanFulFilledField() {
        //given
        dayPlan1.setFulfilled(false);
        boolean expected1 = dayPlan1.isFulfilled();
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        //when
        dayPlanService.updateFulfilledStatus(1L, 1);
        //then
        assertAll(
                () -> assertFalse(expected1),
                () -> assertTrue(dayPlan1.isFulfilled())
        );
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method save() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dayPlan1));
        given(repository.save(dayPlan1)).willReturn(dayPlan1);
        //when
        dayPlanService.updateFulfilledStatus(1L, 1);
        //then
        verify(repository, Mockito.times(1)).save(any(DayPlan.class));
    }
}