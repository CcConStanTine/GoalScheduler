package com.pk.ms.entities.month;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Plan;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class MonthPlan extends Plan<LocalDate> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "month_plan_seq")
    @SequenceGenerator(name = "month_plan_seq", sequenceName = "month_plan_seq", allocationSize = 1)
    private Long monthPlanId;

    @ManyToOne
    @JoinColumn(name = "month_id")
    @JsonIgnore
    private Month month;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonIgnore
    private Schedule schedule;

    public MonthPlan() {
    }

    public MonthPlan(String content, LocalDate startDate, LocalDate endDate, Month month, Schedule schedule) {
        super(content, startDate, endDate);
        this.month = month;
        this.schedule = schedule;
    }

    public Long getMonthPlanId() {
        return monthPlanId;
    }

    public void setMonthPlanId(Long monthPlanId) {
        this.monthPlanId = monthPlanId;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
