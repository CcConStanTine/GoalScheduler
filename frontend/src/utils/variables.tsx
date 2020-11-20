import AppImage from '../images/app_logo.png';
import { selectLanguageInterface } from './interfaces';

export const appName = "MySchedule";
export const appLogo = AppImage;

export const AccountFormTypes = {
    CREATE: 'create',
    LOGIN: 'login',
};

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

export const navigationTypes = {
    ADDITION: 'addition',
    SUBTRACTION: 'subtraction'
}


export const languageOptions: selectLanguageInterface = {
    'en-US': [{
        label: 'English',
        value: 'en-US'
    },
    {
        label: 'Polish',
        value: 'pl-PL'
    }],
    'pl-PL': [{
        label: 'Angielski',
        value: 'en-US'
    },
    {
        label: 'Polski',
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