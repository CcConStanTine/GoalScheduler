package com.pk.ms.entities.month;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Summary;
import com.pk.ms.entities.schedule.Schedule;
import com.pk.ms.entities.year.YearPlan;

import javax.persistence.*;

@Entity
public class MonthSummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "month_summary_seq")
    @SequenceGenerator(name = "month_summary_seq", sequenceName = "month_summary_seq", allocationSize = 1)
    private Long monthSummaryId;

    @ManyToOne
    @JoinColumn(name = "month_id")
    @JsonIgnore
    private Month month;

    public MonthSummary() {
    }

    public MonthSummary(Schedule schedule, Month month) {
        super(schedule);
        this.month = month;
        countFulfilledAmount();
        countFailedAmount();
    }

    public Long getMonthSummaryId() {
        return monthSummaryId;
    }

    public void setMonthSummaryId(Long monthSummaryId) {
        this.monthSummaryId = monthSummaryId;
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

    @Override
    public void countFulfilledAmount() {
        int fulfilled=0;
        for (MonthPlan monthPlan : schedule.getParticularMonthPlansList(month.getMonthId())) {
            if(monthPlan.isFulfilled())
                fulfilled++;
        }
        setFulfilledAmount(fulfilled);
    }

    @Override
    public void countFailedAmount() {
        int failed=0;
        for (MonthPlan monthPlan : schedule.getParticularMonthPlansList(month.getMonthId())) {
            if(!monthPlan.isFulfilled())
                failed++;
        }
        setFailedAmount(failed);
    }
}
