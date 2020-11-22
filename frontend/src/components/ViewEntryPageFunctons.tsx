import { PlanTypes } from '../utils/variables';
import auth from '../authentication/database';

export const deletePlanByType = async (type: string, id: number) => {
    if (type === PlanTypes.YEAR)
        return await auth.deleteYearPlanByYearId(id);
    else if (type === PlanTypes.MONTH)
        return await auth.deleteMonthPlanByMonthId(id);

    return await auth.deleteDayPlanByPlanId(id)
}

export const togglePlanByType = async (type: string, id: number) => {
    if (type === PlanTypes.YEAR)
        return await auth.toggleFinishYearPlanByPlanId(id);
    else if (type === PlanTypes.MONTH)
        return await auth.toggleFinishMonthPlanByPlanId(id);

    return await auth.toggleFinishDayPlanByPlanId(id);
}

export const getEntryDataByType = async (type: string, id: number) => {
    if (type === PlanTypes.DAY)
        return await auth.getDayPlanByPlanId(id);
    else if (type === PlanTypes.MONTH)
        return await auth.getMonthPlanByMonthPlanId(id);

    return await auth.getYearPlanByYearPlanId(id);
}