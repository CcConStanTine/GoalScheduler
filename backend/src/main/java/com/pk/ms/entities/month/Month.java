package com.pk.ms.entities.month;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.year.Year;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Month {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "month_seq")
    @SequenceGenerator(name = "month_seq", sequenceName = "month_seq", allocationSize = 1)
    private long monthId;

    private MonthName monthName;

    // min 28, max 31
    private int daysAmount;

    // foreign key
    @ManyToOne
    @JoinColumn(name="year_id")
    @JsonIgnore
    private Year year;

    @OneToMany(mappedBy = "month")
    private List<Day> dayList;

    @OneToMany(mappedBy = "month")
    private List<MonthPlan> monthPlansList;

    @OneToOne(mappedBy = "month")
    private MonthSummary monthSummary;

    public Month() {

    }

    public Month(MonthName monthName, Year year) {
        this.monthName = monthName;
        this.year = year;
        setDaysAmount(year);
        this.dayList = new ArrayList<Day>();
        this.monthPlansList = new ArrayList<MonthPlan>();
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

    private void setMonthName(MonthName monthName) {
        this.monthName = monthName;
    }

    public int getDaysAmount() {
        return daysAmount;
    }

    public void setDaysAmount(Year year) {
        int monthNumber = this.monthName.getMonthNumber();
        if(monthNumber==1 || monthNumber==3 || monthNumber==5 || monthNumber==7 ||
                                    monthNumber==8 || monthNumber==10 || monthNumber==12)
            this.daysAmount = 31;
        else if (monthNumber==2) {
            if (year.isLeapYear())
                this.daysAmount = 29;
            else
                this.daysAmount = 28;
        }
        else
            this.daysAmount = 30;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public List<MonthPlan> getMonthPlansList() {
        return monthPlansList;
    }

    public void setMonthPlansList(List<MonthPlan> monthPlansList) {
        this.monthPlansList = monthPlansList;
    }

    public MonthSummary getMonthSummary() {
        return monthSummary;
    }

    public void setMonthSummary(MonthSummary monthSummary) {
        this.monthSummary = monthSummary;
    }
}
