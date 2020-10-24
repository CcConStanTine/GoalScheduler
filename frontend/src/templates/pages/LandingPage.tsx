import React from 'react';
import auth from '../../authentication/auth';

interface LandingInterface {
    history: any
}

const LandingPage = ({ history }: LandingInterface) => {
    const handleClick = () => auth.login(() => history.push("./app"));

    return (
        <>
            <h1>LandingPage</h1>
            <button onClick={handleClick}>Login</button>
        </>
    )
}

export default LandingPage;