import React, { useEffect, useState } from 'react';
import { FaChevronRight, FaChevronLeft } from 'react-icons/fa';
import renderTodayPlans from './RenderTodayPlans';
import auth from '../authentication/database';

const HomePageOtherEntries = () => {
    const [entryType, setEntryType] = useState('year');
    const [entryData, setEntryData] = useState([]);

    useEffect(() => {
        const getEntryData = async () => {
            const data = await auth.getYearPlans();

            setEntryData(data);
        }

        getEntryData()
    }, [entryType])

    return (
        <div className='other-entries-container'>
            <div className='year-navigation'>
                <FaChevronLeft />
                <p>2020</p>
                <FaChevronRight />
            </div>
            <div className='entries-navigation'>
                <button className={`default-button ${entryType === 'year' && 'on'}`}>Year Plan</button>
                <button className={`default-button ${entryType === 'month' && 'on'}`}>Month Plan</button>
                <button className={`default-button ${entryType === 'day' && 'on'}`}>Day Plan</button>
            </div>
            <div className='entries'>
                {renderTodayPlans(entryData)}
            </div>
        </div>
    )
}

export default HomePageOtherEntries;