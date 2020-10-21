package com.pk.ms.exceptions;

import com.pk.ms.dto.day.DayInputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidDataException extends RuntimeException {
    public NotValidDataException(DayInputDTO dayInputDTO) {
        super("Date " + dayInputDTO.getDayDate() + " contains wrong month. ");
    }
}
