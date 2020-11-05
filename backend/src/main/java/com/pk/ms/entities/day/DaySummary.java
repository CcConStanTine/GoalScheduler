package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Summary;
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

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonIgnore
    private Schedule schedule;

    public DaySummary() {
    }

    public DaySummary(Day day, Schedule schedule) {
        this.day = day;
        this.schedule = schedule;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
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
        setFailedAmount(schedule.getParticularDayPlansList(this.day.getDayId()).size() - getFulfilledAmount());
    }
}