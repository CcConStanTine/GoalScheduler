import React, { useContext, useState } from 'react';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';

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
    const response = await auth.changeUserPhoto(formData);

    if (response === "OK") {
      const { fileUrl } = await auth.getUserPhoto();
      setLoggedIn!({ ...userContext, userPhoto: fileUrl });

      return setMessage(languagePack[language].uploadImageSuccessed);
    }

    return setMessage(response);
  }

  return (
    <section className='change-user-photo-page'>
      <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={languagePack[language].userPhotoText} />
      <input type="file" onChange={event => showPhoto(event.target.files![0])} />
      <button onClick={fileUploadHandler} disabled={!photo}>Upload</button>
      {message && <p>{message}</p>}
    </section>
  );
};

export default ChangeUserPhoto;
