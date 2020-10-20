package com.pk.ms.entities.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.user.MyScheduleUser;
import com.pk.ms.entities.year.Year;

import javax.persistence.*;
import java.util.List;

@Entity
public class Schedule {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_seq")
    @SequenceGenerator(name = "schedule_seq", sequenceName = "schedule_seq", allocationSize = 1)
    private long scheduleId;

    // foreign key
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private MyScheduleUser user;

    @OneToMany(mappedBy = "schedule")
    private List<Year> yearsList;

    @OneToMany(mappedBy = "schedule")
    private List<LongTermPlan> longTermPlansList;

    public Schedule() {

    }

    public Schedule(MyScheduleUser user, List<Year> yearsList, List<LongTermPlan> longTermPlansList) {
        this.user = user;
        this.yearsList = yearsList;
        this.longTermPlansList = longTermPlansList;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public MyScheduleUser getUser() {
        return user;
    }

    public void setUser(MyScheduleUser user) {
        this.user = user;
    }

    public List<Year> getYearsList() {
        return yearsList;
    }

    public void setYearsList(List<Year> yearsList) {
        this.yearsList = yearsList;
    }

    public List<LongTermPlan> getLongTermPlansList() {
        return longTermPlansList;
    }

    public void setLongTermPlansList(List<LongTermPlan> longTermPlansList) {
        this.longTermPlansList = longTermPlansList;
    }
}
