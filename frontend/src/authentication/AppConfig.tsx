import axios from 'axios';
import { registerUser, loginUser } from '../utils/interfaces';
import { RequestsMethods, UserValues } from '../utils/requestsInterfaces';

class AppConfig {
    private readonly serverAddress: string = "https://goalscheduler.herokuapp.com";
    private readonly axiosType = "Bearer";
    private token: string;
    private userId: number;
    private username: string;

    constructor() {
        this.token = null!;
        this.userId = null!;
        this.username = null!;
    };

    protected UploadFileWithProgessBar = async (url: string, data: FormData, config: any) => {
        axios.defaults.baseURL = `${this.serverAddress}/`;
        axios.defaults.headers.common['Authorization'] = `${this.axiosType} ${this.token}`;

        axios.put(url, data, config)
            .then(function (res) {
                return res.data;
            })
            .catch(({ response }) => response.data);
    }

    protected handleRequests = async (method: RequestsMethods, url: string, data?: object | FormData | loginUser | registerUser) => {
        axios.defaults.baseURL = `${this.serverAddress}/`;
        axios.defaults.headers.common['Authorization'] = `${this.axiosType} ${this.token}`;

        return axios({ method, url, data })
            .then(({ data }) => data)
            .catch(({ response }) => response.data);
    }

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