package com.pk.ms.entities.year;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Summary;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;

@Entity
public class YearSummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "year_summary_seq")
    @SequenceGenerator(name = "year_summary_seq", sequenceName = "year_summary_seq", allocationSize = 1)
    private Long yearSummaryId;

    @ManyToOne
    @JoinColumn(name = "year_id")
    @JsonIgnore
    private Year year;

    public YearSummary() {
    }

    public YearSummary(Schedule schedule, Year year) {
        super(schedule);
        this.year = year;
    }

    public Long getYearSummaryId() {
        return yearSummaryId;
    }

    public void setYearSummaryId(Long yearSummaryId) {
        this.yearSummaryId = yearSummaryId;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    @Override
    public void countFulfilledAmount() {
        int fulfilled=0;
        for (YearPlan yearPlan : schedule.getParticularYearPlansList(year.getYearId())) {
            if(yearPlan.isFulfilled())
                fulfilled++;
        }
        setFulfilledAmount(fulfilled);
    }

    @Override
    public void countFailedAmount() {
        int failed=0;
        for (YearPlan yearPlan : schedule.getParticularYearPlansList(year.getYearId())) {
            if(!yearPlan.isFulfilled())
                failed++;
        }
        setFailedAmount(failed);
    }
}
