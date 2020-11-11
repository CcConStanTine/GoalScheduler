import React, { useState, useContext } from 'react';
import { useForm } from "react-hook-form";
import { AccountFormTypes, createAccountText, loginIn } from '../utils/variables';
import { accountFormInterface, registerUser } from '../utils/interfaces';
import auth from '../authentication/database';
import renderAccountFormInputs from './RenderAccountFormInputs';
import { AppContext } from '../authentication/AppContext';


const AccountForm = ({ type, history }: accountFormInterface) => {
    const { register, handleSubmit, errors } = useForm();
    const [loginMessage, setLoginMessage] = useState('');
    const [registerMessage, setRegisterMessage] = useState('');
    const { setLoggedIn } = useContext(AppContext);

    const LoginAccountInputData = [{
        name: "username",
        type: "text",
        placeholder: "nickname",
        ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.nickname
    },
    {
        name: "password",
        type: "password",
        placeholder: "password",
        ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.password
    }]

    const CreateAccountInputData = [{
        name: "firstName",
        type: "text",
        placeholder: "first name",
        ref: register({ required: true, pattern: /^[a-zA-Z]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.firstName
    }, {
        name: "lastName",
        type: "text",
        placeholder: "last name",
        ref: register({ required: true, pattern: /^[a-zA-Z]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.firstName
    }, {
        name: "email",
        type: "email",
        placeholder: "e-mail",
        ref: register({ required: true, pattern: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.email
    }, {
        name: "username",
        type: "text",
        placeholder: "username",
        ref: register({ required: true, pattern: /^[a-zA-Z0-9]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.username
    },
    {
        name: "password",
        type: "password",
        placeholder: "password",
        ref: register({ required: true, pattern: /^[a-zA-Z0-9]+$/i, minLength: 2, maxLength: 50 }),
        errors: errors.password
    }];

    // const checkIfCanLogIn = (key: number) => {
    //     if (key === 200) return true;
    //     return false
    // }

    // const logInUser = (userInfo?: object) => {
    //     if (setLoggedIn)
    //         setLoggedIn({ ...userInfo, loggedIn: auth.isAuthenticated() })

    //     setTimeout(() => {
    //         return history.push('/app/home');
    //     }, 1000);
    // }

    const sendRequestToCreateUser = async (data: any) => {
        await console.log(auth.register(data));
        // console.log(message);
    }

    const sendRequestToLoginUser = async (data: any) => {
        const { token, message } = await auth.login(data);

        if (token && setLoggedIn) return setLoggedIn(auth.getCurrentUser);

        return setLoginMessage(message)
    }

    if (type === AccountFormTypes.CREATE) return (
        <form onSubmit={handleSubmit(sendRequestToCreateUser)}>
            {renderAccountFormInputs(CreateAccountInputData)}
            {registerMessage && <span className="database-message">{registerMessage}</span>}
            <input className="send-form-button" type="submit" value={createAccountText} />
        </form>
    )

    return (
        <form onSubmit={handleSubmit(sendRequestToLoginUser)}>
            {renderAccountFormInputs(LoginAccountInputData)}
            {loginMessage && <span className="database-message">{loginMessage}</span>}
            <input className="send-form-button" type="submit" value={loginIn} />
        </form>
    )
};

export default AccountForm;