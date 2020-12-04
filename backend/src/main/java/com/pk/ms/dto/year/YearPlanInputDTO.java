package com.pk.ms.dto.year;

import com.pk.ms.abstracts.PlanDTO;

import java.time.LocalDate;

public class YearPlanInputDTO extends PlanDTO<LocalDate> {

    public YearPlanInputDTO() {
    }

    public YearPlanInputDTO(String content, LocalDate startDate, LocalDate endDate) {
        super(content, startDate, endDate);
    }
}
