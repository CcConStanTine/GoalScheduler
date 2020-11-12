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
export const successfullyLogedIn = 'Successfully';
export const errorLogedIn = 'Username or Password might be wrong';
export const appLogoDescription = `${appName} logo`;
export const createAccountText = "Create Account";
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

export const darkModeText = "Dark Mode";
export const signOutText = "Sign Out";
export const languageText = "Language";
export const changePasswordText = "Change Password";
export const checkOldPasswordError = "Old password is wrong";
export const checkNewPasswordError = "New passwords are not equal";
export const changeUsernameText = "Change Username";
export const LogAgainMessage = "You need to log in again";
export const theSamePasswordError = "New password can't be the same like a previous one";
export const settingsNavigationText = "Settings";
export const changeUserEmail = "Change Email";
export const changedUserEmailSuccessfully = "Email changed successfully";
export const changedUsernameSuccessfully = "Username changed successfully";
export const checkOldEmailError = "Current email is wrong";
export const theSameEmailError = "New email can't be the same like a previous one";
export const theSameUsernameError = "New username can't be the same like a previous one";
export const changeUserSettingsInputValue = "Save";
export const checkPasswordError = "Password is wrong";

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