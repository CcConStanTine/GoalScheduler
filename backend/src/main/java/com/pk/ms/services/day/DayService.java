package com.pk.ms.services.day;

import com.pk.ms.dao.day.DayRepository;
import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.day.DayMapService;
import com.pk.ms.services.month.MonthService;
import com.pk.ms.services.week.WeekService;
import com.pk.ms.services.year.YearService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DayService {

    private final DayRepository dayRepo;

    private final MonthService monthService;

    private final DayMapService dayMapService;

    public DayService(DayRepository dayRepo, MonthService monthService, DayMapService dayMapService) {
        this.dayRepo = dayRepo;
        this.monthService = monthService;
        this.dayMapService = dayMapService;
    }

    public Day getDayById(long dayId) {
        return getNotNullDayById(dayId);
    }

    public List<DayBasicInfoDTO> getDayDTOsByMonthId(long monthId) {
        List<Day> dayList = getDaysByMonthIdFromRepo(monthId);
        List<DayBasicInfoDTO> dayBasicInfoDTOList = new ArrayList<>();
        for(Day day : dayList)
            dayBasicInfoDTOList.add(mapToDTO(day));
        return dayBasicInfoDTOList;
    }

    public List<DayBasicInfoDTO> getDayDTOsByLocalDate(LocalDate date) {
        long monthId = monthService.getMonthDTOByLocalDate(date).getMonthId();
        List<Day> dayList = getDaysByMonthIdFromRepo(monthId);
        List<DayBasicInfoDTO> dayBasicInfoDTOList = new ArrayList<>();
        for(Day day : dayList)
            dayBasicInfoDTOList.add(mapToDTO(day));
        return dayBasicInfoDTOList;
    }

    public DayBasicInfoDTO getDayDTOByDayId(long dayId) {
        return mapToDTO(getNotNullDayById(dayId));
    }

    public DayBasicInfoDTO getDayDTOByLocalDate(LocalDate date) {
        return mapToDTO(getNotNullDayByLocalDate(date));
    }


    private List<Day> getDaysByMonthIdFromRepo(long monthId) {
        return dayRepo.findAllByMonthId(monthId);
    }

    private Day getNotNullDayById(long dayId) {
        Day day = getDayByIdFromRepo(dayId);
        if (isObjectNull(day))
            throw new ResourceNotAvailableException();
        return day;
    }

    private Day getDayByIdFromRepo(long dayId) {
        return dayRepo.findById(dayId);
    }

    private Day getNotNullDayByLocalDate(LocalDate date) {
        return getDayByLocalDateFromRepo(date);
    }

    private Day getDayByLocalDateFromRepo(LocalDate date) {
        return dayRepo.findByDate(date);
    }

    private DayBasicInfoDTO mapToDTO(Day day) {
        return dayMapService.mapToDTO(day);
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }
}

