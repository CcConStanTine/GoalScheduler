package com.pk.ms.dto.day;

import com.pk.ms.abstracts.PlanDTO;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

public class DayPlanInputDTO extends PlanDTO<LocalTime> {

    public DayPlanInputDTO() {
    }

    public DayPlanInputDTO(@NotNull @Size(max = 200) String content, @NotNull LocalTime startDate,
                           @NotNull LocalTime endDate, Importance importance, Urgency urgency,
                           boolean isFulfilled) {
        super(content, startDate, endDate, importance, urgency, isFulfilled);
    }
}

