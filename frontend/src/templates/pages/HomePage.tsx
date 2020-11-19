import React, { useContext, useState, useEffect } from 'react';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import userDefaultPhoto from '../../images/planner.jpg';
import renderTodayPlans from '../../components/RenderTodayPlans';
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
        const { token } = auth.getCurrentUser();
        const updateUserInfo = async () => {
            const userInfo = await auth.getCurrentUserInfo();
            const { fileUrl } = token && await auth.getUserPhoto();

            setLoggedIn && setLoggedIn({ ...userInfo, token, userPhoto: fileUrl ? fileUrl : userDefaultPhoto })
        }

        const updateTodayPlans = async () => {
            const todayPlansTable = await auth.getTodayPlans();

            setTodayPlans(todayPlansTable);
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
                    placeholder={languagePack[language].search}
                    onChange={event => setSearch(event.target.value)} />
            </div>
            <div className='entries'>
                <p className='today-plans'>{languagePack[language].todayPlansText}</p>
                {renderTodayPlans(todayPlans)}
            </div>
            <div className='other-plans'>
                <p className='other-plans-text'>{languagePack[language].otherPlansText}</p>
                <HomePageOtherEntries />
            </div>
        </div>
    )
}

export default HomePage;