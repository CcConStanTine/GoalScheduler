package com.pk.ms.dto.day;

import com.pk.ms.entities.day.DayName;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

// Day DTO for purpose of creating
public class DayInputDTO {

    @NotNull
    private Date dayDate;

    @NotNull
    private DayName dayName;

    public DayInputDTO() {
    }

    public DayInputDTO(Date dayDate, DayName dayName) {
        this.dayDate = dayDate;
        this.dayName = dayName;
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
