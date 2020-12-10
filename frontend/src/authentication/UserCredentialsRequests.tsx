import { registerUser, loginUser } from '../utils/interfaces';
import { UserSignIn, RequestsMethods } from '../utils/requestsInterfaces';
import AppConfig from './AppConfig';

class UserCredentialsRequests extends AppConfig {
    private getLocalStorageAndSetConstructorValues = () => {
        const { token, id, username } = JSON.parse(localStorage.getItem('user')!);

        this.setUserValues = { token, id, username };
    }

    private getUserValue = (value: string) => `/user/${this.getUserId}/${value}`;

    public deleteUserPhoto = () =>
        this.handleRequests(RequestsMethods.DELETE, this.getUserValue('image'));

    public getCurrentUserInfo = () =>
        this.handleRequests(RequestsMethods.GET, this.getUserValue('info'));

    public getUserPhoto = () =>
        this.handleRequests(RequestsMethods.GET, this.getUserValue('image'));

    public register = (data: registerUser) =>
        this.handleRequests(RequestsMethods.POST, 'sign-up', data);

    public login = (data: loginUser): Promise<UserSignIn> =>
        this.handleRequests(RequestsMethods.POST, 'sign-in', data);

    public checkIfPasswordIsCorrect = (password: string) =>
        this.handleRequests(RequestsMethods.POST, 'sign-in', { username: this.getUserame, password });

    public changeUserPhoto = (file: FormData) =>
        this.handleRequests(RequestsMethods.POST, this.getUserValue('image'), file);

    public changeUsername = (firstName: string, lastName: string, nick: string,) =>
        this.handleRequests(RequestsMethods.PATCH, this.getUserValue('basic'), { firstName, lastName, nick });

    public changeUserEmail = (email: string) =>
        this.handleRequests(RequestsMethods.PATCH, this.getUserValue('email'), { email });

    public changeUserPassword = (password: string) =>
        this.handleRequests(RequestsMethods.PATCH, this.getUserValue('password'), { password });

    public setLocalStorageValues = (data: any) => {
        if (data.token)
            localStorage.setItem('user', JSON.stringify(data));

        return {
            ...data
        };
    }

    public getCurrentUser = () => {
        if (localStorage.getItem('user')) {
            this.getLocalStorageAndSetConstructorValues();
        }

        return { token: this.getToken }
    }

    public logout = () => localStorage.removeItem("user");

}

export default UserCredentialsRequests;