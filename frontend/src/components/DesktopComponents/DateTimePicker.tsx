import React, { useContext, useState } from 'react';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import DataRequests from '../../authentication/DataRequests';
import languagePack from '../../utils/languagePack';
import { setMonthValue, setMonthName } from '../OtherEntriesFunctions';
import { DateSequences } from '../../utils/interfaces';

const DateTimePicker = () => {
    const { openDatePicker, setDate, date } = useContext(DatePickerContext);
    const { language } = useContext(LanguageContext);
    const [monthName, setMonth] = useState<string>(setMonthName(DataRequests.setDateBySequence(DataRequests.changeEngToPlDate(date, DateSequences.MONTH, language), DateSequences.MONTH), language));
    const [year, setYear] = useState<number>(DataRequests.setDateBySequence(DataRequests.changeEngToPlDate(date, DateSequences.YEAR, language), DateSequences.YEAR));

    const setTime = () => {
        const monthNumber = setMonthValue(monthName.toUpperCase());
        setDate!(`${year}-${monthNumber < 10 ? `0${monthNumber}` : monthNumber}-01`);
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
                            {languagePack[language].MONTHS.namesTable.map(month =>
                                <p
                                    onClick={() => setMonth(month)}
                                    className={month === monthName ? 'active' : ''}
                                    key={month}>
                                    {month}
                                </p>)}
                        </div>
                    </div>
                    <div className='years-container'>
                        <div className='years'>
                            {renderYears()}
                        </div>
                    </div>
                </div>
                <div className='bottom-info'>
                    <p className='time'>{monthName} {year}</p>
                    <button className='set-time desktop-default-button' onClick={setTime}>Zaaktualizuj</button>
                </div>
            </div>
        </div>
    )
}

export default DateTimePicker;