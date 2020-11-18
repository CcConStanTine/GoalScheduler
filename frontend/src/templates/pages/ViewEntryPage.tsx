import React, { useContext, useState, useEffect } from 'react';
import { PageNavigationTypes, EntryPageConfirmWindowTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { useParams } from "react-router-dom";
import { FaPen, FaCheck, FaTimes } from 'react-icons/fa';
import EntryPageConfirmWindow from '../../components/EntryPageConfirmWindow';

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

            {deleteEntryWindow && <EntryPageConfirmWindow
                type={EntryPageConfirmWindowTypes.DELETE}
                placeholder={languagePack[language].entryDeleteText}
                optionText={languagePack[language].deleteText}
                closeWindowFunction={showDeleteEntryWindow}
                sendRequestFunction={deleteEntry}
            />}

            {finishEntryWindow && <EntryPageConfirmWindow
                type={EntryPageConfirmWindowTypes.FINISH}
                placeholder={languagePack[language].successfulyFulfilledText}
                optionText={languagePack[language].successfulyFulfilledTextSendOption}
                closeWindowFunction={showFinishEntryWindow}
                sendRequestFunction={toggleFulfilledPlan}
                fulfilled={entry?.fulfilled}
                unFulfilledPlaceholder={languagePack[language].unFulfilledText}
                unFullfilledOptionText={languagePack[language].unFulfilledTextSendOption}
            />}

            {entry?.fulfilled && <div className='fulfilled'>
                <p>{languagePack[language].viewFulfilledPlan}</p>
            </div>}
        </section>
    )
}

export default ViewEntryPage;