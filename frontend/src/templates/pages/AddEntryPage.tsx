import React, { useContext, useState, useEffect } from 'react';
import { PageNavigationTypes, EntriesPlanType } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { useParams } from "react-router-dom";
import { FaPen, FaCheck, FaTimes } from 'react-icons/fa';
import EntryPageConfirmWindow from '../../components/EntryPageConfirmWindow';
import { getEntryDataByType, deletePlanByType, togglePlanByType } from '../../components/ViewEntryPageFunctons';
import { landingPageInterface, ViewEntryRouteParams, ViewEntryParams } from '../../utils/interfaces';
import { setActiveClassName } from '../../components/OtherEntriesFunctions';
import { IonButton, IonDatetime } from '@ionic/react';

const AddEntryPage = ({ history }: landingPageInterface) => {
    const { language } = useContext(LanguageContext);
    const [entryType, setEntryType] = useState<string>(EntriesPlanType.DAY);
    const [selectedStartDate, setSelectedStartDate] = useState('');
    const [selectedEndDate, setSelectedEndDate] = useState('');
    const { id, type } = useParams<ViewEntryRouteParams>();
    const [entry, setEntry] = useState<ViewEntryParams>({});

    // useEffect(() => {
    //     const getEntryData = async () => {
    //         const entryData = await getEntryDataByType(type, parseInt(id));

    //         setEntry(entryData);
    //     }

    //     getEntryData();
    // }, [id, finishEntryWindow, type])

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
                <div className='date-picker'>
                    <IonDatetime
                        displayFormat="DD-MMMM-YYYY"
                        value={selectedStartDate}
                        onIonChange={e => setSelectedStartDate(e.detail.value!)}
                        placeholder={languagePack[language].inputPlaceholderStartText}
                        cancelText={languagePack[language].cancelText}
                        doneText={languagePack[language].inputDateAcceptText}
                        min={auth.getCurrentDate()}
                        monthNames={languagePack[language].inputDateMonthNames}
                        max={'2030'}
                        className='default-button add-entry-input'
                    />
                    <IonDatetime
                        displayFormat="DD-MMMM-YYYY"
                        value={selectedEndDate}
                        onIonChange={e => setSelectedEndDate(e.detail.value!)}
                        placeholder={languagePack[language].inputPlaceholderEndText}
                        cancelText={languagePack[language].cancelText}
                        doneText={languagePack[language].inputDateAcceptText}
                        min={auth.getCurrentDate()}
                        monthNames={languagePack[language].inputDateMonthNames}
                        max={'2030'}
                        className='default-button add-entry-input'
                    />
                </div>
            </div>
        </section>
    )
}

export default AddEntryPage;