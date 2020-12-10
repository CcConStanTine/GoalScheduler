package com.pk.ms.services.week;

import com.pk.ms.dao.week.WeekRepository;
import com.pk.ms.dto.week.WeekBasicInfoDTO;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.week.WeekMapService;
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
class WeekServiceTest {

    @Mock
    WeekRepository repository;

    @Mock
    WeekMapService weekMapService;

    @Mock
    YearService yearService;


    Year year2020;
    Year year2021;

    Week week1;
    Week week2;

    WeekBasicInfoDTO weekBasicInfoDTO1;

    WeekService weekService;

    @BeforeEach
    void setUp() {
        year2020 = new Year(2020);
        year2021 = new Year(2021);
        year2020.setYearId(1L);
        year2021.setYearId(2L);
        week1 = new Week(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), year2020);
        week2 = new Week(LocalDate.of(2020, 1, 6), LocalDate.of(2020, 1, 12), year2020);
        week1.setWeekId(1L);
        week2.setWeekId(2L);

        weekBasicInfoDTO1 = new WeekBasicInfoDTO(week1.getWeekId(), week1.getStartDate(), week1.getEndDate());

        weekService = new WeekService(repository, yearService, weekMapService);
    }

    @Test
    @DisplayName("Check if method getWeekById() calls repository's method findById()")
    void should_MethodGetWeekByIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(week1));
        //when
        weekService.getWeekById(1L);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getWeekById() throws ResourceNotAvailableException when week with given id does not exist")
    void should_MethodGetWeekByIdThrowsResourceNotAvailableException_When_GivenWeekDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekService.getWeekById(1L));
    }

    @Test
    @DisplayName("Check if method getWeekDTOsByYearId() calls repository's method findAllByYearId()")
    void should_MethodGetWeekDTOsByYearIdCallsRepositoryMethodFindAllByYearId() {
        //given
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(week1, week2));
        //when
        weekService.getWeekDTOsByYearId(1L);
        //then
        verify(repository,Mockito.times(1)).findAllByYearId(1L);
    }

    @Test
    @DisplayName("Check if method getWeekDTOsByYearId() calls weekMapService method mapToDTO()")
    void should_MethodGetWeekDTOsByYearIdCallsWeekMapServiceMethodMapToDTO() {
        //given
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(week1, week2));
        //when
        weekService.getWeekDTOsByYearId(1L);
        //then
        verify(weekMapService, Mockito.times(2)).mapToDTO(any()) ;
    }

    @Test
    @DisplayName("Check if method getWeekDTOsByYearId() returns same sized list of mapped weeks given from repository")
    void should_MethodGetWeekDTOsByYearIdReturnsListOfWeeksGivenFromRepository() {
        //given
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(week1, week2));
        //when
        List<WeekBasicInfoDTO> list = weekService.getWeekDTOsByYearId(1L);
        //then
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Check if method getWeekDTOsByYearId() returns empty list when there is no weeks")
    void should_MethodGetWeekDTOsByYearIdReturnsEmptyList_When_ThereIsNoWeeks() {
        //given
        given(repository.findAllByYearId(1L)).willReturn(Collections.emptyList());
        //when
        List<WeekBasicInfoDTO> list = weekService.getWeekDTOsByYearId(1L);
        //then
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Check if method getWeekDTOsByLocalDate() calls yearService's method getYearByYearNumber()")
    void should_MethodGetWeekDTOsByLocalDateCallsYearServiceMethodGetYearByYearNumber() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        //when
        weekService.getWeekDTOsByLocalDate(LocalDate.of(2020, 1 ,2));
        //then
        verify(yearService, Mockito.times(1)).getYearByYearNumber(2020);
    }

    @Test
    @DisplayName("Check if method getWeekDTOsByLocalDate() calls repository's method findAllByYearId()")
    void should_MethodGetWeekDTOsByLocalDateCallsRepositoryMethodFindAllByYearId() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        //when
        weekService.getWeekDTOsByLocalDate(LocalDate.of(2020, 1 ,2));
        //then
        verify(repository, Mockito.times(1)).findAllByYearId(1);
    }

    @Test
    @DisplayName("Check if method getWeekDTOsByLocalDate() calls weekMapService's method mapToDTO()")
    void should_MethodGetWeekDTOsByLocalDateCallsWeekMapServiceMethodMapToDTO() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(week1, week2));
        //when
        weekService.getWeekDTOsByLocalDate(LocalDate.of(2020, 1 ,2));
        //then
        verify(weekMapService, Mockito.times(2)).mapToDTO(any()) ;
    }

    @Test
    @DisplayName("Check if method getWeekDTOsByLocalDate() returns same sized list of mapped weeks given from repository")
    void should_MethodGetWeekDTOsByLocalDateReturnsListOfWeeksGivenFromRepository() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        given(repository.findAllByYearId(1L)).willReturn(Arrays.asList(week1, week2));
        //when
        List<WeekBasicInfoDTO> list = weekService.getWeekDTOsByLocalDate(LocalDate.of(2020, 1 ,2));
        //then
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Check if method getWeekDTOsByLocalDate() returns empty list when there is no weeks")
    void should_MethodGetWeekDTOsByLocalDateReturnsEmptyList_When_ThereIsNoWeeks() {
        //given
        given(yearService.getYearByYearNumber(2020)).willReturn(year2020);
        given(repository.findAllByYearId(1L)).willReturn(Collections.emptyList());
        //when
        List<WeekBasicInfoDTO> list = weekService.getWeekDTOsByLocalDate(LocalDate.of(2020, 1 ,2));
        //then
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Check if method getWeekDTOByWeekId() calls repository's method findById()")
    void should_MethodGetWeekDTOByWeekIdCallsRepositoryMethodFindById() {
        //given
        given(repository.findById(1L)).willReturn(Optional.of(week1));
        //when
        weekService.getWeekDTOByWeekId(1);
        //then
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getWeekDTOByWeekId() throws ResourceNotAvailableException given when week does not exist")
    void should_MethodGetWeekDTOByWeekIdThrowsResourceNotAvailableException_When_GivenWeekDoesNotExist() {
        //given
        given(repository.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekService.getWeekDTOByWeekId(1));
    }

    @Test
    @DisplayName("Check if method getWeekDTOByWeekId() calls weekMapService's method mapToDTO()")
    void should_MethodGetWeekDTOByWeekIdCallsWeekMapServiceMethodMapToDTO() {
        given(repository.findById(1L)).willReturn(Optional.of(week1));
        //when
        weekService.getWeekDTOByWeekId(1);
        //then
        verify(weekMapService, Mockito.times(1)).mapToDTO(week1);
    }

    @Test
    @DisplayName("Check if method getWeekDTOByLocalDate() calls repository's method findByDate()")
    void should_MethodGetWeekDTOByLocalDateCallsYearServiceMethodGetYearByYearNumber() {
        //given
        given(repository.findByDate(LocalDate.of(2020, 1 ,1))).willReturn(Optional.of(week1));
        //when
        weekService.getWeekDTOByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        verify(repository, Mockito.times(1)).findByDate(LocalDate.of(2020, 1 ,1));
    }

    @Test
    @DisplayName("Check if method getWeekDTOByLocalDate() throws ResourceNotAvailableException given when week does not exist")
    void should_MethodGetWeekDTOByLocalDateThrowsResourceNotAvailableException_When_GivenWeekDoesNotExist() {
        //given
        given(repository.findByDate(LocalDate.of(2020, 1 ,20))).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> weekService.getWeekDTOByLocalDate(LocalDate.of(2020, 1 ,20)));
    }

    @Test
    @DisplayName("Check if method getWeekDTOByLocalDate() calls weekMapService's method mapToDTO()")
    void should_MethodGetWeekDTOByLocalDateCallsWeekMapServiceMethodMapToDTO() {
        given(repository.findByDate(LocalDate.of(2020, 1 ,1))).willReturn(Optional.of(week1));
        //when
        weekService.getWeekDTOByLocalDate(LocalDate.of(2020, 1 ,1));
        //then
        verify(weekMapService, Mockito.times(1)).mapToDTO(week1);
    }
}