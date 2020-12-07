import React, { useEffect } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { IonDatetime, IonTextarea } from '@ionic/react';
import auth from '../authentication/database';
import { inputData, EditEntryParams } from '../utils/interfaces';
import { useHistory } from 'react-router-dom';
import { AddEntryPageDefaultValues as defaultValues, EntriesPlanType, dateTimeTypes } from '../utils/variables';

interface AddEntry {
    languagePack: any;
    entryType: any;
    entry: EditEntryParams;
    id: number;
}

const formatDate = (data: inputData, type: string) => {
    const { startDate, endDate, day, content } = data;

    if (day) {
        return {
            content,
            day: auth.setProprietDate(day, dateTimeTypes.DEFAULT),
            startDate: auth.setProprietDate(startDate, type),
            endDate: auth.setProprietDate(endDate, type),
        }
    }

    return {
        content,
        startDate: auth.setProprietDate(startDate, type),
        endDate: auth.setProprietDate(endDate, type),
    };
}

const editEntry = async (entryType: string, data: inputData, id: number) => {
    const type = entryType === EntriesPlanType.DAY ? dateTimeTypes.EDITDAY : dateTimeTypes.DEFAULT;
    const dayData = formatDate(data, type);

    return await auth.changePlanByType(entryType, id, dayData);
}

const addEntry = async (entryType: string, data: inputData) => {
    const type = entryType === EntriesPlanType.DAY ? dateTimeTypes.ADDDAY : dateTimeTypes.DEFAULT;
    const dayData = formatDate(data, type);

    return await auth.addPlanByPlanType(entryType, dayData);
}

const RenderAddEntryInputs = ({ languagePack, entryType, entry, id }: AddEntry) => {
    const history = useHistory();
    const { handleSubmit, control, reset } = useForm<inputData>({ defaultValues });

    useEffect(() => {
        if (entry.content) {
            const setInputEntries = async () => {
                const { startDate, endDate, content, dayPlanId } = entry;
                if (entryType === EntriesPlanType.DAY) {
                    const { dayDate } = await auth.getTypeDataById(entryType, dayPlanId!);
                    return reset({
                        day: dayDate,
                        startDate,
                        endDate,
                        content
                    })
                }

                return reset({
                    startDate,
                    endDate,
                    content
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
            {entryType === EntriesPlanType.DAY ?
                <>
                    <Controller
                        render={({ onChange, name, value }) =>
                            <IonDatetime
                                displayFormat="DD MMMM YYYY"
                                onIonChange={onChange}
                                placeholder={languagePack.ADD.selectDay}
                                cancelText={languagePack.GLOBAL.selectOptionCancel}
                                doneText={languagePack.GLOBAL.selectOptionDone}
                                className='default-button add-entry-input'
                                name={name}
                                value={value}
                            />}
                        control={control}
                        name='day'
                        rules={{
                            required: true
                        }}
                    />
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
                        name='startDate'
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
                <>
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
                        }} />
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
                </>
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