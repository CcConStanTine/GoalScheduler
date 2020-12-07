import React, { useEffect, useState } from 'react';
import renderPlanEntries from './RenderPlanEntries';
import { EntriesPlanType } from '../utils/variables';
import RenderEntriesNavigation from './RenderEntriesNavigation';
import RenderEntriesDateNavigation from './RenderEntriesDateNavigation';
import { getActualDateAsAObject, getDataByType } from './OtherEntriesFunctions';
import { dateParams, entryParams } from '../utils/interfaces';
import auth from '../authentication/database';

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
        const { plans, id } = await auth.getTypePlans(type, date);
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
                {renderPlanEntries(entryData)}
            </div>
        </div>
    )
}

export default HomePageOtherEntries;