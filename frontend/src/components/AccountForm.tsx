import React, { useState, useContext } from 'react';
import { useForm } from "react-hook-form";
import { AccountFormTypes } from '../utils/variables';
import languagePack from '../utils/languagePack';
import { accountFormInterface, registerUser, loginUser } from '../utils/interfaces';
import DataRequests from '../authentication/DataRequests';
import renderAccountFormInputs from './RenderAccountFormInputs';
import { AppContext } from '../authentication/AppContext';
import { LanguageContext } from '../authentication/LanguageContext';

const AccountForm = ({ type }: accountFormInterface): JSX.Element => {
    const { register, handleSubmit, errors } = useForm();
    const [loginMessage, setLoginMessage] = useState<string>('');
    const [registerMessage, setRegisterMessage] = useState<string>('');
    const { setLoggedIn } = useContext(AppContext);
    const { language } = useContext(LanguageContext);

    const LoginAccountInputData = [{
        name: "username",
        type: "text",
        placeholder: languagePack[language].WELCOME.username,
        ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.nickname
    },
    {
        name: "password",
        type: "password",
        placeholder: languagePack[language].WELCOME.password,
        ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.password
    }]

    const CreateAccountInputData = [{
        name: "firstName",
        type: "text",
        placeholder: languagePack[language].WELCOME.firstName,
        ref: register({ required: true, pattern: /^[a-zA-Z]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.firstName
    }, {
        name: "lastName",
        type: "text",
        placeholder: languagePack[language].WELCOME.lastName,
        ref: register({ required: true, pattern: /^[a-zA-Z]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.firstName
    }, {
        name: "email",
        type: "email",
        placeholder: languagePack[language].WELCOME.email,
        ref: register({ required: true, pattern: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.email
    }, {
        name: "username",
        type: "text",
        placeholder: languagePack[language].WELCOME.username,
        ref: register({ required: true, pattern: /^[a-zA-Z0-9]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.username
    },
    {
        name: "password",
        type: "password",
        placeholder: languagePack[language].WELCOME.password,
        ref: register({ required: true, pattern: /^[a-zA-Z0-9]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.password
    }];

    const sendRequestToCreateUser = async (data: registerUser): Promise<void> => {
        const { status, message } = await DataRequests.register(data);
        if (status === 200) {
            setTimeout(() => setRegisterMessage(languagePack[language].WELCOME.logginIn), 1000);
            setTimeout(() => sendRequestToLoginUser(data), 2000);
        }

        return setRegisterMessage(message);
    }

    const sendRequestToLoginUser = async (data: loginUser): Promise<void> => {
        const userData = await DataRequests.login(data);
        DataRequests.setLocalStorageValues(userData);

        if (userData.token && setLoggedIn) return setLoggedIn(DataRequests.getCurrentUser);

        const message = userData.status ? languagePack[language].WELCOME.errorLoggedIn : languagePack[language].WELCOME.logginIn;

        return setLoginMessage(message)
    }

    if (type === AccountFormTypes.CREATE) return (
        <form onSubmit={handleSubmit(sendRequestToCreateUser)}>
            {renderAccountFormInputs(CreateAccountInputData)}
            {registerMessage && <span className="database-message">{registerMessage}</span>}
            <input className="default-button send-form-button" type="submit" value={languagePack[language].WELCOME.createAccount} />
        </form>
    )

    return (
        <form onSubmit={handleSubmit(sendRequestToLoginUser)}>
            {renderAccountFormInputs(LoginAccountInputData)}
            {loginMessage && <span className="database-message">{loginMessage}</span>}
            <input className="default-button send-form-button" type="submit" value={languagePack[language].WELCOME.logInButton} />
        </form>
    )
};

export default AccountForm;