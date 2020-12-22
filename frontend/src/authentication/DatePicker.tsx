import React, { createContext, useState, ReactNode, useContext } from 'react';
import DataRequests from './DataRequests';
import { LanguageContext } from './LanguageContext';
import { DatePickerContextInterface, Date } from '../utils/interfaces';

interface UseDatePickerContextInterface {
    children: ReactNode;
}

export const DatePickerContext = createContext<DatePickerContextInterface>(
    {
        isDatePickerActive: false,
        date: {
            date: ''
        }
    }
);

export const UseDatePickerContext = ({ children }: UseDatePickerContextInterface) => {
    const { language } = useContext(LanguageContext)
    const [isDatePickerActive, openDatePicker] = useState<boolean>(false);
    const [date, setDate] = useState<Date>({ date: DataRequests.getCurrentDate(language) });

    return (
        <DatePickerContext.Provider value={{ isDatePickerActive, openDatePicker, date, setDate }}>
            {children}
        </DatePickerContext.Provider>
    )
}