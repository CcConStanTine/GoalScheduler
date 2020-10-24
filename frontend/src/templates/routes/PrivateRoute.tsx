import React from 'react';
import auth from '../../authentication/auth';
import { Route, Redirect } from 'react-router-dom';

interface PrivateInterface {
    component: any;
    exact?: boolean;
    path?: string;
}

const PrivateRoute = ({ component: Component, ...rest }: PrivateInterface) => {
    return (
        <Route
            {...rest}
            render={props => {
                if (auth.isAuthenticated())
                    return <Component {...props} />
                return <Redirect to="/" />
            }}
        />
    );
}

export default PrivateRoute;