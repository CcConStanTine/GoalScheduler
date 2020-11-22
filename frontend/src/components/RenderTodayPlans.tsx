import React from 'react';
import { FaCaretRight } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import { entryParams, singleEntryParams } from '../utils/interfaces';
import { PlanTypes } from '../utils/variables';

const renderTodayPlans = (planList: entryParams) => planList.map(({ content, dayPlanId, yearPlanId, monthPlanId }: singleEntryParams) => {
    const checkLinkValue = () => {
        if (dayPlanId)
            return `${PlanTypes.DAY}/${dayPlanId}`;
        else if (monthPlanId)
            return `${PlanTypes.MONTH}/${monthPlanId}`;

        return `${PlanTypes.YEAR}/${yearPlanId}`;
    }

    return (
        <div className='entry' key={`${content}${dayPlanId ? dayPlanId : yearPlanId}`}>
            <div className='entry-info'>
                <span className='color'></span>
                <p>{content}</p>
            </div>
            <Link to={`view/${checkLinkValue()}`}>
                <FaCaretRight />
            </Link>
        </div>
    );
})



export default renderTodayPlans;
