import React from 'react';
import { FaChevronLeft, FaPlus, FaEllipsisV } from 'react-icons/fa';
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
    </nav>

export default NavigationBar;
