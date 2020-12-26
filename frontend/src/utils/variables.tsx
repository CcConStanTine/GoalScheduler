import AppImage from '../images/app_logo.png';
import { Questions, selectLanguageInterface } from './interfaces';

export const appName = "MySchedule";
export const appLogo = AppImage;

export const AccountFormErrorTypes = {
    REQUIRED: 'this field is required',
    PATTERN: 'this field contains of inappropriate sign',
    MINLENGTH: 'this field required at least 2 characters',
    MAXLENGTH: 'this field required between 2 and 50 characters'
}

export const PageNavigationTypes = {
    HOMEPAGE: 'homepage',
    DEFAULT: 'settings',
    VIEWENTRY: 'viewEntry',
}

export const ChangeUserCredentialsTypes = {
    USERNAME: 'username',
    USERPASSWORD: 'password',
    EMAIL: 'email',
    USERPHOTO: 'photo',
};

export const EntryPageConfirmWindowTypes = {
    DELETE: 'delete',
    FINISH: 'finish',
}

export const EntriesPlanType = {
    YEAR: 'year',
    MONTH: 'month',
    DAY: 'day'
}

export const dateTimeTypes = {
    ADDDAY: 'addDay',
    EDITDAY: 'editDay',
    DEFAULT: 'default',
}

export const navigationTypes = {
    ADDITION: 'addition',
    SUBTRACTION: 'subtraction'
}

export const AddEntryPageDefaultValues = {
    startDate: '',
    endDate: '',
    content: '',
    day: '',
}

export const userQuestions: Questions = {
    'en-US': [{
        title: 'Dlaczego tak długo zajmuje wczytanie aplikacji',
        answer: 'Jest to uzależnione od połączenia internetowego, każde zapytanie wysyłane jest do bazdy danych, z której zwracana jest odpowiedź.'
    }],
    'pl-PL': [{
        title: 'Dlaczego tak długo zajmuje wczytanie aplikacji',
        answer: 'Jest to uzależnione od połączenia internetowego, każde zapytanie wysyłane jest do bazdy danych, z której zwracana jest odpowiedź.'
    }]
}

export const languageOptions: selectLanguageInterface = {
    'en-US': [{
        text: 'English',
        value: 'en-US'
    },
    {
        text: 'Polish',
        value: 'pl-PL'
    }],
    'pl-PL': [{
        text: 'Angielski',
        value: 'en-US'
    },
    {
        text: 'Polski',
        value: 'pl-PL'
    }],
}

export const todayPlans = [{
    color: "#f00",
    topic: 'Throw away garbage',
},
{
    color: "darkgreen",
    topic: 'Finish database',
},
{
    color: "darkgreen",
    topic: 'Setup test',
}];