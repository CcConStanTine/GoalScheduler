import React, { useContext } from 'react';
import { LanguageContext } from '../../authentication/LanguageContext';
import { EditEntryParams, RenderDayPlansInterface } from '../../utils/interfaces';
import languagePack from '../../utils/languagePack';
import { FaPen, FaTrash } from 'react-icons/fa';
import { OptionTypes } from '../../utils/enums';

const RenderDayPlans = ({ planList, setDeleteWindow, setEdit, setOptionActiveType }: RenderDayPlansInterface) => {
    const { language } = useContext(LanguageContext);

    return (
        <div className='day-plans'>
            {planList
                .sort((a: EditEntryParams, b: EditEntryParams) => a.dayPlanId! - b.dayPlanId!)
                .map(({ dayPlanId, startDate, content, fulfilled }: EditEntryParams) =>
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
                                <span onClick={() => {
                                    setEdit({ isActive: true, startDate: startDate!, taskDescription: content!, id: dayPlanId! })
                                    setOptionActiveType(OptionTypes.ADD);
                                }}><FaPen /></span>
                                <span onClick={() => setDeleteWindow({ isActive: true, id: dayPlanId! })}><FaTrash /></span>
                            </div>
                        </div>
                    </details>
                )}
        </div>
    )
}

export default RenderDayPlans;