package com.pk.ms.abstracts;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class PlanDTO<T> {

    @NotNull
    @Size(max = 200)
    private String content;

    @NotNull
    private T startDate;

    @NotNull
    private T endDate;

    private Importance importance;

    private Urgency urgency;

    private boolean isFulfilled;

    public PlanDTO() {
    }

    public PlanDTO(@NotNull @Size(max = 200) String content, @NotNull T startDate, @NotNull T endDate,
                   Importance importance, Urgency urgency, boolean isFulfilled) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.importance = importance;
        this.urgency = urgency;
        this.isFulfilled = isFulfilled;
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
}

