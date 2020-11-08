package com.pk.ms.dto.week;

import java.time.LocalDate;

public class WeekBasicInfoDTO {

    private long weekId;
    private LocalDate startDate;
    private LocalDate endDate;

    public WeekBasicInfoDTO() {
    }

    public WeekBasicInfoDTO(long weekId, LocalDate startDate, LocalDate endDate) {
        this.weekId = weekId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getWeekId() {
        return weekId;
    }

    public void setWeekId(long weekId) {
        this.weekId = weekId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
