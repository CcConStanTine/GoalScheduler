package com.pk.ms.dto.day;

import com.pk.ms.entities.day.DayName;
import com.pk.ms.entities.day.DayPlan;

import java.sql.Date;
import java.util.List;

// Day DTO for purpose of storing the fields of Day needed on Home Page
public class ActualDayHomePageDTO {

    private long dayId;
    private Date dayDate;
    private DayName dayName;
    private List<DayPlan> dayPlanList;

    public ActualDayHomePageDTO() {
    }

    public ActualDayHomePageDTO(long dayId, Date dayDate, DayName dayName, List<DayPlan> dayPlanList) {
        this.dayId = dayId;
        this.dayDate = dayDate;
        this.dayName = dayName;
        this.dayPlanList = dayPlanList;
    }

    public long getDayId() {
        return dayId;
    }

    public void setDayId(long dayId) {
        this.dayId = dayId;
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public DayName getDayName() {
        return dayName;
    }

    public void setDayName(DayName dayName) {
        this.dayName = dayName;
    }

    public List<DayPlan> getDayPlanList() {
        return dayPlanList;
    }

    public void setDayPlanList(List<DayPlan> dayPlanList) {
        this.dayPlanList = dayPlanList;
    }
}
