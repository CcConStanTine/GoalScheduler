package com.pk.ms.dto.week;

import com.pk.ms.entities.Plan;

import java.sql.Date;

// WeekPlan DTO for purpose of creating, and updating some of WeekPlan attributes
public class WeekPlanInputDTO extends Plan<Date> {

    public WeekPlanInputDTO() {
    }

    public WeekPlanInputDTO(String content, Date startDate, Date endDate) {
        super(content, startDate, endDate);
    }
}
