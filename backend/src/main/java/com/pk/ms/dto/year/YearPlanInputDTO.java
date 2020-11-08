package com.pk.ms.dto.year;

import com.pk.ms.abstracts.Plan;

import java.time.LocalDate;

public class YearPlanInputDTO extends Plan<LocalDate> {

    public YearPlanInputDTO() {
    }

    public YearPlanInputDTO(String content, LocalDate startDate, LocalDate endDate) {
        super(content, startDate, endDate);
    }
}
