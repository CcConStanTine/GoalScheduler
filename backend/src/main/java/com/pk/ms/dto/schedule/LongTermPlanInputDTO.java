package com.pk.ms.dto.schedule;

import com.pk.ms.entities.Plan;

import java.sql.Date;

// LongTermPlan DTO for purpose of creating, and updating some of LongTermPlan attributes
public class LongTermPlanInputDTO extends Plan<Date> {

    public LongTermPlanInputDTO() {
    }

    public LongTermPlanInputDTO(String content, Date startDate, Date endDate) {
        super(content, startDate, endDate);
    }
}
