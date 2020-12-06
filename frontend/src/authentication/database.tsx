import axios from 'axios';
import { registerUser, loginUser, inputData } from '../utils/interfaces';
import { PlanTypes, dateTimeTypes } from '../utils/variables';

class Database {
    serverAddress = 'https://goalscheduler.herokuapp.com';
    axiosType = "Bearer";
    token: string;
    userId: number;
    username: string;

    constructor() {
        this.token = null!;
        this.userId = null!;
        this.username = null!;
    }

    setProprietDate = (date: string, type: string) => {
        if (type === dateTimeTypes.ADDDAY)
            return date.slice(11, 19);
        if (type === dateTimeTypes.EDITDAY)
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

    getAuthConfig = () => ({
        headers: {
            Authorization: `${this.axiosType} ${this.token}`,
        }
    });

    getUserValue = (value: string) => `${this.serverAddress}/user/${this.userId}/${value}`;

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
        .get(`${this.serverAddress}/schedule/${this.userId}/${type}?local_date=${date}`, this.getAuthConfig())
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
            .catch(({ response }) => console.log(response.data))
    };

    getDayByDayId = async (dayId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/day/${dayId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getDayPlans = async (date: string | undefined) => {
        let _date = date ? this.validateDate(date) : this.getCurrentDate();
        const dayId = await this.getPlanIdByTypeAndDate(PlanTypes.DAY, _date);
        const plans = await this.getDayPlansByDayID(dayId);

        return { id: dayId, plans }
    }

    getMonthPlanByMonthPlanId = (monthId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/month_plans/${monthId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getMonthById = async (monthId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/month/${monthId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getMonthPlans = async (date: string | undefined) => {
        let _date = date ? this.validateDate(date) : this.getCurrentDate();
        const monthId = await this.getPlanIdByTypeAndDate(PlanTypes.MONTH, _date);
        const plans = await this.getMonthPlansByMonthId(monthId);

        return { id: monthId, plans };
    }

    getMonthsByDate = (date: string) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/months?local_date=${this.validateDate(date)}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getMonthPlansByMonthId = (monthId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/month/${monthId}/month_plans`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getYearByYearId = async (yearId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/year/${yearId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getYearPlans = async (date: string | undefined) => {
        let _date = date ? this.validateDate(date) : this.getCurrentDate();
        const yearId = await this.getPlanIdByTypeAndDate(PlanTypes.YEAR, _date);
        const plans = await this.getYearPlansByYearId(yearId);

        return { id: yearId, plans };
    }

    deleteYearPlanByYearId = (yearId: number) => axios
        .delete(`${this.serverAddress}/schedule/${this.userId}/year_plan/${yearId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    toggleFinishYearPlanByPlanId = (yearId: number) => axios
        .patch(`${this.serverAddress}/schedule/${this.userId}/year_plan/${yearId}/fulfilled`, {}, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    deleteMonthPlanByMonthId = (monthId: number) => axios
        .delete(`${this.serverAddress}/schedule/${this.userId}/month_plan/${monthId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    toggleFinishMonthPlanByPlanId = (monthId: number) => axios
        .patch(`${this.serverAddress}/schedule/${this.userId}/month_plan/${monthId}/fulfilled`, {}, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getYearPlansByYearId = (yearId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/year/${yearId}/year_plans`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getYearPlanByYearPlanId = (yearId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/year_plans/${yearId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    toggleFinishDayPlanByPlanId = (planId: number) => axios
        .patch(`${this.serverAddress}/schedule/${this.userId}/day_plan/${planId}/fulfilled`, {}, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    deleteDayPlanByPlanId = (planId: number) => axios
        .delete(`${this.serverAddress}/schedule/${this.userId}/day_plan/${planId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message)

    getDayPlanByPlanId = (planId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/day_plans/${planId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message)

    getDaysByDate = (date: string) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/days?local_date=${this.validateDate(date)}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message)

    getDayPlansByDayID = (dayId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/day/${dayId}/day_plans`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message)

    getTodayPlans = async () => {
        const dayId = await this.getPlanIdByTypeAndDate(PlanTypes.DAY, this.getCurrentDate());
        const todayPlans = await this.getDayPlansByDayID(dayId);
        return todayPlans;
    }

    deleteUserPhoto = () => axios
        .delete(this.getUserValue('image'), this.getAuthConfig())
        .then(({ statusText }) => statusText)
        .catch(({ response }) => response.data.message)

    getUserPhoto = () => axios
        .get(this.getUserValue('image'), this.getAuthConfig())
        .then(({ data }) => data)
        .catch(error => console.log(error))

    changeUserPhoto = (file: FormData) => axios
        .post(this.getUserValue('image'), file, this.getAuthConfig())
        .then(({ statusText }) => statusText)
        .catch(({ response }) => response.data.message);

    changeUsername = (firstName: string, lastName: string, nick: string,) => axios
        .patch(this.getUserValue('basic'), { firstName, lastName, nick }, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data)

    changeUserEmail = (email: string) => axios
        .patch(this.getUserValue('email'), { email }, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(error => console.log(error))

    changeUserPassword = (password: string) => axios
        .patch(this.getUserValue('password'), { password }, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(error => console.log(error))

    checkIfPasswordIsCorrect = (oldPassword: string) => axios
        .post(`${this.serverAddress}/sign-in`, { username: this.username, password: oldPassword }, this.getAuthConfig())
        .then(({ status }) => status)
        .catch(error => false);

    getCurrentUser = () => {
        if (localStorage.getItem('user')) {
            const { token, id, username } = JSON.parse(localStorage.getItem('user')!);
            this.token = token;
            this.userId = id;
            this.username = username
        }

        return { token: this.token }
    }

    getCurrentUserInfo = () => axios
        .get(this.getUserValue('info'), this.getAuthConfig())
        .then(({ data }) => data)
        .catch(error => console.log(error))

    register = async ({ username, password, email, firstName, lastName }: registerUser) => axios
        .post(`${this.serverAddress}/sign-up`, {
            username,
            password,
            email,
            firstName,
            lastName
        })
        .then(({ data }) => { return { status: 200, message: data.message } })
        .catch(({ response }) => response.data);


    login = ({ username, password }: loginUser): any => axios
        .post(`${this.serverAddress}/sign-in`, { username, password })
        .then(({ data }) => {
            if (data.token) {
                localStorage.setItem('user', JSON.stringify(data));
            }

            return {
                ...data,
                code: 200,
            };
        })
        .catch(error => { return { code: 400 } })

    logout = () => localStorage.removeItem("user");

}

export default new Database();