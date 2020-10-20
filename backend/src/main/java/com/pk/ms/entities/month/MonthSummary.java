package com.pk.ms.entities.month;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class MonthSummary {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monthsummary_seq")
    @SequenceGenerator(name = "monthsummary_seq", sequenceName = "monthsummary_seq", allocationSize = 1)
    private long monthSumId;

    private int fulfilledAmount;

    private int failedAmount;

    // foreign key
    @OneToOne
    @JoinColumn(name = "month_id")
    @JsonIgnore
    private Month month;

    public MonthSummary() {

    }

    public MonthSummary(int fulfilledAmount, int failedAmount, Month month) {
        this.fulfilledAmount = fulfilledAmount;
        this.failedAmount = failedAmount;
        this.month = month;
    }

    public long getMonthSumId() {
        return monthSumId;
    }

    public void setMonthSumId(long monthSumId) {
        this.monthSumId = monthSumId;
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

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}
