import React, { MouseEvent, useContext } from 'react';
import DataRequests from '../../authentication/DataRequests';
import { LanguageContext } from '../../authentication/LanguageContext';
import { PlanTypes } from '../../utils/enums';
import { FulfilledMessage } from '../../utils/interfaces';
import languagePack from '../../utils/languagePack';
import { FulfilledWindowDefaultValues } from '../../utils/variables';

const FulfilledWindow = ({ fulfilledWindow, setFulfilledWindow }: FulfilledMessage) => {
    const { language } = useContext(LanguageContext);
    const { fulfilled, id } = fulfilledWindow;

    const fulfillTask = async () => {
        const { status } = await DataRequests.toggleFinishPlanByTypeAndId(PlanTypes.DAY, id!);

        return !status && setFulfilledWindow(FulfilledWindowDefaultValues);
    }

    const closeWindow = (event: MouseEvent) => {
        setFulfilledWindow(FulfilledWindowDefaultValues);
        event.stopPropagation();
    }

    return (
        <div className='delete-container' onClick={closeWindow}>
            <div className='delete-window' onClick={event => event.stopPropagation()}>
                <p>{fulfilled ? languagePack[language].VIEWENTRY.restoreTask : languagePack[language].VIEWENTRY.finishedTask}</p>
                <div>
                    <button
                        onClick={fulfillTask}
                        className='desktop-default-button two'>
                        {fulfilled ? languagePack[language].VIEWENTRY.restoreTaskConfirm : languagePack[language].VIEWENTRY.finishedTaskConfirm}
                    </button>
                    <button
                        onClick={() => setFulfilledWindow(FulfilledWindowDefaultValues)}
                        className='desktop-default-button two'>
                        {languagePack[language].GLOBAL.cancel}
                    </button>
                </div>
            </div>
        </div>
    )
}

export default FulfilledWindow;