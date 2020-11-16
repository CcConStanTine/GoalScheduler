import React, { useContext, useState } from 'react';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { useForm } from "react-hook-form";
import renderAccountFormInputs from '../../components/RenderAccountFormInputs';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';

interface emailInterface {
  password: string;
  username: string;
}

const ChangeUsernamePage: React.FC = ({ history }: any) => {
  const { setLoggedIn } = useContext(AppContext);
  const { language } = useContext(LanguageContext);
  const { register, handleSubmit, errors } = useForm();
  const [message, setMessage] = useState('');

  const ChangeNicknameInputData = [{
    name: "password",
    type: "password",
    placeholder: languagePack[language].oldPassword,
    ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.password
  },
  {
    name: "username",
    type: "text",
    placeholder: languagePack[language].newUsername,
    ref: register({ required: true, pattern: /^[a-zA-Z0-9]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.username
  }];

  const checkUsername = async (username: string) => {
    const { nick, firstName, lastName } = await auth.getCurrentUserInfo();
    if (nick !== username) {
      const { message } = await auth.changeUsername(firstName, lastName, username);

      if (message) return setMessage(message)

      setTimeout(() => setMessage(languagePack[language].LogAgainMessage), 1000);

      setTimeout(() => {
        auth.logout();
        return setLoggedIn!({})
      }, 2000);

      return setMessage(languagePack[language].changedUsernameSuccessfully);
    }

    return setMessage(languagePack[language].theSameUsernameError);
  }

  const sendRequestToChangeUsername = async ({ password, username }: emailInterface) => {
    const response = await auth.checkIfPasswordIsCorrect(password);

    if (response)
      return checkUsername(username);

    return setMessage(languagePack[language].checkPasswordError)
  }

  return (
    <section className='change-username-page'>
      <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={languagePack[language].changeUsernameText} />
      <form onSubmit={handleSubmit(sendRequestToChangeUsername)}>
        {renderAccountFormInputs(ChangeNicknameInputData)}
        {message && <p className='change-username-message'>{message}</p>}
        <input className="default-button" type="submit" value={languagePack[language].changeUserSettingsInputValue} />
      </form>
    </section>
  );
};

export default ChangeUsernamePage;
