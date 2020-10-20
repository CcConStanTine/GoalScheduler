package com.pk.ms.entities.year;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class YearSummary {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yearsummary_seq")
    @SequenceGenerator(name = "yearsummary_seq", sequenceName = "yearsummary_seq", allocationSize = 1)
    private long yearSumId;

    private int fulfilledAmount;

    private int failedAmount;

    // foreign key
    @OneToOne
    @JoinColumn(name = "year_id")
    @JsonIgnore
    private Year year;

    public YearSummary() {

    }

    public YearSummary(int fulfilledAmount, int failedAmount, Year year) {
        this.fulfilledAmount = fulfilledAmount;
        this.failedAmount = failedAmount;
        this.year = year;
    }

    public long getYearSumId() {
        return yearSumId;
    }

    public void setYearSumId(long yearSumId) {
        this.yearSumId = yearSumId;
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

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
