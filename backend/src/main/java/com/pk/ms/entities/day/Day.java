package com.pk.ms.entities.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.constants.DayName;
import com.pk.ms.entities.month.Month;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_seq")
    @SequenceGenerator(name = "day_seq", sequenceName = "day_seq", allocationSize = 1)
    private Long dayId;

    private LocalDate dayDate;

    private DayName dayName;

    @ManyToOne
    @JoinColumn(name = "month_id")
    @JsonIgnore
    private Month month;

    public Day() {
    }

    public Day(LocalDate dayDate, DayName dayName, Month month) {
        this.dayDate = dayDate;
        this.dayName = dayName;
        this.month = month;
    }

    public Long getDayId() {
        return dayId;
    }

    public void setDayId(Long dayId) {
        this.dayId = dayId;
    }

    public LocalDate getDayDate() {
        return dayDate;
    }

    public DayName getDayName() {
        return dayName;
    }

    public Month getMonth() {
        return month;
    }
}