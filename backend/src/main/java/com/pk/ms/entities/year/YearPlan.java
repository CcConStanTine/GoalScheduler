package com.pk.ms.entities.year;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.Plan;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class YearPlan extends Plan<Date> {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yearplan_seq")
    @SequenceGenerator(name = "yearplan_seq", sequenceName = "yearplan_seq", allocationSize = 1)
    private long yearPlanId;

    // foreign key
    @ManyToOne
    @JoinColumn(name="year_id")
    @JsonIgnore
    private Year year;

    public YearPlan() {

    }

    public YearPlan(String content, Date startDate, Date endDate, Year year) {
        super(content, startDate, endDate);
        this.year = year;
    }

    public long getYearPlanId() {
        return yearPlanId;
    }

    public void setYearPlanId(long yearPlanId) {
        this.yearPlanId = yearPlanId;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
