import React, { useContext, useEffect, useState } from 'react';
import RenderHeader from '../../../components/DesktopComponents/RenderHeader';
import languagePack from '../../../utils/languagePack';
import { LanguageContext } from '../../../authentication/LanguageContext';
import DataRequests from '../../../authentication/DataRequests';
import { DatePickerContext } from '../../../authentication/DatePicker';
import { DateSequences, DateTypes, ContextMenu, OpenWindow } from '../../../utils/interfaces';
import { ReturnTypeData } from '../../../utils/requestsInterfaces';
import { DatePickerOptions, PlanTypes } from '../../../utils/enums';
import { setMonthName } from '../../../components/OtherEntriesFunctions';
import ContextMenuContainer from '../../../components/DesktopComponents/ContextMenuContainer';
import WindowContainer from './WindowContainer';
import { renderDays } from '../../../components/DesktopComponents/HomePageContentFunctions';

const HomePageContent = () => {
    const { language } = useContext(LanguageContext);
    const { isDatePickerActive, openDatePicker, date, setDate } = useContext(DatePickerContext);
    const [dayList, setDayList] = useState<ReturnTypeData | []>([]);
    const [contextMenu, setContextMenu] = useState<ContextMenu>({ isActive: false });
    const [openWindow, setOpenWindow] = useState<OpenWindow>({ isActive: false });

    const setDateValue = () => {
        const monthName = setMonthName(DataRequests.getSequenceFromDate(date.date!, DateSequences.MONTH), language);
        const year = DataRequests.getSequenceFromDate(date.date!, DateSequences.YEAR);

        return `${monthName} ${year}`;
    };

    useEffect(() => {
        const getPlans = async () => {
            const _date = DataRequests.convertDate(DateTypes.REQUEST, date.date!);
            const plans = await DataRequests.getTypeDataByDate(PlanTypes.DAY, _date);

            const plansWithData: any = [];

            const checkIfThisDayHaveAPlan = async (id: number) => {
                const planList = await DataRequests.getTypePlansByTypeAndId(PlanTypes.DAY, id);

                return planList.length;
            }

            for (const { dayId, dayName } of plans) {
                plansWithData.push({ id: dayId, dayName: dayName, plansNumber: await checkIfThisDayHaveAPlan(dayId) });
            }

            setDayList(plansWithData);
        }

        getPlans();
    }, [date])

    return (
        <section className='home-page-content' onClick={() => setContextMenu({ isActive: false })}>
            <RenderHeader
                header={languagePack[language].HOME.title}
                date={<p onClick={() => {
                    openDatePicker!(!isDatePickerActive);
                    setDate!({ ...date, option: DatePickerOptions.SELECTDATE })
                }}>{setDateValue()}</p>} />

            <div className='calendar-container'>
                {languagePack[language].DAYS.daysAsAnArray.map(day => <p key={day} className='day_number'>{day}</p>)}
                {dayList.length && renderDays(dayList, setContextMenu, setOpenWindow)}
                {contextMenu.isActive &&
                    <ContextMenuContainer
                        setContextMenu={setContextMenu}
                        setOpenWindow={setOpenWindow}
                        pageX={contextMenu.pageX}
                        pageY={contextMenu.pageY}
                        isActive={contextMenu.isActive}
                        id={contextMenu.id}
                    />}
                {openWindow.isActive &&
                    <WindowContainer
                        setOpenWindow={setOpenWindow}
                        id={openWindow.id}
                        type={openWindow.type}
                        isActive={openWindow.isActive}
                    />}
            </div>
        </section>
    );
}

export default HomePageContent;