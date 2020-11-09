package com.pk.ms.services.week;

import com.pk.ms.dao.week.WeekRepository;
import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.dto.week.WeekBasicInfoDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.day.DayMapService;
import com.pk.ms.mappers.week.WeekMapService;
import com.pk.ms.services.day.DayService;
import com.pk.ms.services.year.YearService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeekService {

    private final WeekRepository weekRepo;

    private final YearService yearService;

    private final WeekMapService weekMapService;

    public WeekService(WeekRepository weekRepo, YearService yearService, WeekMapService weekMapService) {
        this.weekRepo = weekRepo;
        this.yearService = yearService;
        this.weekMapService = weekMapService;
    }

    public Week getWeekById(long weekId) {
        return getNotNullWeekById(weekId);
    }

    public List<WeekBasicInfoDTO> getWeekDTOsByYearId(long yearId) {
        List<Week> weekList = getWeeksByYearIdFromRepo(yearId);
        List<WeekBasicInfoDTO> weekBasicInfoDTOList = new ArrayList<>();
        for(Week week : weekList)
            weekBasicInfoDTOList.add(mapWeekToDTO(week));
        return weekBasicInfoDTOList;
    }

    public List<WeekBasicInfoDTO> getWeekDTOsByLocalDate(LocalDate date) {
        long yearId = yearService.getYearByYearNumber(date.getYear()).getYearId();
        List<Week> weekList = getWeeksByYearIdFromRepo(yearId);
        List<WeekBasicInfoDTO> weekBasicInfoDTOList = new ArrayList<>();
        for(Week week : weekList)
            weekBasicInfoDTOList.add(mapWeekToDTO(week));
        return weekBasicInfoDTOList;
    }

    public WeekBasicInfoDTO getWeekDTOByWeekId(long weekId) {
        return mapWeekToDTO(getNotNullWeekById(weekId));
    }

    public WeekBasicInfoDTO getWeekDTOByLocalDate(LocalDate date) {
        return mapWeekToDTO(getNotNullWeekByLocalDate(date));
    }


    private List<Week> getWeeksByYearIdFromRepo(long yearId) {
        return weekRepo.findAllByYearId(yearId);
    }

    private Week getNotNullWeekById(long weekId) {
        Week week = getWeekByIdFromRepo(weekId);
        if (isObjectNull(week))
            throw new ResourceNotAvailableException();
        return week;
    }

    private Week getWeekByIdFromRepo(long weekId) {
        return weekRepo.findById(weekId);
    }

    private Week getNotNullWeekByLocalDate(LocalDate date) {
        return getWeekByLocalDateFromRepo(date);
    }

    private Week getWeekByLocalDateFromRepo(LocalDate date) {
        return weekRepo.findByDate(date);
    }

    private WeekBasicInfoDTO mapWeekToDTO(Week week) {
        return weekMapService.mapToDTO(week);
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }
}

