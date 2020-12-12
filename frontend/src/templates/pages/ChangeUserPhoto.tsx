import React, { useContext, useState, useRef, useEffect } from 'react';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import DataRequests from '../../authentication/DataRequests';
import { PageNavigationTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import NavigationBar from '../../components/NavigationBar';
import userDefaultPhoto from '../../images/planner.jpg';

const ChangeUserPhoto = (): JSX.Element => {
  const { userContext, setLoggedIn } = useContext(AppContext);
  const [photo, setPhoto] = useState<null | File>(null);
  const { language } = useContext(LanguageContext);
  const [message, setMessage] = useState<string>('');
  const [showDeleteOption, setShowDeleteOption] = useState<Boolean>(false);

  const inputPhoto = useRef<HTMLInputElement>(null);

  const updatePhoto = (photo: File) => setPhoto(photo);

  const fileUploadHandler = async () => {
    const formData = new FormData();
    formData.append('file', photo!);
    const { path, message } = await DataRequests.changeUserPhoto(formData);

    if (path) {
      const { fileUrl } = await DataRequests.getUserPhoto();
      setLoggedIn!({ ...userContext, userPhoto: fileUrl });

      return setMessage(languagePack[language].CHANGEUSERPHOTO.upload);
    }

    return setMessage(message);
  }

  useEffect(() => {
    const showDeleteUserPhotoOption = async () => {
      const { imageId } = await DataRequests.getUserPhoto();

      return setShowDeleteOption(!!imageId);
    };
    showDeleteUserPhotoOption()
  }, [userContext?.userPhoto])

  const deleteUserPhoto = async () => {
    const { message, status } = await DataRequests.deleteUserPhoto();

    if (!status) {
      setLoggedIn!({ ...userContext, userPhoto: userDefaultPhoto });

      return setMessage(languagePack[language].CHANGEUSERPHOTO.deleteImageSuccessed);
    }

    return setMessage(message);
  }

  return (
    <section className='change-user-photo-page'>
      <NavigationBar type={PageNavigationTypes.DEFAULT} placeholder={languagePack[language].CHANGEUSERPHOTO.title} />
      <div className='actual-photo'>
        <img src={photo ? URL.createObjectURL(photo) : userContext?.userPhoto} alt={`${userContext?.nick}`} />
      </div>
      <div className="options">
        <input
          type="file"
          onChange={event => updatePhoto(event.target.files![0])}
          ref={inputPhoto}
          accept="image/png, image/jpeg"
        />
        <button onClick={() => inputPhoto.current?.click()}>{languagePack[language].CHANGEUSERPHOTO.select}</button>
        <button onClick={fileUploadHandler} disabled={!photo} className="upload-button">{languagePack[language].CHANGEUSERPHOTO.upload}</button>
      </div>
      <button onClick={deleteUserPhoto} disabled={!showDeleteOption} className="delete-button">{languagePack[language].CHANGEUSERPHOTO.delete}</button>

      {message && <p className='user-photo-message'>{message}</p>}
    </section>
  );
};

export default ChangeUserPhoto;
