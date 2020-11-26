export interface landingPageInterface {
    history: any
}

export interface inputData {
    startDate: string;
    endDate: string;
    content: string;
    startDateTime?: string;
    day?: string;
}

export interface dateParams {
    year: number;
    month: number;
    day: number;
}

export interface ViewEntryRouteParams {
    id: string;
    type: string;
}

export interface EditEntryParams {
    content?: string;
    startDate?: string;
    endDate?: string;
    yearPlanId?: number;
    monthPlanId?: number;
    dayPlanId?: number;
    fulfilled?: boolean;
    length?: any;
}

export interface ViewEntryParams {
    content?: string;
    endDate?: string;
    startDate?: string;
    fulfilled?: boolean;
    dayPlanId?: number;
};

export interface entriesNavigation {
    entryType: string;
    onClick: any;
    date?: dateParams;
}

export interface entryParams {
    [index: number]: singleEntryParams;
    map: any;
}

export interface singleEntryParams {
    content: string;
    yearPlanId?: number;
    monthPlanId?: number;
    dayPlanId?: number;
    fulfilled?: boolean;
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
            text: string;
            value: string;
        },
        {
            text: string;
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
        userPhotoText: string;
        uploadImageSuccessed: string;
        deleteImageSuccessed: string;
        selectPhoto: string;
        uploadImage: string;
        deletePhoto: string;
        viewEntry: string;
        viewStartDate: string;
        viewEndDate: string;
        viewFulfilledPlan: string;
        viewPlanDescription: string;
        deleteText: string;
        cancelText: string;
        entryDeleteText: string;
        successfulyFulfilledText: string;
        successfulyFulfilledTextSendOption: string;
        unFulfilledText: string;
        unFulfilledTextSendOption: string;
        january: string;
        february: string;
        march: string;
        april: string;
        may: string;
        june: string;
        july: string;
        august: string;
        september: string;
        october: string;
        november: string;
        december: string;
        yearPlanText: string;
        monthPlanText: string;
        dayPlanText: string;
        addEntryText: string;
        inputDateAcceptText: string;
        inputDateMonthNames: string[];
        inputPlaceholderStartText: string;
        inputPlaceholderEndText: string;
        textareaPlaceholderText: string;
        dayInputSelectDay: string;
        dayInputSelectStartTime: string;
        dayInputSelectEndTime: string;
        inputSendRequestText: string;
        editEntryText: string;
        selectLanguageText: string;
    };
}