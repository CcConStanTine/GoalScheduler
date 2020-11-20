import { EntriesPlanType, navigationTypes } from '../utils/variables';
import languagePack from '../utils/languagePack';
import { dateParams } from '../utils/interfaces';
import auth from '../authentication/database';

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

const getFirstAndLastId = (entryType: string, entryList: any) => {
    if (entryType === EntriesPlanType.MONTH)
        return { firstId: entryList[0].monthId, lastId: entryList[entryList.length - 1].monthId };
    else if (entryType === EntriesPlanType.DAY)
        return { firstId: entryList[0].dayId, lastId: entryList[entryList.length - 1].dayId };

    return { firstId: null, lastId: 1 };
}

const calculateId = (sign: string, firstId: number, lastId: number, id: number) => {
    if (sign === navigationTypes.ADDITION)
        return ++id > lastId ? firstId : id;

    return --id < firstId ? lastId : id;
}

export const setIdValue = (sign: string, entryType: string, id: number, entryList?: any) => {
    const { firstId, lastId } = getFirstAndLastId(entryType, entryList);

    if (firstId)
        return calculateId(sign, firstId, lastId, id);

    if (sign === navigationTypes.ADDITION)
        return ++id

    return --id < 1 ? lastId : id;
};

export const setPlaceholderValue = (date: dateParams, entryType: string, language: string) => {
    const { year, month, day } = date;
    const monthValue = setMonthName(month, language);

    if (entryType === EntriesPlanType.YEAR) return year
    else if (entryType === EntriesPlanType.MONTH) return `${monthValue} ${year}`

    return `${day} ${monthValue} ${year}`
};

export const setActiveClassName = (type: string, entryType: string) => {
    if (entryType === type)
        return 'active';

    return null;
};

export const getRequestByType = async (type: string, date?: string) => {
    if (type === EntriesPlanType.YEAR)
        return await auth.getYearPlans(date);
    else if (type === EntriesPlanType.MONTH)
        return await auth.getMonthPlans(date);

    return await auth.getDayPlans(date);
};

export const getDataByType = async (type: string, sign: string, id: number, date: dateParams) => {
    if (type === EntriesPlanType.YEAR) {
        const { yearId: _id, yearNumber: year } = await auth.getYearByYearId(setIdValue(sign, type, id!));
        const plans = await auth.getYearPlansByYearId(_id);

        return { _id, _date: { ...date, year }, plans };
    }
    else if (type === EntriesPlanType.MONTH) {
        const monthList = await auth.getMonthsByDate(getDateAsAString(date));
        const { monthId: _id, monthName } = await auth.getMonthById(setIdValue(sign, type, id!, monthList));
        const { plans } = await auth.getMonthPlans(getDateAsAString(date, monthName));
        const month = setMonthValue(monthName);

        return { _id, _date: { ...date, month }, plans };
    }

    const dayList = await auth.getDaysByDate(getDateAsAString(date));
    const { dayId: _id, dayDate } = await auth.getDayByDayId(setIdValue(sign, type, id!, dayList));
    const { plans } = await auth.getDayPlans(dayDate);
    const day = dayDate.slice(dayDate.length - 2, dayDate.length);

    return { _id, _date: { ...date, day }, plans };
};

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
};