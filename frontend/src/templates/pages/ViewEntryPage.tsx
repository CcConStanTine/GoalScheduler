import React, { useContext, useState, useEffect } from 'react';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { useParams } from "react-router-dom";
import { FaPen } from 'react-icons/fa';

interface HomeInterface {
    history: any
}

interface RouteParams {
    id: string;
}

interface EntryParams {
    content?: string;
    endDate?: string;
    startDate?: string;
    fulfilled?: boolean;
    dayPlanId?: number;
}

const ViewEntryPage = ({ history }: HomeInterface) => {
    const { language } = useContext(LanguageContext);
    const { id } = useParams<RouteParams>();
    const [entry, setEntry] = useState<EntryParams>({});

    useEffect(() => {
        const getEntryData = async () => {
            const entryData = await auth.getPlanByPlanId(parseInt(id));

            setEntry(entryData);
        }

        getEntryData();
    }, [id])

    return (
        <section className={`view-entry-page ${entry?.fulfilled}`}>
            <NavigationBar type={PageNavigationTypes.VIEWENTRY} history={history} placeholder={languagePack[language].viewEntry} />
            <div className='content'>
                <div className="title">
                    <p>{languagePack[language].viewStartDate}</p>
                    <p>{languagePack[language].viewEndDate}</p>
                    <p>{languagePack[language].viewPlanDescription}</p>
                </div>
                <div className="data">
                    <p>{entry?.startDate}</p>
                    <p>{entry?.endDate}</p>
                    <p>{entry?.content}</p>
                </div>
            </div>
            <button className='edit-button'>
                <FaPen />
            </button>
            {entry?.fulfilled && <div className='fulfilled'>
                <p>{languagePack[language].viewFulfilledPlan}</p>
            </div>}
        </section>
    )
}

export default ViewEntryPage;