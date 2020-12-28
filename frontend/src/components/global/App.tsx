import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';

import AppTabs from '../../templates/routes/AppTabs';
import WelcomePage from '../../templates/routes/WelcomePage';
import NotFoundPage from '../../templates/pages/mobile/NotFoundPage';

const App = () => (
  <Switch>
    <Route path="/app" >
      <AppTabs />
    </Route>
    <Route exact path="/welcome" component={WelcomePage} />
    <Redirect exact path="/" to="/app/home" />
    <Route path="*" component={NotFoundPage} />
  </Switch>
);

export default App;
