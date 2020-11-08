package com.pk.ms.entities.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.day.DayPlan;
import com.pk.ms.entities.day.DaySummary;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.entities.month.MonthSummary;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.week.WeekPlan;
import com.pk.ms.entities.week.WeekSummary;
import com.pk.ms.entities.year.YearPlan;
import com.pk.ms.entities.year.YearSummary;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_seq")
    @SequenceGenerator(name = "schedule_seq", sequenceName = "schedule_seq", allocationSize = 1)
    private Long scheduleId;


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private MyScheduleUser user;


    @OneToMany(mappedBy = "schedule")
    private List<LongTermPlan> longTermPlansList;


    @OneToMany(mappedBy = "schedule")
    private List<YearPlan> yearPlansList;

    @OneToMany(mappedBy = "schedule")
    private List<YearSummary> yearSummariesList;


    @OneToMany(mappedBy = "schedule")
    private List<MonthPlan> monthPlansList;

    @OneToMany(mappedBy = "schedule")
    private List<MonthSummary> monthSummariesList;


    @OneToMany(mappedBy = "schedule")
    private List<WeekPlan> weekPlansList;

    @OneToMany(mappedBy = "schedule")
    private List<WeekSummary> weekSummariesList;


    @OneToMany(mappedBy = "schedule")
    private List<DayPlan> dayPlansList;

    @OneToMany(mappedBy = "schedule")
    private List<DaySummary> daySummariesList;

    public Schedule() {
    }

    public Schedule(MyScheduleUser user) {
        this.user = user;
        this.longTermPlansList = new ArrayList<>();
        this.yearPlansList = new ArrayList<>();
        this.yearSummariesList = new ArrayList<>();
        this.monthPlansList = new ArrayList<>();
        this.monthSummariesList = new ArrayList<>();
        this.weekPlansList = new ArrayList<>();
        this.weekSummariesList = new ArrayList<>();
        this.dayPlansList = new ArrayList<>();
        this.daySummariesList = new ArrayList<>();
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public MyScheduleUser getUser() {
        return user;
    }

    public void setUser(MyScheduleUser user) {
        this.user = user;
    }

    public List<LongTermPlan> getLongTermPlansList() {
        return longTermPlansList;
    }

    public void setLongTermPlansList(List<LongTermPlan> longTermPlansList) {
        this.longTermPlansList = longTermPlansList;
    }

    public List<YearPlan> getYearPlansList() {
        return yearPlansList;
    }

    public void setYearPlansList(List<YearPlan> yearPlansList) {
        this.yearPlansList = yearPlansList;
    }

    public List<YearSummary> getYearSummariesList() {
        return yearSummariesList;
    }

    public void setYearSummariesList(List<YearSummary> yearSummariesList) {
        this.yearSummariesList = yearSummariesList;
    }

    public List<MonthPlan> getMonthPlansList() {
        return monthPlansList;
    }

    public void setMonthPlansList(List<MonthPlan> monthPlansList) {
        this.monthPlansList = monthPlansList;
    }

    public List<MonthSummary> getMonthSummariesList() {
        return monthSummariesList;
    }

    public void setMonthSummariesList(List<MonthSummary> monthSummariesList) {
        this.monthSummariesList = monthSummariesList;
    }

    public List<WeekPlan> getWeekPlansList() {
        return weekPlansList;
    }

    public void setWeekPlansList(List<WeekPlan> weekPlansList) {
        this.weekPlansList = weekPlansList;
    }

    public List<WeekSummary> getWeekSummariesList() {
        return weekSummariesList;
    }

    public void setWeekSummariesList(List<WeekSummary> weekSummariesList) {
        this.weekSummariesList = weekSummariesList;
    }

    public List<DayPlan> getDayPlansList() {
        return dayPlansList;
    }

    public void setDayPlansList(List<DayPlan> dayPlansList) {
        this.dayPlansList = dayPlansList;
    }

    public List<DaySummary> getDaySummariesList() {
        return daySummariesList;
    }

    public void setDaySummariesList(List<DaySummary> daySummariesList) {
        this.daySummariesList = daySummariesList;
    }


    public void addLongTermPlan(LongTermPlan longTermPlan) {
        this.longTermPlansList.add(longTermPlan);
    }

    public void addYearPlan(YearPlan yearPlan) {
        this.yearPlansList.add(yearPlan);
    }
    public void addYearSummary(YearSummary yearSummary) {
        this.yearSummariesList.add(yearSummary);
    }

    public void addMonthPlan(MonthPlan monthPlan) {
        this.monthPlansList.add(monthPlan);
    }
    public void addMonthSummary(MonthSummary monthSummary) {
        this.monthSummariesList.add(monthSummary);
    }

    public void addWeekPlan(WeekPlan weekPlan) {
        this.weekPlansList.add(weekPlan);
    }
    public void addWeekSummary(WeekSummary weekSummary) {
        this.weekSummariesList.add(weekSummary);
    }

    public void addDayPlan(DayPlan dayPlan) {
        this.dayPlansList.add(dayPlan);
    }
    public void addDaySummary(DaySummary daySummary) {
        this.daySummariesList.add(daySummary);
    }


    public List<YearPlan> getParticularYearPlansList(long yearId) {
        List<YearPlan> yearPlansList = new LinkedList<>();
        for(YearPlan yearPlan : this.yearPlansList)
            if(yearPlan.getYear().getYearId() == yearId) {
                yearPlansList.add(yearPlan);
            }
        return yearPlansList;
    }

    public List<MonthPlan> getParticularMonthPlansList(long monthId) {
        List<MonthPlan> monthPlansList = new LinkedList<>();
        for(MonthPlan monthPlan : this.monthPlansList)
            if(monthPlan.getMonth().getMonthId() == monthId) {
                monthPlansList.add(monthPlan);
            }
        return monthPlansList;
    }

    public List<WeekPlan> getParticularWeekPlansList(long weekId) {
        List<WeekPlan> weekPlansList = new LinkedList<>();
        for(WeekPlan weekPlan : this.weekPlansList)
            if(weekPlan.getWeek().getWeekId() == weekId) {
                weekPlansList.add(weekPlan);
            }
        return weekPlansList;
    }

    public List<DayPlan> getParticularDayPlansList(long dayId) {
        List<DayPlan> dayPlansList = new LinkedList<>();
        for(DayPlan dayPlan : this.dayPlansList)
            if(dayPlan.getDay().getDayId() == dayId) {
                dayPlansList.add(dayPlan);
            }
        return dayPlansList;
    }
}
