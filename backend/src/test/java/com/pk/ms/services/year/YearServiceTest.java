package com.pk.ms.services.year;

import com.pk.ms.dao.year.YearRepository;
import com.pk.ms.dto.year.YearBasicInfoDTO;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.year.YearMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(SpringExtension.class)
class YearServiceTest {

    @Mock
    YearRepository yearRepo;

    @Mock
    YearMapService yearMapService;

    Year year2020;
    Year year2021;
    YearBasicInfoDTO year2020BasicInfoDTO;
    YearBasicInfoDTO year2021BasicInfoDTO;

    YearService yearService;

    @BeforeEach
    void setUp() {
        year2020 = new Year(2020);
        year2021 = new Year(2021);
        year2020.setYearId(1L);
        year2021.setYearId(2L);
        year2020BasicInfoDTO = new YearBasicInfoDTO(year2020.getYearId(), year2020.getYearNumber(),
                year2020.isLeapYear(), year2020.getDaysAmount());
        year2021BasicInfoDTO = new YearBasicInfoDTO(year2021.getYearId(), year2021.getYearNumber(),
                year2021.isLeapYear(), year2021.getDaysAmount());
        yearService = new YearService(yearRepo, yearMapService);
    }

    @Test
    @DisplayName("Check if method getYearById() calls repository's method findById()")
    void should_MethodGetYearByIdCallsRepositoryMethodFindById() {
        //given
        given(yearRepo.findById(1L)).willReturn(Optional.of(year2020));
        //when
        yearService.getYearById(1L);
        //then
        verify(yearRepo, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check if method getYearById() throws ResourceNotAvailableException when year with given id does not exist")
    void should_MethodGetYearByIdThrowsResourceNotAvailableException_When_GivenYearDoesNotExist() {
        //given
        given(yearRepo.findById(1L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> yearService.getYearById(1L));

    }

    @Test
    @DisplayName("Check if method getYearByYearNumber() calls repository's method findByYearNumber()")
    void should_MethodGetYearByYearNumberCallsRepositoryMethodFindById() {
        //given
        given(yearRepo.findByYearNumber(2020)).willReturn(Optional.of(year2020));
        //when
        yearService.getYearByYearNumber(2020);
        //then
        verify(yearRepo, Mockito.times(1)).findByYearNumber(2020);
    }

    @Test
    @DisplayName("Check if method getYearByYearNumber() throws ResourceNotAvailableException when year with given yearNumber does not exist")
    void should_MethodGetYearByYearNumberThrowsResourceNotAvailableException_When_GivenYearDoesNotExist() {
        //given
        given(yearRepo.findByYearNumber(2020)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class,
                () -> yearService.getYearByYearNumber(2020));

    }

    @Test
    @DisplayName("Check if method getAllYearsDTOs() calls repository's method findAll()")
    void should_MethodGetAllYearDTOsCallsRepositoryMethodFindAll() {
        //given
        given(yearRepo.findAll()).willReturn(Arrays.asList(year2020, year2021));
        //when
        yearService.getAllYearsDTOs();
        //then
        verify(yearRepo, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Check if method getAllYearsDTOs() return empty List when no Years exist")
    void should_ReturnEmptyListOfDTOs_When_NoYearsExist() {
        //given
        List<Year> emptyList = new ArrayList<>();
        given(yearRepo.findAll()).willReturn(emptyList);
        //when
        List<YearBasicInfoDTO> yearBasicInfoDTOListActual = yearService.getAllYearsDTOs();
        //then
        verify(yearRepo, Mockito.times(1)).findAll();
        assertEquals(0, yearBasicInfoDTOListActual.size());
    }

    @Test
    @DisplayName("Check if method getYearDTO() throws ResourceNotAvailableException when there is no Year with given id")
    void should_ThrowsResourceNotAvailableException_When_ThereIsNoYearWithGivenId() {
        //given
        given(yearRepo.findById(3L)).willReturn(Optional.empty());
        //when + then
        assertThrows(ResourceNotAvailableException.class, () -> yearService.getYearById(3L));
        verify(yearRepo, Mockito.times(1)).findById(3L);
    }
}
