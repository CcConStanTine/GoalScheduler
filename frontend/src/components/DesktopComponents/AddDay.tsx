import React, { useContext, useState, useEffect } from 'react';
import DataRequests from '../../authentication/DataRequests';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import { DatePickerOptions, OptionTypes, PlanTypes } from '../../utils/enums';
import { AddDayDesktop } from '../../utils/interfaces';
import languagePack from '../../utils/languagePack';
import { checkIfCanSendRequest } from './EmptyPlansFunction';

const AddDay = ({ setRecentlyAddedPlanId, id, edit, setOptionActiveType, setEdit }: AddDayDesktop) => {
    const { openDatePicker, setDate, date } = useContext(DatePickerContext);
    const [taskDescription, setTaskDescription] = useState<string>('');
    const { language } = useContext(LanguageContext);

    const editDay = async () => {

        const { dayPlanId } = await DataRequests.changePlanByType(PlanTypes.DAY, edit.id!,
            {
                content: taskDescription,
                startDate: date.startDate!,
                endDate: '23:59:59'
            })

        setRecentlyAddedPlanId(dayPlanId!);
        setTaskDescription('');
        setDate!({ ...date, startDate: '' });
        setEdit({ isActive: false, id: null, startDate: '', taskDescription: '' });
        setOptionActiveType(OptionTypes.DEFAULT);
    }

    const addDay = async () => {
        const { startDate } = date;

        const { dayPlanId } = await DataRequests.addPlanByPlanTypeAndId(PlanTypes.DAY, id,
            {
                startDate: startDate!,
                endDate: '23:59:59',
                content: taskDescription
            });

        setRecentlyAddedPlanId(dayPlanId!);
        setTaskDescription('');
        setDate!({ ...date, startDate: '' });
    }

    useEffect(() => {
        const { isActive, startDate, taskDescription } = edit;

        if (isActive) {
            setTaskDescription(taskDescription);
            return setDate!({ ...date, startDate })
        }

        setTaskDescription('');
        return setDate!({ ...date, startDate: '' });
    }, [id, setDate, edit])

    return (
        <div className='add-entry'>
            <div className='dates'>
                <button
                    onClick={() => {
                        openDatePicker!(true);
                        setDate!({ ...date, option: DatePickerOptions.STARTDATE })
                    }}
                    className={`desktop-default-button ${date.startDate && 'fulfilled'}`}>{date.startDate ? date.startDate : 'Godzina rozpoczęcia'}
                </button>
            </div>
            <div className='content'>
                <textarea
                    className={taskDescription && 'fulfilled'}
                    value={taskDescription}
                    placeholder='Opis planu'
                    onChange={event => setTaskDescription(event.target.value)}></textarea>
            </div>
            <button onClick={edit.isActive ? editDay : addDay}
                disabled={!checkIfCanSendRequest(date.startDate!, taskDescription)}
                className={`desktop-default-button two add-task-button ${checkIfCanSendRequest(date.startDate!, taskDescription)}`}>
                {edit.isActive ? languagePack[language].EDIT.title : languagePack[language].ADD.addTask}
            </button>
        </div>
    )
}

export default AddDay;