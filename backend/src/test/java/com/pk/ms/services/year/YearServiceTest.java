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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(SpringExtension.class)
class YearServiceTest {

    @Mock
    YearRepository yearRepo;

    YearMapService yearMapService;

    Year year2020;
    Year year2021;

    YearService yearService;

    @BeforeEach
    void setUp() {
        year2020 = new Year(2020);
        year2021 = new Year(2021);
        year2020.setYearId(1L);
        year2021.setYearId(2L);
        yearMapService = new YearMapService();
        yearService = new YearService(yearRepo, yearMapService);
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
    @DisplayName("Check if method getAllYearsDTOs() return all Years in YearBasicInfoDTO form when Years exist")
    void should_ReturnAllYearsInYearBasicInfoDTOForm_When_YearsExist() {
        //given
        List<YearBasicInfoDTO> yearBasicInfoDTOListExpected =
                Arrays.asList(yearMapService.mapToDTO(year2020), yearMapService.mapToDTO(year2021));
        given(yearRepo.findAll()).willReturn(Arrays.asList(year2020, year2021));
        //when
        List<YearBasicInfoDTO> yearBasicInfoDTOListActual = yearService.getAllYearsDTOs();
        //then
        assertAll(
                () -> assertEquals(2, yearBasicInfoDTOListExpected.size()),
                () -> assertEquals(yearBasicInfoDTOListExpected.get(0).getYearId(),
                        yearBasicInfoDTOListActual.get(0).getYearId()),
                () -> assertEquals(yearBasicInfoDTOListExpected.get(1).getYearId(),
                        yearBasicInfoDTOListActual.get(1).getYearId())
        );
    }

    @Test
    @DisplayName("Check if method getYearDTO(long yearId) throws ResourceNotAvailableException when there is no Year with given id")
    void should_ThrowResourceNotAvailableException_When_ThereIsNoYearWithGivenId() {
        //given
        given(yearRepo.findById(3L)).willReturn(null);
        //when + then
        assertThrows(ResourceNotAvailableException.class, () -> yearService.getYearById(3L));
        verify(yearRepo, Mockito.times(1)).findById(3L);
    }

    @Test
    @DisplayName("Check if method getYearDTO(long yearId) return Year in YearBasicInfoDTO form when Year with given id exists")
    void should_ReturnYearInYearBasicInfoDTOForm_When_YearWithGivenIdExists() {
        //given
        YearBasicInfoDTO yearBasicInfoDTOExpected = yearMapService.mapToDTO(year2020);
        given(yearRepo.findById(1L)).willReturn(year2020);
        //when
        YearBasicInfoDTO yearBasicInfoDTOActual = yearService.getYearDTOById(1L);
        //then
        verify(yearRepo, Mockito.times(1)).findById(1L);
        assertEquals(yearBasicInfoDTOActual.getYearId(), yearBasicInfoDTOExpected.getYearId());
    }

}
