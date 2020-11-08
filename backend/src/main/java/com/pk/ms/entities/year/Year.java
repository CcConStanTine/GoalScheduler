package com.pk.ms.entities.year;

import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.week.Week;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "year_seq")
    @SequenceGenerator(name = "year_seq", sequenceName = "year_seq", allocationSize = 1)
    private Long yearId;

    private int yearNumber;

    private boolean isLeapYear;

    private int daysAmount;

    @OneToMany(mappedBy = "year")
    private List<Month> monthsList;

    @OneToMany(mappedBy = "year")
    private List<Week> weeksList;

    public Year() {
    }

    public Year(int yearNumber) {
        this.yearNumber = yearNumber;
        this.isLeapYear = setIfYearIsLeap(yearNumber);
        this.daysAmount = setDaysAmount(this.isLeapYear);
        this.monthsList = new ArrayList<>();
        this.weeksList = new ArrayList<>();
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public boolean isLeapYear() {
        return isLeapYear;
    }

    public int getDaysAmount() {
        return daysAmount;
    }

    public List<Month> getMonthsList() {
        return monthsList;
    }

    public List<Week> getWeeksList() {
        return weeksList;
    }

    private boolean setIfYearIsLeap(int yearNumber) {
        if(yearNumber % 4 == 0)
        {
            if(yearNumber % 100 == 0)
            {
                // year is divisible by 400, hence the year is a leap year
                if (yearNumber % 400 == 0)
                    return true;
                else
                    return false;
            }
            else
                return true;
        }
        else
            return false;
    }

    private int setDaysAmount(boolean isLeapYear) {
        if(isLeapYear)
            return 366;
        else
            return 365;
    }
}
