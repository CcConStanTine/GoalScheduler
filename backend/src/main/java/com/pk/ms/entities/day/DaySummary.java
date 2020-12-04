package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Summary;
import com.pk.ms.entities.month.MonthPlan;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;

@Entity
public class DaySummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_summary_seq")
    @SequenceGenerator(name = "day_summary_seq", sequenceName = "day_summary_seq", allocationSize = 1)
    private Long daySummaryId;

    @ManyToOne
    @JoinColumn(name = "day_id")
    @JsonIgnore
    private Day day;

    public DaySummary() {
    }

    public DaySummary(Schedule schedule, Day day) {
        super(schedule);
        this.day = day;
        countFulfilledAmount();
        countFailedAmount();
    }

    public Long getDaySummaryId() {
        return daySummaryId;
    }

    public void setDaySummaryId(Long daySummaryId) {
        this.daySummaryId = daySummaryId;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public void countFulfilledAmount() {
        int fulfilled=0;
        for (DayPlan dayPlan : schedule.getParticularDayPlansList(this.day.getDayId())) {
            if(dayPlan.isFulfilled())
                fulfilled++;
        }
        setFulfilledAmount(fulfilled);
    }

    @Override
    public void countFailedAmount() {
        int failed=0;
        for (DayPlan dayPlan : schedule.getParticularDayPlansList(day.getDayId())) {
            if(!dayPlan.isFulfilled())
                failed++;
        }
        setFailedAmount(failed);    }
}