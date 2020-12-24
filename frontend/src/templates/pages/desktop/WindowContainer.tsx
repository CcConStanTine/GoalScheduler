import React, { useState, useEffect } from 'react';
import DataRequests from '../../../authentication/DataRequests';
import { OpenWindow } from '../../../utils/interfaces';
import { PlanTypes } from '../../../utils/enums';
import EmptyPlans from '../../../components/DesktopComponents/EmptyPlans';
import RenderDayPlans from '../../../components/DesktopComponents/RenderDayPlans';

interface DeleteWindow {
    isActive: boolean;
    id: null | number;
}

const WindowContainer = ({ id, type, setOpenWindow }: OpenWindow) => {
    const [dayPlanList, setDayPlanList] = useState([]);
    const [recentlyAddedPlanId, setRecentlyAddedPlanId] = useState<number | null>(null);
    const [deleteWindow, setDeleteWindow] = useState<DeleteWindow>({ isActive: false, id: null });

    const deleteTask = async () => {
        const { status } = await DataRequests.deletePlanByTypeAndId(PlanTypes.DAY, deleteWindow.id!);

        if (!status)
            return setDeleteWindow({ isActive: false, id: null });
    }

    useEffect(() => {
        const getDayPlanList = async () => {
            const dayPlans = await DataRequests.getTypePlansByTypeAndId(PlanTypes.DAY, id!);

            setDayPlanList(dayPlans);
        }

        getDayPlanList();
    }, [id, recentlyAddedPlanId, deleteWindow])

    return (
        <section className={`window-container ${type}`} onClick={() => setOpenWindow!({ isActive: false })}>
            <div className='type-container' onClick={event => event.stopPropagation()}>
                {dayPlanList.length ?
                    <RenderDayPlans
                        setDeleteWindow={setDeleteWindow}
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
            {deleteWindow.isActive &&
                <div className='delete-container' onClick={event => {
                    setDeleteWindow({ isActive: false, id: null });
                    event.stopPropagation();
                }}>
                    <div className='delete-window' onClick={event => event.stopPropagation()}>
                        <p>Are you sure you want to delete this task?</p>
                        <div>
                            <button
                                onClick={deleteTask}
                                className='desktop-default-button two'>Yes</button>
                            <button
                                onClick={() => setDeleteWindow({ isActive: false, id: null })}
                                className='desktop-default-button two'>No</button>
                        </div>
                    </div>
                </div>
            }
        </section>
    )
}

export default WindowContainer;