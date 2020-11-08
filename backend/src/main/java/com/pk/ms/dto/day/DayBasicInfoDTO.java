package com.pk.ms.dto.day;

import com.pk.ms.entities.day.DayName;

import java.sql.Date;
import java.time.LocalDate;

public class DayBasicInfoDTO {

    private long dayId;
    private LocalDate dayDate;
    private DayName dayName;

    public DayBasicInfoDTO() {
    }

    public DayBasicInfoDTO(long dayId, LocalDate dayDate, DayName dayName) {
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

    public LocalDate getDayDate() {
        return dayDate;
    }

    public void setDayDate(LocalDate dayDate) {
        this.dayDate = dayDate;
    }

    public DayName getDayName() {
        return dayName;
    }

    public void setDayName(DayName dayName) {
        this.dayName = dayName;
    }
}
