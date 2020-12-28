import React, { useContext, useState } from 'react';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import DataRequests from '../../authentication/DataRequests';
import { setMonthName } from '../OtherEntriesFunctions';
import { DatePickerTimeValues, DateSequences } from '../../utils/interfaces';
import DateTimePickerBottomInfo from './DateTimePickerBottomInfo';
import RenderDatePickerContent from './RenderDatePickerContent';

const DateTimePicker = (): JSX.Element => {
    const { openDatePicker, date } = useContext(DatePickerContext);
    const { date: dateValue } = date;
    const { language } = useContext(LanguageContext);
    const [timeValues, setDateValues] = useState<DatePickerTimeValues>({
        minutes: new Date().getMinutes(),
        hours: new Date().getHours(),
        monthName: setMonthName(DataRequests.getSequenceFromDate(dateValue!, DateSequences.MONTH), language),
        year: DataRequests.getSequenceFromDate(dateValue!, DateSequences.YEAR),
    });

    return (
        <div className='desktop-date-time-picker-container' onClick={() => openDatePicker!(false)}>
            <div className='date-time-picker' onClick={event => event.stopPropagation()}>
                <RenderDatePickerContent setDateValues={setDateValues} timeValues={timeValues} />
                <DateTimePickerBottomInfo timeValues={timeValues} />
            </div>
        </div>
    )
}

export default DateTimePicker;