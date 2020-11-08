package com.pk.ms.entities.year;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Plan;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class YearPlan extends Plan<LocalDate> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "year_plan_seq")
    @SequenceGenerator(name = "year_plan_seq", sequenceName = "year_plan_seq", allocationSize = 1)
    private Long yearPlanId;

    @ManyToOne
    @JoinColumn(name = "year_id")
    @JsonIgnore
    private Year year;

    @ManyToOne
    @JoinColumn(name="schedule_id")
    @JsonIgnore
    private Schedule schedule;

    public YearPlan() {
    }

    public YearPlan(String content, LocalDate startDate, LocalDate endDate, Year year, Schedule schedule) {
        super(content, startDate, endDate);
        this.year = year;
        this.schedule = schedule;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
