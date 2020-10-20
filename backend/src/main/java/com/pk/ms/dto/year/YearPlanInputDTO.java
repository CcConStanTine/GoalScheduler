package com.pk.ms.dto.year;

import com.pk.ms.entities.Plan;

import java.sql.Date;

// YearPlan DTO for purpose of creating, and updating some of YearPlan attributes
public class YearPlanInputDTO extends Plan<Date> {

    public YearPlanInputDTO() {
    }

    public YearPlanInputDTO(String content, Date startDate, Date endDate) {
        super(content, startDate, endDate);
    }
}
