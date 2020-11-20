import React from 'react';
import { EntriesPlanType, navigationTypes } from '../utils/variables';
import languagePack from '../utils/languagePack';

interface EntriesNavigation {
    entryType: string;
    handlePlanData: any;
    placeholder: string;
}

interface dateParams {
    year: number;
    month: number;
    day: number;
}

interface navlistInterface {
    [index: number]: {
        entryType: string;
        handlePlanData: any;
        placeholder: string;
    };
    map: any;
};

export const getActualDateAsAObject = () => {
    const date = new Date();

    return { year: date.getFullYear(), month: date.getMonth() + 1, day: date.getDate() };
}

export const setMonthValue = (monthName: string) => {
    switch (monthName) {
        case 'JANUARY': return 1;
        case 'FEBRUARY': return 2;
        case 'MARCH': return 3;
        case 'APRIL': return 4;
        case 'MAY': return 5;
        case 'JUNE': return 6;
        case 'JULY': return 7;
        case 'AUGUST': return 8;
        case 'SEPTEMBER': return 9;
        case 'OCTOBER': return 10;
        case 'NOVEMBER': return 11;
        case 'DECEMBER': return 12;

        default: return 1;
    }
};

export const getDateAsAString = (date: dateParams, monthName?: string) => {
    const { year, month, day } = date;
    let _month = month;

    if (monthName)
        _month = setMonthValue(monthName);

    const monthValue = _month < 10 ? `0${_month}` : _month;

    return `${year}-${monthValue}-${day}`;
};

export const setIdValue = (sign: string, entryType: string, id: number, entryList?: any) => {
    console.log(entryList);
    if (entryType === EntriesPlanType.MONTH) {
        const firstMonthId = entryList[0].monthId;
        const lastMonthId = entryList[entryList.length - 1].monthId;

        console.log(firstMonthId, lastMonthId, id, sign)

        if (sign === navigationTypes.ADDITION) {
            console.log('dodaje')
            ++id;
            if (id > lastMonthId) return firstMonthId;

            return id;
        }
        else {
            console.log('odejmuje')
            --id;
            if (id < firstMonthId) return lastMonthId;

            return id;
        }
    }
    else if (entryType === EntriesPlanType.YEAR) {
        if (sign === navigationTypes.ADDITION)
            return ++id
        else {
            --id;
            if (id < 1) return 1;

            return id;
        }
    }

    const firstDayId = entryList[0].dayId;
    const lastDayId = entryList[entryList.length - 1].dayId;

    if (sign === navigationTypes.ADDITION) {
        console.log('dodaje')
        ++id;
        if (id > lastDayId) return firstDayId;

        return id;
    }
    else {
        console.log('odejmuje')
        --id;
        if (id < firstDayId) return lastDayId;

        return id;
    }
}

const setMonthName = (monthNumber: number, language: string) => {
    switch (monthNumber) {
        case 1: return languagePack[language].january;
        case 2: return languagePack[language].february;
        case 3: return languagePack[language].march;
        case 4: return languagePack[language].april;
        case 5: return languagePack[language].may;
        case 6: return languagePack[language].june;
        case 7: return languagePack[language].july;
        case 8: return languagePack[language].august;
        case 9: return languagePack[language].september;
        case 10: return languagePack[language].october;
        case 11: return languagePack[language].november;
        case 12: return languagePack[language].december;

        default: return languagePack[language].january;
    }
}

export const setPlaceholderValue = (date: dateParams, entryType: string, language: string) => {
    const { year, month, day } = date;
    const monthValue = setMonthName(month, language);

    if (entryType === EntriesPlanType.YEAR) return year
    else if (entryType === EntriesPlanType.MONTH) return `${monthValue} ${year}`

    return `${day} ${monthValue} ${year}`
}

const setActiveClassName = (type: string, entryType: string) => {
    if (entryType === type)
        return 'active';

    return null;
}

export const renderEntriesNavigation = (navList: navlistInterface) => navList.map(({ entryType, handlePlanData, placeholder }: EntriesNavigation) =>
    <button
        className={`default-button ${setActiveClassName(EntriesPlanType.YEAR, entryType)}`}
        onClick={() => handlePlanData}
        key={`${entryType}${placeholder}`}
    >{placeholder}</button>)
