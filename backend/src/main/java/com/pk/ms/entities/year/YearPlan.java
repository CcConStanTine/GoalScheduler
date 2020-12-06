package com.pk.ms.entities.year;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.PlanEntity;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class YearPlan extends PlanEntity<LocalDate> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "year_plan_seq")
    @SequenceGenerator(name = "year_plan_seq", sequenceName = "year_plan_seq", allocationSize = 1)
    private Long yearPlanId;

    @ManyToOne
    @JoinColumn(name = "year_id")
    @JsonIgnore
    private Year year;

    public YearPlan() {
    }

    public YearPlan(String content, LocalDate startDate, LocalDate endDate, Schedule schedule, Year year) {
        super(content, startDate, endDate, schedule);
        this.year = year;
    }

    public Long getYearPlanId() {
        return yearPlanId;
    }

    public void setYearPlanId(Long yearPlanId) {
        this.yearPlanId = yearPlanId;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
