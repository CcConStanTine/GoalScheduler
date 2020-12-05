package com.pk.ms.entities.week;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Summary;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;

@Entity
public class WeekSummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "week_summary_seq")
    @SequenceGenerator(name = "week_summary_seq", sequenceName = "week_summary_seq", allocationSize = 1)
    private Long weekSummaryId;

    @ManyToOne
    @JoinColumn(name = "week_id")
    @JsonIgnore
    private Week week;

    public WeekSummary() {
    }

    public WeekSummary(Schedule schedule, Week week) {
        super(schedule);
        this.week = week;
        countSummary();
    }

    public Long getWeekSummaryId() {
        return weekSummaryId;
    }

    public void setWeekSummaryId(Long weekSummaryId) {
        this.weekSummaryId = weekSummaryId;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
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
        for (WeekPlan weekPlan : schedule.getParticularWeekPlansList(week.getWeekId())) {
            if(weekPlan.isFulfilled()) {
                fulfilled++;
                switch(weekPlan.getImportance()) {
                    case NOTIMPORTANT: {
                        maxSum+=8;
                        actualSum+=8;
                    } break;
                    case REGULAR: {
                        maxSum+=17;
                        actualSum+=17;
                    } break;
                    case IMPORTANT: {
                        maxSum+=38;
                        actualSum+=38;
                    } break;
                    case VERYIMPORTANT: {
                        maxSum+=55;
                        actualSum+=55;
                    } break;
                }
            }
            else {
                failed++;
                switch(weekPlan.getImportance()) {
                    case NOTIMPORTANT: {
                        maxSum+=8;
                    } break;
                    case REGULAR: {
                        maxSum+=17;
                    } break;
                    case IMPORTANT: {
                        maxSum+=38;
                    } break;
                    case VERYIMPORTANT: {
                        maxSum+=55;
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
