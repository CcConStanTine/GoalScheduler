import React from 'react';
import { Route, Redirect } from 'react-router-dom';
// import { useAppAuthentication } from '../../authentication/AppContext';

interface PrivateInterface {
    component: any;
    exact?: boolean;
    path?: string;
}

const PrivateRoute = ({ component: Component, ...rest }: PrivateInterface) => {
    // const isAuthenticated = useAppAuthentication();
    return (
        <Route
            {...rest}
            render={props => {
                if (1)
                    return <Component {...props} />
                return <Redirect to="/" />
            }}
        />
    );
}

export default PrivateRoute;