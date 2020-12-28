import React, { useContext } from 'react';
import { LanguageContext } from '../../authentication/LanguageContext';
import languagePack from '../../utils/languagePack';

interface configWindow {
    type: string;
    placeholder: string;
    optionText: string;
    closeWindowFunction: (value: boolean) => void;
    sendRequestFunction: () => void;
    fulfilled?: boolean;
    unFulfilledPlaceholder?: string;
    unFullfilledOptionText?: string;
}

const EntryPageConfirmWindow = ({ type, placeholder, optionText, closeWindowFunction, sendRequestFunction, fulfilled, unFulfilledPlaceholder, unFullfilledOptionText }: configWindow) => {
    const { language } = useContext(LanguageContext);

    return (
        <div className={`${type}-window`}>
            <div className='message-container'>
                <p>{fulfilled ? unFulfilledPlaceholder : placeholder}</p>
                <div className='options'>
                    <button className='default-button' onClick={sendRequestFunction}>{fulfilled ? unFullfilledOptionText : optionText}</button>
                    <button className='default-button' onClick={() => closeWindowFunction(false)}>{languagePack[language].GLOBAL.cancel}</button>
                </div>
            </div>
        </div>
    )
}

export default EntryPageConfirmWindow