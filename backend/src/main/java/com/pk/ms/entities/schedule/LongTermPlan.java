package com.pk.ms.entities.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Plan;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class LongTermPlan extends Plan<LocalDate> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "long_term_plan_seq")
    @SequenceGenerator(name = "long_term_plan_seq", sequenceName = "long_term_plan_seq", allocationSize = 1)
    private Long longTermPlanId;

    @ManyToOne
    @JoinColumn(name="schedule_id")
    @JsonIgnore
    private Schedule schedule;

    public LongTermPlan() {
    }

    public LongTermPlan(String content, LocalDate startDate, LocalDate endDate, Schedule schedule) {
        super(content, startDate, endDate);
        this.schedule = schedule;
    }

    public Long getLongTermPlanId() {
        return longTermPlanId;
    }

    public void setLongTermPlanId(Long longTermPlanId) {
        this.longTermPlanId = longTermPlanId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}

