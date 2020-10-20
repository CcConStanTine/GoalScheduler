package com.pk.ms.entities.week;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class WeekSummary {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weeksummary_seq")
    @SequenceGenerator(name = "weeksummary_seq", sequenceName = "weeksummary_seq", allocationSize = 1)
    private long weekSumId;

    private int fulfilledAmount;

    private int failedAmount;

    // foreign key
    @OneToOne
    @JoinColumn(name = "week_id")
    @JsonIgnore
    private Week week;

    public WeekSummary() {

    }

    public WeekSummary(int fulfilledAmount, int failedAmount, Week week) {
        this.fulfilledAmount = fulfilledAmount;
        this.failedAmount = failedAmount;
        this.week = week;
    }

    public long getWeekSumId() {
        return weekSumId;
    }

    public void setWeekSumId(long weekSumId) {
        this.weekSumId = weekSumId;
    }

    public int getFulfilledAmount() {
        return fulfilledAmount;
    }

    public void setFulfilledAmount(int fulfilledAmount) {
        this.fulfilledAmount = fulfilledAmount;
    }

    public int getFailedAmount() {
        return failedAmount;
    }

    public void setFailedAmount(int failedAmount) {
        this.failedAmount = failedAmount;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }
}
