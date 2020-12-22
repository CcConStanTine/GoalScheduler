import React from 'react';
import { OptionTypes } from '../../utils/enums';
import { OptionActiveInterface } from '../../utils/interfaces';
import { formatDate } from './EmptyPlansFunction';

const OptionActive = ({ date, dateValue, language, setDefaultValues, setOptionActiveType }: OptionActiveInterface) => (
    <div className='option-active'>
        <p className='date'>{date && formatDate(dateValue, language)}</p>
        <span className='cancel' onClick={() => {
            setDefaultValues!();
            setOptionActiveType(OptionTypes.DEFAULT);
        }}></span>
    </div>
);

export default OptionActive;