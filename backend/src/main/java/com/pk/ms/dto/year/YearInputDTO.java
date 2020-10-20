package com.pk.ms.dto.year;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// Year DTO for purpose of creating
public class YearInputDTO {

    @NotNull
    @Min(value = 2019)
    @Max(value = 2050)
    private int yearNumber;

    public YearInputDTO() {
    }

    public YearInputDTO(int yearNumber) {
        this.yearNumber = yearNumber;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }
}
