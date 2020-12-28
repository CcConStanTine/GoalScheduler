import React, { useContext, useEffect, useState } from 'react';
import { AppContext } from '../../authentication/AppContext';
import DataRequests from '../../authentication/DataRequests';

const UserData = () => {
    const { userContext } = useContext(AppContext);
    const [userPhoto, setUserPhoto] = useState<string>('');

    useEffect(() => {
        const getUserPhoto = async () => {
            const { fileUrl } = await DataRequests.getUserPhoto();

            setUserPhoto(fileUrl);
        }

        getUserPhoto();
    }, [])
    return (
        <div className='user-data'>
            <img src={userContext?.userPhoto ? userContext.userPhoto : userPhoto} alt={`${userContext?.lastName}`} />
            <p>{userContext?.firstName} {userContext?.lastName}</p>
        </div>
    )
}

export default UserData;