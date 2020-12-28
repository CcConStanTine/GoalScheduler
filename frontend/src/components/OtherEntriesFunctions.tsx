import { EntriesPlanType, navigationTypes } from '../utils/variables';
import languagePack from '../utils/languagePack';
import { dateParams } from '../utils/interfaces';
import DataRequests from '../authentication/DataRequests';
import { PlanTypes } from '../utils/enums';

export const getActualDateAsAObject = () => {
    const date = new Date();

    return { year: date.getFullYear(), month: date.getMonth() + 1, day: date.getDate() };
}

export const getDateAsAString = (date: dateParams, monthName?: string, language?: string) => {
    const { year, month, day } = date;
    let _month = month;

    if (monthName)
        _month = setMonthValue(monthName, language!);

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

const getPlansByEntryType = async (type: PlanTypes, data: any, date: dateParams, language: string) => {
    const { yearId, yearNumber, monthName, dayDate } = data;

    if (type === EntriesPlanType.YEAR) {
        const plans = await DataRequests.getTypePlansByTypeAndId(type, yearId);
        const typeValue = yearNumber;

        return { plans, typeValue };
    }
    else if (type === EntriesPlanType.MONTH) {
        const plans = await DataRequests.getTypePlans(type, getDateAsAString(date, monthName, language));
        const typeValue = setMonthValue(monthName, language);

        return { plans, typeValue }
    }

    const plans = await DataRequests.getTypePlans(type, dayDate);
    const typeValue = dayDate.slice(dayDate.length - 2, dayDate.length);

    return { plans, typeValue }
}

export const getDataByType = async (type: PlanTypes, sign: string, id: number, date: dateParams, language: string) => {
    const planList = await DataRequests.getTypeDataByDate(type, getDateAsAString(date, language));
    const data = await DataRequests.getTypeDataById(type, setIdValue(sign, type, id!, planList));

    const { plans, typeValue } = await getPlansByEntryType(type, data, date, language);
    let tempId;
    if (type === EntriesPlanType.DAY)
        tempId = data.dayId;
    else if (type === EntriesPlanType.MONTH)
        tempId = data.monthId;
    else tempId = data.yearId

    return { _id: tempId, _date: { ...date, [`${type}`]: typeValue }, plans };
};

export const setMonthName = (monthNumber: number, language: string) => languagePack[language].MONTHS.namesTable[monthNumber - 1];

export const setMonthValue = (monthName: string, language: string) => languagePack[language].MONTHS.namesTable.map(month => month.toUpperCase()).indexOf(monthName.toUpperCase()) + 1;