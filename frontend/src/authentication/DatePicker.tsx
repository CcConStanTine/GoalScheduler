import React, { createContext, useState, ReactNode, useContext } from 'react';
import DataRequests from './DataRequests';
import { LanguageContext } from './LanguageContext';

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
        date: ''
    }
);

export const UseDatePickerContext = ({ children }: UseDatePickerContextInterface) => {
    const { language } = useContext(LanguageContext)
    const [isDatePickerActive, openDatePicker] = useState<boolean>(false);
    const [date, setDate] = useState<string>(DataRequests.getCurrentDate(language));

    return (
        <DatePickerContext.Provider value={{ isDatePickerActive, openDatePicker, date, setDate }}>
            {children}
        </DatePickerContext.Provider>
    )
}