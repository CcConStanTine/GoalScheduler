package com.pk.ms.services.week;

import com.pk.ms.dao.week.WeekRepository;
import com.pk.ms.dto.week.WeekBasicInfoDTO;
import com.pk.ms.entities.week.Week;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.week.WeekMapService;
import com.pk.ms.services.year.YearService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeekService {

    private final WeekRepository repository;

    private final YearService yearService;

    private final WeekMapService weekMapService;

    public WeekService(WeekRepository repository, YearService yearService, WeekMapService weekMapService) {
        this.repository = repository;
        this.yearService = yearService;
        this.weekMapService = weekMapService;
    }

    public Week getWeekById(long weekId) {
        return getNotNullWeekById(weekId);
    }

    public List<WeekBasicInfoDTO> getWeekDTOsByYearId(long yearId) {
        List<Week> weekList = repository.findAllByYearId(yearId);;
        return mapToDTOs(weekList);
    }

    public List<WeekBasicInfoDTO> getWeekDTOsByLocalDate(LocalDate date) {
        long yearId = yearService.getYearByYearNumber(date.getYear()).getYearId();
        List<Week> weekList = repository.findAllByYearId(yearId);
        return mapToDTOs(weekList);
    }

    public WeekBasicInfoDTO getWeekDTOByWeekId(long weekId) {
        return mapToDTO(getNotNullWeekById(weekId));
    }

    public WeekBasicInfoDTO getWeekDTOByLocalDate(LocalDate date) {
        return mapToDTO(getNotNullWeekByLocalDate(date));
    }

    private Week getNotNullWeekById(long weekId) {
        return repository.findById(weekId).orElseThrow(ResourceNotAvailableException::new);
    }

    private Week getNotNullWeekByLocalDate(LocalDate date) {
        return repository.findByDate(date).orElseThrow(ResourceNotAvailableException::new);
    }

    private WeekBasicInfoDTO mapToDTO(Week week) {
        return weekMapService.mapToDTO(week);
    }

    private List<WeekBasicInfoDTO> mapToDTOs(List<Week> weekList) {
        List<WeekBasicInfoDTO> weekBasicInfoDTOList = new ArrayList<>();
        for (Week week : weekList)
            weekBasicInfoDTOList.add(mapToDTO(week));
        return weekBasicInfoDTOList;
    }
}

