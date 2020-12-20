import React from 'react';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import { UseAppContext } from '../authentication/AppContext';
import { UseThemeContext } from '../authentication/ThemeContext';
import { UseLanguageContext } from '../authentication/LanguageContext';
import { UseLoadingContext } from '../authentication/LoadingPageContext';
import { UseDatePickerContext } from '../authentication/DatePicker';

import AppTabs from '../templates/routes/AppTabs';
import WelcomePage from '../templates/routes/WelcomePage';
import NotFoundPage from '../templates/pages/mobile/NotFoundPage';

const App = () => (
  <UseLoadingContext>
    <UseAppContext>
      <UseThemeContext>
        <UseLanguageContext>
          <UseDatePickerContext>
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
          </UseDatePickerContext>
        </UseLanguageContext>
      </UseThemeContext>
    </UseAppContext>
  </UseLoadingContext>
);

export default App;
