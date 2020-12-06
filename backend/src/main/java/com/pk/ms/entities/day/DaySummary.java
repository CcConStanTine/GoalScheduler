package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Summary;
import com.pk.ms.constants.Importance;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;

@Entity
public class DaySummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_summary_seq")
    @SequenceGenerator(name = "day_summary_seq", sequenceName = "day_summary_seq", allocationSize = 1)
    private Long daySummaryId;

    @ManyToOne
    @JoinColumn(name = "day_id")
    @JsonIgnore
    private Day day;

    public DaySummary() {
    }

    public DaySummary(Schedule schedule, Day day) {
        super(schedule);
        this.day = day;
        countSummary();
    }

    public Long getDaySummaryId() {
        return daySummaryId;
    }

    public void setDaySummaryId(Long daySummaryId) {
        this.daySummaryId = daySummaryId;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public void countSummary() {
        int fulfilled=0, failed=0;
        double maxSum=0, actualSum=0;
        for (DayPlan dayPlan : schedule.getParticularDayPlansList(day.getDayId())) {
            if(dayPlan.isFulfilled()) {
                fulfilled++;
                switch(dayPlan.getImportance()) {
                    case NOTIMPORTANT: {
                        maxSum+= Importance.NOTIMPORTANT.getDayImportanceWeight();
                        actualSum+=Importance.NOTIMPORTANT.getDayImportanceWeight();
                    } break;
                    case REGULAR: {
                        maxSum+= Importance.REGULAR.getDayImportanceWeight();
                        actualSum+=Importance.REGULAR.getDayImportanceWeight();
                    } break;
                    case IMPORTANT: {
                        maxSum+= Importance.IMPORTANT.getDayImportanceWeight();
                        actualSum+=Importance.IMPORTANT.getDayImportanceWeight();
                    } break;
                    case VERYIMPORTANT: {
                        maxSum+= Importance.VERYIMPORTANT.getDayImportanceWeight();
                        actualSum+=Importance.VERYIMPORTANT.getDayImportanceWeight();
                    } break;
                }
            }
            else {
                failed++;
                switch(dayPlan.getImportance()) {
                    case NOTIMPORTANT: {
                        maxSum+= Importance.NOTIMPORTANT.getDayImportanceWeight();
                    } break;
                    case REGULAR: {
                        maxSum+= Importance.REGULAR.getDayImportanceWeight();
                    } break;
                    case IMPORTANT: {
                        maxSum+= Importance.IMPORTANT.getDayImportanceWeight();
                    } break;
                    case VERYIMPORTANT: {
                        maxSum+= Importance.VERYIMPORTANT.getDayImportanceWeight();
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