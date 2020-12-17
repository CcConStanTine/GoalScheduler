import React, { useEffect, useContext, useState } from 'react';
import { AppContext } from '../../../authentication/AppContext';
import { LanguageContext } from '../../../authentication/LanguageContext';
import DataRequests from '../../../authentication/DataRequests';
import { PlanTypes } from '../../../utils/variables';
import { FaHome, FaUser, FaComment, FaInfoCircle, FaCog, FaLock, FaSignOutAlt } from 'react-icons/fa';

const HomePage = () => {
    const { setLoggedIn } = useContext(AppContext);
    const { language } = useContext(LanguageContext);
    const [todayPlans, setTodayPlans] = useState([]);

    useEffect(() => {
        const { token } = DataRequests.getCurrentUser();

        const updateUserInfo = async (): Promise<void> => {
            const userInfo = await DataRequests.getCurrentUserInfo();
            const { fileUrl: userPhoto } = await DataRequests.getUserPhoto();

            setLoggedIn && setLoggedIn({ ...userInfo, token, userPhoto })
        }

        const updateTodayPlans = async (): Promise<void> => {
            const plans = await DataRequests.getTypePlans(PlanTypes.DAY);
            setTodayPlans(plans);
        }

        updateUserInfo();
        updateTodayPlans();
    }, [setLoggedIn])
    return (
        <div className='home-page-desktop'>
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
                    <li>Home</li>
                    <li>Profile</li>
                    <li>Messages</li>
                    <li>Help</li>
                    <li>Settings</li>
                    <li>Password</li>
                    <li>Sign Out</li>
                </ul>
            </aside>
        </div>
    )
}

export default HomePage;