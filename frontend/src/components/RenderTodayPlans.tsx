import React from 'react';
import { FaCaretRight } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import { entryParams, singleEntryParams } from '../utils/interfaces';

const renderTodayPlans = (planList: entryParams) => planList.map(({ content, dayPlanId, yearPlanId }: singleEntryParams) =>
    <div className='entry' key={`${content}${dayPlanId ? dayPlanId : yearPlanId}`}>
        <div className='entry-info'>
            <span className='color'></span>
            <p>{content}</p>
        </div>
        <Link to={`view/${dayPlanId ? dayPlanId : yearPlanId}`}>
            <FaCaretRight />
        </Link>
    </div>
)


export default renderTodayPlans;
