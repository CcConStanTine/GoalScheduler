import React from 'react';
import DataRequests from '../../authentication/DataRequests';
import { PlanTypes } from '../../utils/enums';
import { DateSequences, DateTypes, OpenWindowTypes } from "../../utils/interfaces";
import { ReturnTypeData, ReturnTypeDataById } from "../../utils/requestsInterfaces";
import { setMonthName } from '../OtherEntriesFunctions';

export const getDayValue = (dayName: string) => {
    switch (dayName) {
        case 'MONDAY':
            return 0;
        case 'TUESDAY':
            return 1;
        case 'WEDNESDAY':
            return 2;
        case 'THURSDAY':
            return 3;
        case 'FRIDAY':
            return 4;
        case 'SATURDAY':
            return 5;
        case 'SUNDAY':
            return 6;
        default:
            return 0;
    }
}

export const renderDays = (dayList: ReturnTypeData | [], setContextMenu: any, setOpenWindow: any) => {
    const firstDayOfTheMonth = getDayValue(dayList[0].dayName ? dayList[0].dayName : 'MONDAY');
    const dayTable = [];

    let timer: number;

    const renderDots = (number: number) => {
        const dots = [];

        for (let i = 0; i < number; i++) {
            dots.push(i);
        }

        return <div className='dot-container'>
            {dots.map((dot: number) => <span className='dot' key={dot}></span>)}
        </div>
    }

    const pressedDayButton = (dayId: number) => {
        timer = window.setTimeout(() => {
            setOpenWindow({ isActive: true, id: dayId, type: OpenWindowTypes.SHOW })
        }, 1000);
    }

    const releaseDayButton = () => clearTimeout(timer)

    for (let i = 0; i < firstDayOfTheMonth; i++) {
        dayTable.push({ empty: true, id: `day${i}` });
    };

    dayList.forEach((element: ReturnTypeDataById) => dayTable.push(element));

    return dayTable.map(({ id, empty, plansNumber }: any, index: number) => <button
        disabled={empty}
        className={empty ? 'day empty' : 'day'}
        onMouseUp={() => releaseDayButton()}
        onMouseDown={() => pressedDayButton(id)}
        onClick={() => setOpenWindow({ isActive: true, id: id, type: OpenWindowTypes.SHOW })}
        onContextMenu={event => {
            event.preventDefault();
            setContextMenu({ pageX: event.pageX - 20, pageY: event.pageY - 20, id, isActive: true })
        }}
        key={empty ? id : id}>
        {empty ? null : index - firstDayOfTheMonth + 1}
        {Boolean(plansNumber) && renderDots(plansNumber)}
    </button>);
}

export const setDateValue = (date: string, language: string) => {
    const monthName = setMonthName(DataRequests.getSequenceFromDate(date, DateSequences.MONTH), language);
    const year = DataRequests.getSequenceFromDate(date, DateSequences.YEAR);

    return `${monthName} ${year}`;
};

export const checkIfThisDayHaveAPlan = async (id: number) => {
    const planList = await DataRequests.getTypePlansByTypeAndId(PlanTypes.DAY, id);

    return planList.length;
};

export const getActualPlans = async (date: string): Promise<ReturnTypeData> => {
    const _date = DataRequests.convertDate(DateTypes.REQUEST, date);
    const plans = await DataRequests.getTypeDataByDate(PlanTypes.DAY, _date);

    return plans;
};

export const generatePlansInfo = async (plans: any) => {
    const plansInfo: any = [];

    for (const { dayId: id, dayName } of plans) {
        plansInfo.push({ id, dayName, plansNumber: await checkIfThisDayHaveAPlan(id) });
    }

    return plansInfo;
};