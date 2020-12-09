import axios from 'axios';
import { registerUser, loginUser } from '../utils/interfaces';
import { requestMethods } from '../utils/variables';
import AppConfig from './AppConfig';

class UserCredentialsRequests extends AppConfig {
    private getLocalStorageAndSetConstructorValues = () => {
        const { token, id, username } = JSON.parse(localStorage.getItem('user')!);

        this.token = token;
        this.userId = id;
        this.username = username;
    }

    private getUserValue = (value: string) => `${this.serverAddress}/user/${this.userId}/${value}`;

    private handleUserCredentials = async (method: string, value: string, data?: object | FormData | loginUser | registerUser) => {
        switch (method) {
            case requestMethods.GET:
                return axios
                    .get(this.getUserValue(value), this.getAuthConfig())
                    .then(({ data }) => data)
                    .catch(error => console.log(error));
            case requestMethods.POST:
                return axios
                    .post(this.getUserValue(value), data, this.getAuthConfig())
                    .then(({ statusText }) => statusText)
                    .catch(({ response }) => response.data.message);
            case requestMethods.PATCH:
                return axios
                    .patch(this.getUserValue(value), data, this.getAuthConfig())
                    .then(({ data }) => data)
                    .catch(({ response }) => response.data);
            case requestMethods.DELETE:
                return axios
                    .delete(this.getUserValue(value), this.getAuthConfig())
                    .then(({ statusText }) => statusText)
                    .catch(({ response }) => response.data.message);
            case requestMethods.NOAUTH:
                return axios
                    .post(`${this.serverAddress}/${value}`, data)
                    .then(({ data }) => data)
                    .catch(({ response }) => response.data);
            default:
                return axios
                    .post(`${this.serverAddress}/${value}`, data, this.getAuthConfig())
                    .then(({ status }) => status)
                    .catch(error => false);
        }
    }

    public deleteUserPhoto = () => this.handleUserCredentials(requestMethods.DELETE, 'image');

    public getUserPhoto = () => this.handleUserCredentials(requestMethods.GET, 'image');

    public changeUserPhoto = (file: FormData) => this.handleUserCredentials(requestMethods.POST, 'image', file);

    public changeUsername = (firstName: string, lastName: string, nick: string,) => this.handleUserCredentials(requestMethods.PATCH, 'basic', { firstName, lastName, nick });

    public changeUserEmail = (email: string) => this.handleUserCredentials(requestMethods.PATCH, 'email', { email });

    public changeUserPassword = (password: string) => this.handleUserCredentials(requestMethods.PATCH, 'password', { password });

    public checkIfPasswordIsCorrect = (password: string) => this.handleUserCredentials(requestMethods.DEFAULT, 'sign-in', { username: this.username, password });

    public getCurrentUserInfo = () => this.handleUserCredentials(requestMethods.GET, 'info');

    public register = (data: registerUser) => this.handleUserCredentials(requestMethods.NOAUTH, 'sign-up', data);

    public login = (data: loginUser): any => this.handleUserCredentials(requestMethods.NOAUTH, 'sign-in', data);

    public setLocalStorageValues = (data: any) => {
        if (data.token)
            localStorage.setItem('user', JSON.stringify(data));

        return {
            ...data
        };
    }

    public getCurrentUser = () => {
        if (localStorage.getItem('user'))
            this.getLocalStorageAndSetConstructorValues();

        return { token: this.token }
    }

    public logout = () => localStorage.removeItem("user");

}

export default UserCredentialsRequests;