import { EntriesPlanType, navigationTypes } from '../utils/variables';
import languagePack from '../utils/languagePack';
import { dateParams } from '../utils/interfaces';
import DataRequests from '../authentication/DataRequests';

export const getActualDateAsAObject = () => {
    const date = new Date();

    return { year: date.getFullYear(), month: date.getMonth() + 1, day: date.getDate() };
}

export const getDateAsAString = (date: dateParams, monthName?: string) => {
    const { year, month, day } = date;
    let _month = month;

    if (monthName)
        _month = setMonthValue(monthName);

    const monthValue = _month < 10 ? `0${_month}` : _month;

    return `${year}-${monthValue}-${day}`;
};

const getFirstAndLastId = (entryType: string, entryList: any) => ({
    firstId: entryList[0][`${entryType}Id`],
    lastId: entryList[entryList.length - 1][`${entryType}Id`]
});

const calculateId = (sign: string, firstId: number, lastId: number, id: number) => {
    if (sign === navigationTypes.ADDITION)
        return ++id > lastId ? firstId : id;

    return --id < firstId ? lastId : id;
}

export const setIdValue = (sign: string, entryType: string, id: number, entryList?: any) => {
    const { firstId, lastId } = getFirstAndLastId(entryType, entryList);

    return calculateId(sign, firstId, lastId, id);
};

export const setPlaceholderValue = (date: dateParams, entryType: string, language: string) => {
    const { year, month, day } = date;
    const monthValue = setMonthName(month, language);

    if (entryType === EntriesPlanType.YEAR) return year;
    else if (entryType === EntriesPlanType.MONTH) return `${monthValue} ${year}`;

    return `${day} ${monthValue} ${year}`;
};

export const setActiveClassName = (type: string, entryType: string) => {
    if (entryType === type)
        return 'active';

    return null;
};

const getPlansByEntryType = async (type: string, data: any, date: dateParams) => {
    const { yearId, yearNumber, monthName, dayDate } = data;

    if (type === EntriesPlanType.YEAR) {
        const plans = await DataRequests.getTypePlansByTypeAndId(type, yearId);
        const typeValue = yearNumber;

        return { plans, typeValue };
    }
    else if (type === EntriesPlanType.MONTH) {
        const plans = await DataRequests.getTypePlans(type, getDateAsAString(date, monthName));
        const typeValue = setMonthValue(monthName);

        return { plans, typeValue }
    }

    const plans = await DataRequests.getTypePlans(type, dayDate);
    const typeValue = dayDate.slice(dayDate.length - 2, dayDate.length);

    return { plans, typeValue }
}

export const getDataByType = async (type: string, sign: string, id: number, date: dateParams) => {
    const planList = await DataRequests.getTypeDataByDate(type, getDateAsAString(date));
    const data = await DataRequests.getTypeDataById(type, setIdValue(sign, type, id!, planList));

    const { plans, typeValue } = await getPlansByEntryType(type, data, date);

    return { _id: data[`${type}Id`], _date: { ...date, [`${type}`]: typeValue }, plans };
};

const setMonthName = (monthNumber: number, language: string) => languagePack[language].MONTHS.namesTable[monthNumber - 1];

export const setMonthValue = (monthName: string) => languagePack['en-US'].MONTHS.namesTable.map(month => month.toUpperCase()).indexOf(monthName) + 1;