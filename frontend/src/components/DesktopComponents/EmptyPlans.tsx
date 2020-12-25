import React, { useEffect, useState } from 'react';
import DataRequests from '../../authentication/DataRequests';
import { PlanTypes } from '../../utils/enums';
import { EmpPlans } from '../../utils/interfaces';
import AddDay from './AddDay';
import EmptyPlansInfoAndNavigation from './EmptyPlansInfoAndNavigation';
import OptionActive from './OptionActive';

const EmptyPlans = ({ setOpenWindow, id, setRecentlyAddedPlanId, edit, setEdit, optionActiveType, setOptionActiveType }: EmpPlans) => {
    const [dateValue, setDateValue] = useState<string>('');


    useEffect(() => {
        const getDateById = async () => {
            const { dayDate } = await DataRequests.getTypeDataById(PlanTypes.DAY, id);

            setDateValue(dayDate!);
        }

        getDateById();
    }, [id])

    return (
        <div className={`empty-plans-container ${optionActiveType}`}>
            {optionActiveType ?
                <OptionActive
                    dateValue={dateValue}
                    setOptionActiveType={setOptionActiveType}
                    setEdit={setEdit}
                />
                :
                <EmptyPlansInfoAndNavigation
                    dateValue={dateValue}
                    id={id}
                    setOptionActiveType={setOptionActiveType}
                    setOpenWindow={setOpenWindow}
                />
            }
            <AddDay
                id={id}
                setRecentlyAddedPlanId={setRecentlyAddedPlanId}
                setOptionActiveType={setOptionActiveType}
                edit={edit}
                setEdit={setEdit}
            />
        </div>
    )
}

export default EmptyPlans;