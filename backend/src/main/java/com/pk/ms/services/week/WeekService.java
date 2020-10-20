package com.pk.ms.services.week;

import com.pk.ms.dao.week.IWeekRepository;
import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.dto.week.WeekInputDTO;
import com.pk.ms.dto.week.WeekWithBasicDayDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.week.Week;
import com.pk.ms.mappers.day.DayMapService;
import com.pk.ms.services.day.DayService;
import com.pk.ms.services.year.YearService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeekService {

    private final IWeekRepository weekRepo;

    private YearService yearService;

    private final DayMapService dayMapService;

    private final DayService dayService;

    public WeekService(IWeekRepository weekRepo, DayMapService dayMapService, DayService dayService) {
        this.weekRepo = weekRepo;
        this.dayMapService = dayMapService;
        this.dayService = dayService;
    }

    public void setYearService(YearService yearService) {
        this.yearService = yearService;
    }

    public Week saveWeek(Week week) {
        return weekRepo.save(week);
    }

    public Week getWeekById(long id) {
        return weekRepo.findById(id);
    }

    public List<Week> getWeeksByMonthId(long id) { return weekRepo.findAllByYearId(id); }

    public void deleteWeek(long id) { weekRepo.deleteById(id); }


    public Week getActualWeek(long yearId, LocalDate date) {
        Date aDate = Date.valueOf(date);
        return weekRepo.findByYearIdAndDate(yearId, aDate, aDate);
    }

    public Week createWeek(long yearId, WeekInputDTO weekInputDTO) {
        return saveWeek(new Week(weekInputDTO.getStartDate(), weekInputDTO.getEndDate(),
                yearService.getYearById(yearId)));
    }

    public WeekWithBasicDayDTO getWeek(long weekId) {
        Week week = getWeekById(weekId);
        List<Day> dayList = dayService.getDaysByWeekId(weekId);
        List<DayBasicInfoDTO> dayBasicInfoDTOList = new ArrayList<>();
        for(Day day : dayList)
            dayBasicInfoDTOList.add(dayMapService.mapToDTO(day));
        return new WeekWithBasicDayDTO(week.getWeekId(), week.getStartDate(), week.getEndDate(),
                dayBasicInfoDTOList, week.getWeekPlansList());
    }

}
