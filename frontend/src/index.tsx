import React from 'react';
import ReactDOM from 'react-dom';
import './style/style.scss';
import App from './components/global/App';
import '@ionic/react/css/core.css';
import { UseLoadingContext } from './authentication/LoadingPageContext';
import { UseThemeContext } from './authentication/ThemeContext';
import { UseLanguageContext } from './authentication/LanguageContext';
import { BrowserRouter as Router } from 'react-router-dom';
import { UseDatePickerContext } from './authentication/DatePicker';
import { UseAppContext } from './authentication/AppContext';
// import reportWebVitals from './reportWebVitals';

ReactDOM.render(
  <React.StrictMode>
    <UseLoadingContext>
      <UseAppContext>
        <UseThemeContext>
          <UseLanguageContext>
            <UseDatePickerContext>
              <Router>
                <App />
              </Router>
            </UseDatePickerContext>
          </UseLanguageContext>
        </UseThemeContext>
      </UseAppContext>
    </UseLoadingContext>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
