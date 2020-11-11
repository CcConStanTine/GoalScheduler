import React, { useContext } from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';
import { AppContext } from '../../authentication/AppContext';
import HomePage from './HomePage';
import SettingsPage from './SettingsPage';
import ChangePasswordPage from './ChangePasswordPage';

const AppTabs: React.FC = () => {
  const { userContext } = useContext(AppContext);

  if (!userContext?.token) return <Redirect to="/welcome" />
  return (
    <Switch>
      {console.log(userContext)}
      <Route exact path="/app/home" component={HomePage}></Route>
      <Route exact path="/app/settings" component={SettingsPage}></Route>
      <Route exact path="/app/change-password" component={ChangePasswordPage}></Route>
    </Switch >
  );
};

export default AppTabs;
