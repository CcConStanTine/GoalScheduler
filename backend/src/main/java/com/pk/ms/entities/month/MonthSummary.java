package com.pk.ms.entities.month;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Summary;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;

@Entity
public class MonthSummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "month_summary_seq")
    @SequenceGenerator(name = "month_summary_seq", sequenceName = "month_summary_seq", allocationSize = 1)
    private Long monthSummaryId;

    @ManyToOne
    @JoinColumn(name = "month_id")
    @JsonIgnore
    private Month month;

    public MonthSummary() {
    }

    public MonthSummary(Schedule schedule, Month month) {
        super(schedule);
        this.month = month;
        countSummary();
    }

    public Long getMonthSummaryId() {
        return monthSummaryId;
    }

    public void setMonthSummaryId(Long monthSummaryId) {
        this.monthSummaryId = monthSummaryId;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public void countSummary() {
        int fulfilled=0, failed=0;
        double maxSum=0, actualSum=0;
        for (MonthPlan monthPlan : schedule.getParticularMonthPlansList(month.getMonthId())) {
            if(monthPlan.isFulfilled()) {
                fulfilled++;
                switch(monthPlan.getImportance()) {
                    case NOTIMPORTANT: {
                        maxSum+=7;
                        actualSum+=7;
                    } break;
                    case REGULAR: {
                        maxSum+=18;
                        actualSum+=18;
                    } break;
                    case IMPORTANT: {
                        maxSum+=40;
                        actualSum+=40;
                    } break;
                    case VERYIMPORTANT: {
                        maxSum+=58;
                        actualSum+=58;
                    } break;
                }
            }
            else {
                failed++;
                switch(monthPlan.getImportance()) {
                    case NOTIMPORTANT: {
                        maxSum+=7;
                    } break;
                    case REGULAR: {
                        maxSum+=18;
                    } break;
                    case IMPORTANT: {
                        maxSum+=40;
                    } break;
                    case VERYIMPORTANT: {
                        maxSum+=58;
                    } break;
                }
            }

        }
        double percentagePlansRating = 0;
        if(maxSum!=0)
            percentagePlansRating = actualSum/maxSum * 100;
        setFulfilledAmount(fulfilled);
        setFailedAmount(failed);
        setPercentagePlansRating((int)percentagePlansRating);
    }
}
