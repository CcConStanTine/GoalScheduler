package com.pk.ms.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PlanEntity <T> {

    private String content;

    private T startDate;

    private T endDate;

    private Importance importance;

    private Urgency urgency;

    private boolean isFulfilled;

    @ManyToOne
    @JoinColumn(name="schedule_id")
    @JsonIgnore
    private Schedule schedule;

    public PlanEntity() {
    }

    public PlanEntity(String content, T startDate, T endDate, Importance importance,
                      Urgency urgency, Schedule schedule) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.importance = importance;
        this.urgency = urgency;
        this.schedule = schedule;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public T getStartDate() {
        return startDate;
    }

    public void setStartDate(T startDate) {
        this.startDate = startDate;
    }

    public T getEndDate() {
        return endDate;
    }

    public void setEndDate(T endDate) {
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

    public boolean isFulfilled() {
        return isFulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        isFulfilled = fulfilled;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
