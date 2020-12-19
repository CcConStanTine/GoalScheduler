import React, { useContext } from 'react';
import { LanguageContext } from '../../../authentication/LanguageContext';
import RenderHeader from '../../../components/DesktopComponents/RenderHeader';
import languagePack from '../../../utils/languagePack';
import { FaChevronUp, FaChevronDown } from 'react-icons/fa';

const HelpPage = () => {
    const { language } = useContext(LanguageContext);



    return (
        <section className='help-page-desktop'>
            <RenderHeader header={languagePack[language].HELP.title} />
            <details className='question-container entry-container'>
                <summary >System Requirements <p></p></summary>
                <span>Answer</span>
            </details>
        </section>
    )
}

export default HelpPage;