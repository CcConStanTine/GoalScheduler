import { PlanTypes } from '../utils/variables';
import auth from '../authentication/database';

export const getEntryDataByType = async (type: string, id: number) => {
    if (type === PlanTypes.DAY)
        return await auth.getDayPlanByPlanId(id);
    else if (type === PlanTypes.MONTH)
        return await auth.getMonthPlanByMonthPlanId(id);

    return await auth.getYearPlanByYearPlanId(id);
}