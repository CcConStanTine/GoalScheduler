package com.pk.ms.dto.year;

// Year DTO for purpose of storing the fields needed while displaying the Years List
public class YearBasicInfoDTO {

    private long yearId;
    private int yearNumber;
    private boolean isLeapYear;
    private int daysAmount;

    public YearBasicInfoDTO() {
    }

    public YearBasicInfoDTO(long yearId, int yearNumber, boolean isLeapYear, int daysAmount) {
        this.yearId = yearId;
        this.yearNumber = yearNumber;
        this.isLeapYear = isLeapYear;
        this.daysAmount = daysAmount;
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
}
