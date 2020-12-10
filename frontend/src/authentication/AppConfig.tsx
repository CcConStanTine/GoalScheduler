import axios from 'axios';
import { registerUser, loginUser } from '../utils/interfaces';
import { RequestsMethods } from '../utils/requestsInterfaces';

class AppConfig {
    protected readonly serverAddress: string = "https://goalscheduler.herokuapp.com";
    private readonly axiosType = "Bearer";
    protected token: string;
    protected userId: number;
    protected username: string;

    constructor() {
        this.token = null!;
        this.userId = null!;
        this.username = null!;
    }

    protected handleRequests = async (method: RequestsMethods, url: string, data?: object | FormData | loginUser | registerUser) => {
        axios.defaults.baseURL = `${this.serverAddress}/`;
        axios.defaults.headers.common['Authorization'] = `${this.axiosType} ${this.token}`;

        return axios({ method, url, data })
            .then(({ data }) => data)
            .catch(({ response }) => response.data);
    }

    protected getAuthConfig = () => ({
        headers: {
            Authorization: `${this.axiosType} ${this.token}`,
        }
    });
}

export default AppConfig;