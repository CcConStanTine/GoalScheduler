package com.pk.ms.entities.year;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.week.Week;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Year {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "year_seq")
    @SequenceGenerator(name = "year_seq", sequenceName = "year_seq", allocationSize = 1)
    private long yearId;

    private int yearNumber;

    private boolean isLeapYear;

    private int daysAmount;

    // foreign key
    @ManyToOne
    @JoinColumn(name="schedule_id")
    @JsonIgnore
    private Schedule schedule;

    @OneToMany(mappedBy = "year")
    private List<Month> monthsList;

    @OneToMany(mappedBy = "year")
    private List<Week> weeksList;

    @OneToMany(mappedBy = "year")
    private List<YearPlan> yearPlansList;

    @OneToOne(mappedBy = "year")
    private YearSummary yearSummary;

    public Year() {

    }

    public Year(int yearNumber, Schedule schedule) {
        this.yearNumber = yearNumber;
        setLeapYear();
        setDaysAmount();
        this.schedule = schedule;
        this.monthsList = new ArrayList<Month>();
        this.weeksList = new ArrayList<Week>();
        this.yearPlansList = new ArrayList<YearPlan>();
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

    private void setLeapYear() {
        if(this.yearNumber % 4 == 0)
        {
            if(this.yearNumber % 100 == 0)
            {
                // year is divisible by 400, hence the year is a leap year
                if (this.yearNumber % 400 == 0)
                    this.isLeapYear = true;
                else
                    this.isLeapYear = false;
            }
            else
                this.isLeapYear = true;
        }
        else
            this.isLeapYear = false;
    }

    public int getDaysAmount() {
        return daysAmount;
    }

    private void setDaysAmount() {
        if(this.isLeapYear)
            this.daysAmount=366;
        else
            this.daysAmount=365;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Month> getMonthsList() {
        return monthsList;
    }

    public void setMonthsList(List<Month> monthsList) {
        this.monthsList = monthsList;
    }

    public List<Week> getWeeksList() {
        return weeksList;
    }

    public void setWeeksList(List<Week> weeksList) {
        this.weeksList = weeksList;
    }

    public List<YearPlan> getYearPlansList() {
        return yearPlansList;
    }

    public void setYearPlansList(List<YearPlan> yearPlansList) {
        this.yearPlansList = yearPlansList;
    }

    public YearSummary getYearSummary() {
        return yearSummary;
    }

    public void setYearSummary(YearSummary yearSummary) {
        this.yearSummary = yearSummary;
    }
}
