package com.pk.ms.services.year;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.dao.year.YearPlanRepository;
import com.pk.ms.dto.year.YearPlanInputDTO;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.year.Year;
import com.pk.ms.entities.year.YearPlan;
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
class YearPlanServiceTest {

    @Mock
    YearPlanRepository repository;

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

    YearPlanInputDTO yearPlanInputDTO;

    YearPlanService yearPlanService;

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
        yearPlan1.setYearPlanId(1L);
        yearPlan2.setYearPlanId(2L);
        yearPlan3.setYearPlanId(3L);
        yearPlan4.setYearPlanId(4L);

        yearPlanInputDTO = new YearPlanInputDTO("Do some stuff 1",
                LocalDate.of(2020, 1, 3),
                LocalDate.of(2020, 5, 12),
                Importance.REGULAR, Urgency.REGULAR, false);

        yearPlanService = new YearPlanService(repository, yearService, scheduleService);
    }

    @Test
    @DisplayName("Check if method getYearPlansByScheduleIdAndYearId() calls repository's method findYearPlansByScheduleIdAndYearId")
    void should_MethodGetYearPlansByScheduleIdAndYearIdCallsRepositoryMethodFindYearPlansByScheduleIdAndYearId() {
        //given
        given(repository.findYearPlansByScheduleIdAndYearId(1L, 1L))
                .willReturn(Arrays.asList(yearPlan1, yearPlan2));
        //when
        yearPlanService.getYearPlansByScheduleIdAndYearId(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findYearPlansByScheduleIdAndYearId(1L, 1L);
    }

    @Test
    @DisplayName("Check if method getYearPlansByScheduleIdAndYearId() return list of plans given from repository")
    void should_MethodGetYearPlansByScheduleIdAndYearIdReturnsListOfPlansGivenFromRepository() {
        //given
        List<YearPlan> expected = Arrays.asList(yearPlan1, yearPlan2);
        given(repository.findYearPlansByScheduleIdAndYearId(1L, 1L))
                .willReturn(expected);
        //when
        List<YearPlan> actual = yearPlanService.getYearPlansByScheduleIdAndYearId(1L, 1L);
        //then
        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertSame(expected.get(0), actual.get(0)),
                () -> assertSame(expected.get(1), actual.get(1))
        );
    }

    @Test
    @DisplayName("Check if method getYearPlan() calls repository's method findById()")
    void should_MethodGetYearPlanCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        yearPlanService.getYearPlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getYearPlan() throws ResourceNotAvailableException when given plan does not exists")
    void should_MethodGetYearPlanThrowsResourceNotAvailableException_When_GivenPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> yearPlanService.getYearPlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method getYearPlan() throws AccessDeniedException when given user has no access to the plan")
    void should_MethodGetYearPlanThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenPlan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(yearPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> yearPlanService.getYearPlan(1L, 4L));
    }

    @Test
    @DisplayName("Check if method getYearPlan() return plan given from repository")
    void should_MethodGetYearPlanReturnPlanGivenFromRepository() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        YearPlan actual = yearPlanService.getYearPlan(1L, 1L);
        //then
        assertSame(yearPlan1, actual);
    }

    @Test
    @DisplayName("Check if method createYearPlan() calls yearService's method getYearById()")
    void should_MethodCreateYearPlanCallsYearServiceMethodGetYearById() {
        //given
        given(yearService.getYearById(1L)).willReturn(year2020);
        //when
        yearPlanService.createYearPlan(1L, 1L, yearPlanInputDTO);
        //then
        verify(yearService, Mockito.times(1)).getYearById(1L);
    }

    @Test
    @DisplayName("Check if method createYearPlan() throws IllegalArgumentException when startDate input is invalid")
    void should_MethodCreateYearPlanThrowsIllegalArgumentException_When_StarDateInputIsInValid() {
        //given
        yearPlanInputDTO.setStartDate(LocalDate.of(2022, 1, 1));
        given(yearService.getYearById(1L)).willReturn(year2020);
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> yearPlanService.createYearPlan(1L, 1L, yearPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method createYearPlan() throws IllegalArgumentException when endDate input is invalid")
    void should_MethodCreateYearPlanThrowsIllegalArgumentException_When_EndDateInputIsInValid() {
        //given
        yearPlanInputDTO.setEndDate(LocalDate.of(2022, 1, 1));
        given(yearService.getYearById(1L)).willReturn(year2020);
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> yearPlanService.createYearPlan(1L, 1L, yearPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method createYearPlan() calls scheduleService's method getScheduleById()")
    void should_MethodCreateYearPlanCallsScheduleServiceMethodGetScheduleById() {
        //given
        given(yearService.getYearById(1L)).willReturn(year2020);
        //when
        yearPlanService.createYearPlan(1L, 1L, yearPlanInputDTO);
        //then
        verify(scheduleService, Mockito.times(1)).getScheduleById(1L);
    }

    @Test
    @DisplayName("Check if method createYearPlan() calls repository's method save()")
    void should_MethodCreateYearPlanCallsRepositoryMethodSave() {
        //given
        given(yearService.getYearById(1L)).willReturn(year2020);
        //when
        yearPlanService.createYearPlan(1L, 1L, yearPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Check if method updateYearPlan() calls repository's method findById()")
    void should_MethodUpdateYearPlanCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        yearPlanService.updateYearPlan(1L, 1L, yearPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method updateYearPlan() throws ResourceNotAvailableException when called plan does not exist")
    void should_MethodUpdateYearPlanThrowsResourceNotAvailableExceptionWhenCalledPlanDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> yearPlanService.updateYearPlan(1L, 1L, yearPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateYearPlan() throws AccessDeniedException when given user has no access to the plan")
    void should_MethodUpdateYearPlanThrowsAccessDeniedException_When_GivenUserHasNoAccessToGivenPlan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(yearPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> yearPlanService.updateYearPlan(1L, 4L, yearPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateYearPlan() throws IllegalArgumentException when startDate input is invalid")
    void should_MethodUpdateYearPlanThrowsIllegalArgumentException_When_StarDateInputIsInValid() {
        //given
        yearPlanInputDTO.setStartDate(LocalDate.of(2022, 1, 1));
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> yearPlanService.updateYearPlan(1L, 1L, yearPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateYearPlan() throws IllegalArgumentException when endDate input is invalid")
    void should_MethodUpdateYearPlanThrowsIllegalArgumentException_When_EndDateInputIsInValid() {
        //given
        yearPlanInputDTO.setEndDate(LocalDate.of(2022, 1, 1));
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when + then
        assertThrows(IllegalArgumentException.class,
                () -> yearPlanService.updateYearPlan(1L, 1L, yearPlanInputDTO));
    }

    @Test
    @DisplayName("Check if method updateYearPlan() update year plan fields")
    void should_MethodUpdateYearPlanUpdateYearPlanFields() {
        //given
        yearPlanInputDTO.setContent("updated content");
        yearPlanInputDTO.setStartDate(LocalDate.of(2020, 3, 1));
        yearPlanInputDTO.setStartDate(LocalDate.of(2020, 5, 15));
        yearPlanInputDTO.setImportance(Importance.VERYIMPORTANT);
        yearPlanInputDTO.setUrgency(Urgency.VERYURGENT);
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        yearPlanService.updateYearPlan(1L, 1, yearPlanInputDTO);
        //then
        assertAll(
                () -> assertEquals(yearPlanInputDTO.getContent(), yearPlan1.getContent()),
                () -> assertEquals(yearPlanInputDTO.getStartDate(), yearPlan1.getStartDate()),
                () -> assertEquals(yearPlanInputDTO.getEndDate(), yearPlan1.getEndDate()),
                () -> assertEquals(yearPlanInputDTO.getImportance(), yearPlan1.getImportance()),
                () -> assertEquals(yearPlanInputDTO.getUrgency(), yearPlan1.getUrgency())
        );
    }

    @Test
    @DisplayName("Check if method updateYearPlan() do not update importance when yearPlanInputDTO importance is null")
    void should_MethodUpdateYearPlanNotUpdateYearPlanImportanceField_When_YearPlanInputDTOImportanceIsNull() {
        //given
        yearPlanInputDTO.setImportance(null);
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        yearPlanService.updateYearPlan(1L, 1, yearPlanInputDTO);
        //then
        assertNotEquals(yearPlanInputDTO.getImportance(), yearPlan1.getImportance());
    }

    @Test
    @DisplayName("Check if method updateYearPlan() do not update urgency when yearPlanInputDTO urgency is null")
    void should_MethodUpdateYearPlanNotUpdateYearPlanImportanceField_When_YearPlanInputDTOUrgencyIsNull() {
        //given
        yearPlanInputDTO.setUrgency(null);
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        yearPlanService.updateYearPlan(1L, 1, yearPlanInputDTO);
        //then
        assertNotEquals(yearPlanInputDTO.getUrgency(), yearPlan1.getUrgency());
    }

    @Test
    @DisplayName("Check if method updateYearPlan() do not update fulfilled field")
    void should_MethodUpdateYearPlanNotUpdateYearPlanFulfilledField() {
        //given
        yearPlan1.setFulfilled(true);
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        yearPlanService.updateYearPlan(1L, 1, yearPlanInputDTO);
        //then
        assertNotEquals(yearPlanInputDTO.isFulfilled(), yearPlan1.isFulfilled());
    }

    @Test
    @DisplayName("Check if method updateYearPlan() call repository method save() ")
    void should_MethodUpdateYearPlanCallRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        given(repository.save(yearPlan1)).willReturn(yearPlan1);
        //when
        yearPlanService.updateYearPlan(1L, 1, yearPlanInputDTO);
        //then
        verify(repository, Mockito.times(1)).save(any(YearPlan.class));
    }

    @Test
    @DisplayName("Check if method deleteYearPlan() call repository method findById() ")
    void should_MethodDeleteYearPlanCallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        yearPlanService.deleteYearPlan(1L, 1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method deleteYearPlan() throws ResourceNotAvailableException when called plan does not exists")
    void should_MethodDeleteYearPlanThrowResourceNotAvailableException_When_CalledPlanDoesNotExists() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> yearPlanService.deleteYearPlan(1L, 1L));
    }

    @Test
    @DisplayName("Check if method deleteYearPlan() throws AccessDeniedException given User has no access to the plan")
    void should_MethodDeleteYearPlanThrowAccessDeniedException_When_UserHasNoAccessToThePLan() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(yearPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> yearPlanService.deleteYearPlan(1L, 4L));
    }

    @Test
    @DisplayName("Check if method deleteYearPlan() returns proper message when the plan has been deleted")
    void should_ReturnProperMessage_When_PlanHasBeenDeleted() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        String message = yearPlanService.deleteYearPlan(1L, 1L);
        //then
        assertEquals("Plan deleted successfully. ", message);
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method findById() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        yearPlanService.updateFulfilledStatus(1L, 1L);
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
                () -> yearPlanService.updateFulfilledStatus(1L, 1L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() throws AccessDeniedException given User has no access to the plan with given id")
    void should_MethodUpdateFulfilledStatusThrowAccessDeniedException_When_UserHasNoAccessToThePLanWithGivenId() {
        //given
        given(repository.findById(4L)).willReturn(Optional.of(yearPlan4));
        //when + then
        assertThrows(AccessDeniedException.class,
                () -> yearPlanService.updateFulfilledStatus(1L, 4L));
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() updates fulfilled field")
    void should_MethodUpdateFulfilledStatusUpdateYearPlanFulFilledField() {
        //given
        yearPlan1.setFulfilled(false);
        boolean expected1 = yearPlan1.isFulfilled();
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        //when
        yearPlanService.updateFulfilledStatus(1L, 1);
        //then
        assertAll(
                () -> assertFalse(expected1),
                () -> assertTrue(yearPlan1.isFulfilled())
        );
    }

    @Test
    @DisplayName("Check if method updateFulfilledStatus() call repository method save() ")
    void should_MethodUpdateFulfilledStatusCallRepositoryMethodSave() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(yearPlan1));
        given(repository.save(yearPlan1)).willReturn(yearPlan1);
        //when
        yearPlanService.updateFulfilledStatus(1L, 1);
        //then
        verify(repository, Mockito.times(1)).save(any(YearPlan.class));
    }

}