import React from 'react';
import { Route } from 'react-router-dom';
import HomePage from '../pages/desktop/HomePage';

const MobileRoutes = (): JSX.Element => (
    <>
        <Route exact path="/app/home" component={HomePage}></Route>
    </>
);

export default MobileRoutes;