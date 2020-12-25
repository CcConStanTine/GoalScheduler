import React, { useContext } from 'react';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import { NavigationOptions } from '../../utils/enums';
import { OptionActiveInterface } from '../../utils/interfaces';
import { checkIfDateIsLowerThanCurrentDate, formatDate, navigationButton } from './EmptyPlansFunction';

const EmptyPlansInfoAndNavigation = ({ setOpenWindow, id, dateValue, setOptionActiveType }: OptionActiveInterface) => {
    const { language } = useContext(LanguageContext);
    const { date } = useContext(DatePickerContext);

    return (
        <div className='empty-plans'>
            {navigationButton(setOpenWindow!, id!, NavigationOptions.PREVIOUS)}
            <div>
                <p className='date'>{date.date && formatDate(dateValue, language)}</p>
                {checkIfDateIsLowerThanCurrentDate(dateValue, language, setOptionActiveType)}
            </div>
            {navigationButton(setOpenWindow!, id!, NavigationOptions.NEXT)}
        </div>
    )
};

export default EmptyPlansInfoAndNavigation;