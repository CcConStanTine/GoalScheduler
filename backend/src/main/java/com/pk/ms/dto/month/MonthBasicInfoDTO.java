package com.pk.ms.dto.month;

import com.pk.ms.entities.month.MonthName;

public class MonthBasicInfoDTO {

    private long monthId;
    private MonthName monthName;
    private int daysAmount;

    public MonthBasicInfoDTO() {
    }

    public MonthBasicInfoDTO(long monthId, MonthName monthName, int daysAmount) {
        this.monthId = monthId;
        this.monthName = monthName;
        this.daysAmount = daysAmount;
    }

    public long getMonthId() {
        return monthId;
    }

    public void setMonthId(long monthId) {
        this.monthId = monthId;
    }

    public MonthName getMonthName() {
        return monthName;
    }

    public void setMonthName(MonthName monthName) {
        this.monthName = monthName;
    }

    public int getDaysAmount() {
        return daysAmount;
    }

    public void setDaysAmount(int daysAmount) {
        this.daysAmount = daysAmount;
    }
}
