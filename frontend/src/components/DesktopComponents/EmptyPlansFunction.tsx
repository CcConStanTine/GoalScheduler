import React from 'react';
import DataRequests from "../../authentication/DataRequests";
import { NavigationOptions, OptionTypes } from "../../utils/enums";
import { DateSequences, DateTypes, OpenWindowTypes } from "../../utils/interfaces";
import languagePack from "../../utils/languagePack";
import { setMonthName } from "../OtherEntriesFunctions";

export const formatDate = (date: string, language: string) => {
    const convertedDate = DataRequests.convertDate(DateTypes.NORMAL, date);
    const day = DataRequests.getSequenceFromDate(convertedDate, DateSequences.DAY);
    const month = DataRequests.getSequenceFromDate(convertedDate, DateSequences.MONTH);
    const year = DataRequests.getSequenceFromDate(convertedDate, DateSequences.YEAR);

    return `${day} ${setMonthName(month, language)} ${year}`;
}

export const checkIfCanSendRequest = (startDate: string, task: string): boolean => Boolean(startDate && task);

export const checkIfDateIsLowerThanCurrentDate = (date: string, language: string, setOptionActiveType: (value: OptionTypes) => void) => {
    const numberOfSeconds = new Date(date).getTime() / 1000;
    const currentNumberOfSeconds = new Date(DataRequests.getCurrentDate()).getTime() / 1000;

    return numberOfSeconds >= currentNumberOfSeconds && <button
        onClick={() => setOptionActiveType(OptionTypes.ADD)}
        className='desktop-default-button two'>{languagePack[language].ADD.addTask}</button>
}

export const navigationButton = (setOpenWindow: any, id: number, option: NavigationOptions) => (
    <p onClick={option === NavigationOptions.PREVIOUS ?
        () => setOpenWindow({ isActive: true, id: --id!, type: OpenWindowTypes.SHOW })
        :
        () => setOpenWindow({ isActive: true, id: ++id!, type: OpenWindowTypes.SHOW })
    }>
        <span className={`chevron ${option}`}></span>
    </p>
)