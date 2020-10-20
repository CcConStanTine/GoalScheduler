package com.pk.ms.dto.year;

import com.pk.ms.entities.year.YearPlan;

import java.util.List;

// Year DTO for purpose of storing the fields of Year needed on Home Page
public class ActualYearHomePageDTO {

    private long yearId;
    private int yearNumber;
    private List<YearPlan> yearPlanList;

    public ActualYearHomePageDTO() {

    }

    public ActualYearHomePageDTO(long yearId, int yearNumber, List<YearPlan> yearPlanList) {
        this.yearId = yearId;
        this.yearNumber = yearNumber;
        this.yearPlanList = yearPlanList;
    }

    public long getYearId() {
        return yearId;
    }

    public void setYearId(long yearId) {
        this.yearId = yearId;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }

    public List<YearPlan> getYearPlanList() {
        return yearPlanList;
    }

    public void setYearPlanList(List<YearPlan> yearPlanList) {
        this.yearPlanList = yearPlanList;
    }
}
