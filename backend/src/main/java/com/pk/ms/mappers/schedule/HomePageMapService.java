package com.pk.ms.mappers.schedule;

import com.pk.ms.dto.day.ActualDayHomePageDTO;
import com.pk.ms.dto.month.ActualMonthHomePageDTO;
import com.pk.ms.dto.schedule.ScheduleHomePageDTO;
import com.pk.ms.dto.week.ActualWeekHomePageDTO;
import com.pk.ms.dto.year.ActualYearHomePageDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.schedule.LongTermPlan;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.year.Year;
import com.pk.ms.services.day.DayService;
import com.pk.ms.services.month.MonthService;
import com.pk.ms.services.schedule.LongTermPlanService;
import com.pk.ms.services.week.WeekService;
import com.pk.ms.services.year.YearService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HomePageMapService {


    ModelMapper modelMapper = new ModelMapper();

    private final LongTermPlanService longTermPlanService;

    private final YearService yearService;

    private final MonthService monthService;

    private final WeekService weekService;

    private final  DayService dayService;

    public HomePageMapService(LongTermPlanService longTermPlanService, YearService yearService, MonthService monthService, WeekService weekService, DayService dayService) {
        this.longTermPlanService = longTermPlanService;
        this.yearService = yearService;
        this.monthService = monthService;
        this.weekService = weekService;
        this.dayService = dayService;
    }

    public ScheduleHomePageDTO getHomePage(LocalDate date, long scheduleId) {

        ScheduleHomePageDTO scheduleHomePageDTO = new ScheduleHomePageDTO();

        List<LongTermPlan> longTermPlanList = getLongTermPlans(scheduleId);
        setLTPList(scheduleHomePageDTO, longTermPlanList);

        Year actualYear = getActualYear(date, scheduleId);
        long yearId;
        yearId = getYearId(actualYear);
        setActualYear(scheduleHomePageDTO, actualYear);

        Month actualMonth = getActualMonth(date, yearId);
        long monthId;
        monthId = getMonthId(actualMonth);
        setActualMonth(scheduleHomePageDTO, actualMonth);

        Week actualWeek = getActualWeek(date, yearId);
        setActualWeek(scheduleHomePageDTO, actualWeek);

        Day actualDay = getActualDay(date, monthId);
        setActualDay(scheduleHomePageDTO, actualDay);

        return scheduleHomePageDTO;
    }

    private void setActualDay(ScheduleHomePageDTO scheduleHomePageDTO, Day actualDay) {
        if(actualDay !=null) {
            ActualDayHomePageDTO actualDayDTO = modelMapper.map(actualDay, ActualDayHomePageDTO.class);
            actualDayDTO.setDayPlanList(actualDay.getDayPlansList());
            scheduleHomePageDTO.setActualDay(actualDayDTO);
        }
    }

    private void setActualWeek(ScheduleHomePageDTO scheduleHomePageDTO, Week actualWeek) {
        if(actualWeek !=null) {
            ActualWeekHomePageDTO actualWeekDTO = modelMapper.map(actualWeek, ActualWeekHomePageDTO.class);
            actualWeekDTO.setWeekPlanList(actualWeek.getWeekPlansList());
            scheduleHomePageDTO.setActualWeek(actualWeekDTO);
        }
    }

    private void setActualMonth(ScheduleHomePageDTO scheduleHomePageDTO, Month actualMonth) {
        if(actualMonth !=null) {
            ActualMonthHomePageDTO actualMonthDTO = modelMapper.map(actualMonth, ActualMonthHomePageDTO.class);
            actualMonthDTO.setMonthPlanList(actualMonth.getMonthPlansList());
            scheduleHomePageDTO.setActualMonth(actualMonthDTO);
        }
    }

    private long getMonthId(Month actualMonth) {
        return actualMonth.getMonthId();
    }

    private void setActualYear(ScheduleHomePageDTO scheduleHomePageDTO, Year actualYear) {
        if(actualYear !=null) {
            ActualYearHomePageDTO actualYearDTO = modelMapper.map(actualYear, ActualYearHomePageDTO.class);
            actualYearDTO.setYearPlanList(actualYear.getYearPlansList());
            scheduleHomePageDTO.setActualYear(actualYearDTO);
        }
    }

    private long getYearId(Year actualYear) {
        return actualYear.getYearId();
    }

    private void setLTPList(ScheduleHomePageDTO scheduleHomePageDTO, List<LongTermPlan> longTermPlanList) {
        if(!longTermPlanList.isEmpty())
            scheduleHomePageDTO.setLongTermPlanList(longTermPlanList);
    }

    private Day getActualDay(LocalDate date, long monthId) {
        return dayService.getActualDay(monthId, date);
    }

    private Week getActualWeek(LocalDate date, long yearId) {
        return weekService.getActualWeek(yearId, date);
    }

    private Month getActualMonth(LocalDate date, long yearId) {
        return monthService.getActualMonth(date, yearId);
    }

    private Year getActualYear(LocalDate date, long scheduleId) {
        return yearService.getActualYear(date, scheduleId);
    }

    private List<LongTermPlan> getLongTermPlans(long scheduleId) {
        return longTermPlanService.getLongTermPlans(scheduleId);
    }


}
