import React, { useEffect, useState, useContext } from 'react';
import { FaChevronRight, FaChevronLeft } from 'react-icons/fa';
import renderTodayPlans from './RenderTodayPlans';
import auth from '../authentication/database';
import { LanguageContext } from '../authentication/LanguageContext';
import { EntriesPlanType, navigationTypes } from '../utils/variables';
import languagePack from '../utils/languagePack';
import { setPlaceholderValue, setMonthValue, getDateAsAString, getActualDateAsAObject, setIdValue } from './OtherEntriesFunctions';

interface entryParams {
    placeholder: number | null | string;
    plans: any;
    id: number | null;
}

interface dateParams {
    year: number;
    month: number;
    day: number;
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
            const monthList = await auth.getMonthsByDate(getDateAsAString(date));
            const { monthId, monthName } = await auth.getMonthById(setIdValue(sign, entryType, id!, monthList));
            setId(monthId);
            setDate({ ...date, month: setMonthValue(monthName) })
            const { plans } = await auth.getMonthPlans(getDateAsAString(date, monthName));
            return setEntryData({ placeholder: monthName, plans, id: monthId });
        }

        const dayList = await auth.getDaysByDate(getDateAsAString(date));
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

    return (
        <div className='other-entries-container'>
            <div className='year-navigation'>
                <FaChevronLeft onClick={() => changeEntry(navigationTypes.SUBTRACTION)} />
                <p>{setPlaceholderValue(date, entryType, language)}</p>
                <FaChevronRight onClick={() => changeEntry(navigationTypes.ADDITION)} />
            </div>
            <div className='entries-navigation'>
                <button
                    className={`default-button ${setActiveClassName(EntriesPlanType.YEAR)}`}
                    onClick={() => handlePlanData(EntriesPlanType.YEAR, getDateAsAString(date))}
                >
                    {languagePack[language].yearPlanText}
                </button>
                <button
                    className={`default-button ${setActiveClassName(EntriesPlanType.MONTH)}`}
                    onClick={() => handlePlanData(EntriesPlanType.MONTH, getDateAsAString(date))}
                >
                    {languagePack[language].monthPlanText}
                </button>
                <button
                    className={`default-button ${setActiveClassName(EntriesPlanType.DAY)}`}
                    onClick={() => handlePlanData(EntriesPlanType.DAY, getDateAsAString(date))}
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