package com.pk.ms.dto.day;

import com.pk.ms.abstracts.PlanDTO;

import java.time.LocalTime;

public class DayPlanInputDTO extends PlanDTO<LocalTime> {

    public DayPlanInputDTO() {
    }

    public DayPlanInputDTO(String content, LocalTime startDate, LocalTime endDate) {
        super(content, startDate, endDate);
    }
}

