package com.pk.ms.dto.week;

import javax.validation.constraints.NotNull;
import java.sql.Date;

// Week DTO for purpose of creating
public class WeekInputDTO {

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    public WeekInputDTO() {
    }

    public WeekInputDTO(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
