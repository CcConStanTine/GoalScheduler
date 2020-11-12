export interface landingPageInterface {
    history: any
}

export interface formDataInterface {
    data: Object,
    nickname: string,
    password: string,
    email?: string,
    name?: string,
}

export interface registerUser {
    username: string,
    password: string,
    email: string,
    firstName: string,
    lastName: string
}

export interface loginUser {
    username: string,
    password: string
}

export interface createAccountDataInterface {
    nickname: string,
    password: string,
    email: string,
    name: string,
}

export interface accountFormInterface {
    type: String,
    history: any
}

export interface appInterface {
    history: any
}

export interface renderAccountDataInterface {
    [index: number]: inputDataInterface,
    map: any,
}

export interface inputDataInterface {
    name: string,
    type: string,
    placeholder: string,
    ref: any,
    errors?: ErrorTypeInterface
}

export interface ErrorTypeInterface {
    type?: string,
    message?: string
}

export interface checkErrorTypeInterface {
    type?: string,
}

export interface selectLanguageInterface {
    [key: string]: [
        {
            label: string;
            value: string;
        },
        {
            label: string;
            value: string;
        }]
};

export interface languageInterface {
    [key: string]: {
        signOutText: string;
        todayPlansText: string;
        darkModeText: string;
        languageText: string;
        changePasswordText: string;
        checkOldPasswordError: string;
        checkNewPasswordError: string;
        changeUsernameText: string;
        LogAgainMessage: string;
        theSamePasswordError: string;
        settingsNavigationText: string;
        changeUserEmail: string;
        changedUserEmailSuccessfully: string;
        changedUsernameSuccessfully: string;
        checkOldEmailError: string;
        theSameEmailError: string;
        theSameUsernameError: string;
        changeUserSettingsInputValue: string;
        checkPasswordError: string;
        otherPlansText: string;
        appWelcomeSpanMessage: string;
        signUp: string;
        loginIn: string;
        successfullyLoggedIn: string;
        errorLoggedIn: string;
        appLogoDescription: string;
        createAccountText: string;
        continueWithoutSigningInMessage: string;
        appWelcomeMessage: string;
        username: string;
        password: string;
        firstName: string;
        lastName: string;
        email: string;
        search: string;
        currentEmail: string;
        newEmail: string;
        oldPassword: string;
        newPassword: string;
        newPasswordRepeat: string;
        newUsername: string;
    };
}