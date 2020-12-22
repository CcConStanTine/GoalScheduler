import React, { useContext, useEffect, useState } from 'react';
import { } from 'react-icons/fa';
import DataRequests from '../../authentication/DataRequests';
import { LanguageContext } from '../../authentication/LanguageContext';
import { PlanTypes } from '../../utils/enums';
import { DateSequences, DateTypes, OpenWindowTypes } from '../../utils/interfaces';
import languagePack from '../../utils/languagePack';
import { setMonthName } from '../OtherEntriesFunctions';

interface EmpPlans {
    empty: boolean;
    setOpenWindow: any;
    id: number;
}

const EmptyPlans = ({ empty, setOpenWindow, id }: EmpPlans) => {
    const [date, setDate] = useState<string>('');
    const { language } = useContext(LanguageContext);

    const formatDate = () => {
        const convertedDate = DataRequests.convertDate(DateTypes.NORMAL, date);
        const day = DataRequests.getSequenceFromDate(convertedDate, DateSequences.DAY);
        const month = DataRequests.getSequenceFromDate(convertedDate, DateSequences.MONTH);
        const year = DataRequests.getSequenceFromDate(convertedDate, DateSequences.YEAR);

        return `${day} ${setMonthName(month, language)} ${year}`;
    }

    const checkDate = () => {
        const numberOfSeconds = new Date(date).getTime() / 1000;
        const currentNumberOfSeconds = new Date(DataRequests.getCurrentDate()).getTime() / 1000;

        return numberOfSeconds >= currentNumberOfSeconds && <button className='desktop-default-button two'>{languagePack[language].ADD.addTask}</button>
    }

    useEffect(() => {
        const getDateById = async () => {
            const { dayDate } = await DataRequests.getTypeDataById(PlanTypes.DAY, id);

            setDate(dayDate!);
        }

        getDateById();
    }, [id, language])

    return (
        <div className='empty-plans'>
            <p onClick={() => setOpenWindow({ isActive: true, id: id - 1, type: OpenWindowTypes.SHOW })}><span className='chevron previous'></span></p>
            <div>
                <p className='date'>{date && formatDate()}</p>
                {checkDate()}
            </div>
            <p onClick={() => setOpenWindow({ isActive: true, id: id + 1, type: OpenWindowTypes.SHOW })}><span className='chevron next'></span></p>
        </div>
    )
}

export default EmptyPlans;