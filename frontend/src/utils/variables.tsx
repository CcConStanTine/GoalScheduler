import AppImage from '../images/app_logo.png';
import { languageInterface, selectLanguageInterface } from './interfaces';

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
}

export const ChangeUserCredentialsTypes = {
    USERNAME: 'username',
    USERPASSWORD: 'password',
    EMAIL: 'email',
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

export const languagePack: languageInterface = {
    'en-US': {
        signOutText: 'Sign Out',
        todayPlansText: "Today's plans:",
        otherPlansText: "Other plans:",
        darkModeText: "Dark Mode",
        languageText: "Language",
        changePasswordText: "Change Password",
        checkOldPasswordError: "Old password is wrong",
        checkNewPasswordError: "New passwords are not equal",
        changeUsernameText: "Change Username",
        LogAgainMessage: "You need to log in again",
        theSamePasswordError: "New password can't be the same like a previous one",
        settingsNavigationText: "Settings",
        changeUserEmail: "Change Email",
        changedUserEmailSuccessfully: "Email changed successfully",
        changedUsernameSuccessfully: "Username changed successfully",
        checkOldEmailError: "Current email is wrong",
        theSameEmailError: "New email can't be the same like a previous one",
        theSameUsernameError: "New username can't be the same like a previous one",
        changeUserSettingsInputValue: "Save",
        checkPasswordError: "Password is wrong",
        continueWithoutSigningInMessage: "Continue Without Signing In",
        appWelcomeSpanMessage: "or",
        signUp: "Sign Up",
        loginIn: "Log In",
        successfullyLoggedIn: 'Successfully',
        errorLoggedIn: 'Username or Password might be wrong',
        appLogoDescription: `${appName} logo`,
        createAccountText: "Create Account",
        appWelcomeMessage: "Hello there general kenobi. Hello there general kenobi",
        username: 'username',
        password: 'password',
        firstName: 'first name',
        lastName: 'last name',
        email: 'e-mail',
        search: 'Search schedule',
        currentEmail: 'Enter your current email',
        newEmail: 'Enter your new email',
        oldPassword: 'Old Password',
        newPassword: 'New Password',
        newPasswordRepeat: 'Repeat New Password',
        newUsername: 'Enter new username'
    },
    'pl-PL': {
        signOutText: 'Wyloguj się',
        todayPlansText: "Dzisiejsze plany:",
        otherPlansText: "Inne plany:",
        darkModeText: "Tryb nocny",
        languageText: "Język",
        changePasswordText: "Zmień hasło",
        checkOldPasswordError: "Aktualne hasło jest błędne",
        checkNewPasswordError: "Nowe hasła nie są identyczne",
        changeUsernameText: "Zmień nazwę użytkownika",
        LogAgainMessage: "Musisz jeszcze raz się zalogować",
        theSamePasswordError: "Nowe hasło nie może być takie same jak stare",
        settingsNavigationText: "Ustawienia",
        changeUserEmail: "Zmień email",
        changedUserEmailSuccessfully: "Udało się zmienić email",
        changedUsernameSuccessfully: "Udało się zmienić nazwę użytkownika",
        checkOldEmailError: "Aktualny email jest błędny",
        theSameEmailError: "Nowy email nie może być taki sam jak poprzedni",
        theSameUsernameError: "Nowa nazwa użytkownika nie może być taka sama jak poprzednia",
        changeUserSettingsInputValue: "Zapisz",
        checkPasswordError: "Hasło jest błędne",
        continueWithoutSigningInMessage: "Kontynuuj bez rejestracji",
        appWelcomeSpanMessage: "lub",
        signUp: "Zarejestruj się",
        loginIn: "Zaloguj się",
        successfullyLoggedIn: 'Zalogowano',
        errorLoggedIn: 'Nazwa użytkownika lub hasło jest błędne',
        appLogoDescription: `${appName} logo`,
        createAccountText: "Zarejestruj",
        appWelcomeMessage: "Hello there general kenobi. Hello there general kenobi",
        username: 'Nazwa użytkownika',
        password: 'Hasło',
        firstName: 'Imie',
        lastName: 'Nazwisko',
        email: 'E-mail',
        search: 'Wyszukaj plan',
        currentEmail: 'Aktualny e-mail',
        newEmail: 'Nowy e-mail',
        oldPassword: 'Aktualne hasło',
        newPassword: 'Nowe hasło',
        newPasswordRepeat: 'Powtórz nowe hasło',
        newUsername: 'Nowa nazwa użytkownika'
    }
}
