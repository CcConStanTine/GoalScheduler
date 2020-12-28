import React, { useContext } from 'react';
import { FaUser, FaSignature, FaEnvelope } from 'react-icons/fa';
import { AppContext } from '../../authentication/AppContext';

const UserCredentials = (): JSX.Element => {
    const { userContext } = useContext(AppContext);

    return (
        <div className='user-credentials'>
            <img src={userContext?.userPhoto} alt={`${userContext?.firstName} ${userContext?.lastName}`} />
            <ul>
                <li>
                    <span><FaSignature /></span>
                    <p>{userContext?.firstName} {userContext?.lastName}</p>
                </li>
                <li>
                    <span><FaUser /></span>
                    <p>{userContext?.nick}</p>
                </li>
                <li>
                    <span><FaEnvelope /></span>
                    <p>{userContext?.email}</p>
                </li>
            </ul>
        </div>
    )
}

export default UserCredentials;