package com.pk.ms.dto.month;

import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.entities.month.MonthName;
import com.pk.ms.entities.month.MonthPlan;

import java.util.List;

// Month DTO for purpose of storing fields needed while displaying particular Month
public class MonthWithBasicDayDTO {

    private long monthId;
    private MonthName monthName;
    private int daysAmount;
    private List<DayBasicInfoDTO> dayBasicInfoDTOList;
    private List<MonthPlan> monthPlanList;

    public MonthWithBasicDayDTO() {
    }

    public MonthWithBasicDayDTO(long monthId, MonthName monthName, int daysAmount, List<DayBasicInfoDTO> dayBasicInfoDTOList, List<MonthPlan> monthPlanList) {
        this.monthId = monthId;
        this.monthName = monthName;
        this.daysAmount = daysAmount;
        this.dayBasicInfoDTOList = dayBasicInfoDTOList;
        this.monthPlanList = monthPlanList;
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

    public List<DayBasicInfoDTO> getDayBasicInfoDTOList() {
        return dayBasicInfoDTOList;
    }

    public void setDayBasicInfoDTOList(List<DayBasicInfoDTO> dayBasicInfoDTOList) {
        this.dayBasicInfoDTOList = dayBasicInfoDTOList;
    }

    public List<MonthPlan> getMonthPlanList() {
        return monthPlanList;
    }

    public void setMonthPlanList(List<MonthPlan> monthPlanList) {
        this.monthPlanList = monthPlanList;
    }
}
