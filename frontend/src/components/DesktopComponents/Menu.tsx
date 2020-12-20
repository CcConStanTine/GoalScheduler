import React, { useContext } from 'react';
import { FaHome, FaUser, FaInfoCircle, FaCog, FaSignOutAlt } from 'react-icons/fa';
import { DesktopDisplayOptions } from '../../utils/interfaces';
import DataRequests from '../../authentication/DataRequests';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import languagePack from '../../utils/languagePack';

interface MenuInterface {
    onClick: any;
}

const Menu = ({ onClick }: MenuInterface) => {
    const { setLoggedIn } = useContext(AppContext);
    const { language } = useContext(LanguageContext);


    const logOutUser = () => {
        DataRequests.logout();
        setLoggedIn!({});
    };

    return (
        <aside>
            <ul className='icons'>
                <li><FaHome /></li>
                <li><FaUser /></li>
                <li><FaInfoCircle /></li>
                <li><FaCog /></li>
                <li><FaSignOutAlt /></li>
            </ul>
            <ul className='menu'>
                <li onClick={() => onClick(DesktopDisplayOptions.HOME)}>{languagePack[language].HOME.title}</li>
                <li onClick={() => onClick(DesktopDisplayOptions.PROFILE)}>{languagePack[language].PROFILE.title}</li>
                <li onClick={() => onClick(DesktopDisplayOptions.HELP)}>{languagePack[language].HELP.title}</li>
                <li onClick={() => onClick(DesktopDisplayOptions.SETTINGS)}>{languagePack[language].SETTINGS.title}</li>
                <li onClick={logOutUser}>{languagePack[language].SETTINGS.signOut}</li>
            </ul>
        </aside>
    )
};

export default Menu;