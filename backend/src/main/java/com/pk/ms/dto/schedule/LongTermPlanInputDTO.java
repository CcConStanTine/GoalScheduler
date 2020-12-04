package com.pk.ms.dto.schedule;

import com.pk.ms.abstracts.PlanDTO;
import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;

import java.time.LocalDate;

public class LongTermPlanInputDTO extends PlanDTO<LocalDate> {

    public LongTermPlanInputDTO() {
    }

    private Importance importance;

    private Urgency urgency;

    public LongTermPlanInputDTO(String content, LocalDate startDate, LocalDate endDate) {
        super(content, startDate, endDate);
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }
}
