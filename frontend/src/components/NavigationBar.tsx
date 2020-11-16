import React from 'react';
import { FaChevronLeft, FaPlus, FaEllipsisV, FaTrash } from 'react-icons/fa';
import { Link } from 'react-router-dom'
import { appName, PageNavigationTypes } from '../utils/variables';

interface Navigation {
    type: string;
    history?: any;
    placeholder?: string;
}

const NavigationBar = ({ type, history, placeholder }: Navigation) =>
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
                <FaTrash className='delete-icon' />
            </div>
        }
    </nav>

export default NavigationBar;
