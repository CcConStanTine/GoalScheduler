package com.pk.ms.dto.week;

import com.pk.ms.abstracts.Plan;

import java.time.LocalDate;

public class WeekPlanInputDTO extends Plan<LocalDate> {

    public WeekPlanInputDTO() {
    }

    public WeekPlanInputDTO(String content, LocalDate startDate, LocalDate endDate) {
        super(content, startDate, endDate);
    }
}
