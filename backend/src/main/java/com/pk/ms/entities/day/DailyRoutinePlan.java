package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.entities.user.MyScheduleUser;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class DailyRoutinePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_routine_plan_seq")
    @SequenceGenerator(name = "daily_routine_plan_seq", sequenceName = "daily_routine_plan_seq", allocationSize = 1)
    private Long dailyRoutinePlanId;

    private String content;

    private LocalTime startDate;

    private LocalTime endDate;

    private Importance importance;

    private Urgency urgency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private MyScheduleUser user;

    public DailyRoutinePlan() {
    }

    public DailyRoutinePlan(String content, LocalTime startDate, LocalTime endDate, MyScheduleUser user) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.importance = Importance.REGULAR;
        this.urgency = Urgency.REGULAR;
        this.user = user;
    }

    public Long getDailyRoutinePlanId() {
        return dailyRoutinePlanId;
    }

    public void setDailyRoutinePlanId(Long dailyRoutinePlanId) {
        this.dailyRoutinePlanId = dailyRoutinePlanId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalTime startDate) {
        this.startDate = startDate;
    }

    public LocalTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalTime endDate) {
        this.endDate = endDate;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public MyScheduleUser getUser() {
        return user;
    }

    public void setUser(MyScheduleUser user) {
        this.user = user;
    }
}
