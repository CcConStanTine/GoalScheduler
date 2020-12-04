package com.pk.ms.entities.week;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.PlanDTO;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class WeekPlan extends PlanDTO<LocalDate> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "week_plan_seq")
    @SequenceGenerator(name = "week_plan_seq", sequenceName = "week_plan_seq", allocationSize = 1)
    private Long weekPlanId;

    @ManyToOne
    @JoinColumn(name = "week_id")
    @JsonIgnore
    private Week week;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonIgnore
    private Schedule schedule;

    public WeekPlan() {
    }

    public WeekPlan(String content, LocalDate startDate, LocalDate endDate, Week week, Schedule schedule) {
        super(content, startDate, endDate);
        this.week = week;
        this.schedule = schedule;
    }

    public Long getWeekPlanId() {
        return weekPlanId;
    }

    public void setWeekPlanId(Long weekPlanId) {
        this.weekPlanId = weekPlanId;
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
}
