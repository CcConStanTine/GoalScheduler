package com.pk.ms.entities.schedule;

import com.pk.ms.abstracts.PlanEntity;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class LongTermPlan extends PlanEntity<LocalDate> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "long_term_plan_seq")
    @SequenceGenerator(name = "long_term_plan_seq", sequenceName = "long_term_plan_seq", allocationSize = 1)
    private Long longTermPlanId;

    public LongTermPlan() {
    }

    public LongTermPlan(String content, LocalDate startDate, LocalDate endDate, Importance importance,
                        Urgency urgency, Schedule schedule) {
        super(content, startDate, endDate, importance, urgency, schedule);
    }

    public LongTermPlan(String content, LocalDate startDate, LocalDate endDate, Schedule schedule) {
        super(content, startDate, endDate, schedule);
    }

    public Long getLongTermPlanId() {
        return longTermPlanId;
    }

    public void setLongTermPlanId(Long longTermPlanId) {
        this.longTermPlanId = longTermPlanId;
    }
}

