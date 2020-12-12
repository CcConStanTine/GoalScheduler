import React, { useContext, useState } from 'react';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import DataRequests from '../../authentication/DataRequests';
import { useForm } from "react-hook-form";
import renderAccountFormInputs from '../../components/RenderAccountFormInputs';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import { EmailInterface } from '../../utils/interfaces';

const ChangeUsernamePage = (): JSX.Element => {
  const { setLoggedIn } = useContext(AppContext);
  const { language } = useContext(LanguageContext);
  const { register, handleSubmit, errors } = useForm();
  const [message, setMessage] = useState<string>('');

  const ChangeNicknameInputData = [{
    name: "password",
    type: "password",
    placeholder: languagePack[language].GLOBAL.oldPassword,
    ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.password
  },
  {
    name: "username",
    type: "text",
    placeholder: languagePack[language].CHANGEUSERNAME.enterNewUsername,
    ref: register({ required: true, pattern: /^[a-zA-Z0-9]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.username
  }];

  const checkUsername = async (username: string) => {
    const { nick, firstName, lastName } = await DataRequests.getCurrentUserInfo();
    if (nick !== username) {
      const { message } = await DataRequests.changeUsername(firstName, lastName, username);

      if (message) return setMessage(message)

      setTimeout(() => setMessage(languagePack[language].GLOBAL.logInAgain), 1000);

      setTimeout(() => {
        DataRequests.logout();
        return setLoggedIn!({})
      }, 2000);

      return setMessage(languagePack[language].CHANGEUSERNAME.changedUsernameSuccessfully);
    }

    return setMessage(languagePack[language].CHANGEUSERNAME.changedUsernameFailed);
  }

  const sendRequestToChangeUsername = async ({ password, username }: EmailInterface) => {
    const response = await DataRequests.checkIfPasswordIsCorrect(password);

    if (response)
      return checkUsername(username);

    return setMessage(languagePack[language].GLOBAL.wrongPassword)
  }

  return (
    <section className='change-username-page'>
      <NavigationBar type={PageNavigationTypes.DEFAULT} placeholder={languagePack[language].CHANGEUSERNAME.title} />
      <form onSubmit={handleSubmit(sendRequestToChangeUsername)}>
        {renderAccountFormInputs(ChangeNicknameInputData)}
        {message && <p className='change-username-message'>{message}</p>}
        <input className="default-button" type="submit" value={languagePack[language].GLOBAL.save} />
      </form>
    </section>
  );
};

export default ChangeUsernamePage;
