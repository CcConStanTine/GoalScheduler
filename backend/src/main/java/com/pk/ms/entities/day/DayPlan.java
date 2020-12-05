package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.PlanDTO;
import com.pk.ms.abstracts.PlanEntity;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class DayPlan extends PlanEntity<LocalTime> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_plan_seq")
    @SequenceGenerator(name = "day_plan_seq", sequenceName = "day_plan_seq", allocationSize = 1)
    private Long dayPlanId;

    @ManyToOne
    @JoinColumn(name = "day_id")
    @JsonIgnore
    private Day day;

    public DayPlan() {
    }

    public DayPlan(String content, LocalTime startDate, LocalTime endDate, Schedule schedule, Day day) {
        super(content, startDate, endDate, schedule);
        this.day = day;
    }

    public Long getDayPlanId() {
        return dayPlanId;
    }

    public void setDayPlanId(Long dayPlanId) {
        this.dayPlanId = dayPlanId;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
