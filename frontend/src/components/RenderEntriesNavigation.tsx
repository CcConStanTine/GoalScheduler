import React, { useContext } from 'react';
import languagePack from '../utils/languagePack';
import { EntriesPlanType } from '../utils/variables';
import { setActiveClassName, getDateAsAString } from './OtherEntriesFunctions';
import { LanguageContext } from '../authentication/LanguageContext';
import { entriesNavigation } from '../utils/interfaces';

const RenderEntriesNavigation = ({ entryType, onClick, date }: entriesNavigation) => {
    const { language } = useContext(LanguageContext);

    return (
        <>
            <button
                className={`default-button ${setActiveClassName(EntriesPlanType.YEAR, entryType)}`}
                onClick={() => onClick(EntriesPlanType.YEAR, getDateAsAString(date))}>
                {languagePack[language].yearPlanText}
            </button>
            <button
                className={`default-button ${setActiveClassName(EntriesPlanType.MONTH, entryType)}`}
                onClick={() => onClick(EntriesPlanType.MONTH, getDateAsAString(date))}>
                {languagePack[language].monthPlanText}
            </button>
            <button
                className={`default-button ${setActiveClassName(EntriesPlanType.DAY, entryType)}`}
                onClick={() => onClick(EntriesPlanType.DAY, getDateAsAString(date))}>
                {languagePack[language].dayPlanText}
            </button>
        </>
    )
}

export default RenderEntriesNavigation;