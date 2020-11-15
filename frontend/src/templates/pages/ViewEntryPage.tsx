import React, { useContext } from 'react';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { LanguageContext } from '../../authentication/LanguageContext';
import { useParams } from "react-router-dom";

interface HomeInterface {
    history: any
}

interface params {
    id: string;
}

const ViewEntryPage = ({ history }: HomeInterface) => {
    const { language } = useContext(LanguageContext);
    const { id } = useParams<params>();


    return (
        <section className='settings-page'>
            <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={languagePack[language].settingsNavigationText} />
            <h1>{id}</h1>
        </section>
    )
}

export default ViewEntryPage;