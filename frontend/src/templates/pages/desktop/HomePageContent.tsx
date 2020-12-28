import React, { useContext, useEffect, useState } from 'react';
import RenderHeader from '../../../components/desktop/RenderHeader';
import { LanguageContext } from '../../../authentication/LanguageContext';
import { DatePickerContext } from '../../../authentication/DatePicker';
import { ContextMenu, OpenWindow } from '../../../utils/interfaces';
import { ReturnTypeData, } from '../../../utils/requestsInterfaces';
import { DatePickerOptions } from '../../../utils/enums';
import languagePack from '../../../utils/languagePack';
import ContextMenuContainer from '../../../components/desktop/ContextMenuContainer';
import { generatePlansInfo, getActualPlans, renderDays, setDateValue } from '../../../components/desktop/HomePageContentFunctions';
import WindowContainer from './WindowContainer';

const HomePageContent = (): JSX.Element => {
    const { language } = useContext(LanguageContext);
    const { isDatePickerActive, openDatePicker, date, setDate } = useContext(DatePickerContext);
    const [dayList, setDayList] = useState<ReturnTypeData | []>([]);
    const [contextMenu, setContextMenu] = useState<ContextMenu>({ isActive: false });
    const [openWindow, setOpenWindow] = useState<OpenWindow>({ isActive: false });

    useEffect(() => {
        const getPlans = async () => {
            const plans = await getActualPlans(date.date!);
            const plansInfo = await generatePlansInfo(plans);

            setDayList(plansInfo);
        }

        getPlans();
    }, [date]);

    const handleCalendarDate = () => {
        openDatePicker!(!isDatePickerActive);
        setDate!({ ...date, option: DatePickerOptions.SELECTDATE });
    }

    const calendarDate = <p onClick={handleCalendarDate}>{setDateValue(date.date!, language)}</p>

    return (
        <section className='home-page-content' onClick={() => setContextMenu({ isActive: false })}>
            <RenderHeader
                header={languagePack[language].HOME.title}
                date={calendarDate}
            />

            <div className='calendar-container'>
                {languagePack[language].DAYS.daysAsAnArray.map(day => <p key={day} className='day_number'>{day}</p>)}
                {dayList.length ?
                    renderDays(dayList, setContextMenu, setOpenWindow)
                    :
                    <p className='loading-calendar'>{languagePack[language].GLOBAL.loadingCalendar}</p>
                }
                {contextMenu.isActive &&
                    <ContextMenuContainer
                        setContextMenu={setContextMenu}
                        setOpenWindow={setOpenWindow}
                        pageX={contextMenu.pageX}
                        pageY={contextMenu.pageY}
                        isActive={contextMenu.isActive}
                        id={contextMenu.id}
                    />
                }
                {openWindow.isActive &&
                    <WindowContainer
                        setOpenWindow={setOpenWindow}
                        id={openWindow.id}
                        type={openWindow.type}
                        isActive={openWindow.isActive}
                    />
                }
            </div>
        </section>
    );
}

export default HomePageContent;