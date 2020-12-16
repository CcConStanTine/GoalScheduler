import React, { useEffect } from 'react';
import { useForm, Controller } from 'react-hook-form';
import { IonDatetime, IonTextarea } from '@ionic/react';
import DataRequests from '../authentication/DataRequests';
import { inputData, FormatDate, AddEntry } from '../utils/interfaces';
import { Plans } from '../utils/requestsInterfaces';
import { useHistory } from 'react-router-dom';
import { AddEntryPageDefaultValues as defaultValues, EntriesPlanType, dateTimeTypes } from '../utils/variables';

const formatDate = (data: inputData, type: string): FormatDate => {
    const { startDate, endDate, day, content } = data;

    if (day) {
        return {
            content,
            day: DataRequests.setProprietDate(day, dateTimeTypes.DEFAULT),
            startDate: DataRequests.setProprietDate(startDate, type),
            endDate: DataRequests.setProprietDate(endDate, type),
        }
    }

    return {
        content,
        startDate: DataRequests.setProprietDate(startDate, type),
        endDate: DataRequests.setProprietDate(startDate, type),
    };
}

const editEntry = async (entryType: string, data: inputData, id: number): Promise<Plans> => {
    const type = entryType === EntriesPlanType.DAY ? dateTimeTypes.EDITDAY : dateTimeTypes.DEFAULT;
    const dayData = formatDate(data, type);

    return await DataRequests.changePlanByType(entryType, id, dayData);
}

const addEntry = async (entryType: string, data: inputData): Promise<Plans> => {
    const type = entryType === EntriesPlanType.DAY ? dateTimeTypes.ADDDAY : dateTimeTypes.DEFAULT;
    const dayData = formatDate(data, type);

    return await DataRequests.addPlanByPlanType(entryType, dayData);
}

const RenderAddEntryInputs = ({ languagePack, entryType, entry, id }: AddEntry): JSX.Element => {
    const history = useHistory();
    const { handleSubmit, control, reset } = useForm<inputData>({ defaultValues });

    useEffect(() => {
        if (entry?.content) {
            const setInputEntries = async () => {
                const { startDate, endDate, content, dayPlanId } = entry;
                if (entryType === EntriesPlanType.DAY) {
                    const { dayDate } = await DataRequests.getTypeDataById(entryType, dayPlanId!);
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

    const onSubmit = async (data: inputData): Promise<void> => {
        if (entry?.content)
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
                                displayFormat={entryType === EntriesPlanType.MONTH ? "MMMM YYYY" : "YYYY"}
                                onIonChange={onChange}
                                placeholder={entryType === EntriesPlanType.DAY ? languagePack.ADD.selectDay : languagePack.ADD.selectStartTime}
                                cancelText={languagePack.GLOBAL.selectOptionCancel}
                                doneText={languagePack.GLOBAL.selectOptionDone}
                                min={DataRequests.getCurrentDate()}
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
            <input type='submit' className='default-button' value={entry?.content ? languagePack.EDIT.title : languagePack.ADD.title} />
        </form>
    )
}

export default RenderAddEntryInputs;