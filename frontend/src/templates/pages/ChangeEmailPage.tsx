import React, { useContext, useState } from 'react';
import { AppContext } from '../../authentication/AppContext';
import auth from '../../authentication/database';
import { useForm } from "react-hook-form";
import renderAccountFormInputs from '../../components/RenderAccountFormInputs';
import {
  changedUserEmailSuccessfully,
  theSameEmailError,
  PageNavigationTypes,
  changeUserEmail,
  checkOldEmailError,
  changeUserSettingsInputValue
} from '../../utils/variables';
import NavigationBar from '../../components/NavigationBar';

interface emailInterface {
  newEmail: string;
  currentEmail: string;
}

const ChangeEmailPage: React.FC = ({ history }: any) => {
  const { userContext, setLoggedIn } = useContext(AppContext);
  const { register, handleSubmit, errors } = useForm();
  const [message, setMessage] = useState('');

  const ChangeEmailInputData = [{
    name: "currentEmail",
    type: "email",
    placeholder: "Enter your current email",
    ref: register({ required: true, pattern: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.currentEmail
  },
  {
    name: "newEmail",
    type: "email",
    placeholder: "Enter your new email",
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

      return setMessage(changedUserEmailSuccessfully);
    }

    return setMessage(theSameEmailError);
  }

  const sendRequestToChangeEmail = async ({ newEmail, currentEmail }: emailInterface) => {
    const { email } = await auth.getCurrentUserInfo();

    if (email === currentEmail)
      return changeEmail(newEmail, currentEmail);

    return setMessage(checkOldEmailError);
  }

  return (
    <section className='change-email-page'>
      <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={changeUserEmail} />
      <form onSubmit={handleSubmit(sendRequestToChangeEmail)}>
        {renderAccountFormInputs(ChangeEmailInputData)}
        {message && <p className='change-email-message'>{message}</p>}
        <input className="send-form-button" type="submit" value={changeUserSettingsInputValue} />
      </form>
    </section>
  );
};

export default ChangeEmailPage;
