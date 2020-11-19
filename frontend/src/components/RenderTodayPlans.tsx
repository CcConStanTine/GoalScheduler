import React from 'react';
import { FaCaretRight } from 'react-icons/fa';
import { Link } from 'react-router-dom';

interface planlistInterface {
    [index: number]: {
        content: string;
        dayPlanId?: number;
        yearPlanId?: number;
    };
    map: any;
};

interface planProperties {
    content: string;
    dayPlanId?: number;
    yearPlanId?: number;
}
const renderTodayPlans = (planList: planlistInterface) => planList.map(({ content, dayPlanId, yearPlanId }: planProperties) =>
    <div className='entry' key={content}>
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
