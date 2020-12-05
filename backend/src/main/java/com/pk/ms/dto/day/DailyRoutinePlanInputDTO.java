package com.pk.ms.dto.day;

import com.pk.ms.constants.Importance;
import com.pk.ms.constants.Urgency;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

public class DailyRoutinePlanInputDTO {

    @NotNull
    @Size(max = 200)
    private String content;

    @NotNull
    private LocalTime startDate;

    @NotNull
    private LocalTime endDate;

    private Importance importance;

    private Urgency urgency;

    public DailyRoutinePlanInputDTO() {
    }

    public DailyRoutinePlanInputDTO(@NotNull @Size(max = 200) String content, @NotNull LocalTime startDate,
                                    @NotNull LocalTime endDate) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.importance = Importance.REGULAR;
        this.urgency = Urgency.REGULAR;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalTime startDate) {
        this.startDate = startDate;
    }

    public LocalTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalTime endDate) {
        this.endDate = endDate;
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
