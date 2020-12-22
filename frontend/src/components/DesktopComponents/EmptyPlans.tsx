import React, { useContext, useEffect, useState } from 'react';
import DataRequests from '../../authentication/DataRequests';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import { PlanTypes, OptionTypes } from '../../utils/enums';
import { EmpPlans } from '../../utils/interfaces';
import AddDay from './AddDay';
import EmptyPlansInfoAndNavigation from './EmptyPlansInfoAndNavigation';
import OptionActive from './OptionActive';

const EmptyPlans = ({ setOpenWindow, id, setRecentlyAddedPlanId }: EmpPlans) => {
    const { setDate, date } = useContext(DatePickerContext);
    const [dateValue, setDateValue] = useState<string>('');
    const { language } = useContext(LanguageContext);
    const [optionActiveType, setOptionActiveType] = useState<OptionTypes>(OptionTypes.DEFAULT);
    const [taskDescription, setTaskDescription] = useState<string>('');

    const setDefaultValues = () => {
        setDate!({ ...date, startDate: '', endDate: '' });
        setTaskDescription('');
    }

    useEffect(() => {
        const getDateById = async () => {
            const { dayDate } = await DataRequests.getTypeDataById(PlanTypes.DAY, id);

            setDateValue(dayDate!);
        }

        getDateById();
    }, [id, language])

    return (
        <div className={`empty-plans-container ${optionActiveType}`}>
            {optionActiveType ?
                <OptionActive
                    date={date.date!}
                    dateValue={dateValue}
                    language={language}
                    setDefaultValues={setDefaultValues}
                    setOptionActiveType={setOptionActiveType}
                />
                :
                <EmptyPlansInfoAndNavigation
                    date={date.date!}
                    dateValue={dateValue}
                    language={language}
                    id={id}
                    setOptionActiveType={setOptionActiveType}
                    setOpenWindow={setOpenWindow}
                />
            }
            <AddDay
                id={id}
                setDefaultValues={setDefaultValues}
                setRecentlyAddedPlanId={setRecentlyAddedPlanId}
                setTaskDescription={setTaskDescription}
                taskDescription={taskDescription}
            />
        </div>
    )
}

export default EmptyPlans;