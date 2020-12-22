import React, { useContext } from 'react';
import { LanguageContext } from '../../authentication/LanguageContext';
import { EditEntryParams } from '../../utils/interfaces';
import languagePack from '../../utils/languagePack';
import { FaPen, FaTrash } from 'react-icons/fa'
import EmptyPlans from './EmptyPlans';

const RenderDayPlans = ({ planList, setOpenWindow, id }: any) => {
    const { language } = useContext(LanguageContext)
    return (
        <div className='day-plans'>
            <EmptyPlans empty={false} setOpenWindow={setOpenWindow} id={id} />
            {planList.map(({ dayPlanId, startDate, endDate, content, fulfilled }: EditEntryParams) =>
                <details className='question-container entry-container' key={dayPlanId} open>
                    <summary>
                        <span className='content'>{content}</span>
                        <p></p>
                    </summary>
                    <div className='entry-data-container'>
                        <div>
                            <p>{languagePack[language].VIEWENTRY.startTime}<span>{startDate}</span></p>
                            <p>{languagePack[language].VIEWENTRY.endTime}<span>{endDate}</span></p>
                            <p>{languagePack[language].VIEWENTRY.description}<span>{content}</span></p>
                        </div>
                        <div className='options'>
                            <span onClick={() => console.log('edit')}><FaPen /></span>
                            <span onClick={() => console.log('delete')}><FaTrash /></span>
                        </div>
                    </div>
                </details>
            )}
        </div>
    )
}

export default RenderDayPlans;