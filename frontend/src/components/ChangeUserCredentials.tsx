import React from 'react';
import { ChangeUserCredentialsTypes } from '../utils/variables';
import { FaUser, FaKey } from 'react-icons/fa';
import { Link } from 'react-router-dom';

interface UserCredentials {
    type: string;
    classType: string;
    placeholder: string;
}

const ChangeUserCredentials = ({ type, classType, placeholder }: UserCredentials) =>
    <div className={`change-${classType}`}>
        <aside>
            {type === ChangeUserCredentialsTypes.USERNAME && <FaUser />}
            {type === ChangeUserCredentialsTypes.USERPASSWORD && <FaKey />}
            <Link to={`/app/change-${classType}`}>{placeholder}</Link>
        </aside>
    </div>

export default ChangeUserCredentials;