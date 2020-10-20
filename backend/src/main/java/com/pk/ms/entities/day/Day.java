package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.week.Week;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Day {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_seq")
    @SequenceGenerator(name = "day_seq", sequenceName = "day_seq", allocationSize = 1)
    private long dayId;

    private Date dayDate;

    private DayName dayName;

    // foreign key
    @ManyToOne
    @JoinColumn(name = "week_id")
    @JsonIgnore
    private Week week;

    // foreign key
    @ManyToOne
    @JoinColumn(name = "month_id")
    @JsonIgnore
    private Month month;

    @OneToMany(mappedBy = "day")
    private List<DayPlan> dayPlansList;

    @OneToOne(mappedBy = "day")
    private DaySummary daySummary;

    public Day() {

    }

    public Day(Date date, DayName dayName, Week week, Month month) {
        this.dayDate = date;
        this.dayName = dayName;
        this.week = week;
        this.month = month;
        this.dayPlansList = new ArrayList<DayPlan>();
    }

    public long getDayId() {
        return dayId;
    }

    public void setDayId(long dayId) {
        this.dayId = dayId;
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

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public List<DayPlan> getDayPlansList() {
        return dayPlansList;
    }

    public void setDayPlansList(List<DayPlan> dayPlansList) {
        this.dayPlansList = dayPlansList;
    }

    public DaySummary getDaySummary() {
        return daySummary;
    }

    public void setDaySummary(DaySummary daySummary) {
        this.daySummary = daySummary;
    }
}
