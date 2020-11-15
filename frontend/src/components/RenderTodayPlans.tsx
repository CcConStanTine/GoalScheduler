import React from 'react';
import { FaCaretRight } from 'react-icons/fa';
import { Link } from 'react-router-dom';

interface plansInterface {
    [index: number]: {
        content: string;
        dayPlanId: number;
    };
    map: any;
};

interface test {
    content: string;
}
const renderTodayPlans = (plans: plansInterface) => plans.map(({ content }: test) =>
    <div className='entry' key={content}>
        <div className='entry-info'>
            <span className='color'></span>
            <p>{content}</p>
        </div>
        <Link to="/app/settings">
            <FaCaretRight />
        </Link>
    </div>
)


export default renderTodayPlans;
