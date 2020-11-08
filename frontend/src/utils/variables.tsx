import AppImage from '../images/app_logo.png';

export const appName = "MySchedule";
export const appLogo = AppImage;
export const appWelcomeMessage = "Hello there general kenobi. Hello there general kenobi";
export const AccountFormTypes = {
    CREATE: 'create',
    LOGIN: 'login',
};
export const continueWithoutSigningInMessage = "Continue Without Signing In";
export const appWelcomeSpanMessage = "or";
export const signUp = "Sign Up";
export const loginIn = "Log In";
export const appLogoDescription = `${appName} logo`;
export const createAccountText = "Create Account";
export const AccountFormErrorTypes = {
    REQUIRED: 'this field is required',
    PATTERN: 'this field contains of inappropriate sign',
    MINLENGTH: 'this field required at least 2 characters',
    MAXLENGTH: 'this field required between 2 and 50 characters'
}

export const darkModeText = "Dark Mode";
export const signOutText = "Sign Out";
export const languageText = "Language";
export const changePasswordText = "Change Password";
export const changeUsernameText = "Change Username";

export const languageOptions = [{
    value: 'polish',
    label: 'Polish'
},
{
    value: 'english',
    label: 'English'
}
];

export const todayPlansText = "Today's plans:";
export const otherPlansText = "Other plans:";

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
}]