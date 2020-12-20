import { Plans } from './requestsInterfaces';

export interface inputData {
    startDate: string;
    endDate: string;
    content: string;
    startDateTime?: string;
    day?: string;
}

export interface QuestionContent {
    title: string;
    answer: string | JSX.Element;
}

export interface renderQues {
    [key: number]: QuestionContent
    map: any;
}

export interface Questions {
    [key: string]: renderQues
};


export enum DesktopDisplayOptions {
    HOME = 'home',
    PROFILE = 'profile',
    HELP = 'help',
    SETTINGS = 'settings',
}
export interface EmailInterface {
    newEmail: string;
    currentEmail: string;
}

export interface EmailInterface {
    password: string;
    username: string;
}

export interface ChangePasswordInteface {
    oldPassword: string;
    newPassword: string;
    newPasswordRepeat: string;
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
    onClick: Function;
    date?: dateParams;
}

export interface entryParams {
    [index: number]: singleEntryParams;
    map: any;
    length: any;
}

export interface AddEntry {
    languagePack: any;
    entryType: any;
    entry?: Plans;
    id: number;
}

export interface FormatDate {
    content: string;
    day?: string;
    startDate: string;
    endDate: string;
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

export interface accountFormInterface {
    type: String;
    headerSignUp?: String;
    headerLogIn?: String;
    mobile?: boolean;
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

interface welcome {
    signUp: string;
    logIn: string;
    logginIn: string;
    logInButton: string;
    appDescription: string;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    createAccount: string;
    or: string;
    continueWithoutSigningIn: string;
    errorLoggedIn: string;
    userRegisterSuccessfuly: string;
}

export enum DateSequences {
    DAY = 'day',
    MONTH = 'month',
    YEAR = 'year',
}

interface home {
    title: string;
    search: string;
    todayPlans: string;
    otherPlans: string;
    emptyEntries: string;
    addEntry: string;
}

interface add {
    title: string;
    addTask: string;
    selectDay: string;
    selectStartTime: string;
    selectEndTime: string;
    description: string;
}

interface settings {
    title: string;
    darkMode: string;
    language: string;
    changeUsername: string;
    changePassword: string;
    changeEmail: string;
    changeUserPhoto: string;
    signOut: string;
}

interface viewEntry {
    title: string;
    startTime: string;
    endTime: string;
    description: string;
    deleteTask: string;
    deleteTaskConfirm: string;
    finishedTask: string;
    finishedTaskConfirm: string;
    restoreTask: string;
    restoreTaskConfirm: string;
    fulfilledTask: string;
}

interface changeUsername {
    title: string;
    enterNewUsername: string;
    changedUsernameFailed: string;
    changedUsernameSuccessfully: string;
}

interface changePassword {
    title: string;
    newPassword: string;
    repeatNewPassword: string;
    changedPasswordFailed: string;
    wrongOldPassword: string;
    wrongNewPasswords: string;
    changedPasswordSuccessfully: string;
}

interface changeEmail {
    title: string;
    currentEmail: string;
    newEmail: string;
    changedEmailSuccessfully: string;
    wrongCurrentEmail: string;
    changedEmailFailed: string;
}

interface changeUserphoto {
    title: string;
    select: string;
    upload: string;
    uploadFailed: string;
    delete: string;
    uploadImageSuccessed: string;
    deleteImageSuccessed: string;
}

interface global {
    appName: string;
    yearPlan: string;
    monthPlan: string;
    dayPlan: string;
    selectOptionsDone: string;
    selectOptionCancel: string;
    logInAgain: string;
    oldPassword: string;
    wrongPassword: string;
    save: string;
    cancel: string;
}

interface months {
    names: {
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
    },
    namesTable: string[];
}

interface editEntry {
    title: string;
}

interface profile {
    title: string;
}

interface help {
    title: string;
}

export interface languageInterface {
    [key: string]: {
        WELCOME: welcome;
        HOME: home;
        ADD: add;
        SETTINGS: settings;
        VIEWENTRY: viewEntry;
        CHANGEUSERNAME: changeUsername;
        CHANGEPASSWORD: changePassword;
        CHANGEEMAIL: changeEmail;
        CHANGEUSERPHOTO: changeUserphoto;
        EDIT: editEntry;
        PROFILE: profile;
        HELP: help;
        GLOBAL: global;
        MONTHS: months;
    };
}