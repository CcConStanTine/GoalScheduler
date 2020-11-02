import React from 'react';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import { UseAppContext } from '../authentication/AppContext';

import AppTabs from '../templates/pages/AppTabs';
import WelcomePage from '../templates/pages/WelcomePage';
import NotFoundPage from '../templates/pages/NotFoundPage';

const App = () => (
  <UseAppContext>
    <Router>
      <Switch>
        <Route path="/app" >
          <AppTabs />
        </Route>
        <Route exact path="/welcome" component={WelcomePage} />
        <Redirect exact path="/" to="/app/home" />
        <Route path="*" component={NotFoundPage} />
      </Switch>
    </Router>
  </UseAppContext>
);

export default App;
