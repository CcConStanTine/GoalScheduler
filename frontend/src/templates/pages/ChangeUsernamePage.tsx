import React, { useContext, useState } from 'react';
import { AppContext } from '../../authentication/AppContext';
import auth from '../../authentication/database';
import { useForm } from "react-hook-form";
import renderAccountFormInputs from '../../components/RenderAccountFormInputs';
import {
  changedUsernameSuccessfully,
  LogAgainMessage,
  PageNavigationTypes,
  changeUsernameText,
  theSameUsernameError,
  checkPasswordError,
  changeUserSettingsInputValue
} from '../../utils/variables';
import NavigationBar from '../../components/NavigationBar';

interface emailInterface {
  password: string;
  username: string;
}

const ChangeUsernamePage: React.FC = ({ history }: any) => {
  const { setLoggedIn } = useContext(AppContext);
  const { register, handleSubmit, errors } = useForm();
  const [message, setMessage] = useState('');

  const ChangeNicknameInputData = [{
    name: "password",
    type: "password",
    placeholder: "Enter your password",
    ref: register({ required: true, pattern: /^[a-zA-Z0-9-]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.password
  },
  {
    name: "username",
    type: "text",
    placeholder: "Enter new username",
    ref: register({ required: true, pattern: /^[a-zA-Z0-9]+$/i, minLength: 2, maxLength: 50 }),
    errors: errors.username
  }];

  const checkUsername = async (username: string) => {
    const { nick, firstName, lastName } = await auth.getCurrentUserInfo();
    if (nick !== username) {
      const { message } = await auth.changeUsername(firstName, lastName, username);

      if (message) return setMessage(message)

      setTimeout(() => setMessage(LogAgainMessage), 1000);

      setTimeout(() => {
        auth.logout();
        return setLoggedIn!({})
      }, 2000);

      return setMessage(changedUsernameSuccessfully);
    }

    return setMessage(theSameUsernameError);
  }

  const sendRequestToChangeUsername = async ({ password, username }: emailInterface) => {
    const response = await auth.checkIfPasswordIsCorrect(password);

    if (response)
      return checkUsername(username);

    return setMessage(checkPasswordError)
  }

  return (
    <section className='change-username-page'>
      <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={changeUsernameText} />
      <form onSubmit={handleSubmit(sendRequestToChangeUsername)}>
        {renderAccountFormInputs(ChangeNicknameInputData)}
        {message && <p className='change-username-message'>{message}</p>}
        <input className="send-form-button" type="submit" value={changeUserSettingsInputValue} />
      </form>
    </section>
  );
};

export default ChangeUsernamePage;
