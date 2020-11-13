import React from 'react';
import { ChangeUserCredentialsTypes } from '../utils/variables';
import { FaUser, FaKey, FaEnvelope, FaPortrait } from 'react-icons/fa';
import { Link } from 'react-router-dom';

interface UserCredentials {
    type: string;
    classType: string;
    placeholder: string;
}

const ChangeUserCredentials = ({ type, classType, placeholder }: UserCredentials) =>
    <div className={`change-${classType}`}>
        <aside>
            {type === ChangeUserCredentialsTypes.USERNAME && <FaUser className='change-username-icon' />}
            {type === ChangeUserCredentialsTypes.USERPASSWORD && <FaKey className='change-password-icon' />}
            {type === ChangeUserCredentialsTypes.EMAIL && <FaEnvelope className='change-email-icon' />}
            {type === ChangeUserCredentialsTypes.USERPHOTO && <FaPortrait className='change-userphoto-icon' />}
            <Link to={`/app/change-${classType}`}>{placeholder}</Link>
        </aside>
    </div>

export default ChangeUserCredentials;