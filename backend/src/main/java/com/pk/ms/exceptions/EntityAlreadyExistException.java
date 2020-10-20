package com.pk.ms.exceptions;

import com.pk.ms.entities.day.DayName;
import com.pk.ms.entities.month.MonthName;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException(int yearNumber) {
        super("Year " + yearNumber + " already exists in your Schedule. ");
    }
    public EntityAlreadyExistException(MonthName monthName) {
        super("Month " + monthName + " already exists in this Year. ");
    }
    public EntityAlreadyExistException(DayName dayName) {
        super("Week " + dayName + " already exists in this Week. ");
    }
}
