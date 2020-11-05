package com.pk.ms.entities.week;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pk.ms.entities.year.Year;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "week_seq")
    @SequenceGenerator(name = "week_seq", sequenceName = "week_seq", allocationSize = 1)
    private Long weekId;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name="year_id")
    @JsonIgnore
    private Year year;

    public Week() {
    }

    public Week(LocalDate startDate, LocalDate endDate, Year year) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.year = year;
    }

    public Long getWeekId() {
        return weekId;
    }

    public void setWeekId(Long weekId) {
        this.weekId = weekId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Year getYear() {
        return year;
    }
}
