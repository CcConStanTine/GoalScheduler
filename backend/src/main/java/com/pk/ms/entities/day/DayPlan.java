package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.Plan;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class DayPlan extends Plan<Time> {
    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dayplan_seq")
    @SequenceGenerator(name = "dayplan_seq", sequenceName = "dayplan_seq", allocationSize = 1)
    private long dayPlanId;

    // foreign key
    @ManyToOne
    @JoinColumn(name="day_id")
    @JsonIgnore
    private Day day;

    public DayPlan() {

    }

    public DayPlan(String content, Time startDate, Time endDate, Day day) {
        super(content, startDate, endDate);
        this.day = day;
    }

    public long getDayPlanId() {
        return dayPlanId;
    }

    public void setDayPlanId(long dayPlanId) {
        this.dayPlanId = dayPlanId;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
