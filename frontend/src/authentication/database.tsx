import axios from 'axios';
import { registerUser, loginUser } from '../utils/interfaces';


class Database {
    serverAddress = '';
    axiosType = "Bearer";
    token: string;
    userId: number;
    username: string;

    constructor() {
        this.token = null!;
        this.userId = null!;
        this.username = null!;
    }

    getAuthConfig = () => ({
        headers: {
            Authorization: `${this.axiosType} ${this.token}`,
        }
    });

    getUserValue = (value: string) => `${this.serverAddress}/user/${this.userId}/${value}`;

    getCurrentDate = () => {
        const date = new Date();

        return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
    }

    getDayByDayId = async (dayId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/day/${dayId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getDayPlans = async (date: string = this.getCurrentDate()) => {
        const { dayId } = await this.getDayByDate(date);
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

    getMonthPlans = async (date: string = this.getCurrentDate()) => {
        const { monthId } = await this.getMonthByDate(date);
        const plans = await this.getMonthPlansByMonthId(monthId);

        return { id: monthId, plans };
    }

    getMonthsByDate = (date: string) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/months?local_date=${date}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getMonthPlansByMonthId = (monthId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/month/${monthId}/month_plans`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getMonthByDate = (date: string) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/month?local_date=${date}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getYearByYearId = async (yearId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/year/${yearId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    getYearPlans = async (date: string = this.getCurrentDate()) => {
        const { yearId } = await this.getYearByDate(date);
        const plans = await this.getYearPlansByYearId(yearId);

        return { id: yearId, plans };
    }

    getYearByDate = (date: string) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/year?local_date=${date}`, this.getAuthConfig())
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

    toggleFinishPlanByPlanId = (planId: number) => axios
        .patch(`${this.serverAddress}/schedule/${this.userId}/day_plan/${planId}/fulfilled`, {}, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => console.log(response.data))

    deletePlanByPlanId = (planId: number) => axios
        .delete(`${this.serverAddress}/schedule/${this.userId}/day_plan/${planId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message)

    getPlanByPlanId = (planId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/day_plans/${planId}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message)

    getDaysByDate = (date: string) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/days?local_date=${date}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message)

    getDayByDate = (date: string = this.getCurrentDate()) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/day?local_date=${date}`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message)

    getDayPlansByDayID = (dayId: number) => axios
        .get(`${this.serverAddress}/schedule/${this.userId}/day/${dayId}/day_plans`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data.message)

    getTodayPlans = async () => {
        const { dayId } = await this.getDayByDate();
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

    register = ({ username, password, email, firstName, lastName }: registerUser): object => axios
        .post(`${this.serverAddress}/sign-up`, {
            username,
            password,
            email,
            firstName,
            lastName
        })

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