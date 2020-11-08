import React, { useContext, useState } from 'react';
import { Link } from 'react-router-dom';
import { AppContext } from '../../authentication/AppContext';
import { appName, todayPlansText, todayPlans, otherPlansText } from '../../utils/variables';
import { FaPlus, FaEllipsisV, FaCaretRight } from 'react-icons/fa';
// import { Redirect } from 'react-router-dom';

interface HomeInterface {
    history: any
}

const HomePage = ({ history }: HomeInterface) => {
    const { userContext } = useContext(AppContext);
    const [search, setSearch] = useState('');



    return (
        <div className='home-page'>
            <nav>
                <Link to="/app/add">
                    <FaPlus />
                </Link>
                <p>{appName}</p>
                <Link to="/app/settings">
                    <FaEllipsisV />
                </Link>
            </nav>
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