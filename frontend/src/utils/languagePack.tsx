import { appName } from './variables';
import { languageInterface } from './interfaces';

const languagePack: languageInterface = {
    'en-US': {
        WELCOME: {
            signUp: 'Sign Up',
            logIn: 'Log In',
            logginIn: 'Logging In',
            appDescription: 'Hello there general kenobi',
            username: 'username',
            password: 'password',
            firstName: 'first name',
            lastName: 'last name',
            email: 'e-mail',
            createAccount: 'Create Account',
            or: 'or',
            continueWithoutSigningIn: 'Continue Without Signing In',
            errorLoggedIn: 'Username or Password might be wrong',
            userRegisterSuccessfuly: 'User registered successfully',
        },
        HOME: {
            search: 'Search schedule',
            todayPlans: "Today's plans:",
            otherPlans: 'Other plans:',
        },
        ADD: {
            title: 'Add task',
            addTask: 'Add',
            selectDay: 'Select Day',
            selectStartTime: 'Select Start Time',
            selectEndTime: 'Select End Time',
            description: 'Description',
        },
        SETTINGS: {
            title: 'Settings',
            darkMode: 'Dark Mode',
            language: 'Language',
            changeUsername: 'Change Username',
            changePassword: 'Change Password',
            changeEmail: 'Change Email',
            changeUserPhoto: 'Change Use Photo',
            signOut: 'Sign Out',
        },
        VIEWENTRY: {
            title: 'View',
            startTime: 'Start Time',
            endTime: 'End Time',
            description: 'Task Description',
            deleteTask: "Are you sure? You won't be able to get your task back",
            deleteTaskConfirm: 'Delete',
            finishedTask: 'Have you finished this task?',
            finishedTaskConfirm: 'I have',
            restoreTask: 'Do u want to restore this task?',
            restoreTaskConfirm: 'I do',
        },
        CHANGEUSERNAME: {
            title: 'Change Username',
            enterNewUsername: 'Enter New Username',
            changedUsernameFailed: "New username can't be the same like a previous one",
            changedUsernameSuccessfully: 'Username changed successfully',
        },
        CHANGEPASSWORD: {
            title: 'Change Password',
            newPassword: 'New Password',
            repeatNewPassword: 'Repeat New Password',
            changedPasswordFailed: "New password can't be the same like a previous one",
            wrongOldPassword: 'Old password is wrong',
            wrongNewPasswords: 'New passwords are not equal',
            changedPasswordSuccessfully: 'Password Changed Successfully',
        },
        CHANGEEMAIL: {
            title: 'Change Email',
            currentEmail: 'Enter Your Current e-mail',
            newEmail: 'Enter Your New e-mail',
            changedEmailSuccessfully: "Email changed successfull",
            wrongCurrentEmail: 'Current email is wrong',
            changedEmailFailed: "New email can't be the same like a previous one",
        },
        CHANGEUSERPHOTO: {
            title: 'Change User Photo',
            select: 'Select Photo',
            upload: 'Upload Photo',
            delete: 'Delete Photo',
            uploadImageSuccessed: 'Image Uploaded Successfully',
            deleteImageSuccessed: 'Image Deleted Successfully',
        },
        GLOBAL: {
            appName: `${appName}`,
            yearPlan: 'Year Plan',
            monthPlan: 'Month Plan',
            dayPlan: 'Day Plan',
            selectOptionsDone: 'Done',
            selectOptionCancel: 'Cancel',
            logInAgain: 'You need to log in again',
            oldPassword: 'Old Password',
            wrongPassword: 'Password is wrong',
            save: 'save',
            cancel: 'Cancel',
        },
        MONTHS: {
            names: {
                january: 'January',
                february: 'February',
                march: 'March',
                april: 'April',
                may: 'May',
                june: 'June',
                july: 'July',
                august: 'August',
                september: 'September',
                october: 'October',
                november: 'November',
                december: 'December',
            },
            namesTable: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        }
    }
}

export default languagePack;

