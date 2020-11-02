package com.pk.ms.services.week;

import com.pk.ms.dao.week.IWeekRepository;
import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.dto.week.WeekInputDTO;
import com.pk.ms.dto.week.WeekWithBasicDayDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.EntityAlreadyExistException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.day.DayMapService;
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

    private final IWeekRepository weekRepo;

    private final YearService yearService;

    private final DayMapService dayMapService;

    private final DayService dayService;

    public WeekService(IWeekRepository weekRepo, @Lazy YearService yearService, DayMapService dayMapService, DayService dayService) {
        this.weekRepo = weekRepo;
        this.yearService = yearService;
        this.dayMapService = dayMapService;
        this.dayService = dayService;
    }

    public Week saveWeek(Week week) {
        return weekRepo.save(week);
    }

    public Week getWeekById(long id) {
        return weekRepo.findById(id);
    }

    public List<Week> getWeeksByMonthId(long id) { return weekRepo.findAllByYearId(id); }

    public String deleteWeek(long scheduleId, long weekId) {
        Week week = getWeekById(weekId);
        if(week == null)
            throw new ResourceNotAvailableException();
        if (hasAccess(scheduleId, week)) {
            weekRepo.deleteById(weekId);
            return "Week deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");
    }


    public Week getActualWeek(long yearId, LocalDate date) {
        Date aDate = Date.valueOf(date);
        return weekRepo.findByYearIdAndDate(yearId, aDate, aDate);
    }

    public boolean existsByYearIdAndDate(long yearId, WeekInputDTO weekInputDTO) {
        if (weekRepo.findYearIncludingDatesBetweenStartDateAndEndDate(yearId, weekInputDTO.getStartDate(), weekInputDTO.getEndDate()) != null) {
            return true;
        }
        else
            return false;
    }

    public Week createWeek(long scheduleId, long yearId, WeekInputDTO weekInputDTO) {
        Year year = yearService.getYearById(yearId);
        if(year == null)
            throw new ResourceNotAvailableException();
        if(yearService.hasAccess(scheduleId, year)) {
            if (existsByYearIdAndDate(yearId, weekInputDTO))
                throw new EntityAlreadyExistException(weekInputDTO);
            return saveWeek(new Week(weekInputDTO.getStartDate(), weekInputDTO.getEndDate(), year));
        }
        else
            throw new AccessDeniedException("This user cannot create Week in this Year. ");
    }

    public WeekWithBasicDayDTO getWeek(long scheduleId, long weekId) {
        Week week = getWeekById(weekId);
        if(week == null)
            throw new ResourceNotAvailableException();
        if(hasAccess(scheduleId, week)) {
            List<Day> dayList = dayService.getDaysByWeekId(weekId);
            List<DayBasicInfoDTO> dayBasicInfoDTOList = new ArrayList<>();
            for (Day day : dayList)
                dayBasicInfoDTOList.add(dayMapService.mapToDTO(day));
            return new WeekWithBasicDayDTO(week.getWeekId(), week.getStartDate(), week.getEndDate(),
                    dayBasicInfoDTOList, week.getWeekPlansList());
        }
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public boolean hasAccess(long scheduleId, Week week) {
        return week.getYear().getSchedule().getScheduleId() == scheduleId;
    }

}
