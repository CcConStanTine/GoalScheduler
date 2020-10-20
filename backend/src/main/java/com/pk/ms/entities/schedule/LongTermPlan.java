package com.pk.ms.entities.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.Plan;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class LongTermPlan extends Plan<Date> {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "longtermplan_seq")
    @SequenceGenerator(name = "longtermplan_seq", sequenceName = "longtermplan_seq", allocationSize = 1)
    private long longTermPlanId;

    // foreign key
    @ManyToOne
    @JoinColumn(name="schedule_id")
    @JsonIgnore
    private Schedule schedule;

    public LongTermPlan() {

    }

    public LongTermPlan(String content, Date startDate, Date endDate, Schedule schedule) {
        super(content, startDate, endDate);
        this.schedule = schedule;
    }

    public long getLongTermPlanId() {
        return longTermPlanId;
    }

    public void setLongTermPlanId(long longTermPlanId) {
        this.longTermPlanId = longTermPlanId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
