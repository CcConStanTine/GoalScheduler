import React, { useContext } from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';
import { AppContext } from '../../authentication/AppContext';
import HomePage from './HomePage';

const AppTabs: React.FC = () => {
  const { userContext } = useContext(AppContext);

  if (!userContext?.loggedIn) return <Redirect to="/welcome" />

  return (
    <Switch>
      {console.log(userContext)}
      <Route exact path="/app/home" component={HomePage}>
      </Route >
    </Switch >
  );
};

export default AppTabs;
