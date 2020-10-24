package com.pk.ms.dto.schedule;


import com.pk.ms.dto.day.ActualDayHomePageDTO;
import com.pk.ms.dto.month.ActualMonthHomePageDTO;
import com.pk.ms.dto.week.ActualWeekHomePageDTO;
import com.pk.ms.dto.year.ActualYearHomePageDTO;
import com.pk.ms.entities.schedule.LongTermPlan;

import java.util.List;

// Schedule DTO - Home Page of application, displaying list of long-term plans and
// current year, month, week, day based on LocalDate
public class ScheduleHomePageDTO {

    private List<LongTermPlan> longTermPlanList;
    private ActualYearHomePageDTO actualYear;
    private ActualMonthHomePageDTO actualMonth;
    private ActualWeekHomePageDTO actualWeek;
    private ActualDayHomePageDTO actualDay;

    public ScheduleHomePageDTO() {

    }

    public ScheduleHomePageDTO(List<LongTermPlan> longTermPlanList, ActualYearHomePageDTO actualYear,
                               ActualMonthHomePageDTO actualMonth, ActualWeekHomePageDTO actualWeek,
                               ActualDayHomePageDTO actualDay) {
        this.longTermPlanList = longTermPlanList;
        this.actualYear = actualYear;
        this.actualMonth = actualMonth;
        this.actualWeek = actualWeek;
        this.actualDay = actualDay;
    }

    public List<LongTermPlan> getLongTermPlanList() {
        return longTermPlanList;
    }

    public void setLongTermPlanList(List<LongTermPlan> longTermPlanList) {
        this.longTermPlanList = longTermPlanList;
    }

    public ActualYearHomePageDTO getActualYear() {
        return actualYear;
    }

    public void setActualYear(ActualYearHomePageDTO actualYear) {
        this.actualYear = actualYear;
    }

    public ActualMonthHomePageDTO getActualMonth() {
        return actualMonth;
    }

    public void setActualMonth(ActualMonthHomePageDTO actualMonth) {
        this.actualMonth = actualMonth;
    }

    public ActualWeekHomePageDTO getActualWeek() {
        return actualWeek;
    }

    public void setActualWeek(ActualWeekHomePageDTO actualWeek) {
        this.actualWeek = actualWeek;
    }

    public ActualDayHomePageDTO getActualDay() {
        return actualDay;
    }

    public void setActualDay(ActualDayHomePageDTO actualDay) {
        this.actualDay = actualDay;
    }
}
