import React from 'react';

interface Header {
    header: string;
    date?: JSX.Element;
}

const RenderHeader = ({ header, date }: Header) => (
    <div className='entry-container title'>
        <h1>{header}</h1>
        {date}
    </div>
)

export default RenderHeader;