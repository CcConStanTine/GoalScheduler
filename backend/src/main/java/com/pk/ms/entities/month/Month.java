package com.pk.ms.entities.month;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.year.Year;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "month_seq")
    @SequenceGenerator(name = "month_seq", sequenceName = "month_seq", allocationSize = 1)
    private Long monthId;

    private MonthName monthName;

    private int daysAmount;

    @ManyToOne
    @JoinColumn(name="year_id")
    @JsonIgnore
    private Year year;

    @OneToMany(mappedBy = "month")
    private List<Day> dayList;

    public Month() {
    }

    public Month(MonthName monthName, Year year) {
        this.monthName = monthName;
        this.daysAmount = setDaysAmount(monthName, year);
        this.year = year;
        this.dayList = new ArrayList<>();
    }

    public Long getMonthId() {
        return monthId;
    }

    public void setMonthId(Long monthId) {
        this.monthId = monthId;
    }

    public MonthName getMonthName() {
        return monthName;
    }

    public int getDaysAmount() {
        return daysAmount;
    }

    public Year getYear() {
        return year;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    private int setDaysAmount(MonthName monthName, Year year) {
        int monthNumber = monthName.getMonthNumber();
        if(monthNumber==1 || monthNumber==3 || monthNumber==5 || monthNumber==7 ||
                monthNumber==8 || monthNumber==10 || monthNumber==12)
            return 31;
        else if (monthNumber==2) {
            if (year.isLeapYear())
                return 29;
            else
                return 28;
        }
        else
            return 30;
    }
}