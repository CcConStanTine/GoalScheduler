import React, { useContext, useState, useEffect } from 'react';
import { PageNavigationTypes, EntriesPlanType } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { LanguageContext } from '../../authentication/LanguageContext';
import DataRequests from '../../authentication/DataRequests';
import { useParams } from "react-router-dom";
import { landingPageInterface, ViewEntryRouteParams } from '../../utils/interfaces';
import RenderAddEntryInputs from '../../components/RenderAddEntryInputs';
import RenderEntriesNavigation from '../../components/RenderEntriesNavigation';
import { ReturnPlans } from '../../utils/requestsInterfaces';

const AddEntryPage = ({ history }: landingPageInterface) => {
    const { language } = useContext(LanguageContext);
    const [entryType, setEntryType] = useState<string>(EntriesPlanType.DAY);
    const { id, type } = useParams<ViewEntryRouteParams>();
    const [entry, setEntry] = useState<ReturnPlans>();

    useEffect(() => {
        if (id) {
            const getEntryData = async () => {
                const entryData = await DataRequests.getPlanByTypeAndId(type, parseInt(id));
                setEntryType(type);
                setEntry(entryData);
            }

            getEntryData();
        }
    }, [id, type])

    return (
        <section className='add-entry-page'>
            <NavigationBar
                type={PageNavigationTypes.DEFAULT}
                history={history}
                placeholder={id ? languagePack[language].EDIT.title : languagePack[language].ADD.title}
            />
            <div className='content'>
                {!id && <div className='entries-navigation'>
                    <RenderEntriesNavigation
                        entryType={entryType}
                        onClick={setEntryType}
                    />
                </div>
                }
                <RenderAddEntryInputs languagePack={languagePack[language]} entryType={entryType} entry={entry ? entry : undefined} id={parseInt(id)} />
            </div>
        </section>
    )
}

export default AddEntryPage;