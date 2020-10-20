package com.pk.ms.dto.month;

import com.pk.ms.entities.Plan;

import java.sql.Date;

// MonthPlan DTO for purpose of creating, and updating some of MonthPlan attributes
public class MonthPlanInputDTO extends Plan<Date> {

    public MonthPlanInputDTO() {
    }

    public MonthPlanInputDTO(String content, Date startDate, Date endDate) {
        super(content, startDate, endDate);
    }
}
