import React, { useContext } from 'react';
import { DatePickerContext } from '../../authentication/DatePicker';
import { LanguageContext } from '../../authentication/LanguageContext';
import { DatePickerOptions, TimePickerOptions } from '../../utils/enums';
import { PickerContent } from '../../utils/interfaces';
import languagePack from '../../utils/languagePack';
import { renderByTimePickerOptions, renderTable } from './TimePickerFunctions';

const RenderDatePickerContent = ({ setDateValues, timeValues }: PickerContent): JSX.Element => {
    const { language } = useContext(LanguageContext);
    const { date } = useContext(DatePickerContext);
    const { option } = date;

    return (
        <div className='date-selection'>
            <div className='months-container'>
                <div className='months'>
                    {option === DatePickerOptions.SELECTDATE &&
                        renderByTimePickerOptions(setDateValues, timeValues, languagePack[language].MONTHS.namesTable, TimePickerOptions.MONTH)}
                    {option === DatePickerOptions.STARTDATE &&
                        renderByTimePickerOptions(setDateValues, timeValues, renderTable(24), TimePickerOptions.HOUR)}
                </div>
            </div>
            <div className='years-container'>
                <div className='years'>
                    {option === DatePickerOptions.SELECTDATE &&
                        renderByTimePickerOptions(setDateValues, timeValues, renderTable(2024, 2019), TimePickerOptions.YEAR)}
                    {option === DatePickerOptions.STARTDATE &&
                        renderByTimePickerOptions(setDateValues, timeValues, renderTable(60), TimePickerOptions.MINUTE)}
                </div>
            </div>
        </div>
    )
}

export default RenderDatePickerContent;