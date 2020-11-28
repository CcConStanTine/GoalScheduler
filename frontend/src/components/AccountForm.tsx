import React, { useState, useContext } from 'react';
import { useForm } from "react-hook-form";
import { AccountFormTypes } from '../utils/variables';
import languagePack from '../utils/languagePack';
import { accountFormInterface } from '../utils/interfaces';
import auth from '../authentication/database';
import renderAccountFormInputs from './RenderAccountFormInputs';
import { AppContext } from '../authentication/AppContext';
import { LanguageContext } from '../authentication/LanguageContext';


const AccountForm = ({ type, history }: accountFormInterface) => {
    const { register, handleSubmit, errors } = useForm();
    const [loginMessage, setLoginMessage] = useState('');
    const [registerMessage, setRegisterMessage] = useState('');
    const { setLoggedIn } = useContext(AppContext);
    const { language } = useContext(LanguageContext);

    const LoginAccountInputData = [{
        name: "username",
        type: "text",
        placeholder: languagePack[language].username,
        ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.nickname
    },
    {
        name: "password",
        type: "password",
        placeholder: languagePack[language].password,
        ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.password
    }]

    const CreateAccountInputData = [{
        name: "firstName",
        type: "text",
        placeholder: languagePack[language].firstName,
        ref: register({ required: true, pattern: /^[a-zA-Z]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.firstName
    }, {
        name: "lastName",
        type: "text",
        placeholder: languagePack[language].lastName,
        ref: register({ required: true, pattern: /^[a-zA-Z]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.firstName
    }, {
        name: "email",
        type: "email",
        placeholder: languagePack[language].email,
        ref: register({ required: true, pattern: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.email
    }, {
        name: "username",
        type: "text",
        placeholder: languagePack[language].username,
        ref: register({ required: true, pattern: /^[a-zA-Z0-9]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.username
    },
    {
        name: "password",
        type: "password",
        placeholder: languagePack[language].password,
        ref: register({ required: true, pattern: /^[a-zA-Z0-9]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.password
    }];

    const sendRequestToCreateUser = async (data: any) => {
        const { status, message } = await auth.register(data);
        if (status === 200) {
            setTimeout(() => setRegisterMessage(languagePack[language].loggingIn), 1000);
            setTimeout(() => sendRequestToLoginUser(data), 2000);
        }

        return setRegisterMessage(message);
    }

    const sendRequestToLoginUser = async (data: any) => {
        const { token, code } = await auth.login(data);
        if (token && setLoggedIn) return setLoggedIn(auth.getCurrentUser);

        const message = code === 200 ? languagePack[language].successfullyLoggedIn : languagePack[language].errorLoggedIn;

        return setLoginMessage(message)
    }

    if (type === AccountFormTypes.CREATE) return (
        <form onSubmit={handleSubmit(sendRequestToCreateUser)}>
            {renderAccountFormInputs(CreateAccountInputData)}
            {registerMessage && <span className="database-message">{registerMessage}</span>}
            <input className="send-form-button" type="submit" value={languagePack[language].createAccountText} />
        </form>
    )

    return (
        <form onSubmit={handleSubmit(sendRequestToLoginUser)}>
            {renderAccountFormInputs(LoginAccountInputData)}
            {loginMessage && <span className="database-message">{loginMessage}</span>}
            <input className="send-form-button" type="submit" value={languagePack[language].loginIn} />
        </form>
    )
};

export default AccountForm;