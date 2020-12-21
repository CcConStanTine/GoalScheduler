import React, { useContext, useEffect, useState } from 'react';
import RenderHeader from '../../../components/DesktopComponents/RenderHeader';
import languagePack from '../../../utils/languagePack';
import { LanguageContext } from '../../../authentication/LanguageContext';
import DataRequests from '../../../authentication/DataRequests';
import { DatePickerContext } from '../../../authentication/DatePicker';
import { DateSequences, DateTypes } from '../../../utils/interfaces';
import { ReturnTypeDataById, ReturnTypeData } from '../../../utils/requestsInterfaces';
import { PlanTypes } from '../../../utils/variables';
import { setMonthName } from '../../../components/OtherEntriesFunctions';

const getDayValue = (dayName: string) => {
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

const renderDays = (dayList: ReturnTypeData | []) => {
    const firstDayOfTheMonth = getDayValue(dayList[0].dayName ? dayList[0].dayName : 'MONDAY');
    const dayTable = [];


    for (let i = 0; i < firstDayOfTheMonth; i++) {
        dayTable.push({ empty: true, id: `day${i}` });
    };

    dayList.forEach((element: ReturnTypeDataById) => dayTable.push(element));

    return dayTable.map(({ dayId, empty, id }: any, index: number) => <button
        disabled={empty}
        className={empty ? 'day empty' : 'day'}
        key={empty ? id : dayId}>
        {empty ? null : index - firstDayOfTheMonth + 1}
    </button>);
};

const HomePageContent = () => {
    const { language } = useContext(LanguageContext);
    const { isDatePickerActive, openDatePicker, date } = useContext(DatePickerContext);
    const [dayList, setDayList] = useState<ReturnTypeData | []>([]);

    const setDate = () => {
        const monthName = setMonthName(DataRequests.getSequenceFromDate(date, DateSequences.MONTH), language);
        const year = DataRequests.getSequenceFromDate(date, DateSequences.YEAR);

        return `${monthName} ${year}`;
    };

    useEffect(() => {
        const getPlans = async () => {
            const _date = DataRequests.convertDate(DateTypes.REQUEST, date);
            const plans = await DataRequests.getTypeDataByDate(PlanTypes.DAY, _date);

            setDayList(plans);
        }

        getPlans();
    }, [date])

    return (
        <section className='home-page-content'>
            <RenderHeader
                header={languagePack[language].HOME.title}
                date={<p onClick={() => openDatePicker!(!isDatePickerActive)}>{setDate()}</p>} />
            <div className='calendar-container'>
                {languagePack[language].DAYS.daysAsAnArray.map(day => <p key={day} className='day_number'>{day}</p>)}
                {dayList.length && renderDays(dayList)}
            </div>
        </section>
    );
}

export default HomePageContent;