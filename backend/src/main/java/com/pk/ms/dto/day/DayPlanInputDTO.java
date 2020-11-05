package com.pk.ms.dto.day;

import java.time.LocalTime;

public class DayPlanInputDTO extends com.pk.ms.abstracts.Plan<LocalTime> {

    public DayPlanInputDTO() {
    }

    public DayPlanInputDTO(String content, LocalTime startDate, LocalTime endDate) {
        super(content, startDate, endDate);
    }
}

