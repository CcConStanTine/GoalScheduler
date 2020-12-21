import React, { useEffect, useState, useContext } from 'react';
import renderPlanEntries from './RenderPlanEntries';
import RenderEntriesNavigation from './RenderEntriesNavigation';
import RenderEntriesDateNavigation from './RenderEntriesDateNavigation';
import { getActualDateAsAObject, getDataByType } from './OtherEntriesFunctions';
import { dateParams, entryParams } from '../utils/interfaces';
import DataRequests from '../authentication/DataRequests';
import { LanguageContext } from '../authentication/LanguageContext';
import { Link } from 'react-router-dom';
import languagePack from '../utils/languagePack';
import { PlanTypes } from '../utils/enums';

const HomePageOtherEntries = (): JSX.Element => {
    const [date, setDate] = useState<dateParams>(getActualDateAsAObject());
    const [id, setId] = useState<number | undefined>();
    const [entryType, setEntryType] = useState<PlanTypes>(PlanTypes.YEAR);
    const [entryData, setEntryData] = useState<entryParams>([]);
    const { language } = useContext(LanguageContext)

    const changeEntry = async (sign: string): Promise<void> => {
        const { _id, _date, plans } = await getDataByType(entryType, sign, id!, date);
        setId(_id);
        setDate(_date);

        return setEntryData(plans);
    };

    const handlePlanData = async (type: PlanTypes = PlanTypes.YEAR, date?: string): Promise<void> => {
        setEntryType(type);
        const _date = date ? DataRequests.validateDate(date) : DataRequests.getCurrentDate();
        const id = await DataRequests.getPlanIdByTypeAndDate(type, _date);
        const plans = await DataRequests.getTypePlans(type, date);
        setId(id);

        return setEntryData(plans);
    };

    useEffect(() => { handlePlanData(PlanTypes.YEAR) }, []);

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
                {!entryData.length ?
                    <div className='empty-entry'>
                        <p>{languagePack[language].HOME.emptyEntries}</p>
                        <Link to={`/app/add/${entryType}`}>
                            <button className='default-button'>{languagePack[language].HOME.addEntry}</button>
                        </Link>
                    </div>
                    : renderPlanEntries(entryData)}
            </div>
        </div>
    )
}

export default HomePageOtherEntries;