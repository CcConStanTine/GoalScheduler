import React, { useContext } from 'react';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import { DatePickerOptions } from '../../utils/enums';
import { DatePickerTimeValues } from '../../utils/interfaces';
import languagePack from '../../utils/languagePack';
import { setMonthValue } from '../global/OtherEntriesFunctions';

interface BottomInfo {
    timeValues: DatePickerTimeValues;
}

const DateTimePickerBottomInfo = ({ timeValues }: BottomInfo): JSX.Element => {
    const { language } = useContext(LanguageContext);
    const { openDatePicker, setDate, date } = useContext(DatePickerContext);
    const { hours, minutes, monthName, year } = timeValues;
    const { option } = date;

    const setTime = () => {
        option === DatePickerOptions.SELECTDATE ?
            setDate!({ ...date, date: `01-${setMonthValue(monthName, language) < 10 ? `0${setMonthValue(monthName, language)}` : setMonthValue(monthName, language)}-${year}` })
            :
            setDate!({ ...date, startDate: `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}` });

        return openDatePicker!(false);
    }

    return (
        <div className='bottom-info'>
            <p className='time'>
                {option === DatePickerOptions.SELECTDATE && `${monthName} ${year}`}
                {option === DatePickerOptions.STARTDATE &&
                    `${hours < 10 ? `0${hours}` : hours} : ${minutes < 10 ? `0${minutes}` : minutes}`}
            </p>
            <button className='set-time desktop-default-button' onClick={setTime}>{languagePack[language].TIMEPICKER.update}</button>
        </div>
    )
}

export default DateTimePickerBottomInfo;