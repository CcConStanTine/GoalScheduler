import React, { useContext, useState } from 'react';
import { AppContext } from '../../../authentication/AppContext';
import { LanguageContext } from '../../../authentication/LanguageContext';
import DataRequests from '../../../authentication/DataRequests';
import { useForm } from "react-hook-form";
import renderAccountFormInputs from '../../../components/mobile/RenderAccountFormInputs';
import { PageNavigationTypes } from '../../../utils/variables';
import languagePack from '../../../utils/languagePack';
import NavigationBar from '../../../components/mobile/NavigationBar';
import { ChangePasswordInteface } from '../../../utils/interfaces'

interface PasswordPage {
  mobile?: boolean;
}

const ChangePasswordPage = ({ mobile = true }: PasswordPage): JSX.Element => {
  const { setLoggedIn } = useContext(AppContext);
  const { language } = useContext(LanguageContext);
  const { register, handleSubmit, errors } = useForm();
  const [message, setMessage] = useState<string>('');

  const ChangePasswordInputData = [{
    name: "oldPassword",
    type: "text",
    placeholder: languagePack[language].GLOBAL.oldPassword,
    ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.oldPassword
  },
  {
    name: "newPassword",
    type: "text",
    placeholder: languagePack[language].CHANGEPASSWORD.newPassword,
    ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.newPassword
  },
  {
    name: "newPasswordRepeat",
    type: "text",
    placeholder: languagePack[language].CHANGEPASSWORD.repeatNewPassword,
    ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.newPasswordRepeat
  }];

  const changePasswordAndForceUserToLogAgain = async (newPassword: string) => {
    const { message } = await DataRequests.changeUserPassword(newPassword);
    setMessage(message);

    setTimeout(() => {
      setMessage(languagePack[language].GLOBAL.logInAgain)
    }, 1000);
    return setTimeout(() => {
      DataRequests.logout();
      setLoggedIn!({});
    }, 2000);
  }

  const checkPassword = (oldPassword: string, newPassword: string, newPasswordRepeat: string): void | Promise<NodeJS.Timeout> => {
    if (newPassword === newPasswordRepeat) {
      if (newPassword !== oldPassword)
        return changePasswordAndForceUserToLogAgain(newPassword)

      return setMessage(languagePack[language].CHANGEPASSWORD.changedPasswordFailed);
    }

    return setMessage(languagePack[language].CHANGEPASSWORD.wrongNewPasswords);
  }

  const sendRequestToChangePassword = async ({ oldPassword, newPassword, newPasswordRepeat }: ChangePasswordInteface) => {
    const response = await DataRequests.checkIfPasswordIsCorrect(oldPassword);

    if (response)
      return checkPassword(oldPassword, newPassword, newPasswordRepeat);

    return setMessage(languagePack[language].GLOBAL.wrongPassword)
  };

  return (
    <section className={`change-password-page ${!mobile && 'desktop'}`}>
      {mobile && <NavigationBar type={PageNavigationTypes.DEFAULT} placeholder={languagePack[language].CHANGEPASSWORD.title} />}
      <form onSubmit={handleSubmit(sendRequestToChangePassword)}>
        {renderAccountFormInputs(ChangePasswordInputData)}
        {message && <p className='change-password-message'>{message}</p>}
        <input className={mobile ? "default-button" : "desktop-default-button two"} type="submit" value={languagePack[language].GLOBAL.save} />
      </form>
    </section>
  );
};

export default ChangePasswordPage;
