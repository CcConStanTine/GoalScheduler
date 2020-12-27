import React, { useContext, useState, useEffect } from 'react';
import DataRequests from '../../authentication/DataRequests';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import { DatePickerOptions, OptionTypes, PlanTypes } from '../../utils/enums';
import { AddDayDesktop } from '../../utils/interfaces';
import languagePack from '../../utils/languagePack';
import { EditDayDesktopDefaultValues } from '../../utils/variables';
import { checkIfCanSendRequest } from './EmptyPlansFunction';

const AddOrEditDay = ({ setRecentlyAddedPlanId, id, edit, setOptionActiveType, setEdit }: AddDayDesktop) => {
    const { openDatePicker, setDate, date } = useContext(DatePickerContext);
    const [taskDescription, setTaskDescription] = useState<string>('');
    const { language } = useContext(LanguageContext);

    const setDefaultValues = (dayPlanId: number) => {
        setRecentlyAddedPlanId(dayPlanId);
        setTaskDescription('');
        setDate!({ ...date, startDate: '' });
    }

    const setEditDefaultValues = () => {
        setEdit(EditDayDesktopDefaultValues);
        setOptionActiveType(OptionTypes.DEFAULT);
    }

    const handleDayByType = async (type: OptionTypes) => {
        const { startDate } = date;

        const data = {
            startDate: startDate!,
            endDate: '23:59:59',
            content: taskDescription,
        };

        const { dayPlanId } = type === OptionTypes.ADD ?
            await DataRequests.addPlanByPlanTypeAndId(PlanTypes.DAY, id, data)
            :
            await DataRequests.changePlanByType(PlanTypes.DAY, edit.id!, data);

        setDefaultValues(dayPlanId!);

        type === OptionTypes.EDIT && setEditDefaultValues();
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
            <button
                onClick={() => edit.isActive ? handleDayByType(OptionTypes.EDIT) : handleDayByType(OptionTypes.ADD)}
                disabled={!checkIfCanSendRequest(date.startDate!, taskDescription)}
                className={`desktop-default-button two add-task-button ${checkIfCanSendRequest(date.startDate!, taskDescription)}`}>
                {edit.isActive ? languagePack[language].EDIT.title : languagePack[language].ADD.addTask}
            </button>
        </div>
    )
}

export default AddOrEditDay;