import React from 'react';
import { ContextMenu } from '../../utils/interfaces';

const ContextMenuContainer = ({ pageY, pageX }: ContextMenu) => (
    <div
        onClick={event => event.stopPropagation()}
        className='context-menu'
        style={{ top: `${pageY}px`, left: `${pageX}px` }}>
        <p>Pokaż dzień</p>
        <p>Edytuj dzień</p>
        <p>Usuń wpisy</p>
    </div>
);

export default ContextMenuContainer