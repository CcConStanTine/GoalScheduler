import React, { useContext, useState, useEffect } from 'react';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { useParams } from "react-router-dom";

interface HomeInterface {
    history: any
}

interface RouteParams {
    id: string;
}

const ViewEntryPage = ({ history }: HomeInterface) => {
    const { language } = useContext(LanguageContext);
    const { id } = useParams<RouteParams>();
    const [entry, setEntry] = useState({});

    useEffect(() => {
        const getEntryData = async () => {
            const entryData = await auth.getPlanByPlanId(parseInt(id));

            setEntry(entryData);
        }

        getEntryData();
    }, [id])

    return (
        <section className='settings-page'>
            <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={languagePack[language].viewEntry} />
            <div className='content'>
                <div className="title">
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                </div>
                <div className="data">
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                </div>
            </div>
        </section>
    )
}

export default ViewEntryPage;