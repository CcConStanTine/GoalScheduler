import React, { useState, useEffect } from 'react';
import DataRequests from '../../../authentication/DataRequests';
import { OpenWindow } from '../../../utils/interfaces';
import { PlanTypes } from '../../../utils/enums';
import EmptyPlans from '../../../components/DesktopComponents/EmptyPlans';
import RenderDayPlans from '../../../components/DesktopComponents/RenderDayPlans';

const WindowContainer = ({ id, type, setOpenWindow }: OpenWindow) => {
    const [dayPlanList, setDayPlanList] = useState([]);
    const [recentlyAddedPlanId, setRecentlyAddedPlanId] = useState<number | null>(null);

    useEffect(() => {
        const getDayPlanList = async () => {
            const dayPlans = await DataRequests.getTypePlansByTypeAndId(PlanTypes.DAY, id!);

            setDayPlanList(dayPlans);
        }

        getDayPlanList();
    }, [id, recentlyAddedPlanId])

    return (
        <section className={`window-container ${type}`} onClick={() => setOpenWindow!({ isActive: false })}>
            <div className='type-container' onClick={event => event.stopPropagation()}>
                {dayPlanList.length ?
                    <RenderDayPlans
                        setRecentlyAddedPlanId={setRecentlyAddedPlanId}
                        planList={dayPlanList}
                        setOpenWindow={setOpenWindow}
                        id={id!} />
                    :
                    <EmptyPlans
                        setRecentlyAddedPlanId={setRecentlyAddedPlanId}
                        setOpenWindow={setOpenWindow}
                        id={id!} />}
            </div>
        </section>
    )
}

export default WindowContainer;