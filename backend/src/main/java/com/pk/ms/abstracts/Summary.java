package com.pk.ms.abstracts;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Summary {

    private int fulfilledAmount;

    private int failedAmount;

    public Summary() {
    }

    public int getFulfilledAmount() {
        return fulfilledAmount;
    }

    public void setFulfilledAmount(int fulfilledAmount) {
        this.fulfilledAmount = fulfilledAmount;
    }

    public int getFailedAmount() {
        return failedAmount;
    }

    public void setFailedAmount(int failedAmount) {
        this.failedAmount = failedAmount;
    }

    public abstract void countFulfilledAmount();

    public abstract void countFailedAmount();
}

