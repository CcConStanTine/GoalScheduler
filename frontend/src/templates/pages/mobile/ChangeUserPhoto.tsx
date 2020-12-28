import React, { useContext, useState, useRef, useEffect } from 'react';
import { AppContext } from '../../../authentication/AppContext';
import { LoadingPageContext } from '../../../authentication/LoadingPageContext';
import { LanguageContext } from '../../../authentication/LanguageContext';
import DataRequests from '../../../authentication/DataRequests';
import { PageNavigationTypes } from '../../../utils/variables';
import languagePack from '../../../utils/languagePack';
import NavigationBar from '../../../components/mobile/NavigationBar';
import LoaderPage from '../../../components/mobile/Loader';

interface UserPhoto {
  mobile?: boolean
}

const ChangeUserPhoto = ({ mobile = true }: UserPhoto): JSX.Element => {
  const { userContext, setLoggedIn } = useContext(AppContext);
  const { setLoading } = useContext(LoadingPageContext);
  const [photo, setPhoto] = useState<null | File>(null);
  const [defaultPhoto, setDefaultPhoto] = useState<string>(userContext?.userPhoto ? userContext?.userPhoto : '');
  const [uploadPercentage, setUploadPercentage] = useState(0);
  const { language } = useContext(LanguageContext);
  const [message, setMessage] = useState<string>('');
  const [showDeleteOption, setShowDeleteOption] = useState<Boolean>(false);

  const inputPhoto = useRef<HTMLInputElement>(null);

  const updatePhoto = (photo: File) => {
    if (photo.size > 1000000) {
      setPhoto(null);
      return setMessage(languagePack[language].CHANGEUSERPHOTO.uploadFailed);
    }

    setMessage('');
    return setPhoto(photo);
  };

  const fileUploadHandler = async () => {
    setLoading!(true);
    const formData = new FormData();
    formData.append('file', photo!);

    const config = {
      onUploadProgress: (progressEvent: any) => {
        let percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
        setUploadPercentage(percentCompleted);

        if (percentCompleted === 100) {
          setLoggedIn!({ ...userContext, userPhoto: URL.createObjectURL(photo) });

          return setMessage(languagePack[language].CHANGEUSERPHOTO.uploadImageSuccessed);
        }
      }
    }

    await DataRequests.changeUserPhoto(formData, config);

    setLoading!(false);
  }

  useEffect(() => {
    const showDeleteUserPhotoOption = async () => {
      const { imageId, fileUrl } = await DataRequests.getUserPhoto();

      setDefaultPhoto(fileUrl);

      return setShowDeleteOption(!!imageId);
    };
    showDeleteUserPhotoOption()
  }, [userContext?.userPhoto])

  const deleteUserPhoto = async () => {
    const { message, status } = await DataRequests.deleteUserPhoto();
    const { fileUrl } = await DataRequests.getUserPhoto();

    if (!status) {
      setLoggedIn!({ ...userContext, userPhoto: fileUrl });

      return setMessage(languagePack[language].CHANGEUSERPHOTO.deleteImageSuccessed);
    }

    return setMessage(message);
  }

  return (
    <section className={`change-user-photo-page ${!mobile && 'desktop'}`}>
      {(uploadPercentage > 0 && uploadPercentage < 100) && <LoaderPage loadingProgress={uploadPercentage} />}
      {mobile && <NavigationBar type={PageNavigationTypes.DEFAULT} placeholder={languagePack[language].CHANGEUSERPHOTO.title} />}
      <div className='actual-photo'>
        <img src={photo ? URL.createObjectURL(photo) : defaultPhoto} alt={`${userContext?.nick}`} />
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
