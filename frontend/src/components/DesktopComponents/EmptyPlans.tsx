import React, { useContext, useEffect, useState } from 'react';
import { } from 'react-icons/fa';
import DataRequests from '../../authentication/DataRequests';
import { LanguageContext } from '../../authentication/LanguageContext';
import { PlanTypes } from '../../utils/enums';
import { DateSequences, DateTypes, OpenWindowTypes } from '../../utils/interfaces';
import { setMonthName } from '../OtherEntriesFunctions';

interface EmpPlans {
    empty: boolean;
    setOpenWindow: any;
    id: number;
}

const EmptyPlans = ({ empty, setOpenWindow, id }: EmpPlans) => {
    const [date, setDate] = useState<string>('');
    const { language } = useContext(LanguageContext)

    useEffect(() => {
        const getDateById = async () => {
            const { dayDate } = await DataRequests.getTypeDataById(PlanTypes.DAY, id);

            const convertedDate = DataRequests.convertDate(DateTypes.NORMAL, dayDate!);
            const day = DataRequests.getSequenceFromDate(convertedDate, DateSequences.DAY);
            const month = DataRequests.getSequenceFromDate(convertedDate, DateSequences.MONTH);
            const year = DataRequests.getSequenceFromDate(convertedDate, DateSequences.YEAR);

            setDate(`${day} ${setMonthName(month, language)} ${year}`)
        }

        getDateById();
    }, [id, language])

    return (
        <div className='empty-plans'>
            <p onClick={() => setOpenWindow({ isActive: true, id: id - 1, type: OpenWindowTypes.SHOW })}><span className='chevron previous'></span></p>
            <div>
                <p className='date'>{date}</p>
                {empty && <p>Looks like you dont have a plans for this day</p>}
                <button className='desktop-default-button two'>Add Plan</button>
            </div>
            <p onClick={() => setOpenWindow({ isActive: true, id: id + 1, type: OpenWindowTypes.SHOW })}><span className='chevron next'></span></p>
        </div>
    )
}

export default EmptyPlans;