package com.pk.ms.services.year;

import com.pk.ms.dao.year.YearRepository;
import com.pk.ms.dto.year.YearBasicInfoDTO;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.year.YearMapService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class YearService {

    private final YearRepository repository;

    private final YearMapService yearMapService;

    public YearService(YearRepository repository, YearMapService yearMapService) {
        this.repository = repository;
        this.yearMapService = yearMapService;
    }

    public Year getYearById(long yearId) {
        return getNotNullYearById(yearId);
    }

    public Year getYearByYearNumber(int yearNumber) {
        return getNotNullYearById(yearNumber);
    }

    public List<YearBasicInfoDTO> getAllYearsDTOs() {
        List<Year> yearList = repository.findAll();
        return mapToDTOs(yearList);
    }

    public YearBasicInfoDTO getYearDTOById(long yearId) {
        return mapYearToDTO(getNotNullYearById(yearId));
    }

    public YearBasicInfoDTO getYearDTOByLocalDate(LocalDate date) {
        return mapYearToDTO(getNotNullYearByYearNumber(date.getYear()));
    }

    private Year getNotNullYearById(long id) {
        return repository.findById(id).orElseThrow(ResourceNotAvailableException::new);
    }

    private Year getNotNullYearByYearNumber(int yearNumber) {
        return repository.findByYearNumber(yearNumber).orElseThrow(ResourceNotAvailableException::new);
    }

    private YearBasicInfoDTO mapYearToDTO(Year year) {
        return yearMapService.mapToDTO(year);
    }

    private List<YearBasicInfoDTO> mapToDTOs(List<Year> yearList) {
        List<YearBasicInfoDTO> yearBasicInfoDTOList = new ArrayList<>();
        for(Year year : yearList)
            yearBasicInfoDTOList.add(mapYearToDTO(year));
        return yearBasicInfoDTOList;
    }

}

