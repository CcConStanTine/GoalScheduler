package com.pk.ms.dto.day;

import com.pk.ms.entities.Plan;

import java.sql.Time;

// DayPlan DTO for purpose of creating, and updating some of DayPlan attributes
public class DayPlanInputDTO extends Plan<Time> {

    public DayPlanInputDTO() {
    }

    public DayPlanInputDTO(String content, Time startDate, Time endDate) {
        super(content, startDate, endDate);
    }
}

