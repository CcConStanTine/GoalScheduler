import React, { useContext } from 'react';
import languagePack from '../utils/languagePack';
import { EntriesPlanType } from '../utils/variables';
import { setActiveClassName, getDateAsAString } from './OtherEntriesFunctions';
import { LanguageContext } from '../authentication/LanguageContext';
import { entriesNavigation } from '../utils/interfaces';

const RenderEntriesNavigation = ({ entryType, onClick, date }: entriesNavigation) => {
    const { language } = useContext(LanguageContext);

    const handleOnClickValue = (type: string) => {
        if (date)
            return onClick(type, getDateAsAString(date!));

        return onClick(type);
    }

    return (
        <>
            <button
                className={`default-button ${setActiveClassName(EntriesPlanType.YEAR, entryType)}`}
                onClick={() => handleOnClickValue(EntriesPlanType.YEAR)}>
                {languagePack[language].yearPlanText}
            </button>
            <button
                className={`default-button ${setActiveClassName(EntriesPlanType.MONTH, entryType)}`}
                onClick={() => handleOnClickValue(EntriesPlanType.MONTH)}>
                {languagePack[language].monthPlanText}
            </button>
            <button
                className={`default-button ${setActiveClassName(EntriesPlanType.DAY, entryType)}`}
                onClick={() => handleOnClickValue(EntriesPlanType.DAY)}>
                {languagePack[language].dayPlanText}
            </button>
        </>
    )
}

export default RenderEntriesNavigation;