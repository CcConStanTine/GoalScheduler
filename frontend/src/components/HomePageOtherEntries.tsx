import React, { useEffect, useState, useContext } from 'react';
import { FaChevronRight, FaChevronLeft } from 'react-icons/fa';
import renderTodayPlans from './RenderTodayPlans';
import auth from '../authentication/database';
import { LanguageContext } from '../authentication/LanguageContext';
import { EntriesPlanType, navigationTypes } from '../utils/variables';
import languagePack from '../utils/languagePack';

interface entryParams {
    placeholder: number | null | string;
    plans: any;
    id: number | null;
}

const getActualDateAsAObject = () => {
    const date = new Date()

    return { year: date.getFullYear(), month: date.getMonth() + 1, day: date.getDate() }
}

const setIdValue = (sign: string, entryType: string, id: number, entryList?: any) => {
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

interface dateParams {
    year: number;
    month: number;
    day: number;
}

const setMonthValue = (monthName: string) => {
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


const HomePageOtherEntries = () => {
    const [date, setDate] = useState<dateParams>(getActualDateAsAObject());
    const [id, setId] = useState(null);
    const [entryType, setEntryType] = useState<string>(EntriesPlanType.YEAR);
    const [entryData, setEntryData] = useState<entryParams>({ placeholder: null, plans: [], id: null });
    const { language } = useContext(LanguageContext);

    const changeEntry = async (sign: string) => {
        if (entryType === EntriesPlanType.YEAR) {
            const { yearId, yearNumber } = await auth.getYearByYearId(setIdValue(sign, entryType, id!));
            const plans = await auth.getYearPlansByYearId(yearId);
            setDate({ ...date, year: yearNumber })
            setId(yearId);
            return setEntryData({ placeholder: yearNumber, plans, id: yearId })
        }
        else if (entryType === EntriesPlanType.MONTH) {
            const monthList = await auth.getMonthsByDate(`${date.year}-${date.month < 10 ? `0${date.month}` : date.month}-${date.day}`);
            const { monthId, monthName } = await auth.getMonthById(setIdValue(sign, entryType, id!, monthList));
            setId(monthId);
            setDate({ ...date, month: setMonthValue(monthName) })
            const { plans } = await auth.getMonthPlans(`${date.year}-${setMonthValue(monthName) < 10 ? `0${setMonthValue(monthName)}` : setMonthValue(monthName)}-${date.day}`);
            return setEntryData({ placeholder: monthName, plans, id: monthId });
        }

        const dayList = await auth.getDaysByDate(`${date.year}-${date.month < 10 ? `0${date.month}` : date.month}-${date.day}`);
        const { dayId, dayName, dayDate } = await auth.getDayByDayId(setIdValue(sign, entryType, id!, dayList));
        const { plans } = await auth.getDayPlans(dayDate);
        setId(dayId);
        setDate({ ...date, day: dayDate.slice(dayDate.length - 2, dayDate.length) })
        return setEntryData({ placeholder: dayName, plans, id: dayId })
    }

    const handlePlanData = async (type: string = EntriesPlanType.YEAR, date?: string) => {
        if (type === EntriesPlanType.YEAR) {
            setEntryType(EntriesPlanType.YEAR);
            const plans = await auth.getYearPlans(date);
            setId(plans.id);
            return setEntryData(plans);
        }
        else if (type === EntriesPlanType.MONTH) {
            setEntryType(EntriesPlanType.MONTH)
            const plans = await auth.getMonthPlans(date);
            setId(plans.id);
            return setEntryData(plans)
        }

        setEntryType(EntriesPlanType.DAY);
        const plans = await auth.getDayPlans(date);
        setId(plans.id);
        return setEntryData(await auth.getDayPlans(date))
    }

    useEffect(() => {
        handlePlanData(EntriesPlanType.YEAR)
    }, [])

    const setActiveClassName = (type: string) => {
        if (entryType === type)
            return 'on'

        return null
    }

    const setPlaceholderValue = () => {
        const { year, month, day } = date;
        if (entryType === EntriesPlanType.YEAR) return year
        else if (entryType === EntriesPlanType.MONTH) return `${setMonthName(month, language)} ${year}`

        return `${day} ${setMonthName(month, language)} ${year}`
    }

    return (
        <div className='other-entries-container'>
            <div className='year-navigation'>
                <FaChevronLeft onClick={() => changeEntry(navigationTypes.SUBTRACTION)} />
                <p>{setPlaceholderValue()}</p>
                <FaChevronRight onClick={() => changeEntry(navigationTypes.ADDITION)} />
            </div>
            <div className='entries-navigation'>
                <button
                    className={`default-button ${setActiveClassName(EntriesPlanType.YEAR)}`}
                    onClick={() => handlePlanData(EntriesPlanType.YEAR, `${date.year}-${date.month < 10 ? `0${date.month}` : date.month}-${date.day}`)}
                >
                    {languagePack[language].yearPlanText}
                </button>
                <button
                    className={`default-button ${setActiveClassName(EntriesPlanType.MONTH)}`}
                    onClick={() => handlePlanData(EntriesPlanType.MONTH, `${date.year}-${date.month < 10 ? `0${date.month}` : date.month}-${date.day}`)}
                >
                    {languagePack[language].monthPlanText}
                </button>
                <button
                    className={`default-button ${setActiveClassName(EntriesPlanType.DAY)}`}
                    onClick={() => handlePlanData(EntriesPlanType.DAY, `${date.year}-${date.month < 10 ? `0${date.month}` : date.month}-${date.day}`)}
                >
                    {languagePack[language].dayPlanText}
                </button>
            </div>
            <div className='entries'>
                {renderTodayPlans(entryData.plans)}
            </div>
        </div>
    )
}

export default HomePageOtherEntries;