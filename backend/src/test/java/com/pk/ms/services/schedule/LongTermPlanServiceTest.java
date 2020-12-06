package com.pk.ms.services.schedule;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.dao.schedule.LongTermPlanRepository;
import com.pk.ms.dto.schedule.LongTermPlanInputDTO;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class LongTermPlanServiceTest {

    @Mock
    LongTermPlanRepository longTermPlanRepo;

    @Mock
    ScheduleService scheduleService;

    MyScheduleUser user1;
    MyScheduleUser user2;
    Schedule schedule1;
    Schedule schedule2;

    LongTermPlan longTermPlan1;
    LongTermPlan longTermPlan2;
    LongTermPlan longTermPlan3;

    LongTermPlanInputDTO longTermPlanInputDTO;

    LongTermPlanService longTermPlanService;

    @BeforeEach
    void setUpBeforeEach() {
        user1 = new MyScheduleUser();
        user2 = new MyScheduleUser();
        schedule1 = new Schedule(user1);
        schedule2 = new Schedule(user2);
        schedule1.setScheduleId(1L);
        schedule2.setScheduleId(2L);
        longTermPlan1 = new LongTermPlan("Do some stuff 1", LocalDate.of(2020, 1, 1),
                LocalDate.of(2025, 12, 31), schedule1);
        longTermPlan2 = new LongTermPlan("Do some stuff 2", LocalDate.of(2020, 1, 2),
                LocalDate.of(2025, 10, 25), schedule1);
        longTermPlan3 = new LongTermPlan("Do some stuff 3 ", LocalDate.of(2020, 1, 3),
                LocalDate.of(2025, 5, 12), schedule2);
        longTermPlan1.setLongTermPlanId(1L);
        longTermPlan2.setLongTermPlanId(2L);
        longTermPlan3.setLongTermPlanId(3L);

        longTermPlanInputDTO = new LongTermPlanInputDTO(longTermPlan1.getContent(),
                longTermPlan1.getStartDate(), longTermPlan1.getEndDate(), Importance.REGULAR,
                Urgency.REGULAR, false);

        longTermPlanService = new LongTermPlanService(longTermPlanRepo, scheduleService);
    }

    @Test
    @DisplayName("Check if method getLongTermPlansByScheduleId() returns empty list when there are no plans")
    void should_MethodGetLongPlansByScheduleIdReturnsEmptyList_When_ThereAreNoPlans() {
        //given
        List<LongTermPlan> emptyList = new ArrayList<>();
        given(longTermPlanRepo.findAllByScheduleId(1L)).willReturn(emptyList);
        //when
        List<LongTermPlan> longTermPlanListActual = longTermPlanService.getLongTermPlansByScheduleId(1L);
        //then
        assertSame(emptyList, longTermPlanListActual);
    }

    @Test
    @DisplayName("Check if method getLongTermPlansByScheduleId() returns a list of long term plans when there are plans")
    void should_MethodGetLongPlansByScheduleIdReturnsListOfLongTermPlans_When_ThereArePlans() {
        //given
        List<LongTermPlan> longTermPlanListExpected = Arrays.asList(longTermPlan1, longTermPlan2);
        given(longTermPlanRepo.findAllByScheduleId(1L)).willReturn(longTermPlanListExpected);
        //when
        List<LongTermPlan> longTermPlanListActual = longTermPlanService.getLongTermPlansByScheduleId(1L);
        //then
        assertAll(
                () -> assertEquals(2, longTermPlanListActual.size()),
                () -> assertSame(longTermPlanListExpected.get(0), longTermPlanListActual.get(0)),
                () -> assertSame(longTermPlanListExpected.get(1), longTermPlanListActual.get(1))
        );
    }

    @Test
    @DisplayName("Check if method createLongTermPlan() call scheduleService method getScheduleById() ")
    void should_MethodCreateLongTermPlanCallScheduleServiceMethodGetScheduleById_When_CreateLongTermPlanMethodCalled() {
        //given
        LongTermPlanInputDTO longTermPlanInputDTO = new LongTermPlanInputDTO(longTermPlan1.getContent(),
                longTermPlan1.getStartDate(), longTermPlan1.getEndDate(), Importance.REGULAR,
                Urgency.REGULAR, false);
        given(longTermPlanRepo.save(longTermPlan1)).willReturn(longTermPlan1);
        //when
        longTermPlanService.createLongTermPlan(1L, longTermPlanInputDTO);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method createLongTermPlan() call repository method save() ")
    void should_MethodCreateLongTermPlanCallRepositoryMethodSave_When_CreateLongTermPlanMethodCalled() {
        //given
        LongTermPlanInputDTO longTermPlanInputDTO = new LongTermPlanInputDTO(longTermPlan1.getContent(),
                longTermPlan1.getStartDate(), longTermPlan1.getEndDate(), Importance.REGULAR,
                Urgency.REGULAR, false);
        given(longTermPlanRepo.save(longTermPlan1)).willReturn(longTermPlan1);
        //when
        longTermPlanService.createLongTermPlan(1L, longTermPlanInputDTO);
        //then
        verify(longTermPlanRepo, Mockito.times(1)).save(any(LongTermPlan.class));
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() call repository method findById() ")
    void should_MethodUpdateLongTermPlanCallRepositoryMethodFindById_When_UpdateLongTermPlanMethodCalled() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        //when
        longTermPlanService.updateLongTermPlan(1L, 1L, longTermPlanInputDTO);
        //then
        verify(longTermPlanRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() throws ResourceNotAvailableException when method findById() returns null ")
    void should_MethodUpdateLongTermPlanThrowResourceNotAvailableException_When_MethodFindByIdReturnsNull() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> longTermPlanService.updateLongTermPlan(1L, 1L, longTermPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() throws AccessDeniedException given User has no access to the LongTermPlan with given id")
    void should_MethodUpdateLongTermPlanThrowAccessDeniedException_When_UserHasNoAccessToTheLongTermPLanWithGivenId() {
        //given
        given(longTermPlanRepo.findById(3L)).willReturn(Optional.of(longTermPlan3));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> longTermPlanService.updateLongTermPlan(1L, 3L, longTermPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() update long term plan fields")
    void should_MethodUpdateLongTermPlanUpdateLongTermPlanFields() {
        //given
        longTermPlanInputDTO.setContent("updated content");
        longTermPlanInputDTO.setStartDate(LocalDate.of(2021, 1, 1));
        longTermPlanInputDTO.setStartDate(LocalDate.of(2026, 12, 31));
        longTermPlanInputDTO.setImportance(Importance.VERYIMPORTANT);
        longTermPlanInputDTO.setUrgency(Urgency.VERYURGENT);
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        //when
        longTermPlanService.updateLongTermPlan(1L, 1, longTermPlanInputDTO);
        //then
        assertAll(
                () -> assertEquals(longTermPlanInputDTO.getContent(), longTermPlan1.getContent()),
                () -> assertEquals(longTermPlanInputDTO.getStartDate(), longTermPlan1.getStartDate()),
                () -> assertEquals(longTermPlanInputDTO.getEndDate(), longTermPlan1.getEndDate()),
                () -> assertEquals(longTermPlanInputDTO.getImportance(), longTermPlan1.getImportance()),
                () -> assertEquals(longTermPlanInputDTO.getUrgency(), longTermPlan1.getUrgency())
        );
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() do not update importance when ltpDTO importance is null")
    void should_MethodUpdateLongTermPlanNotUpdateLongTermPlanImportanceField_When_LTPDTOImportanceIsNull() {
        //given
        longTermPlanInputDTO.setImportance(null);
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        //when
        longTermPlanService.updateLongTermPlan(1L, 1, longTermPlanInputDTO);
        //then
        assertNotEquals(longTermPlanInputDTO.getImportance(), longTermPlan1.getImportance());
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() do not update urgency when ltpDTO urgency is null")
    void should_MethodUpdateLongTermPlanNotUpdateLongTermPlanImportanceField_When_LTPDTOUrgencyIsNull() {
        //given
        longTermPlanInputDTO.setUrgency(null);
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        //when
        longTermPlanService.updateLongTermPlan(1L, 1, longTermPlanInputDTO);
        //then
        assertNotEquals(longTermPlanInputDTO.getUrgency(), longTermPlan1.getUrgency());
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() do not update fulfilled field")
    void should_MethodUpdateLongTermPlanNotUpdateLongTermPlanFulfilledField() {
        //given
        longTermPlan1.setFulfilled(true);
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        //when
        longTermPlanService.updateLongTermPlan(1L, 1, longTermPlanInputDTO);
        //then
        assertNotEquals(longTermPlanInputDTO.isFulfilled(), longTermPlan1.isFulfilled());
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() call repository method save() ")
    void should_MethodUpdateLongTermPlanCallRepositoryMethodSave_When_UpdateLongTermPlanMethodCalled() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        given(longTermPlanRepo.save(longTermPlan1)).willReturn(longTermPlan1);
        //when
        longTermPlanService.updateLongTermPlan(1L, 1, longTermPlanInputDTO);
        //then
        verify(longTermPlanRepo, Mockito.times(1)).save(any(LongTermPlan.class));
    }

    @Test
    @DisplayName("Check if method deleteLongTermPlan() call repository method findById() ")
    void should_MethodDeleteLongTermPlanCallRepositoryMethodFindById_When_DeleteLongTermPlanMethodCalled() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        //when
        longTermPlanService.deleteLongTermPlan(1L, 1L);
        //then
        verify(longTermPlanRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method deleteLongTermPlan() throws ResourceNotAvailableException when method findById() returns null ")
    void should_MethodDeleteLongTermPlanThrowResourceNotAvailableException_When_MethodFindByIdReturnsNull() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> longTermPlanService.updateLongTermPlan(1L, 1L, longTermPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method deleteLongTermPlan() throws AccessDeniedException given User has no access to the LongTermPlan with given id")
    void should_MethodDeleteLongTermPlanThrowAccessDeniedException_When_UserHasNoAccessToTheLongTermPLanWithGivenId() {
        //given
        given(longTermPlanRepo.findById(3L)).willReturn(Optional.of(longTermPlan3));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> longTermPlanService.updateLongTermPlan(1L, 3L, longTermPlanInputDTO));
    }


    @Test
    @DisplayName("Check if method deleteLongTermPlan() returns proper message when LongTermPlan has been deleted")
    void should_ReturnProperMessage_When_LongTermPlanHasBeenDeleted() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        //when
        String message = longTermPlanService.deleteLongTermPlan(1L, 1L);
        //then
        assertEquals("Plan deleted successfully. ", message);
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method findById() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodFindById_When_UpdateLongTermPlanMethodCalled() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        //when
        longTermPlanService.updateFulfilledStatus(1L, 1L);
        //then
        verify(longTermPlanRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() throws ResourceNotAvailableException when method findById() returns null ")
    void should_MethodUpdateFulfilledStatusThrowResourceNotAvailableException_When_MethodFindByIdReturnsNull() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> longTermPlanService.updateFulfilledStatus(1L, 1L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() throws AccessDeniedException given User has no access to the LongTermPlan with given id")
    void should_MethodUpdateFulfilledStatusThrowAccessDeniedException_When_UserHasNoAccessToTheLongTermPLanWithGivenId() {
        //given
        given(longTermPlanRepo.findById(3L)).willReturn(Optional.of(longTermPlan3));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> longTermPlanService.updateFulfilledStatus(1L, 3L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() updates fulfilled field")
    void should_MethodUpdateFulfilledStatusUpdateLongTermPlanFulFilledField() {
        //given
        longTermPlan1.setFulfilled(false);
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        //when
        longTermPlanService.updateFulfilledStatus(1L, 1);
        //then
        assertTrue(longTermPlan1.isFulfilled());
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method save() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodSave_When_UpdateLongTermPlanMethodCalled() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(Optional.of(longTermPlan1));
        given(longTermPlanRepo.save(longTermPlan1)).willReturn(longTermPlan1);
        //when
        longTermPlanService.updateLongTermPlan(1L, 1, longTermPlanInputDTO);
        //then
        verify(longTermPlanRepo, Mockito.times(1)).save(any(LongTermPlan.class));
    }
}

