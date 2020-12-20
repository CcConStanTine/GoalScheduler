import React, { createContext, useState, ReactNode } from 'react';
import DataRequests from './DataRequests';
import { dateTimeTypes } from '../utils/variables';

interface DatePickerContextInterface {
    isDatePickerActive: boolean;
    date: string;
    setDate?: (value: string) => void;
    openDatePicker?: (value: boolean) => void;
}


interface UseDatePickerContextInterface {
    children: ReactNode;
}

export const DatePickerContext = createContext<DatePickerContextInterface>(
    {
        isDatePickerActive: false,
        date: DataRequests.setProprietDate(DataRequests.getCurrentDate(), dateTimeTypes.DEFAULT)
    }
);

export const UseDatePickerContext = ({ children }: UseDatePickerContextInterface) => {
    const [isDatePickerActive, openDatePicker] = useState<boolean>(false);
    const [date, setDate] = useState<string>(DataRequests.setProprietDate(DataRequests.getCurrentDate(), dateTimeTypes.DEFAULT));

    return (
        <DatePickerContext.Provider value={{ isDatePickerActive, openDatePicker, date, setDate }}>
            {children}
        </DatePickerContext.Provider>
    )
}