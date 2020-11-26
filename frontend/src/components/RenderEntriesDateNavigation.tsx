import React, { useContext } from 'react';
import { FaChevronRight, FaChevronLeft } from 'react-icons/fa';
import { setPlaceholderValue } from './OtherEntriesFunctions';
import { navigationTypes } from '../utils/variables';
import { LanguageContext } from '../authentication/LanguageContext';
import { entriesNavigation } from '../utils/interfaces';

const RenderEntriesDateNavigation = ({ entryType, onClick, date }: entriesNavigation) => {
    const { language } = useContext(LanguageContext);

    return (
        <>
            <FaChevronLeft onClick={() => onClick(navigationTypes.SUBTRACTION)} />
            <p>{setPlaceholderValue(date!, entryType, language)}</p>
            <FaChevronRight onClick={() => onClick(navigationTypes.ADDITION)} />
        </>
    )
}

export default RenderEntriesDateNavigation;