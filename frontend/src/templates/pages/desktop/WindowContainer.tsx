import React, { useState, useEffect } from 'react';
import DataRequests from '../../../authentication/DataRequests';
import { DeleteWindowInterface, EditDayDesktop, OpenWindow, FulfilledWindowInterface } from '../../../utils/interfaces';
import { OptionTypes, PlanTypes } from '../../../utils/enums';
import { DeleteWindowDefaultValues, EditDayDesktopDefaultValues, FulfilledWindowDefaultValues } from '../../../utils/variables';
import EmptyPlans from '../../../components/desktop/EmptyPlans';
import RenderDayPlans from '../../../components/desktop/RenderDayPlans';
import DeleteWindow from '../../../components/desktop/DeleteWindow';
import FulfilledWindow from '../../../components/desktop/FulfilledWindow';

const WindowContainer = ({ id, type, setOpenWindow }: OpenWindow) => {
    const [dayPlanList, setDayPlanList] = useState([]);
    const [recentlyAddedPlanId, setRecentlyAddedPlanId] = useState<number | null>(null);
    const [deleteWindow, setDeleteWindow] = useState<DeleteWindowInterface>(DeleteWindowDefaultValues);
    const [fulfilledWindow, setFulfilledWindow] = useState<FulfilledWindowInterface>(FulfilledWindowDefaultValues);
    const [edit, setEdit] = useState<EditDayDesktop>(EditDayDesktopDefaultValues);
    const [optionActiveType, setOptionActiveType] = useState<OptionTypes>(OptionTypes.DEFAULT);

    useEffect(() => {
        const getDayPlanList = async () => {
            const dayPlans = await DataRequests.getTypePlansByTypeAndId(PlanTypes.DAY, id!);

            setDayPlanList(dayPlans);
        }

        getDayPlanList();
    }, [id, recentlyAddedPlanId, deleteWindow, fulfilledWindow])

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
                    setFulfilledWindow={setFulfilledWindow}
                    setDeleteWindow={setDeleteWindow}
                    planList={dayPlanList}
                    setEdit={setEdit}
                    setOptionActiveType={setOptionActiveType}
                /> : null}
            </div>

            {deleteWindow.isActive && <DeleteWindow deleteWindow={deleteWindow} setDeleteWindow={setDeleteWindow} />}
            {fulfilledWindow.isActive && <FulfilledWindow fulfilledWindow={fulfilledWindow} setFulfilledWindow={setFulfilledWindow} />}
        </section>
    )
}

export default WindowContainer;