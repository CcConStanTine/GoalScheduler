import axios from 'axios';
import { registerUser, loginUser } from '../utils/interfaces';
import { RequestsMethods } from '../utils/requestsInterfaces';

interface UserValues {
    token: string;
    id: number;
    username: string;
}

class AppConfig {
    protected readonly serverAddress: string = "https://goalscheduler.herokuapp.com";
    private readonly axiosType = "Bearer";
    private token: string;
    private userId: number;
    private username: string;

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

    protected get getUserId(): number {
        return this.userId;
    }

    protected get getUserame(): string {
        return this.username;
    }

    protected get getToken(): string {
        return this.token;
    }

    protected set setUserValues({ token, id, username }: UserValues) {
        this.token = token;
        this.userId = id;
        this.username = username;
    }
}

export default AppConfig;