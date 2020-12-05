package com.pk.ms.services.day;

import com.pk.ms.dao.day.DayRepository;
import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.day.DayMapService;
import com.pk.ms.services.month.MonthService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DayService {

    private final DayRepository repository;

    private final MonthService monthService;

    private final DayMapService dayMapService;

    public DayService(DayRepository repository, MonthService monthService, DayMapService dayMapService) {
        this.repository = repository;
        this.monthService = monthService;
        this.dayMapService = dayMapService;
    }

    public Day getDayById(long dayId) {
        return getNotNullDayById(dayId);
    }

    public List<DayBasicInfoDTO> getDayDTOsByMonthId(long monthId) {
        List<Day> dayList = repository.findAllByMonthId(monthId);
        return mapToDTOs(dayList);
    }

    public List<DayBasicInfoDTO> getDayDTOsByLocalDate(LocalDate date) {
        long monthId = monthService.getMonthDTOByLocalDate(date).getMonthId();
        List<Day> dayList = repository.findAllByMonthId(monthId);
        return mapToDTOs(dayList);
    }

    public DayBasicInfoDTO getDayDTOByDayId(long dayId) {
        return mapToDTO(getNotNullDayById(dayId));
    }

    public DayBasicInfoDTO getDayDTOByLocalDate(LocalDate date) {
        return mapToDTO(getNotNullDayByLocalDate(date));
    }

    private Day getNotNullDayById(long dayId) {
        return repository.findById(dayId).orElseThrow(ResourceNotAvailableException::new);
    }

    private Day getNotNullDayByLocalDate(LocalDate date) {
        return repository.findByDate(date).orElseThrow(ResourceNotAvailableException::new);
    }

    private DayBasicInfoDTO mapToDTO(Day day) {
        return dayMapService.mapToDTO(day);
    }

    private List<DayBasicInfoDTO> mapToDTOs(List<Day> dayList) {
        List<DayBasicInfoDTO> dayBasicInfoDTOList = new ArrayList<>();
        for (Day day : dayList)
            dayBasicInfoDTOList.add(mapToDTO(day));
        return dayBasicInfoDTOList;
    }
}

