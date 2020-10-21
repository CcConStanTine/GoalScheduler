package com.pk.ms.dto.month;

import com.pk.ms.entities.month.MonthName;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// Month DTO for purpose of creating
public class MonthInputDTO {

    @NotNull
    private MonthName monthName;

    public MonthInputDTO() {
    }

    public MonthInputDTO(MonthName monthName) {
        this.monthName = monthName;
    }

    public MonthName getMonthName() {
        return monthName;
    }

    public void setMonthName(MonthName monthName) {
        this.monthName = monthName;
    }
}
