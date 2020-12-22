import React from 'react';
import { OpenWindowTypes, OptionActiveInterface } from '../../utils/interfaces';
import { checkIfDateIsLowerThanCurrentDate, formatDate } from './EmptyPlansFunction';

const EmptyPlansInfoAndNavigation = ({ setOpenWindow, id, language, dateValue, date, setOptionActiveType }: OptionActiveInterface) => (
    <div className='empty-plans'>
        <p onClick={() => setOpenWindow!({ isActive: true, id: id! - 1, type: OpenWindowTypes.SHOW })}><span className='chevron previous'></span></p>
        <div>
            <p className='date'>{date && formatDate(dateValue, language)}</p>
            {checkIfDateIsLowerThanCurrentDate(dateValue, language, setOptionActiveType)}
        </div>
        <p onClick={() => setOpenWindow!({ isActive: true, id: id! + 1, type: OpenWindowTypes.SHOW })}><span className='chevron next'></span></p>
    </div>
)

export default EmptyPlansInfoAndNavigation;