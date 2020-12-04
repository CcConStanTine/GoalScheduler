package com.pk.ms.entities.week;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Summary;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;

@Entity
public class WeekSummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "week_summary_seq")
    @SequenceGenerator(name = "week_summary_seq", sequenceName = "week_summary_seq", allocationSize = 1)
    private Long weekSummaryId;

    @ManyToOne
    @JoinColumn(name = "week_id")
    @JsonIgnore
    private Week week;

    public WeekSummary() {
    }

    public WeekSummary(Schedule schedule, Week week) {
        super(schedule);
        this.week = week;
        countFulfilledAmount();
        countFailedAmount();
    }

    public Long getWeekSummaryId() {
        return weekSummaryId;
    }

    public void setWeekSummaryId(Long weekSummaryId) {
        this.weekSummaryId = weekSummaryId;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public void countFulfilledAmount() {
        int fulfilled=0;
        for (WeekPlan weekPlan : schedule.getParticularWeekPlansList(week.getWeekId())) {
            if(weekPlan.isFulfilled())
                fulfilled++;
        }
        setFulfilledAmount(fulfilled);
    }

    @Override
    public void countFailedAmount() {
        int failed=0;
        for (WeekPlan weekPlan : schedule.getParticularWeekPlansList(week.getWeekId())) {
            if(!weekPlan.isFulfilled())
                failed++;
        }
        setFailedAmount(failed);    }
}
