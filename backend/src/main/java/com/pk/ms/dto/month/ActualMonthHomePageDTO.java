package com.pk.ms.dto.month;

import com.pk.ms.entities.month.MonthName;
import com.pk.ms.entities.month.MonthPlan;

import java.util.List;

// Month DTO for purpose of storing the fields of Month needed on Home Page
public class ActualMonthHomePageDTO {

    private long monthId;
    private MonthName monthName;
    private List<MonthPlan> monthPlanList;


    public ActualMonthHomePageDTO() {

    }

    public ActualMonthHomePageDTO(long monthId, MonthName monthName, List<MonthPlan> monthPlanList) {
        this.monthId = monthId;
        this.monthName = monthName;
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

    public List<MonthPlan> getMonthPlanList() {
        return monthPlanList;
    }

    public void setMonthPlanList(List<MonthPlan> monthPlanList) {
        this.monthPlanList = monthPlanList;
    }
}
