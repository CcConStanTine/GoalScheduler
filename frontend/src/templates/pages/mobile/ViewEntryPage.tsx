import React, { useContext, useState, useEffect } from 'react';
import { PageNavigationTypes, EntryPageConfirmWindowTypes } from '../../../utils/variables';
import { Link } from 'react-router-dom';
import languagePack from '../../../utils/languagePack';
import NavigationBar from '../../../components/mobile/NavigationBar';
import { LanguageContext } from '../../../authentication/LanguageContext';
import DataRequests from '../../../authentication/DataRequests';
import { useParams } from "react-router-dom";
import { FaPen, FaCheck, FaTimes } from 'react-icons/fa';
import EntryPageConfirmWindow from '../../../components/mobile/EntryPageConfirmWindow';
import { ViewEntryRouteParams } from '../../../utils/interfaces';
import { Plans } from '../../../utils/requestsInterfaces';
import { useHistory } from 'react-router-dom';

const ViewEntryPage = (): JSX.Element => {
    const { language } = useContext(LanguageContext);
    const { id, type } = useParams<ViewEntryRouteParams>();
    const [entry, setEntry] = useState<Plans>();
    const [deleteEntryWindow, showDeleteEntryWindow] = useState<boolean>(false);
    const [finishEntryWindow, showFinishEntryWindow] = useState<boolean>(false);
    const history = useHistory();

    useEffect(() => {
        const getEntryData = async (): Promise<void> => {
            const entryData = await DataRequests.getPlanByTypeAndId(type, parseInt(id))

            setEntry(entryData);
        }

        getEntryData();
    }, [id, finishEntryWindow, type])

    const deleteEntry = async (): Promise<void> => {
        await DataRequests.deletePlanByTypeAndId(type, parseInt(id));
        return history.goBack();
    }

    const toggleFulfilledPlan = async (): Promise<void> => {
        await DataRequests.toggleFinishPlanByTypeAndId(type, parseInt(id));

        return showFinishEntryWindow(false);
    }

    return (
        <section className={`view-entry-page ${entry?.fulfilled}`}>
            <NavigationBar
                type={PageNavigationTypes.VIEWENTRY}
                placeholder={languagePack[language].VIEWENTRY.title}
                deleteFunction={showDeleteEntryWindow}
            />
            <div className='content'>
                <div className="title">
                    <p>{languagePack[language].VIEWENTRY.startTime}</p>
                    <p>{languagePack[language].VIEWENTRY.endTime}</p>
                    <p>{languagePack[language].VIEWENTRY.description}</p>
                </div>
                <div className="data">
                    <p>{entry?.startDate}</p>
                    <p>{entry?.endDate}</p>
                    <p>{entry?.content}</p>
                </div>
            </div>
            <button className='functionBtn edit-button'>
                <Link to={`/app/edit/${type}/${id}`}>
                    <FaPen />
                </Link>
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
                placeholder={languagePack[language].VIEWENTRY.deleteTask}
                optionText={languagePack[language].VIEWENTRY.deleteTaskConfirm}
                closeWindowFunction={showDeleteEntryWindow}
                sendRequestFunction={deleteEntry}
            />}

            {finishEntryWindow && <EntryPageConfirmWindow
                type={EntryPageConfirmWindowTypes.FINISH}
                placeholder={languagePack[language].VIEWENTRY.finishedTask}
                optionText={languagePack[language].VIEWENTRY.finishedTaskConfirm}
                closeWindowFunction={showFinishEntryWindow}
                sendRequestFunction={toggleFulfilledPlan}
                fulfilled={entry?.fulfilled}
                unFulfilledPlaceholder={languagePack[language].VIEWENTRY.restoreTask}
                unFullfilledOptionText={languagePack[language].VIEWENTRY.restoreTaskConfirm}
            />}

            {entry?.fulfilled && <div className='fulfilled'>
                <p>{languagePack[language].VIEWENTRY.fulfilledTask}</p>
            </div>}
        </section>
    )
}

export default ViewEntryPage;