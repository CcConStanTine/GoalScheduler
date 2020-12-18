import React from 'react';

interface Header {
    header: string;
}

const RenderHeader = ({ header }: Header) => (
    <div className='entry-container title'>
        <h1>{header}</h1>
    </div>
)

export default RenderHeader;