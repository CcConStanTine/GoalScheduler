import React, { useEffect } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { IonDatetime, IonTextarea } from '@ionic/react';
import auth from '../authentication/database';
import { inputData, EditEntryParams } from '../utils/interfaces';
import { useHistory } from 'react-router-dom';
import { AddEntryPageDefaultValues as defaultValues, EntriesPlanType } from '../utils/variables';

interface AddEntry {
    languagePack: any;
    entryType: any;
    entry: EditEntryParams;
    id: number;
}

const formatDate = (data: inputData) => {
    const { startDate, endDate, startDateTime } = data;

    if (startDateTime) {
        return {
            ...data,
            day: auth.changeDateToCorrectFormat(startDate),
            startDate: auth.changeDateTimeToCorrectFormat(startDateTime),
            endDate: auth.changeDateTimeToCorrectFormat(endDate),
        }
    }

    return {
        ...data,
        startDate: auth.changeDateToCorrectFormat(startDate),
        endDate: auth.changeDateToCorrectFormat(endDate),
    };
}

const editEntry = async (entryType: string, data: inputData, id: number) => {
    const dayData = formatDate(data)

    if (entryType === EntriesPlanType.MONTH)
        return await auth.changeMonthPlan(id, dayData);
    else if (entryType === EntriesPlanType.YEAR)
        return await auth.changeYearPlan(id, dayData);

    return await auth.changeDayPlan(id, data);
}

const addEntry = async (entryType: string, data: inputData) => {
    const dayData = formatDate(data);

    return await auth.addPlanByPlanType(entryType, dayData);
}

const RenderAddEntryInputs = ({ languagePack, entryType, entry, id }: AddEntry) => {
    const history = useHistory();
    const { handleSubmit, control, reset } = useForm<inputData>({ defaultValues });

    useEffect(() => {
        if (entry.content) {
            const setInputEntries = async () => {
                if (entryType === EntriesPlanType.DAY) {
                    const { dayDate } = await auth.getDayByDayId(entry.dayPlanId!);
                    return reset({
                        startDate: dayDate,
                        startDateTime: entry.startDate,
                        endDate: entry.endDate,
                        content: entry.content
                    })
                }

                return reset({
                    startDate: entry.startDate,
                    endDate: entry.endDate,
                    content: entry.content
                })
            }
            setInputEntries();
        }
    }, [entry, reset, entryType])

    const onSubmit = async (data: inputData) => {
        if (entry.content)
            await editEntry(entryType, data, id);
        else
            await addEntry(entryType, data);

        return history.goBack();
    }

    return (
        <form className='date-picker' onSubmit={handleSubmit(onSubmit)}>
            <Controller
                render={({ onChange, name, value }) =>
                    <IonDatetime
                        displayFormat="DD MMMM YYYY"
                        onIonChange={onChange}
                        placeholder={entryType === EntriesPlanType.DAY ? languagePack.ADD.selectDay : languagePack.ADD.selectStartTime}
                        cancelText={languagePack.GLOBAL.selectOptionCancel}
                        doneText={languagePack.GLOBAL.selectOptionDone}
                        min={auth.getCurrentDate()}
                        monthNames={languagePack.MONTHS.namesTable}
                        name={name}
                        value={value}
                        max={'2030'}
                        className='default-button add-entry-input'
                    />}
                control={control}
                name='startDate'
                rules={{
                    required: true
                }}
            />
            {entryType === EntriesPlanType.DAY ?
                <>
                    <Controller
                        render={({ onChange, name, value }) =>
                            <IonDatetime
                                displayFormat="HH : mm"
                                onIonChange={onChange}
                                placeholder={languagePack.ADD.selectStartTime}
                                cancelText={languagePack.GLOBAL.selectOptionCancel}
                                doneText={languagePack.GLOBAL.selectOptionDone}
                                className='default-button add-entry-input'
                                name={name}
                                value={value}
                            />}
                        control={control}
                        name='startDateTime'
                        rules={{
                            required: true
                        }}
                    />
                    <Controller
                        render={({ onChange, name, value }) =>
                            <IonDatetime
                                displayFormat="HH : mm"
                                onIonChange={onChange}
                                placeholder={languagePack.ADD.selectEndTime}
                                cancelText={languagePack.GLOBAL.selectOptionCancel}
                                doneText={languagePack.GLOBAL.selectOptionDone}
                                className='default-button add-entry-input'
                                name={name}
                                value={value}
                            />}
                        control={control}
                        name='endDate'
                        rules={{
                            required: true
                        }}
                    />
                </>
                :
                <Controller
                    render={({ onChange, name, value }) =>
                        <IonDatetime
                            displayFormat="DD MMMM YYYY"
                            onIonChange={onChange}
                            placeholder={languagePack.ADD.selectEndTime}
                            cancelText={languagePack.GLOBAL.selectOptionCancel}
                            doneText={languagePack.GLOBAL.selectOptionDone}
                            min={auth.getCurrentDate()}
                            monthNames={languagePack.MONTHS.namesTable}
                            max={'2030'}
                            className='default-button add-entry-input'
                            name={name}
                            value={value}
                        />}
                    control={control}
                    name='endDate'
                    rules={{
                        required: true
                    }}
                />
            }
            <Controller
                render={({ onChange, name, value }) =>
                    <IonTextarea
                        autoGrow={true}
                        inputMode={'text'}
                        onIonChange={onChange}
                        placeholder={languagePack.ADD.description}
                        required={true}
                        className='default-button add-entry-textarea'
                        name={name}
                        value={value}
                    />}
                control={control}
                name='content'
                rules={{
                    required: true,
                    minLength: 5
                }}
            />
            <input type='submit' className='default-button' value={entry.content ? languagePack.EDIT.title : languagePack.ADD.title} />
        </form>
    )
}

export default RenderAddEntryInputs;