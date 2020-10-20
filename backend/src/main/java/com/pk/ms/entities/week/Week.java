package com.pk.ms.entities.week;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.year.Year;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Week {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "week_seq")
    @SequenceGenerator(name = "week_seq", sequenceName = "week_seq", allocationSize = 1)
    private long weekId;

    private Date startDate;

    private Date endDate;

    // foreign key
    @ManyToOne
    @JoinColumn(name="year_id")
    @JsonIgnore
    private Year year;

    @OneToMany(mappedBy = "week")
    private List<Day> dayList;

    @OneToMany(mappedBy = "week")
    private List<WeekPlan> weekPlansList;

    @OneToOne(mappedBy = "week")
    private WeekSummary weekSummary;

    public Week() {

    }

    public Week(Date startDate, Date endDate, Year year) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.year = year;
        this.dayList = new ArrayList<Day>();
        this.weekPlansList = new ArrayList<WeekPlan>();
    }

    public long getWeekId() {
        return weekId;
    }

    public void setWeekId(long weekId) {
        this.weekId = weekId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public List<WeekPlan> getWeekPlansList() {
        return weekPlansList;
    }

    public void setWeekPlansList(List<WeekPlan> weekPlansList) {
        this.weekPlansList = weekPlansList;
    }
}
