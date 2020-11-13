import React, { useContext } from 'react';
import userDefaultPhoto from '../images/planner.jpg';
import { AppContext } from '../authentication/AppContext';

const UserData = () => {
    const { userContext } = useContext(AppContext);

    return (
        <div className='user-data'>
            <img src={userContext?.userPhoto ? userContext?.userPhoto : userDefaultPhoto} alt={`${userContext?.lastName}`} />
            <p>{userContext?.firstName} {userContext?.lastName}</p>
        </div>
    )
}

export default UserData;