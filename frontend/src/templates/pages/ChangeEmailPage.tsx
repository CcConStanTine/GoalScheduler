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
  newEmail: string;
  currentEmail: string;
}

const ChangeEmailPage: React.FC = ({ history }: any) => {
  const { userContext, setLoggedIn } = useContext(AppContext);
  const { language } = useContext(LanguageContext);
  const { register, handleSubmit, errors } = useForm();
  const [message, setMessage] = useState('');

  const ChangeEmailInputData = [{
    name: "currentEmail",
    type: "email",
    placeholder: languagePack[language].CHANGEEMAIL.currentEmail,
    ref: register({ required: true, pattern: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.currentEmail
  },
  {
    name: "newEmail",
    type: "email",
    placeholder: languagePack[language].CHANGEEMAIL.newEmail,
    ref: register({ required: true, pattern: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.newEmail
  }];

  const changeEmail = async (newEmail: string, currentEmail: string) => {
    if (currentEmail !== newEmail) {
      const { email } = await auth.changeUserEmail(newEmail);

      setLoggedIn && setLoggedIn({ ...userContext, email });

      setTimeout(() => {
        return history.goBack();
      }, 1000);

      return setMessage(languagePack[language].CHANGEEMAIL.changedEmailSuccessfully);
    }

    return setMessage(languagePack[language].CHANGEEMAIL.changedEmailFailed);
  }

  const sendRequestToChangeEmail = async ({ newEmail, currentEmail }: emailInterface) => {
    const { email } = await auth.getCurrentUserInfo();

    if (email === currentEmail)
      return changeEmail(newEmail, currentEmail);

    return setMessage(languagePack[language].CHANGEEMAIL.wrongCurrentEmail);
  }

  return (
    <section className='change-email-page'>
      <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={languagePack[language].CHANGEEMAIL.title} />
      <form onSubmit={handleSubmit(sendRequestToChangeEmail)}>
        {renderAccountFormInputs(ChangeEmailInputData)}
        {message && <p className='change-email-message'>{message}</p>}
        <input className="default-button" type="submit" value={languagePack[language].GLOBAL.save} />
      </form>
    </section>
  );
};

export default ChangeEmailPage;
