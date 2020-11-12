import React from 'react';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import { UseAppContext } from '../authentication/AppContext';
import { UseThemeContext } from '../authentication/ThemeContext';

import AppTabs from '../templates/pages/AppTabs';
import WelcomePage from '../templates/pages/WelcomePage';
import NotFoundPage from '../templates/pages/NotFoundPage';

const App = () => (
  <UseAppContext>
    <UseThemeContext>
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
    </UseThemeContext>
  </UseAppContext>
);

export default App;
