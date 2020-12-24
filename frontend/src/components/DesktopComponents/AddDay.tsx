import React, { useContext } from 'react';
import DataRequests from '../../authentication/DataRequests';
import { DatePickerContext } from '../../authentication/DatePicker';
import { DatePickerOptions, PlanTypes } from '../../utils/enums';
import { AddDayDesktop } from '../../utils/interfaces';
import { checkIfCanSendRequest } from './EmptyPlansFunction';

const AddDay = ({ setDefaultValues, setRecentlyAddedPlanId, id, taskDescription, setTaskDescription }: AddDayDesktop) => {
    const { openDatePicker, setDate, date } = useContext(DatePickerContext);

    const addDay = async () => {
        const { startDate } = date;

        const { dayPlanId } = await DataRequests.addPlanByPlanTypeAndId(PlanTypes.DAY, id,
            {
                startDate: startDate!,
                endDate: '23:59:59',
                content: taskDescription
            });

        setDefaultValues();
        setRecentlyAddedPlanId(dayPlanId!);
    }

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
            <button onClick={addDay}
                disabled={!checkIfCanSendRequest(date.startDate!, taskDescription)}
                className={`desktop-default-button two add-task-button ${checkIfCanSendRequest(date.startDate!, taskDescription)}`}>Add Task</button>
        </div>
    )
}

export default AddDay;