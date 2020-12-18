import React, { useContext } from 'react';
import { FaHome, FaUser, FaComment, FaInfoCircle, FaCog, FaLock, FaSignOutAlt } from 'react-icons/fa';
import { DesktopDisplayOptions } from '../../utils/interfaces';
import DataRequests from '../../authentication/DataRequests';
import { AppContext } from '../../authentication/AppContext'

interface Menu {
    onClick: any;
}

const Menu = ({ onClick }: Menu) => {
    const { setLoggedIn } = useContext(AppContext)

    const logOutUser = () => {
        DataRequests.logout();
        setLoggedIn!({});
    };

    return (
        <aside>
            <ul className='icons'>
                <li><FaHome /></li>
                <li><FaUser /></li>
                <li><FaComment /></li>
                <li><FaInfoCircle /></li>
                <li><FaCog /></li>
                <li><FaLock /></li>
                <li><FaSignOutAlt /></li>
            </ul>
            <ul className='menu'>
                <li onClick={() => onClick(DesktopDisplayOptions.HOME)}>Home</li>
                <li onClick={() => onClick(DesktopDisplayOptions.PROFILE)}>Profile</li>
                <li onClick={() => onClick(DesktopDisplayOptions.MESSAGES)}>Messages</li>
                <li onClick={() => onClick(DesktopDisplayOptions.HELP)}>Help</li>
                <li onClick={() => onClick(DesktopDisplayOptions.SETTINGS)}>Settings</li>
                <li onClick={() => onClick(DesktopDisplayOptions.PASSWORD)}>Password</li>
                <li onClick={logOutUser}>Sign Out</li>
            </ul>
        </aside>
    )
};

export default Menu;