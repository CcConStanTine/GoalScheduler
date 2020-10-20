package com.pk.ms.dto.week;

import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.entities.week.WeekPlan;

import java.sql.Date;
import java.util.List;

// Week DTO for purpose of storing fields needed while displaying particular Week
public class WeekWithBasicDayDTO {

    private long weekId;
    private Date startDate;
    private Date endDate;
    private List<DayBasicInfoDTO> dayBasicInfoDTOList;
    private List<WeekPlan> weekPlanList;

    public WeekWithBasicDayDTO() {
    }

    public WeekWithBasicDayDTO(long weekId, Date startDate, Date endDate, List<DayBasicInfoDTO> dayBasicInfoDTOList, List<WeekPlan> weekPlanList) {
        this.weekId = weekId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dayBasicInfoDTOList = dayBasicInfoDTOList;
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

    public List<DayBasicInfoDTO> getDayBasicInfoDTOList() {
        return dayBasicInfoDTOList;
    }

    public void setDayBasicInfoDTOList(List<DayBasicInfoDTO> dayBasicInfoDTOList) {
        this.dayBasicInfoDTOList = dayBasicInfoDTOList;
    }

    public List<WeekPlan> getWeekPlanList() {
        return weekPlanList;
    }

    public void setWeekPlanList(List<WeekPlan> weekPlanList) {
        this.weekPlanList = weekPlanList;
    }
}
