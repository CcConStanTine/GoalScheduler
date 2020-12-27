import React, { useContext, useState } from 'react';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import DataRequests from '../../authentication/DataRequests';
import languagePack from '../../utils/languagePack';
import { setMonthValue, setMonthName } from '../OtherEntriesFunctions';
import { DateSequences } from '../../utils/interfaces';
import { DatePickerOptions } from '../../utils/enums';

const renderTable = (index: number) => {
    const table = [];

    for (let i = 0; i < index; i++) {
        table.push(i);
    }

    return table;
}

const DateTimePicker = (): JSX.Element => {
    const { openDatePicker, setDate, date } = useContext(DatePickerContext);
    const { language } = useContext(LanguageContext);
    const [minutes, setMinutes] = useState(new Date().getMinutes());
    const [hours, setHours] = useState(new Date().getHours());
    const [monthName, setMonth] = useState<string>(setMonthName(DataRequests.getSequenceFromDate(date.date!, DateSequences.MONTH), language));
    const [year, setYear] = useState<number>(DataRequests.getSequenceFromDate(date.date!, DateSequences.YEAR));

    const setTime = () => {
        //add checkpoint for pl-eng month names;
        if (date.option === DatePickerOptions.SELECTDATE)
            setDate!({ ...date, date: `01-${setMonthValue(monthName) < 10 ? `0${setMonthValue(monthName)}` : setMonthValue(monthName)}-${year}` });
        else if (date.option === DatePickerOptions.STARTDATE)
            setDate!({ ...date, startDate: `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}` });

        openDatePicker!(false);
    }

    const renderYears = () => {
        const years = [];

        for (let y = 2019; y < 2030; y++) {
            years.push(y);
        }

        return years.map(yearNumber => <p
            onClick={() => setYear(yearNumber)}
            className={yearNumber === year ? 'active' : ''}
            key={yearNumber}>
            {yearNumber}
        </p>)

    }

    return (
        <div className='desktop-date-time-picker-container' onClick={() => openDatePicker!(false)}>
            <div className='date-time-picker' onClick={event => event.stopPropagation()}>
                <div className='date-selection'>
                    <div className='months-container'>
                        <div className='months'>
                            {date.option === DatePickerOptions.SELECTDATE &&
                                languagePack[language].MONTHS.namesTable.map(month =>
                                    <p
                                        onClick={() => setMonth(month)}
                                        className={month === monthName ? 'active' : ''}
                                        key={month}>
                                        {month}
                                    </p>)
                            }
                            {date.option === DatePickerOptions.STARTDATE &&
                                renderTable(24).map(hour =>
                                    <p
                                        onClick={() => setHours(hour)}
                                        className={hour === hours ? 'active' : ''}
                                        key={hour}>
                                        {hour < 10 ? `0${hour}` : hour}
                                    </p>)
                            }
                        </div>
                    </div>
                    <div className='years-container'>
                        <div className='years'>
                            {date.option === DatePickerOptions.SELECTDATE && renderYears()}
                            {date.option === DatePickerOptions.STARTDATE &&
                                renderTable(60).map(minute =>
                                    <p
                                        onClick={() => setMinutes(minute)}
                                        className={minute === minutes ? 'active' : ''}
                                        key={minute}>
                                        {minute < 10 ? `0${minute}` : minute}
                                    </p>)
                            }
                        </div>
                    </div>
                </div>
                <div className='bottom-info'>
                    <p className='time'>
                        {date.option === DatePickerOptions.SELECTDATE && `${monthName} ${year}`}
                        {date.option === DatePickerOptions.STARTDATE &&
                            `${hours < 10 ? `0${hours}` : hours} : ${minutes < 10 ? `0${minutes}` : minutes}`}
                    </p>
                    <button className='set-time desktop-default-button' onClick={setTime}>Zaaktualizuj</button>
                </div>
            </div>
        </div>
    )
}

export default DateTimePicker;