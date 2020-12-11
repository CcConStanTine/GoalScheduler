package com.pk.ms.services.day;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.dao.day.DailyRoutinePlanRepository;
import com.pk.ms.dto.day.DailyRoutinePlanInputDTO;
import com.pk.ms.entities.day.DailyRoutinePlan;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
class DailyRoutinePlanServiceTest {

    @Mock
    DailyRoutinePlanRepository repository;

    @Mock
    UserService userService;

    MyScheduleUser user1;
    MyScheduleUser user2;

    DailyRoutinePlan dailyRoutinePlan1;
    DailyRoutinePlan dailyRoutinePlan2;
    DailyRoutinePlan dailyRoutinePlan3;

    DailyRoutinePlanInputDTO dailyRoutinePlanInputDTO;

    DailyRoutinePlanService dailyRoutinePlanService;

    @BeforeEach
    void setUp() {
        user1 = new MyScheduleUser(); user1.setUserId(1L);
        user2 = new MyScheduleUser(); user2.setUserId(2L);

        dailyRoutinePlan1 = new DailyRoutinePlan("Do some stuff 1",
                LocalTime.of(9, 0, 0),
                LocalTime.of(15, 0, 0),
                user1);
        dailyRoutinePlan2 = new DailyRoutinePlan("Do some stuff 2",
                LocalTime.of(10, 0, 0),
                LocalTime.of(11, 30, 0),
                user1);
        dailyRoutinePlan3 = new DailyRoutinePlan("Do some stuff 3",
                LocalTime.of(9, 0, 0),
                LocalTime.of(15, 0, 0),
                user2);

        dailyRoutinePlanInputDTO = new DailyRoutinePlanInputDTO(dailyRoutinePlan1.getContent(),
                dailyRoutinePlan1.getStartDate(), dailyRoutinePlan1.getEndDate());

        dailyRoutinePlanService = new DailyRoutinePlanService(repository, userService);
    }

    @Test
    @DisplayName("Check if method getDailyRoutinePlans() calls repository's method findAllByMyScheduleUserId()")
    void should_MethodGetDailyRoutinePlansCallsRepositoryMethodFindAllByMyScheduleUserId() {
        //given
        given(repository.findAllByMyScheduleUserId(1L))
                .willReturn(Arrays.asList(dailyRoutinePlan1, dailyRoutinePlan2));
        //when
        dailyRoutinePlanService.getDailyRoutinePlans(1L);
        //then
        verify(repository, Mockito.times(1)).findAllByMyScheduleUserId(1L);
    }

    @Test
    @DisplayName("Check if method getDailyRoutinePlans() return list of plans given from repository")
    void should_ReturnListOfPlansGivenFromRepository() {
        //given
        List<DailyRoutinePlan> expected = Arrays.asList(dailyRoutinePlan1, dailyRoutinePlan2);
        given(repository.findAllByMyScheduleUserId(1L))
                .willReturn(Arrays.asList(dailyRoutinePlan1, dailyRoutinePlan2));
        //when
        List<DailyRoutinePlan> actual = dailyRoutinePlanService.getDailyRoutinePlans(1L);
        //then
        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertSame(expected.get(0), actual.get(0)),
                () -> assertSame(expected.get(1), actual.get(1))
        );
    }

    @Test
    @DisplayName("Check if method getDailyRoutinePlans() return empty list of plans given from repository")
    void should_ReturnEmptyListOfPlansGivenFromRepository() {
        //given
        List<DailyRoutinePlan> expected = Collections.emptyList();
        given(repository.findAllByMyScheduleUserId(1L))
                .willReturn(Collections.emptyList());
        //when
        List<DailyRoutinePlan> actual = dailyRoutinePlanService.getDailyRoutinePlans(1L);
        //then
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Check if method getDailyRoutinePlan() calls repository's method findById()")
    void should_CallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dailyRoutinePlan1));
        //when
        dailyRoutinePlanService.getDailyRoutinePlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getDailyRoutinePlan() throws ResourceNotAvailableException when given plan does not exists")
    void should_ThrowResourceNotAvailableException_When_GivenPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> dailyRoutinePlanService.getDailyRoutinePlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method getDailyRoutinePlan() throws AccessDeniedException when given user has no access to the plan")
    void should_ThrowAccessDeniedException_When_GivenUserHasNoAccessToGivenPlan() {
        //given
        given(repository.findById(3L)).willReturn(Optional.of(dailyRoutinePlan3));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> dailyRoutinePlanService.getDailyRoutinePlan(1L, 3L));
    }

    @Test
    @DisplayName("Check if method getDailyRoutinePlan() return plan given from repository")
    void should_ReturnPlanGivenFromRepository() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dailyRoutinePlan1));
        //when
        DailyRoutinePlan actual = dailyRoutinePlanService.getDailyRoutinePlan(1L, 1L);
        //then
        assertSame(dailyRoutinePlan1, actual);
    }

    @Test
    @DisplayName("Check if method createDailyRoutinePlan() calls scheduleService's method getUserById()")
    void should_MethodCreateDailyRoutinePlanCallsScheduleServiceMethodGetScheduleById() {
        //given
        given(userService.getUserById(1L)).willReturn(user1);
        //when
        dailyRoutinePlanService.createDailyRoutinePlan(1L, dailyRoutinePlanInputDTO);
        //then
        verify(userService, Mockito.times(1)).getUserById(1L);
    }

    @Test
    @DisplayName("Check if method createDailyRoutinePlan() calls repository's method save()")
    void should_MethodCreateDailyRoutinePlanCallsRepositoryMethodSave() {
        //given
        given(userService.getUserById(1L)).willReturn(user1);
        //when
        dailyRoutinePlanService.createDailyRoutinePlan(1L, dailyRoutinePlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any(DailyRoutinePlan.class));
    }

    @Test
    @DisplayName("Check if method updateDailyRoutinePlan() calls repository's method findById()")
    void should_MethodUpdateDailyRoutinePlanCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dailyRoutinePlan1));
        //when
        dailyRoutinePlanService.updateDailyRoutinePlan(1L, 1L, dailyRoutinePlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateDailyRoutinePlan() throws ResourceNotAvailableException when called plan does not exist")
    void should_MethodUpdateDailyRoutinePlanThrowsResourceNotAvailableExceptionWhenCalledPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> dailyRoutinePlanService.updateDailyRoutinePlan(1L, 1L, dailyRoutinePlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateDailyRoutinePlan() update daily plan fields")
    void should_MethodUpdateDailyRoutinePlanUpdateDailyRoutinePlanFields() {
        //given
        dailyRoutinePlanInputDTO.setContent("updated content");
        dailyRoutinePlanInputDTO.setStartDate(LocalTime.of(1, 1, 1));
        dailyRoutinePlanInputDTO.setEndDate(LocalTime.of(2, 2, 2));
        dailyRoutinePlanInputDTO.setImportance(Importance.VERYIMPORTANT);
        dailyRoutinePlanInputDTO.setUrgency(Urgency.VERYURGENT);
        given(repository.findById(1L)).willReturn(Optional.of(dailyRoutinePlan1));
        //when
        dailyRoutinePlanService.updateDailyRoutinePlan(1L, 1, dailyRoutinePlanInputDTO);
        //then
        assertAll(
                () -> assertEquals(dailyRoutinePlanInputDTO.getContent(), dailyRoutinePlan1.getContent()),
                () -> assertEquals(dailyRoutinePlanInputDTO.getStartDate(), dailyRoutinePlan1.getStartDate()),
                () -> assertEquals(dailyRoutinePlanInputDTO.getEndDate(), dailyRoutinePlan1.getEndDate()),
                () -> assertEquals(dailyRoutinePlanInputDTO.getImportance(), dailyRoutinePlan1.getImportance()),
                () -> assertEquals(dailyRoutinePlanInputDTO.getUrgency(), dailyRoutinePlan1.getUrgency())
        );
    }

    @Test
    @DisplayName("Check if method updateDailyRoutinePlan() do not update importance when dailyRoutinePlanInputDTO importance is null")
    void should_MethodUpdateDailyRoutinePlanNotUpdateDailyRoutinePlanImportanceField_When_DailyRoutinePlanInputDTOImportanceIsNull() {
        //given
        dailyRoutinePlanInputDTO.setImportance(null);
        given(repository.findById(1L)).willReturn(Optional.of(dailyRoutinePlan1));
        //when
        dailyRoutinePlanService.updateDailyRoutinePlan(1L, 1, dailyRoutinePlanInputDTO);
        //then
        assertNotEquals(dailyRoutinePlanInputDTO.getImportance(), dailyRoutinePlan1.getImportance());
    }

    @Test
    @DisplayName("Check if method updateDailyRoutinePlan() do not update urgency when dailyRoutinePlanInputDTO urgency is null")
    void should_MethodUpdateDailyRoutinePlanNotUpdateDailyRoutinePlanImportanceField_When_DailyRoutinePlanInputDTOUrgencyIsNull() {
        //given
        dailyRoutinePlanInputDTO.setUrgency(null);
        given(repository.findById(1L)).willReturn(Optional.of(dailyRoutinePlan1));
        //when
        dailyRoutinePlanService.updateDailyRoutinePlan(1L, 1, dailyRoutinePlanInputDTO);
        //then
        assertNotEquals(dailyRoutinePlanInputDTO.getUrgency(), dailyRoutinePlan1.getUrgency());
    }

    @Test
    @DisplayName("Check if method updateDailyRoutinePlan() call repository method save() ")
    void should_MethodUpdateDailyRoutinePlanCallRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dailyRoutinePlan1));
        given(repository.save(dailyRoutinePlan1)).willReturn(dailyRoutinePlan1);
        //when
        dailyRoutinePlanService.updateDailyRoutinePlan(1L, 1, dailyRoutinePlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any(DailyRoutinePlan.class));
    }

    @Test
    @DisplayName("Check if method deleteDailyRoutinePlan() call repository method findById() ")
    void should_MethodDeleteDailyRoutinePlanCallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dailyRoutinePlan1));
        //when
        dailyRoutinePlanService.deleteDailyRoutinePlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method deleteDailyRoutinePlan() throws ResourceNotAvailableException when called plan does not exists")
    void should_MethodDeleteDailyRoutinePlanThrowResourceNotAvailableException_When_CalledPlanDoesNotExists() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> dailyRoutinePlanService.deleteDailyRoutinePlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method deleteDailyRoutinePlan() throws AccessDeniedException given User has no access to the plan")
    void should_MethodDeleteDailyRoutinePlanThrowAccessDeniedException_When_UserHasNoAccessToThePLan() {
        //given
        given(repository.findById(3L)).willReturn(Optional.of(dailyRoutinePlan3));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> dailyRoutinePlanService.deleteDailyRoutinePlan(1L, 3L));
    }

    @Test
    @DisplayName("Check if method deleteDailyRoutinePlan() returns proper message when the plan has been deleted")
    void should_ReturnProperMessage_When_PlanHasBeenDeleted() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(dailyRoutinePlan1));
        //when
        String message = dailyRoutinePlanService.deleteDailyRoutinePlan(1L, 1L);
        //then
        assertEquals("Plan deleted successfully. ", message);
    }

}