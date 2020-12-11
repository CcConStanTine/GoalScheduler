package com.pk.ms.services.day;

import com.pk.ms.constants.DayName;
import com.pk.ms.constants.MonthName;
import com.pk.ms.dao.day.DayRepository;
import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.dto.month.MonthBasicInfoDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.month.Month;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.day.DayMapService;
import com.pk.ms.services.month.MonthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class DayServiceTest {

    @Mock
    DayRepository repository;

    @Mock
    DayMapService dayMapService;

    @Mock
    MonthService monthService;

    Month month1;
    Month month2;
    MonthBasicInfoDTO monthBasicInfoDTO;

    Day day1;
    Day day2;
    DayBasicInfoDTO dayBasicInfoDTO;

    DayService dayService;

    @BeforeEach
    void setUp() {
        month1 = new Month(MonthName.JANUARY, null);
        month1.setMonthId(1L);
        monthBasicInfoDTO = new MonthBasicInfoDTO(month1.getMonthId(),
                month1.getMonthName(), month1.getDaysAmount());

        day1 = new Day(LocalDate.of(2020, 1, 1), DayName.WEDNESDAY, month1);
        day2 = new Day(LocalDate.of(2020, 1, 2), DayName.THURSDAY, month1);
        day1.setDayId(1L);
        day1.setDayId(25L);

        dayBasicInfoDTO = new DayBasicInfoDTO(day1.getDayId(),
                day1.getDayDate(), day1.getDayName());

        dayService = new DayService(repository, monthService, dayMapService);
    }

    @Test
    @DisplayName("Check if method getDayById() calls repository's method findById()")
    void should_MethodGetDayByIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(day1));
        //when
        dayService.getDayById(1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getDayById() throws ResourceNotAvailableException when day with given id does not exist")
    void should_MethodGetDayByIdThrowsResourceNotAvailableException_When_GivenDayDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> dayService.getDayById(1L));
    }

    @Test
    @DisplayName("Check if method getDayDTOsByMonthId() calls repository's method findAllByMonthId()")
    void should_MethodGetDayDTOsByMonthIdCallsRepositoryMethodFindAllByMonthId() {
        //given
        given(repository.findAllByMonthId(1L)).willReturn(Arrays.asList(day1, day2));
        //when
        dayService.getDayDTOsByMonthId(1L);
        //then
        verify(repository,Mockito.times(1)).findAllByMonthId(1L);
    }

    @Test
    @DisplayName("Check if method getDayDTOsByMonthId() calls dayMapService method mapToDTO()")
    void should_MethodGetDayDTOsByMonthIdCallsDayMapServiceMethodMapToDTO() {
        //given
        given(repository.findAllByMonthId(1L)).willReturn(Arrays.asList(day1, day2));
        //when
        dayService.getDayDTOsByMonthId(1L);
        //then
        verify(dayMapService, Mockito.times(2)).mapToDTO(any()) ;
    }

    @Test
    @DisplayName("Check if method getDayDTOsByMonthId() returns same sized list of mapped days given from repository")
    void should_MethodGetDayDTOsByMonthIdReturnsListOfDaysGivenFromRepository() {
        //given
        given(repository.findAllByMonthId(1L)).willReturn(Arrays.asList(day1, day2));
        //when
        List<DayBasicInfoDTO> list = dayService.getDayDTOsByMonthId(1L);
        //then
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Check if method getDayDTOsByMonthId() returns empty list when there is no days")
    void should_MethodGetDayDTOsByMonthIdReturnsEmptyList_When_ThereIsNoDays() {
        //given
        given(repository.findAllByMonthId(1L)).willReturn(Collections.emptyList());
        //when
        List<DayBasicInfoDTO> list = dayService.getDayDTOsByMonthId(1L);
        //then
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Check if method getDayDTOsByLocalDate() calls monthService's method getMonthDTOByLocalDate()")
    void should_MethodGetDayDTOsByLocalDateCallsMonthServiceMethodGetMonthByLocalDate() {
        //given
        given(monthService.getMonthDTOByLocalDate(LocalDate.of(2020,1,1))).willReturn(monthBasicInfoDTO);
        //when
        dayService.getDayDTOsByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        verify(monthService, Mockito.times(1)).getMonthDTOByLocalDate(LocalDate.of(2020,1,1));
    }

    @Test
    @DisplayName("Check if method getDayDTOsByLocalDate() calls repository's method findAllByMonthId()")
    void should_MethodGetDayDTOsByLocalDateCallsRepositoryMethodFindAllByMonthId() {
        //given
        given(monthService.getMonthDTOByLocalDate(LocalDate.of(2020,1,1))).willReturn(monthBasicInfoDTO);
        //when
        dayService.getDayDTOsByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        verify(repository, Mockito.times(1)).findAllByMonthId(1);
    }

    @Test
    @DisplayName("Check if method getDayDTOsByLocalDate() calls dayMapService method mapToDTO()")
    void should_MethodGetDayDTOsByLocalDateCallsDayMapServiceMethodMapToDTO() {
        //given
        given(monthService.getMonthDTOByLocalDate(LocalDate.of(2020,1,1))).willReturn(monthBasicInfoDTO);
        given(repository.findAllByMonthId(1L)).willReturn(Arrays.asList(day1, day2));
        //when
        dayService.getDayDTOsByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        verify(dayMapService, Mockito.times(2)).mapToDTO(any()) ;
    }

    @Test
    @DisplayName("Check if method getDayDTOsByLocalDate() returns same sized list of mapped days given from repository")
    void should_MethodGetDayDTOsByLocalDateReturnsListOfDaysGivenFromRepository() {
        //given
        given(monthService.getMonthDTOByLocalDate(LocalDate.of(2020,1,1))).willReturn(monthBasicInfoDTO);
        given(repository.findAllByMonthId(1L)).willReturn(Arrays.asList(day1, day2));
        //when
        List<DayBasicInfoDTO> list = dayService.getDayDTOsByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Check if method getDayDTOsByLocalDate() returns empty list when there is no days")
    void should_MethodGetDayDTOsByLocalDateReturnsEmptyList_When_ThereIsNoDays() {
        //given
        given(monthService.getMonthDTOByLocalDate(LocalDate.of(2020,1,1))).willReturn(monthBasicInfoDTO);
        given(repository.findAllByMonthId(1L)).willReturn(Collections.emptyList());
        //when
        List<DayBasicInfoDTO> list = dayService.getDayDTOsByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Check if method getDayDTOByDayId() calls repository's method findById()")
    void should_MethodGetDayDTOByIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(day2));
        //when
        dayService.getDayDTOByDayId(1);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getDayDTOByDayId() throws ResourceNotAvailableException given when day does not exist")
    void should_MethodGetDayDTOByIdThrowsResourceNotAvailableException_When_GivenDayDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> dayService.getDayDTOByDayId(1));
    }

    @Test
    @DisplayName("Check if method getDayDTOByDayId() calls dayMapService's method mapToDTO()")
    void should_MethodGetDayDTOByIdCallsDayMapServiceMethodMapToDTO() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(day1));
        //when
        dayService.getDayDTOByDayId(1);
        //then
        verify(dayMapService, Mockito.times(1)).mapToDTO(day1);
    }

    @Test
    @DisplayName("Check if method getDayDTOByLocalDate() calls repository's method findByDate()")
    void should_MethodGetDayDTOByLocalDateCallsRepositoryMethodFindByMonthIdAndDayName() {
        //given
        given(repository.findByDate(LocalDate.of(2020,1,1))).willReturn(Optional.of(day1));
        //when
        dayService.getDayDTOByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        verify(repository, Mockito.times(1)).findByDate(LocalDate.of(2020,1,1));
    }

    @Test
    @DisplayName("Check if method getDayDTOByLocalDate() throws ResourceNotAvailableException given when day does not exist")
    void should_MethodGetDayDTOByLocalDateThrowsResourceNotAvailableException_When_GivenDayDoesNotExist() {
        //given
        given(repository.findByDate(LocalDate.of(2020,1,1))).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> dayService.getDayDTOByLocalDate(LocalDate.of(2020, 3 ,1)));
    }

    @Test
    @DisplayName("Check if method getDayDTOByLocalDate() calls dayMapService's method mapToDTO()")
    void should_MethodGetDayDTOByLocalDateCallsDayMapServiceMethodMapToDTO() {
        given(repository.findByDate(LocalDate.of(2020,1,1))).willReturn(Optional.of(day1));
        //when
        dayService.getDayDTOByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        verify(dayMapService, Mockito.times(1)).mapToDTO(day1);
    }
}