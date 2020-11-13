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

const ChangeUserPhoto: React.FC = ({ history }: any) => {
  const { userContext, setLoggedIn } = useContext(AppContext);
  const [photo, setPhoto] = useState(null);
  const { language } = useContext(LanguageContext);
  const [message, setMessage] = useState('');

  const showPhoto = (photo: any) => {
    setPhoto(photo);
  }

  const fileUploadHandler = async () => {
    const formData = new FormData();
    formData.append('file', photo!);
    console.log(formData.getAll('file'));
    auth.changeUserPhoto(formData)
  }

  return (
    <section className='change-email-page'>
      <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={languagePack[language].userPhotoText} />
      <input type="file" onChange={event => showPhoto(event.target.files![0])} />
      <button onClick={fileUploadHandler}>Upload</button>
    </section>
  );
};

export default ChangeUserPhoto;
