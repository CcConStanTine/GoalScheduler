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
    type: string;
}

interface EntryParams {
    content?: string;
    endDate?: string;
    startDate?: string;
    fulfilled?: boolean;
    dayPlanId?: number;
}

const deletePlanByType = async (type: string, id: number) => {
    if (type === 'year')
        return await auth.deleteYearPlanByYearId(id);
    else if (type === 'month')
        return await auth.deleteMonthPlanByMonthId(id);

    return await auth.deleteDayPlanByPlanId(id)
}

const togglePlanByType = async (type: string, id: number) => {
    if (type === 'year')
        return await auth.toggleFinishYearPlanByPlanId(id);
    else if (type === 'month')
        return await auth.toggleFinishMonthPlanByPlanId(id);

    return await auth.toggleFinishDayPlanByPlanId(id);
}

const getEntryDataByType = async (type: string, id: number) => {
    if (type === 'day')
        return await auth.getPlanByPlanId(id);
    else if (type === 'month')
        return await auth.getMonthPlanByMonthPlanId(id);

    return await auth.getYearPlanByYearPlanId(id);
}

const ViewEntryPage = ({ history }: HomeInterface) => {
    const { language } = useContext(LanguageContext);
    const { id, type } = useParams<RouteParams>();
    const [entry, setEntry] = useState<EntryParams>({});
    const [deleteEntryWindow, showDeleteEntryWindow] = useState<boolean>(false);
    const [finishEntryWindow, showFinishEntryWindow] = useState<boolean>(false);

    useEffect(() => {
        const getEntryData = async () => {
            const entryData = await getEntryDataByType(type, parseInt(id));

            setEntry(entryData);
        }

        getEntryData();
    }, [id, finishEntryWindow, type])

    const deleteEntry = async () => {
        await deletePlanByType(type, parseInt(id));
        return history.goBack();
    }

    const toggleFulfilledPlan = async () => {
        await togglePlanByType(type, parseInt(id));

        return showFinishEntryWindow(false);
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