import React, { useContext } from 'react';
// import { Route,withRouter } from 'react-router-dom';
import auth from '../../authentication/database';
import { AppContext } from '../../authentication/AppContext';
// import { Redirect } from 'react-router-dom';

interface HomeInterface {
    history: any
}

const HomePage = ({ history }: HomeInterface) => {
    const { setLoggedIn } = useContext(AppContext);

    const logout = () => {
        auth.signout();
        if (setLoggedIn) setLoggedIn({
            loggedIn: auth.isAuthenticated(),
        });
    }

    return (
        <>
            <button onClick={logout}>Logout</button>
        </>
    )
}

export default HomePage;