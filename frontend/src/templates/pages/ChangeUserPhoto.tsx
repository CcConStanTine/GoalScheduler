import React, { useContext, useState, useRef, useEffect } from 'react';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import auth from '../../authentication/database';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import userDefaultPhoto from '../../images/planner.jpg';

const ChangeUserPhoto: React.FC = ({ history }: any) => {
  const { userContext, setLoggedIn } = useContext(AppContext);
  const [photo, setPhoto] = useState(null);
  const { language } = useContext(LanguageContext);
  const [message, setMessage] = useState('');
  const [showDeleteOption, setShowDeleteOption] = useState(false);

  const inputPhoto = useRef<HTMLInputElement>(null);

  const updatePhoto = (photo: any) => setPhoto(photo)

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

  useEffect(() => {
    const showDeleteUserPhotoOption = async () => {
      const { imageId } = await auth.getUserPhoto();

      return setShowDeleteOption(!!imageId);
    };
    showDeleteUserPhotoOption()
  }, [userContext?.userPhoto])

  const deleteUserPhoto = async () => {
    const response = await auth.deleteUserPhoto();

    if (response === "OK") {
      setLoggedIn!({ ...userContext, userPhoto: userDefaultPhoto });

      return setMessage(languagePack[language].deleteImageSuccessed);
    }

    return setMessage(response);
  }

  return (
    <section className='change-user-photo-page'>
      <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={languagePack[language].userPhotoText} />
      <div className='actual-photo'>
        <img src={photo ? URL.createObjectURL(photo) : userContext?.userPhoto} alt={`${userContext?.nick}`} />
      </div>
      <div className="options">
        <input
          type="file"
          onChange={event => updatePhoto(event.target.files![0])}
          ref={inputPhoto}
        />
        <button onClick={() => inputPhoto.current?.click()}>{languagePack[language].selectPhoto}</button>
        <button onClick={fileUploadHandler} disabled={!photo} className="upload-button">{languagePack[language].uploadImage}</button>
      </div>
      <button onClick={deleteUserPhoto} disabled={!showDeleteOption} className="delete-button">{languagePack[language].deletePhoto}</button>

      {message && <p className='user-photo-message'>{message}</p>}
    </section>
  );
};

export default ChangeUserPhoto;
