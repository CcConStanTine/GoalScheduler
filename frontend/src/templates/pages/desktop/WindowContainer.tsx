import React, { useState, useEffect } from 'react';
import DataRequests from '../../../authentication/DataRequests';
import { DeleteWindowInterface, EditDayDesktop, OpenWindow } from '../../../utils/interfaces';
import { OptionTypes, PlanTypes } from '../../../utils/enums';
import EmptyPlans from '../../../components/DesktopComponents/EmptyPlans';
import RenderDayPlans from '../../../components/DesktopComponents/RenderDayPlans';
import DeleteWindow from '../../../components/DesktopComponents/DeleteWindow';

const WindowContainer = ({ id, type, setOpenWindow }: OpenWindow) => {
    const [dayPlanList, setDayPlanList] = useState([]);
    const [recentlyAddedPlanId, setRecentlyAddedPlanId] = useState<number | null>(null);
    const [deleteWindow, setDeleteWindow] = useState<DeleteWindowInterface>({ isActive: false, id: null });
    const [edit, setEdit] = useState<EditDayDesktop>({ isActive: false, startDate: '', taskDescription: '', id: null });
    const [optionActiveType, setOptionActiveType] = useState<OptionTypes>(OptionTypes.DEFAULT);

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
                <EmptyPlans
                    setOpenWindow={setOpenWindow}
                    id={id!}
                    setRecentlyAddedPlanId={setRecentlyAddedPlanId}
                    edit={edit}
                    setEdit={setEdit}
                    setOptionActiveType={setOptionActiveType}
                    optionActiveType={optionActiveType}
                />

                {dayPlanList.length ? <RenderDayPlans
                    setDeleteWindow={setDeleteWindow}
                    planList={dayPlanList}
                    setEdit={setEdit}
                    setOptionActiveType={setOptionActiveType}
                /> : null}
            </div>

            {deleteWindow.isActive && <DeleteWindow deleteWindow={deleteWindow} setDeleteWindow={setDeleteWindow} />}
        </section>
    )
}

export default WindowContainer;