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
            logInButton: 'Sign In',
            or: 'or',
            continueWithoutSigningIn: 'Continue Without Signing In',
            errorLoggedIn: 'Username or Password might be wrong',
            userRegisterSuccessfuly: 'User registered successfully',
        },
        HOME: {
            search: 'Search schedule',
            todayPlans: "Today's plans:",
            otherPlans: 'Other plans:',
            emptyEntries: 'Empty?',
            addEntry: 'Add Entry',
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
            changeUserPhoto: 'Change User Photo',
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
            fulfilledTask: 'This task has been completed',
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
            uploadFailed: 'Image have to be smaller than 1MB',
            delete: 'Delete Photo',
            uploadImageSuccessed: 'Image Uploaded Successfully',
            deleteImageSuccessed: 'Image Deleted Successfully',
        },
        PROFILE: {
            title: 'Profile',
        },
        HELP: {
            title: 'Help',
        },
        GLOBAL: {
            appName: `${appName}`,
            yearPlan: 'Year Plan',
            monthPlan: 'Month Plan',
            dayPlan: 'Day Plan',
            selectOptionsDone: 'Done',
            selectOptionCancel: 'Cancel',
            logInAgain: 'You need to log in again',
            oldPassword: 'Password',
            wrongPassword: 'Password is wrong',
            save: 'save',
            cancel: 'Cancel',
        },
        EDIT: {
            title: 'Edit',
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
    },
    'pl-PL': {
        WELCOME: {
            signUp: 'Zarejestruj',
            logIn: 'Zaloguj',
            logginIn: 'Logowanie',
            appDescription: 'Hello there general kenobi',
            username: 'Pseudonim',
            password: 'Hasło',
            firstName: 'Imie',
            lastName: 'Nazwisko',
            email: 'e-mail',
            createAccount: 'Stwórz konto',
            logInButton: 'Zaloguj się',
            or: 'lub',
            continueWithoutSigningIn: 'Kontynuuj bez logowania się',
            errorLoggedIn: 'Pseudonim lub hasło są błędne',
            userRegisterSuccessfuly: 'Zarejestrowany',
        },
        HOME: {
            search: 'Szukaj planu',
            todayPlans: "Dzisiejsze plany",
            otherPlans: 'Inne plany',
            emptyEntries: 'Brak planów',
            addEntry: 'Dodaj',
        },
        ADD: {
            title: 'Dodaj zadanie',
            addTask: 'Dodaj',
            selectDay: 'Wybierz dzień',
            selectStartTime: 'Wybierz datę rozpoczęcia',
            selectEndTime: 'Wybierz datę zakończenia',
            description: 'Opis zadania',
        },
        SETTINGS: {
            title: 'Ustawienia',
            darkMode: 'Tryb nocny',
            language: 'Język',
            changeUsername: 'Zmień pseudonim',
            changePassword: 'Zmień hasło',
            changeEmail: 'Zmień e-mail',
            changeUserPhoto: 'Zmień obraz',
            signOut: 'Wyloguj się',
        },
        VIEWENTRY: {
            title: 'Podgląd',
            startTime: 'Data rozpoczęcia',
            endTime: 'Data zakończenia',
            description: 'Opis zadania',
            deleteTask: "Jesteś pewny? Nie będzie można przywrócić zadania",
            deleteTaskConfirm: 'Usuń',
            finishedTask: 'Czy to zadanie jest ukończone?',
            finishedTaskConfirm: 'Tak, ukończ',
            restoreTask: 'Czy przywrócić to zadanie do aktywnych?',
            restoreTaskConfirm: 'Tak, przywróć',
            fulfilledTask: 'To zadanie jest ukończone',
        },
        CHANGEUSERNAME: {
            title: 'Zmień pseudonim',
            enterNewUsername: 'Podaj nowy pseudonim',
            changedUsernameFailed: "Nowy pseudonim nie może być taki jak poprzedni",
            changedUsernameSuccessfully: 'Ustawiono nowy pseudonim',
        },
        CHANGEPASSWORD: {
            title: 'Zmień hasło',
            newPassword: 'Nowe hasło',
            repeatNewPassword: 'Powtórz nowe hasło',
            changedPasswordFailed: "Nowe hasło nie może byc takie samo jak poprzednie",
            wrongOldPassword: 'Aktualne hasło jest błędne',
            wrongNewPasswords: 'Nowe hasła nie są identyczne',
            changedPasswordSuccessfully: 'Pomyślnie zmieniono hasło',
        },
        CHANGEEMAIL: {
            title: 'Zmień e-mail',
            currentEmail: 'Wpisz swój aktualny e-mail',
            newEmail: 'Wpisz swój nowy e-mail',
            changedEmailSuccessfully: "Pomyślnie zmieniono e-mail",
            wrongCurrentEmail: 'Aktualny e-mail jest błędny',
            changedEmailFailed: "Nowy e-mail nie może taki sam jak aktualny",
        },
        CHANGEUSERPHOTO: {
            title: 'Zmień zdjęcie',
            select: 'Wybierz zdjęcie',
            upload: 'Ustaw zdjęcie',
            uploadFailed: 'Obraz musi mieć < 1MB',
            delete: 'Usuń zdjęcie',
            uploadImageSuccessed: 'Pomyślnie zmieniono zdjęcie',
            deleteImageSuccessed: 'Pomyślnie usunięto zdjęcie',
        },
        PROFILE: {
            title: 'Profil',
        },
        HELP: {
            title: 'Pomoc',
        },
        GLOBAL: {
            appName: `${appName}`,
            yearPlan: 'Rok',
            monthPlan: 'Miesiąc',
            dayPlan: 'Dzień',
            selectOptionsDone: 'Ustaw',
            selectOptionCancel: 'Anuluj',
            logInAgain: 'Musisz jeszcze raz się zalogować',
            oldPassword: 'Aktualne hasło',
            wrongPassword: 'Hasło jest błędne',
            save: 'Zapisz',
            cancel: 'Anuluj',
        },
        EDIT: {
            title: 'Edytuj',
        },
        MONTHS: {
            names: {
                january: 'Styczeń',
                february: 'Luty',
                march: 'Marzec',
                april: 'Kwiecień',
                may: 'Maj',
                june: 'Czerwiec',
                july: 'Lipiec',
                august: 'Sierpień',
                september: 'Wrzesień',
                october: 'Październik',
                november: 'Listopad',
                december: 'Grudzień',
            },
            namesTable: ['Styczeń', 'Luty', 'Marzec', 'Kwiecień', 'Maj', 'Czerwiec', 'Lipiec', 'Sierpień', 'Wrzesień', 'Październik', 'Listopad', 'Grudzień'],
        }
    }
}

export default languagePack;

