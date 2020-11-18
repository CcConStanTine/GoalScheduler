import React, { useContext, useState, useEffect } from 'react';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { useParams } from "react-router-dom";
import { FaPen, FaCheck, FaTimes } from 'react-icons/fa';

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
    const [deleteEntryWindow, showDeleteEntryWindow] = useState<boolean>(false);
    const [finishEntryWindow, showFinishEntryWindow] = useState<boolean>(false);

    useEffect(() => {
        const getEntryData = async () => {
            const entryData = await auth.getPlanByPlanId(parseInt(id));

            setEntry(entryData);
        }

        getEntryData();
    }, [id, finishEntryWindow])

    const deleteEntry = async () => {
        await auth.deletePlanByPlanId(parseInt(id));
        return history.goBack();
    }

    const toggleFulfilledPlan = async () => {
        await auth.toggleFinishPlanByPlanId(parseInt(id));
        showFinishEntryWindow(false);
    }

    return (
        <section className={`view-entry-page ${entry?.fulfilled}`}>
            <NavigationBar
                type={PageNavigationTypes.VIEWENTRY}
                history={history}
                placeholder={languagePack[language].viewEntry}
                deleteFunction={showDeleteEntryWindow}
            />
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
            <button className='functionBtn edit-button'>
                <FaPen />
            </button>
            <button className={`functionBtn finish-button completed-${entry?.fulfilled}`}>
                {entry?.fulfilled ?
                    <FaTimes onClick={() => showFinishEntryWindow(true)} />
                    :
                    <FaCheck onClick={() => showFinishEntryWindow(true)} />
                }
            </button>

            {deleteEntryWindow && <div className='delete-window'>
                <div className='message-container'>
                    <p>{languagePack[language].entryDeleteText}</p>
                    <div className='options'>
                        <button className='default-button' onClick={deleteEntry}>{languagePack[language].deleteText}</button>
                        <button className='default-button' onClick={() => showDeleteEntryWindow(false)}>{languagePack[language].cancelText}</button>
                    </div>
                </div>
            </div>}

            {finishEntryWindow && <div className='finish-window'>
                <div className='message-container'>
                    <p>{entry?.fulfilled ? languagePack[language].unFulfilledText : languagePack[language].successfulyFulfilledText}</p>
                    <div className='options'>
                        <button className='default-button' onClick={toggleFulfilledPlan}>
                            {entry?.fulfilled ? languagePack[language].unFulfilledTextSendOption : languagePack[language].successfulyFulfilledTextSendOption}
                        </button>
                        <button className='default-button' onClick={() => showFinishEntryWindow(false)}>{languagePack[language].cancelText}</button>
                    </div>
                </div>
            </div>}

            {entry?.fulfilled && <div className='fulfilled'>
                <p>{languagePack[language].viewFulfilledPlan}</p>
            </div>}
        </section>
    )
}

export default ViewEntryPage;