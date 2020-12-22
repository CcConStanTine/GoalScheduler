import React from 'react';
import { OpenWindowTypes } from "../../utils/interfaces";
import { ReturnTypeData, ReturnTypeDataById } from "../../utils/requestsInterfaces";

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

    return dayTable.map(({ dayId, empty, id }: any, index: number) => <button
        disabled={empty}
        className={empty ? 'day empty' : 'day'}
        onMouseUp={() => releaseDayButton()}
        onMouseDown={() => pressedDayButton(dayId)}
        onClick={() => setOpenWindow({ isActive: true, id: dayId, type: OpenWindowTypes.SHOW })}
        onContextMenu={event => {
            event.preventDefault();
            setContextMenu({ pageX: event.pageX - 20, pageY: event.pageY - 20, id: dayId, isActive: true })
        }}
        key={empty ? id : dayId}>
        {empty ? null : index - firstDayOfTheMonth + 1}
    </button>);
};