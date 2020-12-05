package com.pk.ms.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Summary {

    private int fulfilledAmount;

    private int failedAmount;

    private int percentagePlansRating;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonIgnore
    protected Schedule schedule;

    public Summary() {
    }

    public Summary(Schedule schedule) {
        this.schedule = schedule;
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

    public int getPercentagePlansRating() {
        return percentagePlansRating;
    }

    public void setPercentagePlansRating(int percentagePlansRating) {
        this.percentagePlansRating = percentagePlansRating;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public abstract void countSummary();
}

