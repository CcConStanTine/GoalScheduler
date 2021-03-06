package com.pk.ms.dto.week;

import com.pk.ms.abstracts.PlanDTO;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class WeekPlanInputDTO extends PlanDTO<LocalDate> {

    public WeekPlanInputDTO() {
    }

    public WeekPlanInputDTO(@NotNull @Size(max = 200) String content, @NotNull LocalDate startDate,
                            @NotNull LocalDate endDate, Importance importance, Urgency urgency,
                            boolean isFulfilled) {
        super(content, startDate, endDate, importance, urgency, isFulfilled);
    }
}
