import React, { useEffect, useState } from 'react';
import { FaChevronRight, FaChevronLeft } from 'react-icons/fa';
import renderTodayPlans from './RenderTodayPlans';
import auth from '../authentication/database';
import { EntriesPlanType } from '../utils/variables';

interface entryParams {
    placeholder: number | null | string;
    plans: any;
}

const HomePageOtherEntries = () => {
    const [entryType, setEntryType] = useState<string>(EntriesPlanType.YEAR);
    const [entryData, setEntryData] = useState<entryParams>({ placeholder: null, plans: [] });

    const handlePlanData = async (type: string = EntriesPlanType.YEAR, date?: string) => {
        if (type === EntriesPlanType.YEAR) {
            setEntryType(EntriesPlanType.YEAR)
            return setEntryData(await auth.getYearPlans(date));
        }
        else if (type === EntriesPlanType.MONTH) {
            setEntryType(EntriesPlanType.MONTH)
            return setEntryData(await auth.getMonthPlans(date))
        }

        setEntryType(EntriesPlanType.DAY)
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
                <FaChevronLeft />
                <p>{entryData.placeholder}</p>
                <FaChevronRight />
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