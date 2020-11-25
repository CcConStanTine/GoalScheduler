import React, { useContext, useState } from 'react';
import { PageNavigationTypes, EntriesPlanType } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
// import { useParams } from "react-router-dom";
import { landingPageInterface, ViewEntryRouteParams, ViewEntryParams } from '../../utils/interfaces';
import { setActiveClassName } from '../../components/OtherEntriesFunctions';
import { IonDatetime, IonTextarea } from '@ionic/react';
import { useForm, Controller } from 'react-hook-form';

const AddEntryPage = ({ history }: landingPageInterface) => {
    const initalValues = {
        startDate: '',
        endDate: '',
        content: '',
    }

    const { language } = useContext(LanguageContext);
    const [entryType, setEntryType] = useState<string>(EntriesPlanType.DAY);
    const [selectedStartDate, setSelectedStartDate] = useState<string>('');
    const [selectedEndDate, setSelectedEndDate] = useState<string>('');
    const [description, setDescription] = useState<string>('');
    const { register, handleSubmit, errors, control } = useForm({
        defaultValues: initalValues
    });
    // const { id, type } = useParams<ViewEntryRouteParams>();
    // const [entry, setEntry] = useState<ViewEntryParams>({});

    const onSubmit = (data: any) => {
        console.log(data);
    }
    return (
        <section className='add-entry-page'>
            <NavigationBar
                type={PageNavigationTypes.DEFAULT}
                history={history}
                placeholder={languagePack[language].addEntryText}
            />
            <div className='content'>
                <div className='entries-navigation'>
                    <button
                        className={`default-button ${setActiveClassName(EntriesPlanType.DAY, entryType)}`}
                        onClick={() => setEntryType(EntriesPlanType.DAY)}>{languagePack[language].dayPlanText}
                    </button>
                    <button
                        className={`default-button ${setActiveClassName(EntriesPlanType.MONTH, entryType)}`}
                        onClick={() => setEntryType(EntriesPlanType.MONTH)}>{languagePack[language].monthPlanText}
                    </button>
                    <button
                        className={`default-button ${setActiveClassName(EntriesPlanType.YEAR, entryType)}`}
                        onClick={() => setEntryType(EntriesPlanType.YEAR)}>{languagePack[language].yearPlanText}
                    </button>
                </div>
                <form className='date-picker' onSubmit={handleSubmit(onSubmit)}>
                    <Controller
                        render={({ onChange }) =>
                            <IonDatetime
                                displayFormat="DD-MMMM-YYYY"
                                onIonChange={onChange}
                                placeholder={languagePack[language].inputPlaceholderStartText}
                                cancelText={languagePack[language].cancelText}
                                doneText={languagePack[language].inputDateAcceptText}
                                min={auth.getCurrentDate()}
                                monthNames={languagePack[language].inputDateMonthNames}
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
                        as={({ onChange }) =>
                            <IonDatetime
                                displayFormat="DD-MMMM-YYYY"
                                onIonChange={onChange}
                                placeholder={languagePack[language].inputPlaceholderStartText}
                                cancelText={languagePack[language].cancelText}
                                doneText={languagePack[language].inputDateAcceptText}
                                min={auth.getCurrentDate()}
                                monthNames={languagePack[language].inputDateMonthNames}
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
                        as={({ onChange }) =>
                            <IonTextarea
                                autoGrow={true}
                                inputMode={'text'}
                                onIonChange={onChange}
                                placeholder={languagePack[language].textareaPlaceholderText}
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
            </div>
        </section>
    )
}

export default AddEntryPage;