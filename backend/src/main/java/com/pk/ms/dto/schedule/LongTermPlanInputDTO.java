package com.pk.ms.dto.schedule;

import com.pk.ms.abstracts.Plan;

import java.time.LocalDate;

public class LongTermPlanInputDTO extends Plan<LocalDate> {

    public LongTermPlanInputDTO() {
    }

    public LongTermPlanInputDTO(String content, LocalDate startDate, LocalDate endDate) {
        super(content, startDate, endDate);
    }
}
