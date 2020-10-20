package com.pk.ms.dto.week;

import java.sql.Date;

// Week DTO for purpose of storing the fields needed while displaying the Weeks List
public class WeekBasicInfoDTO {

    private long weekId;
    private Date startDate;
    private Date endDate;

    public WeekBasicInfoDTO() {
    }

    public WeekBasicInfoDTO(long weekId, Date startDate, Date endDate) {
        this.weekId = weekId;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
