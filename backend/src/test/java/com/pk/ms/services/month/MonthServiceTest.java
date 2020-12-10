package com.pk.ms.services.month;

import com.pk.ms.constants.MonthName;
import com.pk.ms.dao.month.MonthRepository;
import com.pk.ms.dto.month.MonthBasicInfoDTO;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.month.MonthMapService;
import com.pk.ms.services.year.YearService;
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
class MonthServiceTest {

    @Mock
    MonthRepository repository;

    @Mock
    MonthMapService monthMapService;

    @Mock
    YearService yearService;


    Year year2020;
    Year year2021;

    Month month1_2020;
    Month month2_2020;
    MonthBasicInfoDTO monthBasicInfoDTO1;
    MonthBasicInfoDTO monthBasicInfoDTO2;

    MonthService monthService;

    @BeforeEach
    void setUp() {
        year2020 = new Year(2020);
        year2021 = new Year(2021);
        year2020.setYearId(1L);
        year2021.setYearId(2L);
        month1_2020 = new Month(MonthName.JANUARY, year2020);
        month2_2020 = new Month(MonthName.FEBRUARY, year2020);
        month1_2020.setMonthId(1L);
        month2_2020.setMonthId(2L);
        monthBasicInfoDTO1 = new MonthBasicInfoDTO(month1_2020.getMonthId(),
                month1_2020.getMonthName(), month1_2020.getDaysAmount());
        monthBasicInfoDTO2 = new MonthBasicInfoDTO(month2_2020.getMonthId(),
                month2_2020.getMonthName(), month2_2020.getDaysAmount());
        monthService = new MonthService(repository, monthMapService, yearService);
    }

    @Test
    @DisplayName("Check if method getMonthById() calls repository's method findById()")
    void should_MethodGetMonthByIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(month1_2020));
        //when
        monthService.getMonthById(1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getMonthById() throws ResourceNotAvailableException when month with given id does not exist")
    void should_MethodGetMonthByIdThrowsResourceNotAvailableException_When_GivenMonthDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> monthService.getMonthById(1L));
    }

    @Test
    @DisplayName("Check if method getMonthDTOsByYearId() calls repository's method findAllByYearId()")
    void should_MethodGetMonthDTOsByYearIdCallsRepositoryMethodFindAllByYearId() {
        //given
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(month1_2020, month2_2020));
        //when
        monthService.getMonthDTOsByYearId(1L);
        //then
        verify(repository,Mockito.times(1)).findAllByYearId(1L);
    }

    @Test
    @DisplayName("Check if method getMonthDTOsByYearId() calls monthMapService method mapToDTO()")
    void should_MethodGetMonthDTOsByYearIdCallsMonthMapServiceMethodMapToDTO() {
        //given
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(month1_2020, month2_2020));
        //when
        monthService.getMonthDTOsByYearId(1L);
        //then
        verify(monthMapService, Mockito.times(2)).mapToDTO(any()) ;
    }

    @Test
    @DisplayName("Check if method getMonthDTOsByYearId() returns same sized list of mapped months given from repository")
    void should_MethodGetMonthDTOsByYearIdReturnsListOfMonthsGivenFromRepository() {
        //given
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(month1_2020, month2_2020));
        //when
        List<MonthBasicInfoDTO> list = monthService.getMonthDTOsByYearId(1L);
        //then
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Check if method getMonthDTOsByYearId() returns empty list when there is no months")
    void should_MethodGetMonthDTOsByYearIdReturnsEmptyList_When_ThereIsNoMonths() {
        //given
        given(repository.findAllByYearId(1L)).willReturn(Collections.emptyList());
        //when
        List<MonthBasicInfoDTO> list = monthService.getMonthDTOsByYearId(1L);
        //then
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Check if method getMonthDTOsByLocalDate() calls yearService's method getYearByYearNumber()")
    void should_MethodGetMonthDTOsByLocalDateCallsYearServiceMethodGetYearByYearNumber() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        //when
        monthService.getMonthDTOsByLocalDate(LocalDate.of(2020, 2 ,1));
        //then
        verify(yearService, Mockito.times(1)).getYearByYearNumber(2020);
    }

    @Test
    @DisplayName("Check if method getMonthDTOsByLocalDate() calls repository's method findAllByYearId()")
    void should_MethodGetMonthDTOsByLocalDateCallsRepositoryMethodFindAllByYearId() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        //when
        monthService.getMonthDTOsByLocalDate(LocalDate.of(2020, 2 ,1));
        //then
        verify(repository, Mockito.times(1)).findAllByYearId(1);
    }

    @Test
    @DisplayName("Check if method getMonthDTOsByLocalDate() calls monthMapService method mapToDTO()")
    void should_MethodGetMonthDTOsByLocalDateCallsMonthMapServiceMethodMapToDTO() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(month1_2020, month2_2020));
        //when
        monthService.getMonthDTOsByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        verify(monthMapService, Mockito.times(2)).mapToDTO(any()) ;
    }

    @Test
    @DisplayName("Check if method getMonthDTOsByLocalDate() returns same sized list of mapped months given from repository")
    void should_MethodGetMonthDTOsByLocalDateReturnsListOfMonthsGivenFromRepository() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(month1_2020, month2_2020));
        //when
        List<MonthBasicInfoDTO> list = monthService.getMonthDTOsByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Check if method getMonthDTOsByLocalDate() returns empty list when there is no months")
    void should_MethodGetMonthDTOsByLocalDateReturnsEmptyList_When_ThereIsNoMonths() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        given(repository.findAllByYearId(1L)).willReturn(Collections.emptyList());
        //when
        List<MonthBasicInfoDTO> list = monthService.getMonthDTOsByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Check if method getMonthDTOById() calls repository's method findById()")
    void should_MethodGetMonthDTOByIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(month2_2020));
        //when
        monthService.getMonthDTOById(1);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getMonthDTOById() throws ResourceNotAvailableException given when month does not exist")
    void should_MethodGetMonthDTOByIdThrowsResourceNotAvailableException_When_GivenMonthDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> monthService.getMonthDTOById(1));
    }

    @Test
    @DisplayName("Check if method getMonthDTOById() calls monthMapService's method mapToDTO()")
    void should_MethodGetMonthDTOByIdCallsMonthMapServiceMethodMapToDTO() {
        given(repository.findById(1L)).willReturn(Optional.of(month1_2020));
        //when
        monthService.getMonthDTOById(1);
        //then
        verify(monthMapService, Mockito.times(1)).mapToDTO(month1_2020);
    }

    @Test
    @DisplayName("Check if method getMonthDTOByLocalDate() calls yearService's method getYearByYearNumber()")
    void should_MethodGetMonthDTOByLocalDateCallsYearServiceMethodGetYearByYearNumber() {
        //given
        given(repository.findByYearIdAndMonthName(1L, 2)).willReturn(Optional.of(month2_2020));
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        //when
        monthService.getMonthDTOByLocalDate(LocalDate.of(2020, 2 ,1));
        //then
        verify(repository, Mockito.times(1)).findByYearIdAndMonthName(1L, 2);
    }

    @Test
    @DisplayName("Check if method getMonthDTOByLocalDate() calls repository's method findByYearIdAndMonthName()")
    void should_MethodGetMonthDTOByLocalDateCallsRepositoryMethodFindByYearIdAndMonthName() {
        //given
        given(repository.findByYearIdAndMonthName(1L, 2)).willReturn(Optional.of(month2_2020));
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        //when
        monthService.getMonthDTOByLocalDate(LocalDate.of(2020, 2 ,1));
        //then
        verify(repository, Mockito.times(1)).findByYearIdAndMonthName(1L, 2);
    }

    @Test
    @DisplayName("Check if method getMonthDTOByLocalDate() throws ResourceNotAvailableException given when month does not exist")
    void should_MethodGetMonthDTOByLocalDateThrowsResourceNotAvailableException_When_GivenMonthDoesNotExist() {
        //given
        given(repository.findByYearIdAndMonthName(1L, 3)).willReturn(Optional.empty());
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> monthService.getMonthDTOByLocalDate(LocalDate.of(2020, 3 ,1)));
    }

    @Test
    @DisplayName("Check if method getMonthDTOByLocalDate() calls monthMapService's method mapToDTO()")
    void should_MethodGetMonthDTOByLocalDateCallsMonthMapServiceMethodMapToDTO() {
        given(repository.findByYearIdAndMonthName(1L, 2)).willReturn(Optional.of(month2_2020));
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        //when
        monthService.getMonthDTOByLocalDate(LocalDate.of(2020, 2 ,1));
        //then
        verify(monthMapService, Mockito.times(1)).mapToDTO(month2_2020);
    }
}