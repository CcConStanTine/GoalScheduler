import React from 'react';
import { useForm, Controller } from 'react-hook-form';
import { IonDatetime, IonTextarea } from '@ionic/react';
import auth from '../authentication/database';
import { inputData } from '../utils/interfaces';
import { useHistory } from 'react-router-dom';
import { AddEntryPageDefaultValues as defaultValues, EntriesPlanType } from '../utils/variables';

const RenderAddEntryInputs = ({ languagePack, entryType }: any) => {
    const history = useHistory();
    const { handleSubmit, control } = useForm<inputData>({ defaultValues });

    const formatDate = (data: inputData) => {
        const { startDate, endDate } = data;

        return {
            ...data,
            startDate: auth.changeDateToCorrectFormat(startDate),
            endDate: auth.changeDateToCorrectFormat(endDate),
        };
    }

    const onSubmit = async (data: inputData) => {
        const dayData = formatDate(data);

        if (entryType === EntriesPlanType.DAY)
            await auth.addDayPlan(dayData);
        else if (entryType === EntriesPlanType.MONTH)
            await auth.addMonthPlan(dayData);
        else
            await auth.addYearPlan(dayData);

        return history.goBack();
    }

    return (
        <form className='date-picker' onSubmit={handleSubmit(onSubmit)}>
            <Controller
                render={({ onChange }) =>
                    <IonDatetime
                        displayFormat="DD-MMMM-YYYY"
                        onIonChange={onChange}
                        placeholder={languagePack.inputPlaceholderStartText}
                        cancelText={languagePack.cancelText}
                        doneText={languagePack.inputDateAcceptText}
                        min={auth.getCurrentDate()}
                        monthNames={languagePack.inputDateMonthNames}
                        max={'2030'}
                        className='default-button add-entry-input'
                    />}
                control={control}
                name='startDate'
                rules={{
                    required: true
                }}
            />
            <Controller
                render={({ onChange }) =>
                    <IonDatetime
                        displayFormat="DD-MMMM-YYYY"
                        onIonChange={onChange}
                        placeholder={languagePack.inputPlaceholderEndText}
                        cancelText={languagePack.cancelText}
                        doneText={languagePack.inputDateAcceptText}
                        min={auth.getCurrentDate()}
                        monthNames={languagePack.inputDateMonthNames}
                        max={'2030'}
                        className='default-button add-entry-input'
                    />}
                control={control}
                name='endDate'
                rules={{
                    required: true
                }}
            />
            <Controller
                render={({ onChange }) =>
                    <IonTextarea
                        autoGrow={true}
                        inputMode={'text'}
                        onIonChange={onChange}
                        placeholder={languagePack.textareaPlaceholderText}
                        required={true}
                        className='default-button add-entry-textarea'
                    />}
                control={control}
                name='content'
                rules={{
                    required: true,
                    minLength: 5
                }}
            />
            <input type='submit' className='default-button' />
        </form>
    )
}

export default RenderAddEntryInputs;