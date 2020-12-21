import React, { useContext, useState, useEffect } from 'react';
import { AppContext } from '../../../authentication/AppContext';
import { LanguageContext } from '../../../authentication/LanguageContext';
import DataRequests from '../../../authentication/DataRequests';
import { PageNavigationTypes } from '../../../utils/variables';
import { PlanTypes } from '../../../utils/enums';
import languagePack from '../../../utils/languagePack';
import NavigationBar from '../../../components/NavigationBar';
import RenderPlanEntries from '../../../components/RenderPlanEntries';
import HomePageOtherEntries from '../../../components/HomePageOtherEntries';

const HomePage = (): JSX.Element => {
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
        <div className='home-page'>
            <NavigationBar type={PageNavigationTypes.HOMEPAGE} />
            <div className='entries'>
                <p className='today-plans'>{languagePack[language].HOME.todayPlans}</p>
                {RenderPlanEntries(todayPlans)}
            </div>
            <div className='other-plans'>
                <p className='other-plans-text'>{languagePack[language].HOME.otherPlans}</p>
                <HomePageOtherEntries />
            </div>
        </div>
    )
}

export default HomePage;