import React, { useContext, useState } from 'react';
import { languageOptions } from '../utils/variables';
import languagePack from '../utils/languagePack';
import { FaGlobeEurope } from 'react-icons/fa';
import { LanguageContext } from '../authentication/LanguageContext'
import { IonPicker } from '@ionic/react';
import { PickerColumn } from "@ionic/core";

const UserLanguage = () => {
    const { language, setLanguage } = useContext(LanguageContext);
    const [isPickerOpen, setIsPickerOpen] = useState(false);

    const changeLanguage = (value: string) => {
        setLanguage!(value);
        localStorage.setItem('language', value);
    }

    const languageColumn = {
        name: "First",
        options: languageOptions[language]
    } as PickerColumn;

    return (
        <div className='language'>
            <aside>
                <FaGlobeEurope className='language-icon' />
                <p onClick={() => setIsPickerOpen(true)}>{languagePack[language].languageText}</p>
            </aside>
            <IonPicker
                isOpen={isPickerOpen}
                columns={[languageColumn]}
                buttons={[
                    {
                        text: languagePack[language].cancelText,
                        role: 'cancel',
                        handler: () => setIsPickerOpen(false)
                    },
                    {
                        text: languagePack[language].selectLanguageText,
                        handler: ({ First }) => {
                            changeLanguage(First.value);
                            setIsPickerOpen(false);
                        }
                    }
                ]}
            ></IonPicker>
        </div>
    )
}

export default UserLanguage;