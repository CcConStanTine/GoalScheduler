import React, { useContext } from 'react';
import RenderHeader from '../../../components/DesktopComponents/RenderHeader';
import languagePack from '../../../utils/languagePack';
import { LanguageContext } from '../../../authentication/LanguageContext';
import DataRequests from '../../../authentication/DataRequests';
import { DatePickerContext } from '../../../authentication/DatePicker';
import { DateSequences } from '../../../utils/interfaces';
import { setMonthName } from '../../../components/OtherEntriesFunctions';

const renderDayTest = () => {
    const table = [];
    for (let i = 0; i < 31; i++) {
        table.push(<div className='day' key={i}>
            <p className='no-day'>{i + 1}</p>
            <p className='content'>adsa</p>
        </div>)
    }

    return table.map(day => day);
};

const HomePageContent = () => {
    const { language } = useContext(LanguageContext);
    const { isDatePickerActive, openDatePicker, date } = useContext(DatePickerContext);

    const setDate = () => {
        const monthName = setMonthName(DataRequests.getSequenceFromDate(date, DateSequences.MONTH), language);
        const year = DataRequests.getSequenceFromDate(date, DateSequences.YEAR);

        return `${monthName} ${year}`;
    }

    return (
        <section className='home-page-content'>
            <RenderHeader
                header={languagePack[language].HOME.title}
                date={<p onClick={() => openDatePicker!(!isDatePickerActive)}>{setDate()}</p>} />
            <div className='calendar-container'>
                {renderDayTest()}
            </div>
        </section>
    );
}

export default HomePageContent;