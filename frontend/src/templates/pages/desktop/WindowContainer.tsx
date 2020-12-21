import React, { useState, useEffect } from 'react';
import DataRequests from '../../../authentication/DataRequests';
import { OpenWindow } from '../../../utils/interfaces';
import { PlanTypes } from '../../../utils/enums';

const WindowContainer = ({ id, type, setOpenWindow }: OpenWindow) => {
    const [dayPlanList, setDayPlanList] = useState([]);

    useEffect(() => {
        const getDayPlanList = async () => {
            const dayPlans = await DataRequests.getTypePlansByTypeAndId(PlanTypes.DAY, id!);

            setDayPlanList(dayPlans);
        }

        getDayPlanList();
    }, [id])

    return (
        <section className={`window-container ${type}`} onClick={() => setOpenWindow!({ isActive: false })}>
            <div className='type-container' onClick={event => event.stopPropagation()}>
                {console.log(dayPlanList)}
                <h1>Hello {type} {id}</h1>
            </div>
        </section>
    )
}

export default WindowContainer;