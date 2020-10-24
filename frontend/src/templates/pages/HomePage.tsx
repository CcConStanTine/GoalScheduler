import React from 'react';
// import { Route,withRouter } from 'react-router-dom';
import auth from '../../authentication/auth';

interface HomeInterface {
    history: any
}

const HomePage = ({ history }: HomeInterface) => {
    const logout = () => auth.signout(() => history.push("/"))

    return (
        <>
            <button onClick={logout}>Logout</button>
        </>
    )
}

export default HomePage;