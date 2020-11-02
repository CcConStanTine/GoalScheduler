import React from 'react';
import { checkErrorTypeInterface, inputDataInterface, renderAccountDataInterface } from '../utils/interfaces';
import { AccountFormErrorTypes } from '../utils/variables';

const checkErrorType = ({ type }: checkErrorTypeInterface) => {
    if (type === 'required') return AccountFormErrorTypes.REQUIRED;
    else if (type === 'pattern') return AccountFormErrorTypes.PATTERN;
    else if (type === 'minLength') return AccountFormErrorTypes.MINLENGTH;
    else if (type === 'maxLength') return AccountFormErrorTypes.MAXLENGTH;
    else return type
}

const RenderAccountFormInputs = (inputData: renderAccountDataInterface) => inputData.map(({ name, type, placeholder, ref, errors }: inputDataInterface) =>
    <div className="input-container" key={name}>
        <input
            name={name}
            type={type}
            placeholder={placeholder}
            ref={ref}
        />
        <span>{errors && checkErrorType(errors)}</span>
    </div>
)

export default RenderAccountFormInputs