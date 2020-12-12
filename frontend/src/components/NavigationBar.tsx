import React from 'react';
import { FaChevronLeft, FaPlus, FaEllipsisV, FaTrash } from 'react-icons/fa';
import { Link } from 'react-router-dom'
import { appName, PageNavigationTypes } from '../utils/variables';
import { useHistory } from 'react-router-dom';

interface Navigation {
    type: string;
    placeholder?: string;
    deleteFunction?: (value: boolean) => void;
}

const NavigationBar = ({ type, placeholder, deleteFunction }: Navigation) => {
    const history = useHistory();

    return (
        <nav className="navigation">
            {type === PageNavigationTypes.HOMEPAGE &&
                <>
                    <Link to="/app/add">
                        <FaPlus />
                    </Link>
                    <p className="home-page">{appName}</p>
                    <Link to="/app/settings">
                        <FaEllipsisV />
                    </Link>
                </>
            }
            {type === PageNavigationTypes.DEFAULT &&
                <>
                    <FaChevronLeft onClick={() => history.goBack()} />
                    <p>{placeholder}</p>
                </>
            }
            {type === PageNavigationTypes.VIEWENTRY &&
                <div className='view-entry-navigation'>
                    <div>
                        <FaChevronLeft onClick={() => history.goBack()} />
                        <p>{placeholder}</p>
                    </div>
                    <FaTrash className='delete-icon' onClick={() => deleteFunction!(true)} />
                </div>
            }
        </nav>
    )
}

export default NavigationBar;
