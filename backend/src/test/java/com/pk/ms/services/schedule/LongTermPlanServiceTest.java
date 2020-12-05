package com.pk.ms.services.schedule;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.dao.schedule.LongTermPlanRepository;
import com.pk.ms.dto.schedule.LongTermPlanInputDTO;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.exceptions.AccessDeniedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(SpringExtension.class)
class LongTermPlanServiceTest {
    /*
    @Mock
    LongTermPlanRepository longTermPlanRepo;

    @Mock
    ScheduleService scheduleService;

    static MyScheduleUser user1;
    static MyScheduleUser user2;
    static Schedule schedule1;
    static Schedule schedule2;

    LongTermPlan longTermPlan1;
    LongTermPlan longTermPlan2;
    LongTermPlan longTermPlan3;

    LongTermPlanService longTermPlanService;

    @BeforeAll
    static void setUpBeforeAll() {
        user1 = new MyScheduleUser();
        user2 = new MyScheduleUser();
        schedule1 = new Schedule(user1);
        schedule2 = new Schedule(user2);
        schedule1.setScheduleId(1L);
        schedule2.setScheduleId(2L);
    }

    @BeforeEach
    void setUpBeforeEach() {
        longTermPlan1 = new LongTermPlan("Do some stuff 1", LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 12, 31), Importance.REGULAR, Urgency.REGULAR, schedule1);
        longTermPlan2 = new LongTermPlan("Do some stuff 2", LocalDate.of(2020, 1, 2),
                LocalDate.of(2020, 10, 25), Importance.REGULAR, Urgency.REGULAR, schedule1);
        longTermPlan3 = new LongTermPlan("Do some stuff 3 ", LocalDate.of(2020, 1, 3),
                LocalDate.of(2020, 5, 12), Importance.REGULAR, Urgency.REGULAR, schedule2);

        longTermPlan1.setLongTermPlanId(1L);
        longTermPlan2.setLongTermPlanId(2L);
        longTermPlan3.setLongTermPlanId(3L);

        longTermPlanService = new LongTermPlanService(longTermPlanRepo, scheduleService);
    }

    @Test
    @DisplayName("Check if method getLongTermPlansByScheduleId() returns empty List when there are no plans")
    void should_ReturnEmptyList_When_ThereAreNoPlans() {
        //given
        List<LongTermPlan> emptyList = new ArrayList<>();
        given(longTermPlanRepo.findAllByScheduleId(1L)).willReturn(emptyList);
        //when
        List<LongTermPlan> longTermPlanListActual = longTermPlanService.getLongTermPlansByScheduleId(1L);
        //then
        assertSame(emptyList, longTermPlanListActual);
    }

    @Test
    @DisplayName("Check if method getLongTermPlansByScheduleId() returns plans when there are plans")
    void should_ReturnListOfLongTermPlans_When_ThereArePlans() {
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
    @DisplayName("Check if method createLongTermPlan() creates new LongTermPlan")
    void should_CreateNewLongTermPlan() {
        //given
        LongTermPlanInputDTO longTermPlanInputDTO = new LongTermPlanInputDTO(longTermPlan1.getContent(),
                longTermPlan1.getStartDate(), longTermPlan1.getEndDate());
        given(longTermPlanRepo.save(any(LongTermPlan.class))).willReturn(any(LongTermPlan.class));
        //when
        LongTermPlan longTermPlanActual = longTermPlanService.createLongTermPlan(1L, longTermPlanInputDTO);
        //then
        assertNotSame(longTermPlan1, longTermPlanActual);
    }

    @Test
    @DisplayName("Check if method createLongTermPlan() assigns new created Plan to proper Schedule")
    void should_AssignNewCreatedLongTermPlanToProperSchedule() {
        //given
        LongTermPlanInputDTO longTermPlanInputDTO = new LongTermPlanInputDTO(longTermPlan1.getContent(),
                longTermPlan1.getStartDate(), longTermPlan1.getEndDate());
        given(longTermPlanRepo.save(any(LongTermPlan.class))).willReturn(any(LongTermPlan.class));
        //when
        LongTermPlan longTermPlanActual = longTermPlanService.createLongTermPlan(1L, longTermPlanInputDTO);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() throws AccessDeniedException when updating and given User has no access to the LongTermPlan with given id")
    void should_ThrowAccessDeniedException_When_UpdatingAndUserHasNoAccessToTheLongTermPLanWithGivenId1() {
        //given
        LongTermPlanInputDTO longTermPlanInputDTO = new LongTermPlanInputDTO(longTermPlan1.getContent(),
                longTermPlan1.getStartDate(), longTermPlan1.getEndDate());
        given(longTermPlanRepo.findById(3L)).willReturn(longTermPlan3);
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> longTermPlanService.updateLongTermPlan(1L, 3L, longTermPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateLongTermPlan() returns updated LongTermPlan")
    void should_ReturnUpdatedLongTermPlan() {
        ///given
        LongTermPlanInputDTO longTermPlanInputDTO = new LongTermPlanInputDTO("Updated content",
                LocalDate.of(2020, 5,5), LocalDate.of(2020, 6, 6));
        given(longTermPlanRepo.findById(1L)).willReturn(longTermPlan1);
        given(longTermPlanRepo.save(longTermPlan1)).willReturn(longTermPlan1);
        //when
        LongTermPlan longTermPlanActual =
                longTermPlanService.updateLongTermPlan(1L, 1L, longTermPlanInputDTO);
        //then
        verify(longTermPlanRepo, Mockito.times(1)).save(longTermPlan1);
        assertSame(longTermPlan1, longTermPlanActual);
    }

    @Test
    @DisplayName("Check if method deleteLongTermPlan() throws AccessDeniedException when deleting and given User has no access to the LongTermPlan with given id")
    void should_ThrowAccessDeniedException_When_DeletingAndUserHasNoAccessToTheLongTermPLanWithGivenId() {
        //given
        given(longTermPlanRepo.findById(3L)).willReturn(longTermPlan3);
        //when + then
        assertThrows(AccessDeniedException.class, () -> longTermPlanService.deleteLongTermPlan(1L, 3L));
    }

    @Test
    @DisplayName("Check if method deleteLongTermPlan() returns proper message when LongTermPlan has been deleted")
    void should_ReturnProperMessage_When_LongTermPlanHasBeenDeleted() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(longTermPlan1);
        //when
        String message = longTermPlanService.deleteLongTermPlan(1L, 1L);
        //then
        assertEquals("Plan has been deleted successfully. ", message);
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() throws AccessDeniedException when updating fulfilled status and given User has no access to the LongTermPlan with given id")
    void should_ThrowAccessDeniedException_When_UpdatingFulfilledStatusAndUserHasNoAccessToTheLongTermPLanWithGivenId1() {
        //given
        given(longTermPlanRepo.findById(3L)).willReturn(longTermPlan3);
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> longTermPlanService.updateFulfilledStatus(1L, 3L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() returns LongTermPlan with updated fulfilled status")
    void should_ReturnLongTermPlanWithUpdatedFulfilledStatus() {
        //given
        given(longTermPlanRepo.findById(1L)).willReturn(longTermPlan1);
        given(longTermPlanRepo.save(longTermPlan1)).willReturn(longTermPlan1);
        //when
        LongTermPlan longTermPlanActual = longTermPlanService.updateFulfilledStatus(1L, 1L);
        //then
        verify(longTermPlanRepo, Mockito.times(1)).save(longTermPlan1);
        assertTrue(longTermPlanActual.isFulfilled());
    }

     */
}

