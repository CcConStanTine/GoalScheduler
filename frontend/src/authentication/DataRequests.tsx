import axios from 'axios';
import { inputData } from '../utils/interfaces';
import { dateTimeTypes } from '../utils/variables';
import UserCredentialsRequests from './UserCredentialsRequests';

class DataRequests extends UserCredentialsRequests {
    setProprietDate = (date: string, type: string) => {
        if (type === dateTimeTypes.ADDDAY)
            return date.slice(11, 19);
        else if (type === dateTimeTypes.EDITDAY)
            return date.slice(0, 9);

        return date.slice(0, 10);
    }

    validateDate = (date: any) => {
        const year = date.slice(0, 4);
        const month = date.slice(5, 7);
        const day = parseInt(date.slice(8, 10));

        const _date = `${year}-${month}-${day < 10 ? `0${day}` : day}`;

        return _date;
    }

    getCurrentDate = () => {
        const date = new Date();

        return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate() < 10 ? `0${date.getDate()}` : date.getDate()}`;
    }

    addPlanByPlanType = async (type: string, data: inputData) => {
        const { startDate } = data;
        const { day, ..._data } = data;
        const id = await this.getPlanIdByTypeAndDate(type, day ? day : startDate);

        return await this.addPlanByPlanTypeAndId(type, id, _data);
    };

    getPlanIdByTypeAndDate = (type: string, date: string) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/${type}?local_date=${this.validateDate(date)}`, this.getAuthConfig())
        .then(({ data }) => data[`${type}Id`])
        .catch(({ response }) => console.log(response.data));

    addPlanByPlanTypeAndId = async (type: string, id: number, data: inputData) => axios
        .post(`${this.serverAddress}/schedule/${this.userId}/${type}/${id}/${type}_plan`, data, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data));

    changePlanByType = async (type: string, id: number, data: inputData) => {
        const { day, ..._data } = data;

        return axios
            .patch(`${this.serverAddress}/schedule/${this.userId}/${type}_plan/${id}`, _data, this.getAuthConfig())
            .then(({ data }) => data)
            .catch(({ response }) => console.log(response.data));
    };

    toggleFinishPlanByTypeAndId = (type: string, id: number) => axios
        .patch(`${this.serverAddress}/schedule/${this.userId}/${type}_plan/${id}/fulfilled`, {}, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data));

    deletePlanByTypeAndId = (type: string, id: number) => axios
        .delete(`${this.serverAddress}/schedule/${this.userId}/${type}_plan/${id}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data));

    getPlanByTypeAndId = (type: string, id: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/${type}_plans/${id}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message);

    getTypeDataByDate = (type: string, date: string) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/${type}s?local_date=${this.validateDate(date)}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message);

    getTypeDataById = (type: string, id: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/${type}/${id}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data));

    getTypePlansByTypeAndId = (type: string, id: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/${type}/${id}/${type}_plans`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data));

    getTypePlans = async (type: string, date?: string | undefined) => {
        const _date = date ? this.validateDate(date) : this.getCurrentDate();
        const id = await this.getPlanIdByTypeAndDate(type, _date);
        const plans = await this.getTypePlansByTypeAndId(type, id);

        return { id, plans };
    };
}

export default new DataRequests();