package com.pk.ms.abstracts;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class Plan <T> {

    @NotNull
    @Size(max = 200)
    private String content;

    @NotNull
    private T startDate;

    @NotNull
    private T endDate;

    private boolean isFulfilled;

    public Plan() {
    }

    public Plan(String content, T startDate, T endDate) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFulfilled = false;
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

    public boolean isFulfilled() {
        return isFulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        isFulfilled = fulfilled;
    }
}

