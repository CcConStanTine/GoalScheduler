import React, { useContext } from 'react';
import { Redirect, Switch } from 'react-router-dom';
import { AppContext } from '../../authentication/AppContext';
import useWindowDimensions from '../../utils/windowDimensions';
import MobileRoutes from './MobileRoutes';
import DesktopRoutes from './DesktopRoutes';

const AppTabs: React.FC = () => {
  const { userContext } = useContext(AppContext);
  const { width } = useWindowDimensions();

  if (!userContext?.token) return <Redirect to="/welcome" />

  return (
    <Switch>
      {width < 900 ? <MobileRoutes /> : <DesktopRoutes />}
    </Switch >
  );
};

export default AppTabs;
