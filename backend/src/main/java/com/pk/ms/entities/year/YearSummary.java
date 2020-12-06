package com.pk.ms.entities.year;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.abstracts.Summary;
import com.pk.ms.constants.Importance;
import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;

@Entity
public class YearSummary extends Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "year_summary_seq")
    @SequenceGenerator(name = "year_summary_seq", sequenceName = "year_summary_seq", allocationSize = 1)
    private Long yearSummaryId;

    @ManyToOne
    @JoinColumn(name = "year_id")
    @JsonIgnore
    private Year year;

    public YearSummary() {
    }

    public YearSummary(Schedule schedule, Year year) {
        super(schedule);
        this.year = year;
        countSummary();
    }

    public Long getYearSummaryId() {
        return yearSummaryId;
    }

    public void setYearSummaryId(Long yearSummaryId) {
        this.yearSummaryId = yearSummaryId;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    @Override
    public void countSummary() {
        int fulfilled=0, failed=0;
        double maxSum=0, actualSum=0;
        for (YearPlan yearPlan : schedule.getParticularYearPlansList(year.getYearId())) {
            if(yearPlan.isFulfilled()) {
                fulfilled++;
                switch(yearPlan.getImportance()) {
                    case NOTIMPORTANT: {
                        maxSum+= Importance.NOTIMPORTANT.getYearImportanceWeight();
                        actualSum+=Importance.NOTIMPORTANT.getYearImportanceWeight();
                    } break;
                    case REGULAR: {
                        maxSum+=Importance.REGULAR.getYearImportanceWeight();
                        actualSum+=Importance.REGULAR.getYearImportanceWeight();
                    } break;
                    case IMPORTANT: {
                        maxSum+=Importance.IMPORTANT.getYearImportanceWeight();
                        actualSum+=Importance.IMPORTANT.getYearImportanceWeight();
                    } break;
                    case VERYIMPORTANT: {
                        maxSum+=Importance.VERYIMPORTANT.getYearImportanceWeight();
                        actualSum+=Importance.VERYIMPORTANT.getYearImportanceWeight();
                    } break;
                }
            }
            else {
                failed++;
                switch(yearPlan.getImportance()) {
                    case NOTIMPORTANT: {
                        maxSum+= Importance.NOTIMPORTANT.getYearImportanceWeight();
                    } break;
                    case REGULAR: {
                        maxSum+=Importance.REGULAR.getYearImportanceWeight();
                    } break;
                    case IMPORTANT: {
                        maxSum+=Importance.IMPORTANT.getYearImportanceWeight();
                    } break;
                    case VERYIMPORTANT: {
                        maxSum+=Importance.VERYIMPORTANT.getYearImportanceWeight();
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
