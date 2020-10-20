package com.pk.ms.dto.week;

import com.pk.ms.entities.week.WeekPlan;

import java.sql.Date;
import java.util.List;

// Week DTO for purpose of storing the fields of Week needed on Home Page
public class ActualWeekHomePageDTO {

    private long weekId;
    private Date startDate;
    private Date endDate;
    private List<WeekPlan> weekPlanList;


    public ActualWeekHomePageDTO() {

    }

    public ActualWeekHomePageDTO(long weekId, Date startDate, Date endDate, List<WeekPlan> weekPlanList) {
        this.weekId = weekId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekPlanList = weekPlanList;
    }

    public long getWeekId() {
        return weekId;
    }

    public void setWeekId(long weekId) {
        this.weekId = weekId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<WeekPlan> getWeekPlanList() {
        return weekPlanList;
    }

    public void setWeekPlanList(List<WeekPlan> weekPlanList) {
        this.weekPlanList = weekPlanList;
    }
}
