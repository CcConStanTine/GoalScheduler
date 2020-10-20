package com.pk.ms.dto.day;

import com.pk.ms.entities.day.DayName;

import java.sql.Date;

// Day DTO for purpose of storing the fields needed while displaying the Days List
public class DayBasicInfoDTO {

    private long dayId;
    private Date dayDate;
    private DayName dayName;

    public DayBasicInfoDTO() {
    }

    public DayBasicInfoDTO(long dayId, Date dayDate, DayName dayName) {
        this.dayId = dayId;
        this.dayDate = dayDate;
        this.dayName = dayName;
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
}
