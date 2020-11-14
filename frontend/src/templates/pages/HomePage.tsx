import React, { useContext, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { todayPlans, PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import { FaCaretRight } from 'react-icons/fa';
import NavigationBar from '../../components/NavigationBar';
import userDefaultPhoto from '../../images/planner.jpg';

interface HomeInterface {
    history: any
}

const HomePage = ({ history }: HomeInterface) => {
    const { setLoggedIn } = useContext(AppContext);
    const { language } = useContext(LanguageContext);
    const [search, setSearch] = useState('');

    useEffect(() => {
        const { token } = auth.getCurrentUser();
        const updateUserInfo = async () => {
            const userInfo = await auth.getCurrentUserInfo();
            const { fileUrl } = token && await auth.getUserPhoto();

            setLoggedIn && setLoggedIn({ ...userInfo, token, userPhoto: fileUrl ? fileUrl : userDefaultPhoto })
        }

        updateUserInfo();
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

                {todayPlans.map(({ color, topic }) =>
                    <div className='entry' key={topic}>
                        <div className='entry-info'>
                            <span className='color' style={{ backgroundColor: `${color}` }}></span>
                            <p>{topic}</p>
                        </div>
                        <Link to="/app/settings">
                            <FaCaretRight />
                        </Link>
                    </div>)}
            </div>
            <div className='other-plans'>
                <p className='other-plans-text'>{languagePack[language].otherPlansText}</p>
                <div className='other-plans-container'>
                    <div className='plan'>
                        <p>2020</p>
                    </div>
                    <div className='plan'>
                        <p>2019</p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default HomePage;