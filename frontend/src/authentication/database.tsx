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

    getUserPhoto = () => axios
        .get(`${this.serverAddress}/user/${this.userId}/image`, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(error => console.log(error))

    changeUserPhoto = (file: FormData) => axios
        .post(`${this.serverAddress}/user/${this.userId}/image`, file, this.getAuthConfig())
        .then(({ statusText }) => statusText)
        .catch(({ response }) => response.data.message);

    changeUsername = (firstName: string, lastName: string, nick: string,) => axios
        .patch(`${this.serverAddress}/user/${this.userId}/basic`, { firstName, lastName, nick }, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(({ response }) => response.data)

    changeUserEmail = (email: string) => axios
        .patch(`${this.serverAddress}/user/${this.userId}/email`, { email }, this.getAuthConfig())
        .then(({ data }) => data)
        .catch(error => console.log(error))

    changeUserPassword = (password: string) => axios
        .patch(`${this.serverAddress}/user/${this.userId}/password`, { password }, this.getAuthConfig())
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
        .get(`${this.serverAddress}/user/${this.userId}/info`, this.getAuthConfig())
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