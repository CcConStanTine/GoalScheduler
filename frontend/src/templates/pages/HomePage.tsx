import React, { useContext, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { AppContext } from '../../authentication/AppContext';
import auth from '../../authentication/database';
import { todayPlansText, todayPlans, otherPlansText, PageNavigationTypes } from '../../utils/variables';
import { FaCaretRight } from 'react-icons/fa';
import NavigationBar from '../../components/NavigationBar';

interface HomeInterface {
    history: any
}

const HomePage = ({ history }: HomeInterface) => {
    const { setLoggedIn } = useContext(AppContext);
    const [search, setSearch] = useState('');

    useEffect(() => {
        const { token } = auth.getCurrentUser();
        const updateUserInfo = async () => {
            const userInfo = await auth.getCurrentUserInfo();

            setLoggedIn && setLoggedIn({ ...userInfo, token })
        }

        updateUserInfo();
    }, [setLoggedIn])


    return (
        <div className='home-page'>
            <NavigationBar type={PageNavigationTypes.HOMEPAGE} />
            <div className='search'>
                <input
                    value={search}
                    placeholder="Search"
                    onChange={event => setSearch(event.target.value)} />
            </div>
            <div className='entries'>
                <p className='today-plans'>{todayPlansText}</p>

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
                <p className='other-plans-text'>{otherPlansText}</p>
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