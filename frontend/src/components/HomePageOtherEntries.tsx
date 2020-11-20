import React, { useEffect, useState } from 'react';
import { FaChevronRight, FaChevronLeft } from 'react-icons/fa';
import renderTodayPlans from './RenderTodayPlans';
import auth from '../authentication/database';
import { EntriesPlanType, navigationTypes } from '../utils/variables';

interface entryParams {
    placeholder: number | null | string;
    plans: any;
    id: number | null;
}

const setIdValue = (sign: string, entryType: string, id: number) => {
    if (entryType === EntriesPlanType.MONTH) {
        if (sign === navigationTypes.ADDITION) {
            ++id;
            if (id > 24) return 13;

            return id;
        }
        else {
            --id;
            if (id < 13) return 24;

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

    return 1;
}

const HomePageOtherEntries = () => {
    const [id, setId] = useState(null)
    const [entryType, setEntryType] = useState<string>(EntriesPlanType.YEAR);
    const [entryData, setEntryData] = useState<entryParams>({ placeholder: null, plans: [], id: null });

    const changeEntry = async (sign: string) => {
        if (entryType === EntriesPlanType.YEAR) {
            const { yearId, yearNumber } = await auth.getYearByYearId(setIdValue(sign, entryType, id!));
            const plans = await auth.getMonthPlansByMonthId(yearId);
            setId(yearId);
            return setEntryData({ placeholder: yearNumber, plans, id: yearId })
        }
        else if (entryType === EntriesPlanType.MONTH) {
            const { monthId, monthName } = await auth.getMonthById(setIdValue(sign, entryType, id!));
            const plans = await auth.getMonthPlansByMonthId(monthId);
            setId(monthId);
            return setEntryData({ placeholder: monthName, plans, id: monthId })
        }

        const { dayId, dayName } = await auth.getDayByDayId(setIdValue(sign, entryType, id!));
        const plans = await auth.getMonthPlansByMonthId(dayId);
        setId(dayId);
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
                <p>{entryData.placeholder}</p>
                <FaChevronRight onClick={() => changeEntry(navigationTypes.ADDITION)} />
            </div>
            <div className='entries-navigation'>
                <button className={`default-button ${setActiveClassName(EntriesPlanType.YEAR)}`}
                    onClick={() => handlePlanData(EntriesPlanType.YEAR)}>Year Plan</button>
                <button className={`default-button ${setActiveClassName(EntriesPlanType.MONTH)}`}
                    onClick={() => handlePlanData(EntriesPlanType.MONTH)}>Month Plan</button>
                <button className={`default-button ${setActiveClassName(EntriesPlanType.DAY)}`}
                    onClick={() => handlePlanData(EntriesPlanType.DAY)}>Day Plan</button>
            </div>
            <div className='entries'>
                {renderTodayPlans(entryData.plans)}
            </div>
        </div>
    )
}

export default HomePageOtherEntries;