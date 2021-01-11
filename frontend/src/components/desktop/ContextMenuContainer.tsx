import React from 'react';
import { ContextMenu, OpenWindowTypes } from '../../utils/interfaces';

const ContextMenuContainer = ({ pageY, pageX, setOpenWindow, id, setContextMenu }: ContextMenu) => (
    <div
        onClick={event => event.stopPropagation()}
        className='context-menu'
        style={{ top: `${pageY}px`, left: `${pageX}px` }}>
        <p onClick={() => {
            setOpenWindow!({ isActive: true, id, type: OpenWindowTypes.SHOW });
            setContextMenu!({ isActive: false });
        }}>Pokaż dzień</p>
    </div>
);

export default ContextMenuContainer