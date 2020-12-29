import { DatePickerOptions, OptionTypes, PlanTypes } from './enums';
import { Plans } from './requestsInterfaces';

export interface OptionActiveInterface {
    id?: number;
    dateValue: string;
    setDefaultValues?: () => void;
    setOptionActiveType: (value: OptionTypes) => void;
    setOpenWindow?: (value: OpenWindowProperties) => void;
    setEdit?: (value: EditDayDesktop) => void;
}

export interface UserContext {
    email?: string;
    firstName?: string;
    lastName?: string;
    nick?: string;
    token?: string;
    userId?: number;
    userPhoto?: string;
}

export interface AppContextInterface {
    userContext?: UserContext;
    setLoggedIn?: (value: UserContext) => void;
}

export interface ReturnWindowDimension {
    width: number;
};

export interface FulfilledWindowInterface {
    isActive: boolean;
    id: null | number;
    fulfilled: boolean;
}

export interface DeleteWindowInterface {
    isActive: boolean;
    id: null | number;
}

export interface FulfilledMessage {
    setFulfilledWindow: (value: FulfilledWindowInterface) => void;
    fulfilledWindow: FulfilledWindowInterface;
}

export interface DeleteMessage {
    setDeleteWindow: (value: DeleteWindowInterface) => void;
    deleteWindow: DeleteWindowInterface;
}

export interface DatePickerTimeValues {
    minutes: number;
    hours: number;
    monthName: string;
    year: number;
}

export interface EditDayDesktop {
    isActive: boolean;
    taskDescription: string;
    startDate: string;
    id: null | number;
}

export interface AddDayDesktop {
    id: number;
    setRecentlyAddedPlanId: (value: number) => void;
    setOptionActiveType: (value: OptionTypes) => void;
    edit: EditDayDesktop;
    setEdit: (value: EditDayDesktop) => void;
}

export interface RenderDayPlansInterface {
    planList: any;
    setDeleteWindow: (value: DeleteWindowInterface) => void;
    setEdit: (value: EditDayDesktop) => void;
    setOptionActiveType: (value: OptionTypes) => void;
    setFulfilledWindow: (value: FulfilledWindowInterface) => void;
}

export interface inputData {
    startDate: string;
    endDate: string;
    content: string;
    startDateTime?: string;
    day?: string;
}

export interface Date {
    date?: string;
    option?: DatePickerOptions;
    startDate?: string;
}


export interface DatePickerContextInterface {
    isDatePickerActive: boolean;
    date: Date;
    setDate?: (value: Date) => void;
    openDatePicker?: (value: boolean) => void;
}


export interface EmpPlans {
    setOpenWindow: any;
    id: number;
    setRecentlyAddedPlanId: any;
    edit: EditDayDesktop;
    setEdit: (value: EditDayDesktop) => void;
    optionActiveType: OptionTypes;
    setOptionActiveType: (value: OptionTypes) => void;
}

export interface QuestionContent {
    title: string;
    answer: string | JSX.Element;
}

export interface RenderQues {
    [key: number]: QuestionContent
    map: any;
}

export interface Questions {
    [key: string]: RenderQues;
};

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
    type: PlanTypes;
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

export enum OpenWindowTypes {
    EDIT = 'edit',
    DELETE = 'delete',
    SHOW = 'show',
}

export interface OpenWindowProperties {
    id?: number;
    isActive: boolean;
    type?: OpenWindowTypes;
}

export interface OpenWindow {
    setOpenWindow?: (value: OpenWindowProperties) => void;
    id?: number;
    isActive: boolean;
    type?: OpenWindowTypes;
}

export interface ContextMenuProperties {
    pageX?: number;
    pageY?: number;
    id?: number;
    isActive: boolean;
}

export interface PickerContent {
    timeValues: DatePickerTimeValues;
    setDateValues: (value: DatePickerTimeValues) => void;
};

export interface ContextMenu {
    setOpenWindow?: (value: OpenWindowProperties) => void;
    setContextMenu?: (value: ContextMenuProperties) => void;
    pageX?: number;
    pageY?: number;
    id?: number;
    isActive: boolean;
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
    features: string;
    privacyAndSafety: string;
    contact: string;
}

export enum DateSequences {
    DAY = 'day',
    MONTH = 'month',
    YEAR = 'year',
}

export enum DateTypes {
    NORMAL = 'normal',
    REQUEST = 'request'
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
    userLanguageInfo: string;
    themeInfo: string;
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
    loadingCalendar: string;
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

interface days {
    daysAsAnArray: string[];
}

interface welcomePageDesktopOptions {
    title: string;
    info: string;
    header: string;
}

interface welcomePageDesktop {
    SIGNUP: welcomePageDesktopOptions;
    SIGNIN: welcomePageDesktopOptions;
}

interface timePicker {
    update: string;
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
        WELCOMEPAGEDESKTOP: welcomePageDesktop;
        TIMEPICKER: timePicker;
        GLOBAL: global;
        MONTHS: months;
        DAYS: days;
    };
}