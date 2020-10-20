package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class DaySummary {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daysummary_seq")
    @SequenceGenerator(name = "daysummary_seq", sequenceName = "daysummary_seq", allocationSize = 1)
    private long daySumId;

    private int fulfilledAmount;

    private int failedAmount;

    // foreign key
    @OneToOne
    @JoinColumn(name = "day_id")
    @JsonIgnore
    private Day day;

    public DaySummary() {
    }

    public DaySummary(int fulfilledAmount, int failedAmount, Day day) {
        this.fulfilledAmount = fulfilledAmount;
        this.failedAmount = failedAmount;
        this.day = day;
    }

    public long getDaySumId() {
        return daySumId;
    }

    public void setDaySumId(long daySumId) {
        this.daySumId = daySumId;
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

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
