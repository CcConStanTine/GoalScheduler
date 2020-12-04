package com.pk.ms.entities.month;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.PlanDTO;
import com.pk.ms.abstracts.PlanEntity;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class MonthPlan extends PlanEntity<LocalDate> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "month_plan_seq")
    @SequenceGenerator(name = "month_plan_seq", sequenceName = "month_plan_seq", allocationSize = 1)
    private Long monthPlanId;

    @ManyToOne
    @JoinColumn(name = "month_id")
    @JsonIgnore
    private Month month;

    public MonthPlan() {
    }

    public MonthPlan(String content, LocalDate startDate, LocalDate endDate, Importance importance,
                     Urgency urgency, Schedule schedule, Month month) {
        super(content, startDate, endDate, importance, urgency, schedule);
        this.month = month;
    }

    public MonthPlan(String content, LocalDate startDate, LocalDate endDate, Schedule schedule, Month month) {
        super(content, startDate, endDate, schedule);
        this.month = month;
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
}
