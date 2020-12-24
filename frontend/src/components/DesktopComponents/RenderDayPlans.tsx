import React, { useContext } from 'react';
import { LanguageContext } from '../../authentication/LanguageContext';
import { EditEntryParams } from '../../utils/interfaces';
import languagePack from '../../utils/languagePack';
import { FaPen, FaTrash } from 'react-icons/fa'
import EmptyPlans from './EmptyPlans';

const RenderDayPlans = ({ planList, setOpenWindow, id, setRecentlyAddedPlanId, setDeleteWindow }: any) => {
    const { language } = useContext(LanguageContext);

    const editPlan = () => {
        console.log('edit');
    }

    return (
        <div className='day-plans'>
            <EmptyPlans setOpenWindow={setOpenWindow} id={id} setRecentlyAddedPlanId={setRecentlyAddedPlanId} />
            {planList.map(({ dayPlanId, startDate, content, fulfilled }: EditEntryParams) =>
                <details className='question-container entry-container' key={dayPlanId} open>
                    <summary>
                        <span className='content'>{content}</span>
                        <p></p>
                    </summary>
                    <div className='entry-data-container'>
                        <div>
                            <p>{languagePack[language].VIEWENTRY.startTime}<span>{startDate}</span></p>
                            <p>{languagePack[language].VIEWENTRY.description}<span>{content}</span></p>
                        </div>
                        <div className='options'>
                            <span onClick={editPlan}><FaPen /></span>
                            <span onClick={() => setDeleteWindow({ isActive: true, id: dayPlanId })}><FaTrash /></span>
                        </div>
                    </div>
                </details>
            )}
        </div>
    )
}

export default RenderDayPlans;