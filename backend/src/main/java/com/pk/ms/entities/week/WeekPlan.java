package com.pk.ms.entities.week;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.Plan;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class WeekPlan extends Plan<Date> {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weekplan_seq")
    @SequenceGenerator(name = "weekplan_seq", sequenceName = "weekplan_seq", allocationSize = 1)
    private long weekPlanId;

    // foreign key
    @ManyToOne
    @JoinColumn(name="week_id")
    @JsonIgnore
    private Week week;

    public WeekPlan() {

    }

    public WeekPlan(String content, Date startDate, Date endDate, Week week) {
        super(content, startDate, endDate);
        this.week = week;
    }

    public long getWeekPlanId() {
        return weekPlanId;
    }

    public void setWeekPlanId(long weekPlanId) {
        this.weekPlanId = weekPlanId;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }
}
