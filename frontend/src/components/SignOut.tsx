import React, { useContext } from 'react';
import auth from '../authentication/database';
import { AppContext } from '../authentication/AppContext';
import { signOutText } from '../utils/variables';
import { FaSignOutAlt } from 'react-icons/fa';

const SignOut = () => {
    const { setLoggedIn } = useContext(AppContext);

    const logout = () => {
        auth.logout();
        setLoggedIn!({});
    }

    return (
        <div className='sign-out'>
            <aside onClick={logout}>
                <FaSignOutAlt className='sign-out-icon' />
                <p>{signOutText}</p>
            </aside>
        </div>
    )
}


export default SignOut