package com.pk.ms.exceptions;

import com.pk.ms.dto.week.WeekInputDTO;
import com.pk.ms.entities.day.DayName;
import com.pk.ms.entities.month.MonthName;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Date;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException(int yearNumber) {
        super("Year " + yearNumber + " already exists in your Schedule. ");
    }
    public EntityAlreadyExistException(MonthName monthName) {
        super("Month " + monthName + " already exists in this Year. ");
    }
    public EntityAlreadyExistException(WeekInputDTO weekInputDTO) {
        super("Week including days between " + weekInputDTO.getStartDate() + " and " + weekInputDTO.getEndDate() +
                " already exists. ");
    }
    public EntityAlreadyExistException(DayName dayName) {
        super("Day " + dayName + " already exists in this Week. ");
    }

    public EntityAlreadyExistException(Date date) {
        super("Day with date " + date + " already exists in this Year");
    }
}
