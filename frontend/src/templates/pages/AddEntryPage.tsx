import React, { useContext, useState } from 'react';
import { PageNavigationTypes, EntriesPlanType } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { LanguageContext } from '../../authentication/LanguageContext';
// import { useParams } from "react-router-dom";
import { landingPageInterface } from '../../utils/interfaces';
import RenderAddEntryInputs from '../../components/RenderAddEntryInputs';
import RenderEntriesNavigation from '../../components/RenderEntriesNavigation';

const AddEntryPage = ({ history }: landingPageInterface) => {
    const { language } = useContext(LanguageContext);
    const [entryType, setEntryType] = useState<string>(EntriesPlanType.DAY);
    // const { id, type } = useParams<ViewEntryRouteParams>();
    // const [entry, setEntry] = useState<ViewEntryParams>({});

    return (
        <section className='add-entry-page'>
            <NavigationBar
                type={PageNavigationTypes.DEFAULT}
                history={history}
                placeholder={languagePack[language].addEntryText}
            />
            <div className='content'>
                <div className='entries-navigation'>
                    <RenderEntriesNavigation
                        entryType={entryType}
                        onClick={setEntryType}
                    />
                </div>
                <RenderAddEntryInputs languagePack={languagePack[language]} entryType={entryType} />
            </div>
        </section>
    )
}

export default AddEntryPage;