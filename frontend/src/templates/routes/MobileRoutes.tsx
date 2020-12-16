import React from 'react';
import { Route } from 'react-router-dom';
import HomePage from '../pages/mobile/HomePage';
import SettingsPage from '../pages/mobile/SettingsPage';
import ChangePasswordPage from '../pages/mobile/ChangePasswordPage';
import ChangeEmailPage from '../pages/mobile/ChangeEmailPage';
import ChangeUsernamePage from '../pages/mobile/ChangeUsernamePage';
import ChangeUserPhoto from '../pages/mobile/ChangeUserPhoto';
import ViewEntryPage from '../pages/mobile/ViewEntryPage';
import AddEntryPage from '../pages/mobile/AddEntryPage';

const MobileRoutes = (): JSX.Element => (
    <>
        <Route exact path="/app/home" component={HomePage}></Route>
        <Route exact path="/app/settings" component={SettingsPage}></Route>
        <Route exact path="/app/add" component={AddEntryPage}></Route>
        <Route exact path="/app/add/:type" component={AddEntryPage}></Route>
        <Route exact path="/app/edit/:type/:id" component={AddEntryPage}></Route>
        <Route exact path="/app/change-password" component={ChangePasswordPage}></Route>
        <Route exact path="/app/change-email" component={ChangeEmailPage}></Route>
        <Route exact path="/app/change-username" component={ChangeUsernamePage}></Route>
        <Route exact path="/app/change-photo" component={ChangeUserPhoto}></Route>
        <Route exact path="/app/view/:type/:id" component={ViewEntryPage}></Route>
    </>
);

export default MobileRoutes;