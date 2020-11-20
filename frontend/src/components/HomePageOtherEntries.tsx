import React, { useEffect, useState } from 'react';
import renderTodayPlans from './RenderTodayPlans';
import { EntriesPlanType } from '../utils/variables';
import RenderEntriesNavigation from './RenderEntriesNavigation';
import RenderEntriesDateNavigation from './RenderEntriesDateNavigation';
import { getActualDateAsAObject, getRequestByType, getDataByType } from './OtherEntriesFunctions';
import { dateParams, entryParams } from '../utils/interfaces';

const HomePageOtherEntries = () => {
    const [date, setDate] = useState<dateParams>(getActualDateAsAObject());
    const [id, setId] = useState<number | null>(null);
    const [entryType, setEntryType] = useState<string>(EntriesPlanType.YEAR);
    const [entryData, setEntryData] = useState<entryParams>([]);

    const changeEntry = async (sign: string) => {
        const { _id, _date, plans } = await getDataByType(entryType, sign, id!, date);
        setId(_id);
        setDate(_date);

        return setEntryData(plans);
    };

    const handlePlanData = async (type: string = EntriesPlanType.YEAR, date?: string) => {
        setEntryType(type);
        const { plans, id } = await getRequestByType(type, date);
        setId(id);

        return setEntryData(plans);
    };

    useEffect(() => { handlePlanData(EntriesPlanType.YEAR) }, []);

    return (
        <div className='other-entries-container'>
            <div className='data-navigation'>
                <RenderEntriesDateNavigation
                    entryType={entryType}
                    date={date}
                    onClick={changeEntry}
                />
            </div>
            <div className='entries-navigation'>
                <RenderEntriesNavigation
                    entryType={entryType}
                    onClick={handlePlanData}
                    date={date}
                />
            </div>
            <div className='entries'>
                {renderTodayPlans(entryData)}
            </div>
        </div>
    )
}

export default HomePageOtherEntries;