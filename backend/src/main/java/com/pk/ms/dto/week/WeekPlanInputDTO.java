package com.pk.ms.dto.week;

import com.pk.ms.abstracts.PlanDTO;

import java.time.LocalDate;

public class WeekPlanInputDTO extends PlanDTO<LocalDate> {

    public WeekPlanInputDTO() {
    }

    public WeekPlanInputDTO(String content, LocalDate startDate, LocalDate endDate) {
        super(content, startDate, endDate);
    }
}
