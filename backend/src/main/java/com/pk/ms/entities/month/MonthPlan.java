package com.pk.ms.entities.month;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.Plan;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class MonthPlan extends Plan<Date> {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monthplan_seq")
    @SequenceGenerator(name = "monthplan_seq", sequenceName = "monthplan_seq", allocationSize = 1)
    private long monthPlanId;

    // foreign key
    @ManyToOne
    @JoinColumn(name="month_id")
    @JsonIgnore
    private Month month;

    public MonthPlan() {

    }

    public MonthPlan(String content, Date startDate, Date endDate, Month month) {
        super(content, startDate, endDate);
        this.month = month;
    }

    public long getMonthPlanId() {
        return monthPlanId;
    }

    public void setMonthPlanId(long monthPlanId) {
        this.monthPlanId = monthPlanId;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}
