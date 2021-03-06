import React, { useContext } from 'react';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import { OptionTypes } from '../../utils/enums';
import { OptionActiveInterface } from '../../utils/interfaces';
import { EditDayDesktopDefaultValues } from '../../utils/variables';
import { formatDate } from './EmptyPlansFunction';

const OptionActive = ({ dateValue, setOptionActiveType, setEdit }: OptionActiveInterface) => {
    const { language } = useContext(LanguageContext);
    const { date } = useContext(DatePickerContext);

    return (
        <div className='option-active'>
            <p className='date'>{date.date && formatDate(dateValue, language)}</p>
            <span className='cancel' onClick={() => {
                setEdit!(EditDayDesktopDefaultValues);
                setOptionActiveType(OptionTypes.DEFAULT);
            }}></span>
        </div>
    )
};

export default OptionActive;