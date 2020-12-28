import React from 'react';
import { TimePickerOptions } from '../../utils/enums';
import { DatePickerTimeValues } from "../../utils/interfaces";

export const renderTable = (endIndex: number, startIndex = 0) => {
    const table = [];

    for (let i = startIndex; i < endIndex; i++) {
        table.push(i);
    }

    return table;
}

const checkTimePickerType = (type: TimePickerOptions) => {
    if (type === TimePickerOptions.MINUTE)
        return 'minutes';
    else if (type === TimePickerOptions.HOUR)
        return 'hours';
    else if (type === TimePickerOptions.MONTH)
        return 'monthName';

    return 'year';
}


export const renderByTimePickerOptions = (onClick: (value: DatePickerTimeValues) => void, values: DatePickerTimeValues, table: any, type: TimePickerOptions) =>
    table.map((value: string | number) => <p
        onClick={() => onClick({ ...values, [checkTimePickerType(type)]: value })}
        className={value === values[checkTimePickerType(type)] ? 'active' : ''}
        key={value}>
        {typeof (value) === "number" && value < 10 ? `0${value}` : value}
    </p>)