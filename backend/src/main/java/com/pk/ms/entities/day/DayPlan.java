package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Plan;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class DayPlan extends Plan<LocalTime> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_plan_seq")
    @SequenceGenerator(name = "day_plan_seq", sequenceName = "day_plan_seq", allocationSize = 1)
    private Long dayPlanId;

    @ManyToOne
    @JoinColumn(name = "day_id")
    @JsonIgnore
    private Day day;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonIgnore
    private Schedule schedule;

    public DayPlan() {
    }

    public DayPlan(String content, LocalTime startDate, LocalTime endDate, Day day, Schedule schedule) {
        super(content, startDate, endDate);
        this.day = day;
        this.schedule = schedule;
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
