import React, { useContext } from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';
import { AppContext } from '../../authentication/AppContext';
import HomePage from './HomePage';
import SettingsPage from './SettingsPage';
import ChangePasswordPage from './ChangePasswordPage';
import ChangeEmailPage from './ChangeEmailPage';
import ChangeUsernamePage from './ChangeUsernamePage';
import ChangeUserPhoto from './ChangeUserPhoto';
import ViewEntryPage from './ViewEntryPage';
import AddEntryPage from './AddEntryPage';

const AppTabs: React.FC = () => {
  const { userContext } = useContext(AppContext);

  if (!userContext?.token) return <Redirect to="/welcome" />
  return (
    <Switch>
      {/* {console.table(userContext)} */}
      <Route exact path="/app/home" component={HomePage}></Route>
      <Route exact path="/app/settings" component={SettingsPage}></Route>
      <Route exact path="/app/add" component={AddEntryPage}></Route>
      <Route exact path="/app/edit/:type/:id" component={AddEntryPage}></Route>
      <Route exact path="/app/change-password" component={ChangePasswordPage}></Route>
      <Route exact path="/app/change-email" component={ChangeEmailPage}></Route>
      <Route exact path="/app/change-username" component={ChangeUsernamePage}></Route>
      <Route exact path="/app/change-photo" component={ChangeUserPhoto}></Route>
      <Route exact path="/app/view/:type/:id" component={ViewEntryPage}></Route>
    </Switch >
  );
};

export default AppTabs;
