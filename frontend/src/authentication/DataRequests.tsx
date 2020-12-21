import { PlanTypes } from '../utils/enums';
import { inputData, DateSequences, DateTypes } from '../utils/interfaces';
import {
    RequestsMethods,
    ReturnPlans,
    ReturnTypeDataById,
    Plans,
    ReturnTypeData,
    DeleteOrChange,
} from '../utils/requestsInterfaces';
import { dateTimeTypes } from '../utils/variables';
import UserCredentialsRequests from './UserCredentialsRequests';

class DataRequests extends UserCredentialsRequests {

    private getScheduleValue = (value: string): string => `/schedule/${this.getUserId}/${value}`;

    public convertDate = (toType: DateTypes, date: string): string => {
        const isToTypeNormal = toType === DateTypes.NORMAL
        const day = isToTypeNormal ? date.slice(0, 4) : date.slice(0, 2);
        const month = isToTypeNormal ? date.slice(5, 7) : date.slice(3, 5);
        const year = isToTypeNormal ? date.slice(8, 10) : date.slice(6, 10);

        if (toType === DateTypes.NORMAL)
            return `${day}-${month}-${year}`;

        return `${year}-${month}-${day}`;
    }

    public setProprietDate = (date: string, type: string): string => {
        if (type === dateTimeTypes.ADDDAY)
            return date.slice(11, 19);
        else if (type === dateTimeTypes.EDITDAY)
            return date.slice(0, 9);

        return date.slice(0, 10);
    };

    public getSequenceFromDate = (date: string, sequence: DateSequences): number => {
        if (sequence === DateSequences.DAY)
            return parseInt(date.slice(0, 2));
        else if (sequence === DateSequences.MONTH)
            return parseInt(date.slice(3, 5));

        return parseInt(date.slice(6, 10));
    }

    public validateDate = (date: string): string => {
        const year = date.slice(0, 4);
        const month = date.slice(5, 7);
        const day = parseInt(date.slice(8, 10));

        const _date = `${year}-${month}-${day < 10 ? `0${day}` : day}`;

        return _date;
    }

    public getCurrentDate = (language?: string): string => {
        const date = new Date();
        if (language)
            return `${date.getDate() < 10 ? `0${date.getDate()}` : date.getDate()}-${date.getMonth() + 1}-${date.getFullYear()}`;

        return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate() < 10 ? `0${date.getDate()}` : date.getDate()}`;
    }

    public addPlanByPlanType = async (type: PlanTypes, data: inputData): Promise<Plans> => {
        const { startDate } = data;
        const { day, ..._data } = data;
        const id = await this.getPlanIdByTypeAndDate(type, day ? day : startDate);

        return await this.addPlanByPlanTypeAndId(type, id, _data);
    };

    public getPlanIdByTypeAndDate = (type: PlanTypes, date: string): Promise<number> =>
        this.handleRequests(RequestsMethods.GET, this.getScheduleValue(`${type}?local_date=${this.validateDate(date)}`))
            .then(data => data[`${type}Id`]);

    public addPlanByPlanTypeAndId = async (type: PlanTypes, id: number, data: inputData): Promise<Plans> =>
        this.handleRequests(RequestsMethods.POST, this.getScheduleValue(`${type}/${id}/${type}_plan`), data);

    public changePlanByType = async (type: PlanTypes, id: number, data: inputData): Promise<Plans> => {
        const { day, ..._data } = data;

        return this.handleRequests(RequestsMethods.PATCH, this.getScheduleValue(`${type}_plan/${id}`), _data);
    };

    public toggleFinishPlanByTypeAndId = (type: PlanTypes, id: number): Promise<Plans> =>
        this.handleRequests(RequestsMethods.PATCH, this.getScheduleValue(`${type}_plan/${id}/fulfilled`), {});

    public deletePlanByTypeAndId = (type: PlanTypes, id: number): Promise<DeleteOrChange> =>
        this.handleRequests(RequestsMethods.DELETE, this.getScheduleValue(`${type}_plan/${id}`));

    public getPlanByTypeAndId = (type: PlanTypes, id: number): Promise<ReturnPlans> =>
        this.handleRequests(RequestsMethods.GET, this.getScheduleValue(`${type}_plans/${id}`));

    public getTypeDataByDate = (type: PlanTypes, date: string): Promise<ReturnTypeData> =>
        this.handleRequests(RequestsMethods.GET, this.getScheduleValue(`${type}s?local_date=${this.validateDate(date)}`));

    public getTypeDataById = (type: PlanTypes, id: number): Promise<ReturnTypeDataById> =>
        this.handleRequests(RequestsMethods.GET, this.getScheduleValue(`${type}/${id}`));

    public getTypePlansByTypeAndId = (type: PlanTypes, id: number) =>
        this.handleRequests(RequestsMethods.GET, this.getScheduleValue(`${type}/${id}/${type}_plans`));

    public getTypePlans = async (type: PlanTypes, date?: string) => {
        const _date = date ? this.validateDate(date) : this.getCurrentDate();
        const id = await this.getPlanIdByTypeAndDate(type, _date);
        const plans = await this.getTypePlansByTypeAndId(type, id);

        return plans;
    };
}

export default new DataRequests();