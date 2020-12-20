import React, { useEffect, useContext, useState } from 'react';
import { AppContext } from '../../../authentication/AppContext';
import { DatePickerContext } from '../../../authentication/DatePicker';
import DataRequests from '../../../authentication/DataRequests';
import { DesktopDisplayOptions } from '../../../utils/interfaces';
import RenderHomeContentByDisplay from '../../../components/DesktopComponents/RenderHomeContentByDisplay';
import Menu from '../../../components/DesktopComponents/Menu';
import DateTimePicker from '../../../components/DesktopComponents/DateTimePicker';

const HomePage = () => {
    const { setLoggedIn } = useContext(AppContext);
    const { isDatePickerActive } = useContext(DatePickerContext);
    const [display, setDisplay] = useState<DesktopDisplayOptions>(DesktopDisplayOptions.HOME);

    useEffect(() => {
        const { token } = DataRequests.getCurrentUser();

        const updateUserInfo = async (): Promise<void> => {
            const userInfo = await DataRequests.getCurrentUserInfo();
            const { fileUrl: userPhoto } = await DataRequests.getUserPhoto();

            setLoggedIn && setLoggedIn({ ...userInfo, token, userPhoto })
        }

        updateUserInfo();
    }, [setLoggedIn])
    return (
        <div className='home-page-desktop'>
            <Menu onClick={setDisplay} />
            <RenderHomeContentByDisplay display={display} />
            {isDatePickerActive && <DateTimePicker />}
        </div>
    )
}

export default HomePage;