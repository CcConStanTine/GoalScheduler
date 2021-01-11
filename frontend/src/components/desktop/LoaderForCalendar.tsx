import React, { useContext } from 'react';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import languagePack from '../../utils/languagePack';

const LoaderForCalendar = () => {
    const { language } = useContext(LanguageContext);
    const { isLoaderActive } = useContext(DatePickerContext);

    if (isLoaderActive)
        return <p className='loading-calendar'>{languagePack[language].GLOBAL.loadingCalendar}</p>

    return null;
}

export default LoaderForCalendar