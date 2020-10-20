package com.pk.ms.dto.year;

import com.pk.ms.dto.month.MonthBasicInfoDTO;
import com.pk.ms.dto.week.WeekBasicInfoDTO;
import com.pk.ms.entities.year.YearPlan;

import java.util.List;

// Year DTO for purpose of storing fields needed while displaying particular Year
public class YearWithBasicMonthAndWeekDTO {

    private long yearId;
    private int yearNumber;
    private boolean isLeapYear;
    private int daysAmount;
    private List<MonthBasicInfoDTO> monthBasicInfoDTOList;
    private List<WeekBasicInfoDTO> weekBasicInfoDTOList;
    private List<YearPlan> yearPlansList;


    public YearWithBasicMonthAndWeekDTO() {
    }

    public YearWithBasicMonthAndWeekDTO(long yearId, int yearNumber, boolean isLeapYear, int daysAmount, List<MonthBasicInfoDTO> monthBasicInfoDTOList, List<WeekBasicInfoDTO> weekBasicInfoDTOList, List<YearPlan> yearPlansList) {
        this.yearId = yearId;
        this.yearNumber = yearNumber;
        this.isLeapYear = isLeapYear;
        this.daysAmount = daysAmount;
        this.monthBasicInfoDTOList = monthBasicInfoDTOList;
        this.weekBasicInfoDTOList = weekBasicInfoDTOList;
        this.yearPlansList = yearPlansList;
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

    public boolean isLeapYear() {
        return isLeapYear;
    }

    public void setLeapYear(boolean leapYear) {
        isLeapYear = leapYear;
    }

    public int getDaysAmount() {
        return daysAmount;
    }

    public void setDaysAmount(int daysAmount) {
        this.daysAmount = daysAmount;
    }

    public List<MonthBasicInfoDTO> getMonthBasicInfoDTOList() {
        return monthBasicInfoDTOList;
    }

    public void setMonthBasicInfoDTOList(List<MonthBasicInfoDTO> monthBasicInfoDTOList) {
        this.monthBasicInfoDTOList = monthBasicInfoDTOList;
    }

    public List<YearPlan> getYearPlansList() {
        return yearPlansList;
    }

    public void setYearPlansList(List<YearPlan> yearPlansList) {
        this.yearPlansList = yearPlansList;
    }

    public List<WeekBasicInfoDTO> getWeekBasicInfoDTOList() {
        return weekBasicInfoDTOList;
    }

    public void setWeekBasicInfoDTOList(List<WeekBasicInfoDTO> weekBasicInfoDTOList) {
        this.weekBasicInfoDTOList = weekBasicInfoDTOList;
    }
}
