package com.pk.ms.entities.week;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.PlanEntity;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class WeekPlan extends PlanEntity<LocalDate> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "week_plan_seq")
    @SequenceGenerator(name = "week_plan_seq", sequenceName = "week_plan_seq", allocationSize = 1)
    private Long weekPlanId;

    @ManyToOne
    @JoinColumn(name = "week_id")
    @JsonIgnore
    private Week week;

    public WeekPlan() {
    }

    public WeekPlan(String content, LocalDate startDate, LocalDate endDate, Importance importance, Urgency urgency, Schedule schedule, Week week) {
        super(content, startDate, endDate, importance, urgency, schedule);
        this.week = week;
    }

    public WeekPlan(String content, LocalDate startDate, LocalDate endDate, Schedule schedule, Week week) {
        super(content, startDate, endDate, schedule);
        this.week = week;
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
}
