import React, { useContext, useState, useEffect } from 'react';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import DataRequests from '../../authentication/DataRequests';
import { PageNavigationTypes, PlanTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import userDefaultPhoto from '../../images/planner.jpg';
import RenderPlanEntries from '../../components/RenderPlanEntries';
import HomePageOtherEntries from '../../components/HomePageOtherEntries';

interface HomeInterface {
    history: any
}

const HomePage = ({ history }: HomeInterface) => {
    const { setLoggedIn } = useContext(AppContext);
    const { language } = useContext(LanguageContext);
    const [search, setSearch] = useState('');
    const [todayPlans, setTodayPlans] = useState([]);

    useEffect(() => {
        const { token } = DataRequests.getCurrentUser();
        const updateUserInfo = async () => {
            const userInfo = await DataRequests.getCurrentUserInfo();
            const { fileUrl } = token && await DataRequests.getUserPhoto();

            setLoggedIn && setLoggedIn({ ...userInfo, token, userPhoto: fileUrl ? fileUrl : userDefaultPhoto })
        }

        const updateTodayPlans = async () => {
            const { plans } = await DataRequests.getTypePlans(PlanTypes.DAY);

            setTodayPlans(plans);
        }

        updateUserInfo();
        updateTodayPlans();
    }, [setLoggedIn])

    return (
        <div className='home-page'>
            <NavigationBar type={PageNavigationTypes.HOMEPAGE} />
            <div className='search'>
                <input
                    value={search}
                    placeholder={languagePack[language].HOME.search}
                    onChange={event => setSearch(event.target.value)} />
            </div>
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