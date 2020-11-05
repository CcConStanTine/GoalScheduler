package com.pk.ms.services.year;

import com.pk.ms.dao.year.YearRepository;
import com.pk.ms.dto.month.MonthBasicInfoDTO;
import com.pk.ms.dto.week.WeekBasicInfoDTO;
import com.pk.ms.dto.year.YearBasicInfoDTO;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.month.MonthMapService;
import com.pk.ms.mappers.week.WeekMapService;
import com.pk.ms.mappers.year.YearMapService;
import com.pk.ms.services.month.MonthService;
import com.pk.ms.services.schedule.ScheduleService;
import com.pk.ms.services.week.WeekService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class YearService {

    private final YearRepository yearRepo;

    private final YearMapService yearMapService;

    public YearService(YearRepository yearRepo, YearMapService yearMapService) {
        this.yearRepo = yearRepo;
        this.yearMapService = yearMapService;
    }

    public Year getYearById(long yearId) {
        return getNotNullYearById(yearId);
    }

    public List<YearBasicInfoDTO> getAllYearsDTOs() {
        List<Year> yearList = yearRepo.findAll();
        List<YearBasicInfoDTO> yearBasicInfoDTOList = new ArrayList<>();
        for(Year year : yearList)
            yearBasicInfoDTOList.add(mapYearToDTO(year));
        return yearBasicInfoDTOList;
    }

    public YearBasicInfoDTO getYearDTOById(long yearId) {
        return mapYearToDTO(getNotNullYearById(yearId));
    }

    public YearBasicInfoDTO getYearDTOByLocalDate(LocalDate date) {
        return mapYearToDTO(getNotNullYearByYearNumber(date.getYear()));
    }


    private Year getNotNullYearById(long yearId) {
        Year year = getYearByIdFromRepo(yearId);
        if (isObjectNull(year))
            throw new ResourceNotAvailableException();
        return year;
    }

    private Year getYearByIdFromRepo(long id) {
        return yearRepo.findById(id);
    }

    private Year getNotNullYearByYearNumber(int yearNumber) {
        Year year = getYearByYearNumberFromRepo(yearNumber);
        if (isObjectNull(year))
            throw new ResourceNotAvailableException();
        return year;
    }

    private Year getYearByYearNumberFromRepo(int yearNumber) {
        return yearRepo.findByYearNumber(yearNumber);
    }

    private YearBasicInfoDTO mapYearToDTO(Year year) {
        return yearMapService.mapToDTO(year);
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }
}

